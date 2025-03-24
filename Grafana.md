# Grafana
Grafana는 오픈 소스 데이터 시각화 및 모니터링 도구입니다. 시스템 및 애플리케이션의 성능을 모니터링하고, 그래프, 대시보드, 경고 및 시각적 대시보드를 생성하고 사용자 정의할 수 있습니다[1](https://www.redhat.com/ko/topics/data-services/what-is-grafana).
## 기본
### 주요 기능
1. **데이터 시각화**:
   - 다양한 데이터 소스를 연결하여 데이터를 시각화할 수 있습니다. 예를 들어, Prometheus, InfluxDB, Elasticsearch 등을 지원합니다.
   - 그래프, 게이지, 히트맵 등 다양한 시각화 유형을 제공합니다.

2. **대시보드 생성 및 관리**:
   - 사용자 정의 대시보드를 생성하여 여러 메트릭을 한눈에 모니터링할 수 있습니다.
   - 대시보드를 공유하고, 팀과 협업할 수 있습니다.

3. **알림 설정**:
   - 특정 조건이 충족될 때 알림을 설정하여 문제를 조기에 감지할 수 있습니다.
   - 이메일, Slack, PagerDuty 등 다양한 알림 채널을 지원합니다.

4. **플러그인 지원**:
   - 다양한 플러그인을 설치하여 기능을 확장할 수 있습니다. 예를 들어, 새로운 시각화 유형이나 데이터 소스를 추가할 수 있습니다.

Grafana는 유연하고 강력한 도구로, IT 운영, DevOps, 애플리케이션 개발 등 다양한 분야에서 널리 사용됩니다.

[1](https://www.redhat.com/ko/topics/data-services/what-is-grafana): https://www.redhat.com/ko/topics/data-services/what-is-grafana

## 설치 방법
### Grafana 설치 방법

Grafana를 설치하는 방법은 여러 가지가 있습니다. 여기서는 가장 일반적인 방법인 Docker를 사용한 설치와 패키지 매니저를 사용한 설치 방법을 설명합니다.

#### 1. Docker를 사용한 설치
Docker를 사용하여 Grafana를 설치하는 방법은 다음과 같습니다:
```bash
docker run -d -p 3000:3000 --name=grafana grafana/grafana
```
설치 후, 웹 브라우저에서 `http://localhost:3000`으로 접속하여 Grafana 웹 인터페이스에 로그인합니다. 기본 사용자 이름과 비밀번호는 `admin`입니다[1](https://grafana.com/docs/grafana/latest/setup-grafana/installation/).

#### 2. 패키지 매니저를 사용한 설치
운영 체제에 따라 다양한 패키지 매니저를 사용하여 Grafana를 설치할 수 있습니다:

- **Homebrew (macOS)**:
  ```bash
  brew install grafana
  ```

- **Apt (Debian/Ubuntu)**:
  ```bash
  sudo apt-get install -y software-properties-common
  sudo add-apt-repository "deb https://packages.grafana.com/oss/deb stable main"
  sudo apt-get update
  sudo apt-get install grafana
  ```

- **Yum (CentOS/RHEL)**:
  ```bash
  sudo yum install -y grafana
  ```

- **Windows**:
  1. Grafana 다운로드 페이지에서 Windows 설치 파일을 다운로드합니다.
  2. 설치 파일을 실행하고 지침에 따라 설치를 완료합니다[2](https://grafana.com/docs/grafana/latest/setup-grafana/installation/windows/).

### Grafana와 Kibana의 차이점

Grafana와 Kibana는 모두 데이터 시각화 도구이지만, 그 목적과 기능에서 몇 가지 차이점이 있습니다:

1. **목적**:
   - **Grafana**: 주로 메트릭 데이터의 시각화와 모니터링에 중점을 둡니다. 다양한 데이터 소스(예: Prometheus, InfluxDB, Graphite 등)를 지원하며, 대시보드를 통해 실시간 모니터링을 제공합니다[3](https://logz.io/blog/grafana-vs-kibana/).
   - **Kibana**: 주로 로그 데이터의 탐색, 분석, 시각화에 중점을 둡니다. Elasticsearch와 함께 사용되며, 로그 데이터를 기반으로 대시보드를 생성하고 분석할 수 있습니다[4](https://softteco.com/blog/kibana-vs-grafana-comparison).

2. **데이터 소스**:
   - **Grafana**: Prometheus, InfluxDB, Graphite, Elasticsearch 등 다양한 데이터 소스를 지원합니다.
   - **Kibana**: 주로 Elasticsearch를 데이터 소스로 사용합니다.

3. **시각화 기능**:
   - **Grafana**: 다양한 시각화 유형(그래프, 게이지, 히트맵 등)을 제공하며, 대시보드를 쉽게 커스터마이징할 수 있습니다.
   - **Kibana**: 로그 데이터를 기반으로 한 시각화에 강점을 가지며, 차트, 테이블, 지리적 맵 등 다양한 시각화 옵션을 제공합니다[5](https://betterstack.com/community/comparisons/grafana-vs-kibana/).

4. **알림 기능**:
   - **Grafana**: 다양한 알림 채널(이메일, Slack, PagerDuty 등)을 통해 알림을 설정할 수 있습니다.
   - **Kibana**: 최근 업데이트로 알림 기능이 추가되었으며, 사용자 정의 규칙을 통해 알림을 설정할 수 있습니다[5](https://betterstack.com/community/comparisons/grafana-vs-kibana/).

이러한 차이점을 통해 Grafana와 Kibana를 적절히 선택하여 사용할 수 있습니다.

[3](https://logz.io/blog/grafana-vs-kibana/): https://logz.io/blog/grafana-vs-kibana/
[4](https://softteco.com/blog/kibana-vs-grafana-comparison): https://softteco.com/blog/kibana-vs-grafana-comparison
[5](https://betterstack.com/community/comparisons/grafana-vs-kibana/): https://betterstack.com/community/comparisons/grafana-vs-kibana/
[1](https://grafana.com/docs/grafana/latest/setup-grafana/installation/): https://grafana.com/docs/grafana/latest/setup-grafana/installation/
[2](https://grafana.com/docs/grafana/latest/setup-grafana/installation/windows/): https://grafana.com/docs/grafana/latest/setup-grafana/installation/windows/

Kubernetes에서 특정 Pod에서 지연 발생 알람이 5회 이상 발생했을 때 다른 서비스의 Pod 수량을 조절하는 방법은 Prometheus, Alertmanager, 그리고 Horizontal Pod Autoscaler (HPA)를 함께 사용하는 것입니다. 다음은 그 방법을 단계별로 설명드리겠습니다:

### 1. Prometheus 및 Alertmanager 설정
먼저, Prometheus와 Alertmanager를 설정하여 지연 발생 알람을 모니터링합니다.

#### Prometheus 설정
1. **Prometheus 설치**:
    ```bash
    helm install prometheus stable/prometheus
    ```
2. **Prometheus 설정 파일 수정**:
    Prometheus 설정 파일에서 지연 발생 알람 규칙을 추가합니다. 여기서 알람이 5회 이상 발생했을 때 트리거되도록 설정합니다.
    ```yaml
    groups:
    - name: example
      rules:
      - alert: HighLatency
        expr: increase(http_request_duration_seconds_count[5m]) > 5
        for: 5m
        labels:
          severity: page
        annotations:
          summary: "High request latency detected more than 5 times"
    ```

#### Alertmanager 설정
1. **Alertmanager 설치**:
    ```bash
    helm install alertmanager stable/alertmanager
    ```
2. **Alertmanager 설정 파일 수정**:
    Alertmanager 설정 파일에서 알람 수신 및 처리 방법을 정의합니다.
    ```yaml
    route:
      group_by: ['alertname']
      receiver: 'slack-notifications'
    receivers:
    - name: 'slack-notifications'
      slack_configs:
      - send_resolved: true
        channel: '#alerts'
        api_url: 'https://hooks.slack.com/services/...'
    ```

### 2. Horizontal Pod Autoscaler 설정
HPA를 사용하여 특정 서비스의 Pod 수량을 자동으로 조절합니다.

1. **HPA 설정**:
    ```bash
    kubectl autoscale deployment <deployment-name> --cpu-percent=50 --min=1 --max=10
    ```
    이 명령어는 CPU 사용률이 50%를 초과할 때 Pod 수량을 자동으로 조절합니다.

### 3. 알람 기반 스케일링
Prometheus와 Alertmanager를 사용하여 지연 발생 알람을 감지하고, HPA를 통해 Pod 수량을 조절합니다.

1. **알람 트리거**:
    Prometheus에서 지연 발생 알람이 5회 이상 감지되면 Alertmanager로 알람을 전송합니다.
2. **HPA 조절**:
    Alertmanager에서 알람을 수신하면, HPA를 통해 특정 서비스의 Pod 수량을 조절합니다.

이 과정을 통해 지연 발생 알람이 5회 이상 올 때 특정 서비스의 Pod 수량을 자동으로 조절할 수 있습니다.

¹(https://velog.io/@wanny328/kubernetes-Prometheus-Alertmanager%EB%A5%BC-%ED%86%B5%ED%95%9C-Alert-%EA%B8%B0%EB%8A%A5-%EC%A0%81%EC%9A%A9-%EB%B0%A9%EB%B2%95): [벨로그](https://velog.io/@wanny328/kubernetes-Prometheus-Alertmanager%EB%A5%BC-%ED%86%B5%ED%95%9C-Alert-%EA%B8%B0%EB%8A%A5-%EC%A0%81%EC%9A%A9-%EB%B0%A9%EB%B2%95)
²(https://velog.io/@litiblue/Kubernetes-Pod-%EC%A0%95%EC%83%81%EC%83%81%ED%83%9C-%EC%A0%90%EA%B2%80-Probe-%EC%82%AC%EC%9A%A9): [벨로그](https://velog.io/@litiblue/Kubernetes-Pod-%EC%A0%95%EC%83%81%EC%83%81%ED%83%9C-%EC%A0%90%EA%B2%80-Probe-%EC%82%AC%EC%9A%A9)

원본: Copilot과의 대화, 2025. 2. 20.
(1) [kubernetes] Prometheus - Alertmanager를 통한 Alert 기능 적용 방법 - 벨로그. https://velog.io/@wanny328/kubernetes-Prometheus-Alertmanager%EB%A5%BC-%ED%86%B5%ED%95%9C-Alert-%EA%B8%B0%EB%8A%A5-%EC%A0%81%EC%9A%A9-%EB%B0%A9%EB%B2%95.
(2) Kubernetes Pod 정상상태 점검 - Probe 사용 - 벨로그. https://velog.io/@litiblue/Kubernetes-Pod-%EC%A0%95%EC%83%81%EC%83%81%ED%83%9C-%EC%A0%90%EA%B2%80-Probe-%EC%82%AC%EC%9A%A9.

## sudo apt-get update 오류(Error) 발생 시
```bash
Reading package lists... Done
W: https://packages.grafana.com/oss/deb/dists/stable/InRelease: Key is stored in legacy trusted.gpg keyring (/etc/apt/trusted.gpg), see the DEPRECATION section in apt-key(8) for details.
W: http://pkg.jenkins.io/debian-stable/binary/Release.gpg: Key is stored in legacy trusted.gpg keyring (/etc/apt/trusted.gpg), see the DEPRECATION section in apt-key(8) for details.
```

이 경고는 `apt-key` 명령어가 더 이상 사용되지 않기 때문에 발생합니다. 대신, 키를 `/etc/apt/trusted.gpg.d` 디렉토리에 저장해야 합니다. 다음 단계를 따라 문제를 해결할 수 있습니다:

1. **키링 디렉토리 생성**:
   ```bash
   sudo mkdir -p /etc/apt/keyrings
   ```

2. **Grafana GPG 키 추가**:
   ```bash
   curl -fsSL https://packages.grafana.com/gpg.key | sudo gpg --dearmor -o /etc/apt/keyrings/grafana.gpg
   ```

3. **Jenkins GPG 키 추가**:
   ```bash
   curl -fsSL https://pkg.jenkins.io/debian/jenkins.io.key | sudo gpg --dearmor -o /etc/apt/keyrings/jenkins.gpg
   ```

4. **소스 리스트 업데이트**:
   `/etc/apt/sources.list.d/grafana.list` 파일을 생성하고 다음 내용을 추가합니다:
   ```bash
   echo "deb [signed-by=/etc/apt/keyrings/grafana.gpg] https://packages.grafana.com/oss/deb stable main" | sudo tee /etc/apt/sources.list.d/grafana.list
   ```

   `/etc/apt/sources.list.d/jenkins.list` 파일을 생성하고 다음 내용을 추가합니다:
   ```bash
   echo "deb [signed-by=/etc/apt/keyrings/jenkins.gpg] https://pkg.jenkins.io/debian-stable binary/" | sudo tee /etc/apt/sources.list.d/jenkins.list
   ```

5. **패키지 목록 업데이트**:
   ```bash
   sudo apt-get update
   ```
