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
