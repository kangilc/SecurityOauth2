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

Helm Chart를 사용하면 애플리케이션 배포를 자동화하고, 일관된 환경을 유지할 수 있습니다. 

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

이렇게 하면 Helm을 사용하여 쿠버네티스 애플리케이션을 쉽게 배포하고 관리할 수 있습니다. 

[1](https://velog.io/@chappi/helm%EC%9D%84-%EB%B0%B0%EC%9B%8C%EB%B3%B4%EC%9E%90-3%EC%9D%BC%EC%B0%A8-%EC%84%A4%EC%B9%98%EC%99%80-%EC%8B%9C%EC%9E%91): https://helm.sh/ko/docs/intro/install/
[2](https://bing.com/search?q=Helm+%ec%84%a4%ec%b9%98+%ed%9b%84+%ea%b8%b0%eb%b3%b8+%ec%84%a4%ec%a0%95): https://helm.sh/ko/docs/intro/using_helm/
[3](https://helm.sh/ko/docs/intro/using_helm/): https://velog.io/@salgu1998/Kubernetes-Helm-%EC%BF%A0%EB%B2%84%EB%84%A4%ED%8B%B0%EC%8A%A4-%ED%97%AC%EB%A6%84-Chart-%EC%83%9D%EC%84%B1%ED%95%98%EA%B3%A0-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0

### Helm Chart의 템플릿 구조

Helm Chart는 쿠버네티스 애플리케이션을 정의하고 배포하는 데 필요한 모든 리소스를 포함하는 패키지입니다. Helm Chart의 기본 구조는 다음과 같습니다:

```
mychart/
  Chart.yaml          # Chart에 대한 메타데이터
  values.yaml         # 기본 값 파일
  charts/             # 의존성 Chart
  templates/          # 쿠버네티스 리소스 템플릿
  templates/_helpers.tpl  # 템플릿 헬퍼 파일
```

- **Chart.yaml**: Chart의 메타데이터를 포함하는 파일입니다. 예를 들어, Chart의 이름, 버전, 설명 등이 포함됩니다.
- **values.yaml**: Chart의 기본 설정 값을 정의하는 파일입니다. 사용자는 이 파일을 수정하여 설정 값을 변경할 수 있습니다.
- **charts/**: Chart의 의존성을 포함하는 디렉터리입니다. 다른 Chart를 의존성으로 포함할 수 있습니다.
- **templates/**: 쿠버네티스 리소스 템플릿 파일을 포함하는 디렉터리입니다. 이 디렉터리 내의 파일들은 values.yaml 파일과 결합되어 유효한 쿠버네티스 매니페스트 파일을 생성합니다[1](https://helm.sh/ko/docs/topics/charts/)[2](https://helm.sh/ko/docs/chart_best_practices/templates/).

### Helm을 사용한 롤백 방법

Helm을 사용하여 이전 버전으로 롤백하는 방법은 다음과 같습니다:

1. **릴리스 이력 확인**:
   ```bash
   helm history <release-name>
   ```
   이 명령어를 사용하여 특정 릴리스의 업그레이드 또는 설치 이력을 확인할 수 있습니다.

2. **롤백 실행**:
   ```bash
   helm rollback <release-name> <revision>
   ```
   예를 들어, `revision 1`로 롤백하려면 다음과 같이 실행합니다:
   ```bash
   helm rollback my-release 1
   ```
   이 명령어를 실행하면, Helm은 클러스터 내 모든 관련 구성 요소를 지정된 리비전 상태로 되돌립니다[3](https://velog.io/@captain-yun/Helm%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%9C-%EB%A1%A4%EB%B0%B1-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%EB%A5%BC-%EC%9D%B4%EC%A0%84-%EC%83%81%ED%83%9C%EB%A1%9C-%EB%90%98%EB%8F%8C%EB%A6%AC%EA%B8%B0)[4](https://helm.sh/ko/docs/helm/helm_rollback/).

### Helm으로 비밀 관리하는 법

Helm을 사용하여 비밀(Secrets)을 관리하는 방법은 다음과 같습니다:

1. **values.yaml 파일에 비밀 추가**:
   values.yaml 파일에 비밀 값을 추가합니다. 예를 들어:
   ```yaml
   secret:
     username: admin
     password: secretpassword
   ```

2. **템플릿 파일에서 비밀 사용**:
   templates 디렉터리 내의 템플릿 파일에서 비밀 값을 참조합니다. 예를 들어, 시크릿 리소스를 생성하는 템플릿 파일:
   ```yaml
   apiVersion: v1
   kind: Secret
   metadata:
     name: my-secret
   type: Opaque
   data:
     username: {{ .Values.secret.username | b64enc }}
     password: {{ .Values.secret.password | b64enc }}
   ```

3. **Helm 차트 설치**:
   차트를 설치할 때, values.yaml 파일을 사용하여 비밀 값을 설정합니다:
   ```bash
   helm install my-release ./mychart -f values.yaml
   ```

이렇게 하면 Helm을 사용하여 비밀을 안전하게 관리할 수 있습니다[5](https://helm.sh/ko/docs/intro/quickstart/)[6](https://helm.sh/ko/docs/intro/using_helm/).



[3](https://velog.io/@captain-yun/Helm%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%9C-%EB%A1%A4%EB%B0%B1-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%EB%A5%BC-%EC%9D%B4%EC%A0%84-%EC%83%81%ED%83%9C%EB%A1%9C-%EB%90%98%EB%8F%8C%EB%A6%AC%EA%B8%B0): https://velog.io/@captain-yun/Helm%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%9C-%EB%A1%A4%EB%B0%B1-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%EB%A5%BC-%EC%9D%B4%EC%A0%84-%EC%83%81%ED%83%9C%EB%A1%9C-%EB%90%98%EB%8F%8C%EB%A6%AC%EA%B8%B0
[4](https://helm.sh/ko/docs/helm/helm_rollback/): https://helm.sh/ko/docs/helm/helm_rollback/
[5](https://helm.sh/ko/docs/intro/quickstart/): https://helm.sh/ko/docs/intro/quickstart/
[6](https://helm.sh/ko/docs/intro/using_helm/): https://helm.sh/ko/docs/intro/using_helm/
[1](https://helm.sh/ko/docs/topics/charts/): https://helm.sh/ko/docs/topics/charts/
[2](https://helm.sh/ko/docs/chart_best_practices/templates/): https://helm.sh/ko/docs/chart_best_practices/templates/

### 템플릿에서 조건문 사용법

Helm 템플릿에서 조건문을 사용하면 특정 조건에 따라 리소스를 생성하거나 설정을 변경할 수 있습니다. Go 템플릿 언어를 사용하여 조건문을 작성합니다. 주요 조건문 예시는 다음과 같습니다:

1. **if 조건문**:
   - 특정 조건이 참일 때만 리소스를 생성합니다.
   ```yaml
   apiVersion: v1
   kind: ConfigMap
   metadata:
     name: my-config
   data:
     {{- if .Values.enabled }}
     key: value
     {{- end }}
   ```

2. **if-else 조건문**:
   - 조건이 참일 때와 거짓일 때 각각 다른 리소스를 생성합니다.
   ```yaml
   apiVersion: v1
   kind: ConfigMap
   metadata:
     name: my-config
   data:
     {{- if .Values.enabled }}
     key: enabled-value
     {{- else }}
     key: disabled-value
     {{- end }}
   ```

3. **with 조건문**:
   - 특정 값이 존재할 때만 해당 블록을 실행합니다.
   ```yaml
   apiVersion: v1
   kind: ConfigMap
   metadata:
     name: my-config
   data:
     {{- with .Values.config }}
     key: {{ .key }}
     {{- end }}
   ```

4. **range 조건문**:
   - 리스트나 맵을 반복하여 리소스를 생성합니다.
   ```yaml
   apiVersion: v1
   kind: ConfigMap
   metadata:
     name: my-config
   data:
     {{- range $key, $value := .Values.configs }}
     {{ $key }}: {{ $value }}
     {{- end }}
   ```

이러한 조건문을 사용하면 Helm 템플릿을 더욱 유연하게 작성할 수 있습니다[1](https://helm.sh/ko/docs/chart_template_guide/function_list/)[2](https://velog.io/@showui96/Helm-차트-템플릿-가이드-1-명령어와-내장-객체).

### Helm으로 모니터링 설정하는 법

Helm을 사용하여 모니터링 도구를 설치하고 설정하는 방법은 다음과 같습니다. Prometheus와 Grafana를 예로 들어 설명하겠습니다.

1. **Helm 저장소 추가**:
   - Prometheus와 Grafana 차트를 포함하는 Helm 저장소를 추가합니다.
   ```bash
   helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
   helm repo add grafana https://grafana.github.io/helm-charts
   helm repo update
   ```

2. **Prometheus 설치**:
   - Prometheus를 설치하여 쿠버네티스 클러스터의 메트릭을 수집합니다.
   ```bash
   helm install prometheus prometheus-community/prometheus
   ```

3. **Grafana 설치**:
   - Grafana를 설치하여 Prometheus에서 수집한 메트릭을 시각화합니다.
   ```bash
   helm install grafana grafana/grafana
   ```

4. **Grafana 설정**:
   - Grafana 웹 인터페이스에 접속하여 Prometheus를 데이터 소스로 추가합니다.
     - **URL**: `http://prometheus-server:80`
     - **Access**: `Server`

5. **대시보드 생성**:
   - Grafana에서 새로운 대시보드를 생성하고, Prometheus에서 수집한 메트릭을 시각화합니다. 예를 들어, kube-proxy 메트릭을 시각화할 수 있습니다.

이렇게 하면 Helm을 사용하여 Prometheus와 Grafana를 설치하고, 쿠버네티스 클러스터의 모니터링을 설정할 수 있습니다[3](https://velog.io/@yerimm99/k8s-Helm%EC%9C%BC%EB%A1%9C-Kubernetes-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0-%EC%84%A4%EC%B9%98%EB%B6%80%ED%84%B0-%EC%B0%A8%ED%8A%B8-%EB%B0%B0%ED%8F%AC%EA%B9%8C%EC%A7%80)[4](https://helm.sh/ko/docs/intro/quickstart/).



[3](https://velog.io/@yerimm99/k8s-Helm%EC%9C%BC%EB%A1%9C-Kubernetes-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0-%EC%84%A4%EC%B9%98%EB%B6%80%ED%84%B0-%EC%B0%A8%ED%8A%B8-%EB%B0%B0%ED%8F%AC%EA%B9%8C%EC%A7%80): https://helm.sh/ko/docs/intro/quickstart/
[4](https://helm.sh/ko/docs/intro/quickstart/): https://velog.io/@yerimm99/k8s-Helm%EC%9C%BC%EB%A1%9C-Kubernetes-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0-%EC%84%A4%EC%B9%98%EB%B6%80%ED%84%B0-%EC%B0%A8%ED%8A%B8-%EB%B0%B0%ED%8F%AC%EA%B9%8C%EC%A7%80
[1](https://helm.sh/ko/docs/chart_template_guide/function_list/): https://helm.sh/ko/docs/chart_template_guide/function_list/
[2](https://velog.io/@showui96/Helm-차트-템플릿-가이드-1-명령어와-내장-객체): https://velog.io/@showui96/Helm-차트-템플릿-가이드-1-명령어와-내장-객체

### Helm으로 알림 설정하는 법

Helm을 사용하여 알림을 설정하려면 Prometheus와 Alertmanager를 함께 사용하면 됩니다. Prometheus는 메트릭을 수집하고, Alertmanager는 알림을 관리합니다. 다음은 Helm을 사용하여 Prometheus와 Alertmanager를 설정하는 방법입니다:

1. **Helm 저장소 추가**:
   ```bash
   helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
   helm repo update
   ```

2. **Prometheus 및 Alertmanager 설치**:
   ```bash
   helm install prometheus prometheus-community/prometheus
   ```

3. **Alertmanager 설정**:
   - `values.yaml` 파일을 수정하여 Alertmanager 설정을 추가합니다. 예를 들어, 이메일 알림을 설정하려면 다음과 같이 설정합니다:
     ```yaml
     alertmanager:
       config:
         global:
           smtp_smarthost: 'smtp.example.com:587'
           smtp_from: 'alertmanager@example.com'
           smtp_auth_username: 'your_username'
           smtp_auth_password: 'your_password'
         route:
           receiver: 'email'
         receivers:
           - name: 'email'
             email_configs:
               - to: 'your_email@example.com'
     ```

4. **Prometheus 규칙 추가**:
   - `values.yaml` 파일에 Prometheus 알림 규칙을 추가합니다:
     ```yaml
     serverFiles:
       alerts:
         groups:
           - name: example
             rules:
               - alert: HighMemoryUsage
                 expr: node_memory_Active_bytes / node_memory_MemTotal_bytes * 100 > 80
                 for: 5m
                 labels:
                   severity: 'critical'
                 annotations:
                   summary: 'High memory usage detected'
                   description: 'Memory usage is above 80% for more than 5 minutes.'
     ```

5. **Helm 차트 업데이트**:
   ```bash
   helm upgrade prometheus prometheus-community/prometheus -f values.yaml
   ```

이렇게 하면 Prometheus와 Alertmanager를 사용하여 알림을 설정할 수 있습니다[1](https://helm.sh/ko/docs/intro/quickstart/).

### Prometheus의 주요 기능

Prometheus는 오픈 소스 시스템 모니터링 및 알림 도구로, 다음과 같은 주요 기능을 제공합니다:

1. **다차원 데이터 모델**:
   - 메트릭 이름과 키/값 쌍으로 식별되는 시계열 데이터를 사용합니다.

2. **PromQL**:
   - 유연한 쿼리 언어로, 시계열 데이터를 쿼리하고 집계할 수 있습니다.

3. **독립적인 서버 노드**:
   - 분산 저장소에 의존하지 않으며, 단일 서버 노드가 자율적으로 동작합니다.

4. **Pull 모델**:
   - HTTP를 통해 타겟에서 메트릭을 수집합니다.

5. **서비스 디스커버리**:
   - 서비스 디스커버리 또는 정적 구성을 통해 타겟을 자동으로 발견합니다.

6. **다양한 그래프 및 대시보드 지원**:
   - Grafana와 같은 도구를 통해 데이터를 시각화할 수 있습니다[2](https://apronsksk.tistory.com/5)[3](https://helm.sh/ko/docs/intro/using_helm/).

### Grafana 대시보드 템플릿 사용법

Grafana 대시보드 템플릿을 사용하면 미리 정의된 대시보드를 쉽게 가져와 사용할 수 있습니다. 다음은 Grafana 대시보드 템플릿을 사용하는 방법입니다:

1. **Grafana 설치**:
   ```bash
   docker run -d -p 3000:3000 --name=grafana grafana/grafana
   ```

2. **Prometheus 데이터 소스 추가**:
   - Grafana 웹 인터페이스에 접속하여 Prometheus를 데이터 소스로 추가합니다:
     - **URL**: `http://prometheus-server:80`
     - **Access**: `Server`

3. **대시보드 템플릿 가져오기**:
   - Grafana 웹 인터페이스에서 `Dashboards` > `Manage` > `Import`를 선택합니다.
   - 템플릿 ID 또는 URL을 입력하고 `Load` 버튼을 클릭합니다.
   - 데이터 소스를 선택하고 `Import` 버튼을 클릭하여 대시보드를 가져옵니다.

4. **대시보드 커스터마이징**:
   - 가져온 대시보드를 필요에 맞게 수정하고, 새로운 패널을 추가하거나 기존 패널을 수정할 수 있습니다[4](https://docs.kakaocloud.com/tutorial/observability/grafana-monitoring)[5](https://bing.com/search?q=Grafana+%eb%8c%80%ec%8b%9c%eb%b3%b4%eb%93%9c+%ed%85%9c%ed%94%8c%eb%a6%bf+%ec%82%ac%ec%9a%a9%eb%b2%95).

이렇게 하면 Grafana 대시보드 템플릿을 사용하여 모니터링 대시보드를 쉽게 구성할 수 있습니다. 

[1](https://helm.sh/ko/docs/intro/quickstart/): https://helm.sh/ko/docs/intro/quickstart/

[2](https://apronsksk.tistory.com/5): https://prometheus.io/docs/introduction/overview/

[3](https://helm.sh/ko/docs/intro/using_helm/): https://velog.io/@mag000225/Prometheus-Prometheus%EB%9E%80

[4](https://docs.kakaocloud.com/tutorial/observability/grafana-monitoring): https://docs.kakaocloud.com/tutorial/observability/grafana-monitoring

[5](https://bing.com/search?q=Grafana+%eb%8c%80%ec%8b%9c%eb%b3%b4%eb%93%9c+%ed%85%9c%ed%94%8c%eb%a6%bf+%ec%82%ac%ec%9a%a9%eb%b2%95): https://grafana.com/grafana/dashboards/

## NGINX Ingress 컨트롤러
NGINX Ingress 컨트롤러는 Kubernetes 클러스터에서 HTTP 및 HTTPS 트래픽을 관리하고 라우팅하는 데 사용됩니다. 이 컨트롤러는 다양한 알고리즘을 사용하여 트래픽을 효과적으로 분산시킵니다. 주요 알고리즘은 다음과 같습니다:

1. **라운드 로빈(Round Robin)**:
   - 가장 기본적인 로드 밸런싱 알고리즘으로, 각 요청을 순차적으로 백엔드 서버에 분배합니다. 모든 서버에 균등하게 트래픽을 분산시키는 방식입니다[1](https://docs.nginx.com/nginx-ingress-controller/overview/about/).

2. **최소 연결(Least Connections)**:
   - 현재 연결 수가 가장 적은 서버로 트래픽을 분배합니다. 이는 서버 간의 부하를 균등하게 유지하는 데 유리합니다[1](https://docs.nginx.com/nginx-ingress-controller/overview/about/).

3. **EWMA(Exponentially Weighted Moving Average)**:
   - 지수 가중 이동 평균을 사용하여 서버의 응답 시간을 기반으로 트래픽을 분배합니다. 최근 응답 시간이 짧은 서버에 더 많은 트래픽을 할당하여 성능을 최적화합니다[1](https://docs.nginx.com/nginx-ingress-controller/overview/about/).

4. **IP 해시(IP Hash)**:
   - 클라이언트의 IP 주소를 해시하여 특정 서버에 트래픽을 분배합니다. 이는 동일한 클라이언트가 항상 동일한 서버에 연결되도록 보장합니다[1](https://docs.nginx.com/nginx-ingress-controller/overview/about/).

이 알고리즘들은 NGINX Ingress 컨트롤러의 설정을 통해 선택할 수 있으며, 각 알고리즘은 특정 상황에서 최적의 성능을 제공할 수 있습니다. 예를 들어, EWMA 알고리즘은 응답 시간이 중요한 애플리케이션에 유리하며, IP 해시는 세션 지속성이 필요한 경우에 유용합니다[2](https://docs.nginx.com/nginx-ingress-controller/overview/design/).

[1](https://docs.nginx.com/nginx-ingress-controller/overview/about/): [NGINX Ingress Controller Overview](https://docs.nginx.com/nginx-ingress-controller/overview/about/)

[2](https://docs.nginx.com/nginx-ingress-controller/overview/design/): [The design of NGINX Ingress Controller](https://docs.nginx.com/nginx-ingress-controller/overview/design/)
