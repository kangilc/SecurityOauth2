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
## Ubuntu에서 Jenkins를 설치
3번에서 오류가 발생하는 경우 아래와 같은 순서로 Ubuntu 기반 설치 방법을 새로 정리

### 1. 시스템 업데이트
먼저, 시스템 패키지를 업데이트합니다:
```sh
sudo apt-get update
```

### 2. Java 설치
Jenkins는 Java 기반이므로 Java를 설치해야 합니다. 기본 JDK를 설치합니다:
```sh
sudo apt-get install default-jdk -y
```

### 3. Jenkins 저장소 추가
Jenkins의 GPG 키를 추가합니다:
```sh
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo tee /usr/share/keyrings/jenkins-keyring.asc > /dev/null
```

Jenkins 저장소를 추가합니다:
```sh
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null
```

### 4. Jenkins 설치
패키지 목록을 업데이트하고 Jenkins를 설치합니다:
```sh
sudo apt-get update
```
```sh
sudo apt-get install jenkins -y
```

### 5. Jenkins 시작 및 활성화
Jenkins 서비스를 시작하고 부팅 시 자동으로 시작되도록 활성화합니다:
```sh
sudo systemctl start jenkins
```
```sh
sudo systemctl enable jenkins
```

### 6. 방화벽 설정
Jenkins는 기본적으로 포트 8080을 사용합니다. UFW를 사용하여 포트를 허용합니다:
```sh
sudo ufw allow 8080
```
```sh
sudo ufw enable
```

### 7. Jenkins 초기 설정
웹 브라우저를 열고 `http://<your_server_ip>:8080`에 접속합니다. 초기 관리자 비밀번호를 입력해야 합니다. 비밀번호는 다음 명령어로 확인할 수 있습니다:
```sh
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

비밀번호를 입력하고 "계속"을 클릭합니다. 제안된 플러그인을 설치하고, 관리자 계정을 생성한 후 Jenkins를 사용할 수 있습니다¹(https://ko.linux-console.net/?p=15037)²(https://bing.com/search?q=Ubuntu+jenkins+%ec%84%a4%ec%b9%98).

이 단계를 통해 Ubuntu에 Jenkins를 설치하고 설정할 수 있습니다. 추가로 궁금한 점이 있으면 언제든지 말씀해 주세요!

¹(https://ko.linux-console.net/?p=15037): [Ubuntu 22.04에서 Jenkins를 설치하고 구성하는 방법](https://ko.linux-console.net/?p=15037)
²(https://bing.com/search?q=Ubuntu+jenkins+%ec%84%a4%ec%b9%98): [Ubuntu에 Jenkins 설치 - 벨로그](https://bing.com/search?q=Ubuntu+jenkins+%ec%84%a4%ec%b9%98)

원본: Copilot과의 대화, 2025. 2. 24.
(1) Ubuntu 22.04에서 Jenkins를 설치하고 구성하는 방법 - Linux-Console.net. https://ko.linux-console.net/?p=15037.
(2) Ubuntu에 Jenkins 설치 - 벨로그. https://bing.com/search?q=Ubuntu+jenkins+%ec%84%a4%ec%b9%98.

### 8. Jenkins GPG 관련 오류
GPG 키 문제를 해결하려면 다음 단계를 따르세요:

### 1. GPG 키 추가
누락된 GPG 키를 추가합니다. 오류 메시지에서 제공된 키 ID를 사용합니다. 예를 들어, 키 ID가 `5BA31D57EF5975CA`인 경우:
```sh
sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 5BA31D57EF5975CA
```

### 2. GPG 키 파일로 추가
GPG 키를 파일로 다운로드하고 추가할 수도 있습니다:
```sh
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo tee /usr/share/keyrings/jenkins-keyring.asc > /dev/null
```

### 3. 저장소 추가
GPG 키를 추가한 후, Jenkins 저장소를 추가합니다:
```sh
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null
```

### 4. 패키지 목록 업데이트
패키지 목록을 업데이트합니다:
```sh
sudo apt-get update
```

### 5. Jenkins 설치
Jenkins를 설치합니다:
```sh
sudo apt-get install jenkins -y
```

이 단계를 통해 GPG 키 문제를 해결하고 Jenkins를 설치할 수 있습니다¹(https://ko.linux-console.net/?p=15176)²(https://blog.naver.com/PostView.naver?blogId=4glac&logNo=223202876234). 추가로 궁금한 점이 있으면 언제든지 말씀해 주세요!

¹(https://ko.linux-console.net/?p=15176): [도커 GPG 오류 해결 명령어 - 네이버 블로그](https://blog.naver.com/PostView.naver?blogId=4glac&logNo=223202876234)
²(https://blog.naver.com/PostView.naver?blogId=4glac&logNo=223202876234): [Ubuntu에서 GPG ERROR NO_PUBKEY 에러 해결방법](https://blog.logger.one/entry/Ubuntu-%EC%97%90%EC%84%9C-GPG-ERROR-NOPUBKEY-%EC%97%90%EB%9F%AC-%ED%95%B4%EA%B2%B0%EB%B0%A9%EB%B2%95)

원본: Copilot과의 대화, 2025. 2. 24.
(1) gpg 해결 방법: 암호 해독 실패: 비밀 키 오류 없음 - Linux-Console.net. https://ko.linux-console.net/?p=15176.
(2) 도커 GPG 오류(GPG Error) 해결 명령어 - 네이버 블로그. https://blog.naver.com/PostView.naver?blogId=4glac&logNo=223202876234.

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
    kubectl port-forward svc/nginx 8082:80
    ```

