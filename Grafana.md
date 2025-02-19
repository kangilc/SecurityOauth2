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
