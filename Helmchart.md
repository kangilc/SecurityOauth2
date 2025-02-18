## Helm Chart
쿠버네티스 애플리케이션을 정의하고 설치하는 데 사용되는 패키지입니다. Helm은 쿠버네티스의 패키지 매니저로, Helm Chart를 사용하여 복잡한 애플리케이션을 쉽게 배포하고 관리할 수 있습니다.

### 주요 개념

1. **Chart**:
   - 쿠버네티스 리소스의 집합을 정의하는 템플릿입니다. Chart는 애플리케이션의 모든 구성 요소(예: 디플로이먼트, 서비스, 컨피그맵 등)를 포함합니다.

2. **Release**:
   - Chart를 사용하여 생성된 실행 가능한 인스턴스입니다. 동일한 Chart를 여러 번 설치하여 여러 Release를 생성할 수 있습니다.

3. **Repository**:
   - Chart를 저장하고 공유하는 장소입니다. Helm Repository는 Chart의 버전을 관리하고, 다른 사용자와 공유할 수 있도록 합니다.

### Helm Chart의 구조
Helm Chart는 다음과 같은 디렉터리 구조를 가집니다:
```
mychart/
  Chart.yaml          # Chart에 대한 메타데이터
  values.yaml         # 기본 값 파일
  charts/             # 의존성 Chart
  templates/          # 쿠버네티스 리소스 템플릿
  templates/_helpers.tpl  # 템플릿 헬퍼 파일
```

### 예시
간단한 Nginx Helm Chart의 예시는 다음과 같습니다:

**Chart.yaml**:
```yaml
apiVersion: v2
name: nginx
description: A simple Nginx chart
version: 0.1.0
```

**values.yaml**:
```yaml
replicaCount: 2
image:
  repository: nginx
  tag: stable
service:
  type: ClusterIP
  port: 80
```

**templates/deployment.yaml**:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Release.Name }}-nginx
spec:
  replicas: {{ .Values.replicaCount }}
  selector:
    matchLabels:
      app: {{ .Release.Name }}-nginx
  template:
    metadata:
      labels:
        app: {{ .Release.Name }}-nginx
    spec:
      containers:
      - name: nginx
        image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
        ports:
        - containerPort: 80
