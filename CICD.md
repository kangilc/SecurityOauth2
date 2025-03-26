## EKS 클러스터에서 Helm Chart, App Manifest, ArgoCD, Jenkins를 사용하여 특정 애플리케이션을 CI/CD 파이프라인에 통합하는 방법을 단계별로 설명드릴게요.

EKS 클러스터에서 `ns-test` 네임스페이스를 사용하고, `init`이라는 이름의 리포지토리를 통해 Helm Chart, App Manifest, ArgoCD, Jenkins를 사용하여 `init` 애플리케이션을 CI/CD 파이프라인에 통합하는 방법을 단계별로 설명드릴게요.

### 1. Helm Chart 생성 및 설정
Helm Chart는 Kubernetes 애플리케이션을 패키징하고 배포하는 데 사용됩니다.

1. **Helm Chart 생성**:
   ```bash
   helm create init
   ```
2. **values.yaml 파일 수정**:
   - `values.yaml` 파일에서 애플리케이션의 설정을 정의합니다.
   ```yaml
   replicaCount: 2
   image:
     repository: my-dockerhub-username/init
     tag: latest
   service:
     type: ClusterIP
     port: 80
   ```

### 2. App Manifest 작성
App Manifest는 애플리케이션의 배포 설정을 정의합니다.

1. **manifest.yaml 파일 작성**:
   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: init
     namespace: ns-test
   spec:
     replicas: 2
     selector:
       matchLabels:
         app: init
     template:
       metadata:
         labels:
           app: init
       spec:
         containers:
         - name: init
           image: my-dockerhub-username/init:latest
           ports:
           - containerPort: 80
   ```

### 3. ArgoCD 설정
ArgoCD는 GitOps 방식으로 Kubernetes 애플리케이션을 배포하고 관리합니다.

1. **ArgoCD 설치**:
   ```bash
   kubectl create namespace argocd
   kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
   ```
2. **ArgoCD 애플리케이션 생성**:
   ```yaml
   apiVersion: argoproj.io/v1alpha1
   kind: Application
   metadata:
     name: init
   spec:
     project: default
     source:
       repoURL: 'https://github.com/my-repo/init'
       targetRevision: HEAD
       path: helm/init
     destination:
       server: 'https://kubernetes.default.svc'
       namespace: ns-test
     syncPolicy:
       automated:
         prune: true
         selfHeal: true
   ```

### 4. Jenkins 설정
Jenkins는 CI/CD 파이프라인을 자동화하는 데 사용됩니다.

1. **Jenkins 설치**:
   ```bash
   kubectl create namespace jenkins
   helm repo add jenkins https://charts.jenkins.io
   helm repo update
   helm install jenkins jenkins/jenkins --namespace jenkins
   ```
2. **Jenkinsfile 작성**:
   ```groovy
   pipeline {
       agent any
       stages {
           stage('Build') {
               steps {
                   sh 'docker build -t my-dockerhub-username/init .'
               }
           }
           stage('Push') {
               steps {
                   withCredentials([string(credentialsId: 'dockerhub-credentials', variable: 'DOCKERHUB_PASSWORD')]) {
                       sh 'echo $DOCKERHUB_PASSWORD | docker login -u my-dockerhub-username --password-stdin'
                       sh 'docker push my-dockerhub-username/init:latest'
                   }
               }
           }
           stage('Deploy') {
               steps {
                   script {
                       def helmChart = "helm/init"
                       def releaseName = "init"
                       def namespace = "ns-test"
                       sh "helm upgrade --install ${releaseName} ${helmChart} --namespace ${namespace}"
                   }
               }
           }
       }
   }
   ```

### 5. CI/CD 파이프라인 설정
1. **Jenkins와 GitHub 연동**:
   - GitHub 웹훅을 설정하여 코드 변경 시 Jenkins 파이프라인이 자동으로 트리거되도록 합니다.
2. **ArgoCD와 GitOps 연동**:
   - ArgoCD가 Git 리포지토리를 모니터링하여 변경 사항을 자동으로 배포하도록 설정합니다[1](https://docs.cloudfoundry.org/devguide/deploy-apps/manifest.html)[2](https://learn.microsoft.com/en-us/microsoftteams/platform/toolkit/use-cicd-template).

[1](https://docs.cloudfoundry.org/devguide/deploy-apps/manifest.html): [Argo CD - Declarative GitOps CD for Kubernetes](https://argo-cd.readthedocs.io/)

[2](https://learn.microsoft.com/en-us/microsoftteams/platform/toolkit/use-cicd-template): [Automation from CI Pipelines - Argo CD](https://argo-cd.readthedocs.io/en/stable/user-guide/ci_automation/)