#### Helm Chart
- **MySQL Helm Chart 배포**:
    ```bash
    helm install my-mysql stable/mysql
    ```

### helm install my-mysql stable/mysql 오류가 발생할 경우

```bash
helm install my-mysql stable/mysql
WARNING: This chart is deprecated
NAME: my-mysql
LAST DEPLOYED: Mon Mar 24 13:22:33 2025
NAMESPACE: default
STATUS: deployed
REVISION: 1
NOTES:
MySQL can be accessed via port 3306 on the following DNS name from within your cluster:
my-mysql.default.svc.cluster.local
```

Helm의 `stable/mysql` 차트가 더 이상 유지 관리되지 않기 때문에 경고 메시지가 표시됩니다[2](https://helm.sh/blog/charts-repo-deprecation/)[1](https://stackoverflow.com/questions/65642967/why-almost-all-helm-packages-are-deprecated). 대신, Bitnami에서 제공하는 MySQL 차트를 사용할 수 있습니다. Bitnami 차트는 지속적으로 업데이트되며, 안정적인 배포를 지원합니다[3](https://github.com/bitnami/charts/blob/main/bitnami/mysql/README.md)[4](https://bitnami.com/stack/mysql/helm).

Bitnami MySQL 차트를 설치하는 방법은 다음과 같습니다:

1. **Bitnami MySQL 차트 설치**:
   ```bash
   helm repo add bitnami https://charts.bitnami.com/bitnami
   helm install my-mysql bitnami/mysql
   ```

    ```bash
    helm install my-mysql bitnami/mysql
    ```
    ```bash
   Error: INSTALLATION FAILED: error getting credentials - err: exit status 1, out: `GDBus.Error:org.freedesktop.DBus.Error.ServiceUnknown: The name org.freedesktop.secrets was not provided by any .service files`
    ```

이 오류는 `org.freedesktop.secrets` 서비스가 시스템에 설치되지 않았기 때문에 발생합니다. 이를 해결하려면 `gnome-keyring`을 설치해야 합니다. 다음 단계를 따라주세요:

1. **gnome-keyring 설치**:
   Ubuntu를 사용하는 경우:
   ```bash
   sudo apt-get update
   sudo apt-get install gnome-keyring
   ```

   CentOS 또는 RHEL을 사용하는 경우:
   ```bash
   sudo yum install gnome-keyring
   ```

2. **gnome-keyring 데몬 실행**:
   설치 후, `gnome-keyring` 데몬을 실행합니다:
   ```bash
   /usr/bin/gnome-keyring-daemon --start --components=secrets
   ```

3. **Helm 설치 다시 시도**:
   라이브러리를 설치한 후 Helm 설치를 다시 시도합니다:
   ```bash
   helm install my-mysql bitnami/mysql
   ```

이 단계를 따르면 `org.freedesktop.secrets` 오류를 해결하고 Helm 설치를 완료할 수 있을 것입니다[1](https://github.com/Foundry376/Mailspring/issues/681). 문제가 계속 발생하면 알려주세요! 😊

[1](https://github.com/Foundry376/Mailspring/issues/681): [GitHub: The name org.freedesktop.secrets was not provided by any .service files](https://github.com/Foundry376/Mailspring/issues/681)

3. **설치 확인**:
   설치가 완료되면 다음 명령어를 사용하여 상태를 확인할 수 있습니다:
   ```bash
   helm status my-mysql
   ```

4. **접속 정보 확인**:
   MySQL은 클러스터 내에서 다음 DNS 이름을 통해 포트 3306으로 접근할 수 있습니다:
   ```
   my-mysql.default.svc.cluster.local
   ```

[2](https://helm.sh/blog/charts-repo-deprecation/): [Helm Chart Repository Deprecation Update](https://helm.sh/blog/charts-repo-deprecation/)

[1](https://stackoverflow.com/questions/65642967/why-almost-all-helm-packages-are-deprecated): [Why almost all helm packages are DEPRECATED?](https://stackoverflow.com/questions/65642967/why-almost-all-helm-packages-are-deprecated)

[3](https://github.com/bitnami/charts/blob/main/bitnami/mysql/README.md): [Bitnami package for MySQL](https://github.com/bitnami/charts/blob/main/bitnami/mysql/README.md)

[4](https://bitnami.com/stack/mysql/helm): [Bitnami package for MySQL](https://bitnami.com/stack/mysql/helm)

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

### `kubectl expose` 명령어를 사용하여 `nginx` 파드를 NodePort 서비스로 노출하는 방법은 다음과 같습니다:

1. **명령어 실행**:
   ```bash
   kubectl expose pod nginx --type=NodePort
   ```

2. **서비스 확인**:
   명령어 실행 후, 생성된 서비스를 확인합니다:
   ```bash
   kubectl get services
   ```

3. **NodePort 확인**:
   서비스 목록에서 `nginx` 서비스의 `NodePort`를 확인합니다. 예를 들어, 출력이 다음과 같을 수 있습니다:
   ```
   NAME         TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
   nginx        NodePort    10.96.0.1      <none>        80:32000/TCP     1m
   ```

4. **웹 브라우저에서 접속**:
   웹 브라우저를 열고 다음 URL로 접속합니다:
   ```
   http://<NodeIP>:<NodePort>
   ```
   여기서 `<NodeIP>`는 클러스터 노드의 IP 주소이고, `<NodePort>`는 위에서 확인한 포트 번호입니다.

```bash
http://127.0.0.1:46861
❗ Because you are using a Docker driver on linux, the terminal needs to be open to run it.
```

이 오류는 Minikube가 Docker 드라이버를 사용하고 있을 때 발생합니다. 터널을 통해 서비스를 노출하려면 터미널이 열려 있어야 합니다. 다음 단계를 따라 문제를 해결해 보세요:

1. **Minikube 서비스 터널 시작**:
   ```bash
   minikube service nginx --url
   ```
   이 명령어는 `nginx` 서비스의 URL을 출력하고 터널을 시작합니다.

2. **터널이 열려 있는지 확인**:
   터널이 열려 있는 동안 터미널을 닫지 마세요. 터널이 닫히면 서비스에 접근할 수 없습니다.

3. **포트 포워딩 사용**:
   포트 포워딩을 사용하여 서비스를 노출할 수 있습니다:
   ```bash
   kubectl port-forward svc/nginx 8080:80
   ```
   이 명령어는 로컬 포트 `8080`을 `nginx` 서비스의 포트 `80`에 포워딩합니다.

4. **웹 브라우저에서 접속**:
   웹 브라우저를 열고 다음 URL로 접속합니다:
   ```
   http://127.0.0.1:8080
   ```