```

이 예시는 Nginx 애플리케이션을 배포하는 간단한 Helm Chart입니다. `values.yaml` 파일에서 기본 값을 설정하고, `templates/deployment.yaml` 파일에서 쿠버네티스 디플로이먼트를 정의합니다.

Helm Chart를 사용하면 애플리케이션 배포를 자동화하고, 일관된 환경을 유지할 수 있습니다. 더 궁금한 점이 있으면 언제든지 질문해 주세요! 😊

## Helm을 설치하는 방법
Helm을 설치하는 방법은 여러 가지가 있습니다. 여기서는 가장 일반적인 방법인 스크립트를 사용한 설치와 패키지 매니저를 사용한 설치 방법을 설명해드릴게요.

### 1. 스크립트를 사용한 설치
Helm 설치 스크립트를 사용하여 쉽게 설치할 수 있습니다. 다음 명령어를 실행하세요:
```bash
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh
```
이 스크립트는 최신 버전의 Helm을 다운로드하고 설치합니다[1](https://helm.sh/ko/docs/intro/install/).

### 2. 패키지 매니저를 사용한 설치
운영 체제에 따라 다양한 패키지 매니저를 사용하여 Helm을 설치할 수 있습니다:

- **Homebrew (macOS)**:
  ```bash
  brew install helm
  ```

- **Chocolatey (Windows)**:
  ```bash
  choco install kubernetes-helm
  ```

- **Scoop (Windows)**:
  ```bash
  scoop install helm
  ```

- **Apt (Debian/Ubuntu)**:
  ```bash
  curl https://baltocdn.com/helm/signing.asc | gpg --dearmor | sudo tee /usr/share/keyrings/helm.gpg > /dev/null
  sudo apt-get install apt-transport-https --yes
  echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/helm.gpg] https://baltocdn.com/helm/stable/debian/ all main" | sudo tee /etc/apt/sources.list.d/helm-stable-debian.list
  sudo apt-get update
  sudo apt-get install helm
  ```

- **dnf/yum (Fedora)**:
  ```bash
  sudo dnf install helm
  ```

- **Snap (Linux)**:
  ```bash
  sudo snap install helm --classic
  ```

이 방법들을 통해 Helm을 설치할 수 있습니다. 설치 후에는 `helm version` 명령어를 사용하여 설치가 제대로 되었는지 확인할 수 있습니다.

더 궁금한 점이 있으면 언제든지 질문해 주세요! 😊

[1](https://helm.sh/ko/docs/intro/install/): https://helm.sh/ko/docs/intro/install/

### Helm 설치 후 해야 할 설정

Helm을 설치한 후에는 몇 가지 기본 설정을 해야 합니다:

1. **Helm 저장소 추가**:
   - Helm Chart를 다운로드하고 설치할 수 있는 저장소를 추가합니다. 예를 들어, 공식 stable 저장소를 추가하려면 다음 명령어를 사용합니다:
     ```bash
     helm repo add stable https://charts.helm.sh/stable
     ```

2. **Helm 저장소 업데이트**:
   - 저장소를 추가한 후에는 저장소 정보를 업데이트합니다:
     ```bash
     helm repo update
     ```

3. **kubectl 설정 확인**:
   - Helm은 `kubectl`을 사용하여 쿠버네티스 클러스터와 통신합니다. `kubectl`이 올바르게 설정되어 있는지 확인합니다:
     ```bash
     kubectl config view
     ```

### Helm의 기본 명령어

Helm을 사용하여 쿠버네티스 애플리케이션을 관리할 때 자주 사용하는 기본 명령어는 다음과 같습니다:

1. **helm search**:
   - 저장소에서 차트를 검색합니다.
     ```bash
     helm search repo <chart-name>
     ```

2. **helm install**:
   - 차트를 설치합니다.
     ```bash
     helm install <release-name> <chart-name>
     ```

3. **helm list**:
   - 설치된 릴리스를 나열합니다.
     ```bash
     helm list
     ```

4. **helm upgrade**:
   - 릴리스를 업그레이드합니다.
     ```bash
     helm upgrade <release-name> <chart-name>
     ```

5. **helm uninstall**:
   - 릴리스를 삭제합니다.
     ```bash
     helm uninstall <release-name>
     ```

6. **helm repo add**:
   - 새로운 저장소를 추가합니다.
     ```bash
     helm repo add <repo-name> <repo-url>
     ```

7. **helm repo update**:
   - 저장소 정보를 업데이트합니다.
     ```bash
     helm repo update
     ```

## Helm Chart를 배포하는 방법

Helm Chart를 배포하는 방법은 다음과 같습니다:

1. **Helm Chart 생성**:
   - 새로운 Helm Chart를 생성합니다.
     ```bash
     helm create <chart-name>
     ```

2. **Chart 파일 수정**:
   - 생성된 Chart 디렉토리에서 `values.yaml` 파일과 `templates` 디렉토리의 파일을 수정하여 원하는 설정을 반영합니다.

3. **Chart 배포 전 테스트**:
   - Chart의 문법과 템플릿을 테스트합니다.
     ```bash
     helm lint <chart-directory>
     helm template <chart-directory>
     ```

4. **Chart 배포**:
   - Chart를 쿠버네티스 클러스터에 배포합니다.
     ```bash
     helm install <release-name> <chart-directory>
     ```

5. **배포 확인**:
   - 배포된 릴리스를 확인합니다.
     ```bash
     helm list
     ```

6. **Chart 업그레이드**:
   - Chart를 수정한 후 업그레이드합니다.
     ```bash
     helm upgrade <release-name> <chart-directory>
     ```

이렇게 하면 Helm을 사용하여 쿠버네티스 애플리케이션을 쉽게 배포하고 관리할 수 있습니다. 더 궁금한 점이 있으면 언제든지 질문해 주세요! 😊

[1](https://velog.io/@chappi/helm%EC%9D%84-%EB%B0%B0%EC%9B%8C%EB%B3%B4%EC%9E%90-3%EC%9D%BC%EC%B0%A8-%EC%84%A4%EC%B9%98%EC%99%80-%EC%8B%9C%EC%9E%91): https://helm.sh/ko/docs/intro/install/
[2](https://bing.com/search?q=Helm+%ec%84%a4%ec%b9%98+%ed%9b%84+%ea%b8%b0%eb%b3%b8+%ec%84%a4%ec%a0%95): https://helm.sh/ko/docs/intro/using_helm/
[3](https://helm.sh/ko/docs/intro/using_helm/): https://velog.io/@salgu1998/Kubernetes-Helm-%EC%BF%A0%EB%B2%84%EB%84%A4%ED%8B%B0%EC%8A%A4-%ED%97%AC%EB%A6%84-Chart-%EC%83%9D%EC%84%B1%ED%95%98%EA%B3%A0-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0
