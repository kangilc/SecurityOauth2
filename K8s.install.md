로컬 환경에서 Kubernetes, Helm Chart, Jenkins, Argo CD, Grafana를 설치하고 사용하는 방법을 단계별로 설명드리겠습니다.

### 1. Kubernetes 설치
로컬 환경에서 Kubernetes를 설치하려면 Minikube를 사용하는 것이 가장 간단합니다.

#### Minikube 설치
1. **Minikube 설치**:
    ```bash
    curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
    sudo install minikube-linux-amd64 /usr/local/bin/minikube
    ```
2. **Minikube 시작**:
    ```bash
    minikube start
    ```

### 2. Helm Chart 설치
Helm은 Kubernetes 애플리케이션을 관리하는 패키지 매니저입니다.

#### Helm 설치
1. **Helm 설치**:
    ```bash
    curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
    ```
    ```bash
    chmod 700 get_helm.sh
    ```
    ```bash
    ./get_helm.sh
    ```
2. **Helm Repository 추가**:
    ```bash
    helm repo add stable https://charts.helm.sh/stable
    helm repo update
    ```

### 3. Jenkins 설치
Jenkins는 Java 기반의 CI/CD 도구로, 로컬 환경에 설치할 수 있습니다.

#### Jenkins 설치
1. **Jenkins 설치**:
    ```bash
    wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | sudo apt-key add -
    ```
    ```bash
    sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
    ```
    ```bash
    sudo apt-get update
    ```
    ```bash
    sudo apt-get install jenkins
    ```
2. **Jenkins 시작**:
    ```bash
    sudo systemctl start jenkins
    ```
    ```bash
    sudo systemctl enable jenkins
    ```

### 4. Argo CD 설치
Argo CD는 Kubernetes 클러스터에 애플리케이션을 배포하는 GitOps 도구입니다.

#### Argo CD 설치
1. **Argo CD 설치**:
    ```bash
    kubectl create namespace argocd
    kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
    ```
2. **Argo CD UI 접속**:
    ```bash
    kubectl port-forward svc/argocd-server -n argocd 8080:443
    ```
    브라우저에서 `https://localhost:8080`으로 접속합니다.

### 5. Grafana 설치
Grafana는 시각화 도구로, 다양한 데이터 소스를 시각화할 수 있습니다.

#### Grafana 설치
1. **Grafana 설치**:
    ```bash
    sudo apt-get install -y software-properties-common
    sudo add-apt-repository "deb https://packages.grafana.com/oss/deb stable main"
    sudo apt-get update
    sudo apt-get install grafana
    ```
2. **Grafana 시작**:
    ```bash
    sudo systemctl start grafana-server
    sudo systemctl enable grafana-server
    ```

### 사용 예제
#### Kubernetes
- **Pod 배포**:
    ```bash
    kubectl run nginx --image=nginx --port=80
    kubectl expose pod nginx --type=NodePort
    ```

#### Helm Chart
- **MySQL Helm Chart 배포**:
    ```bash
    helm install my-mysql stable/mysql
    ```

#### Jenkins
- **파이프라인 설정**:
    Jenkins 대시보드에서 새로운 파이프라인을 생성하고, 다음과 같은 파이프라인 스크립트를 추가합니다:
    ```groovy
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    echo 'Building...'
                }
            }
            stage('Test') {
                steps {
                    echo 'Testing...'
                }
            }
            stage('Deploy') {
                steps {
                    echo 'Deploying...'
                }
            }
        }
    }
    ```

#### Argo CD
- **애플리케이션 배포**:
    Argo CD UI에서 새로운 애플리케이션을 생성하고, Git 리포지토리의 Kubernetes 매니페스트를 사용하여 애플리케이션을 배포합니다.

#### Grafana
- **대시보드 생성**:
    Grafana UI에서 새로운 대시보드를 생성하고, 데이터 소스를 추가하여 시각화를 설정합니다.

이 과정을 통해 로컬 환경에서 Kubernetes, Helm Chart, Jenkins, Argo CD, Grafana를 설치하고 사용할 수 있습니다.

원본: Copilot과의 대화, 2025. 2. 20.
(1) github.com. https://github.com/rim-wood/blog/tree/47bfdc70422a41ebb98fb5d5d9cf9818a012aebe/source%2F_posts%2Fjenkins%2Fjenkins.md.
(2) github.com. https://github.com/KasiaMichalowska/jenkins-testy/tree/2741f5892c9dac1fc24f6abe1c185d58aca95352/install_jenkins.sh.
(3) github.com. https://github.com/liam-isles/ExamJava/tree/79d99b8d0bdf66e05f936e51ccafd75ae6e591b2/installscript2.sh.
(4) github.com. https://github.com/JaredMcI/RPS/tree/32ce4341d87672db41ad142f8420269b4cd3055a/install-all.sh.
(5) github.com. https://github.com/kroutley/p4-plugin/tree/7389f2a5596efd87c72c2aa5161904a30c719112/docs%2FWORKFLOW.md.
