# Kibana

Kibana는 Elasticsearch 데이터를 시각화하고 분석하는 데 사용되는 강력한 도구입니다. 다음은 Kibana의 주요 기능과 사용 예시입니다:

### 주요 기능
1. **데이터 시각화**: Kibana는 바 차트, 파이 차트, 히스토그램, 맵 등 다양한 시각화 도구를 제공합니다. 이를 통해 데이터를 쉽게 이해할 수 있습니다¹(https://www.elastic.co/kr/kibana).
2. **대시보드 생성**: 여러 시각화를 조합하여 대시보드를 만들고, 이를 공유할 수 있습니다. 대시보드는 실시간 데이터 모니터링에 유용합니다²(https://www.elastic.co/kr/kibana/features).
3. **데이터 탐색**: Elasticsearch에 저장된 데이터를 검색하고 필터링할 수 있습니다. Discover 기능을 사용하면 데이터를 쉽게 탐색할 수 있습니다³(https://m.blog.naver.com/mu-ze/222065329452).
4. **실시간 분석**: 실시간 데이터 모니터링 및 분석이 가능하여, 빠르게 변화하는 데이터를 즉시 파악할 수 있습니다¹(https://www.elastic.co/kr/kibana).
5. **경보 설정**: 특정 조건이 충족되면 알림을 받을 수 있도록 경보를 설정할 수 있습니다. 이를 통해 중요한 이벤트를 놓치지 않을 수 있습니다²(https://www.elastic.co/kr/kibana/features).

### 사용 예시
#### 웹 서버 로그 분석
1. **로그 데이터 수집**: Filebeat를 사용하여 웹 서버 로그 데이터를 수집합니다.
2. **Elasticsearch로 전송**: 수집된 로그 데이터를 Elasticsearch로 전송합니다.
3. **Kibana에서 시각화**: Kibana에서 로그 데이터를 바 차트나 히스토그램으로 시각화하여 특정 시간대의 트래픽 패턴을 분석합니다³(https://m.blog.naver.com/mu-ze/222065329452).

#### 보안 로그 모니터링
1. **보안 이벤트 수집**: 보안 이벤트 로그를 수집하여 Elasticsearch에 저장합니다.
2. **Kibana에서 분석**: Kibana를 사용하여 보안 이벤트를 시각화하고, 이상 징후를 탐지합니다.
3. **경보 설정**: 특정 보안 이벤트가 발생하면 경보를 설정하여 즉시 대응할 수 있습니다¹(https://www.elastic.co/kr/kibana).

Kibana는 다양한 데이터 소스를 통합하여 시각화하고 분석할 수 있는 유연한 도구입니다. 추가로 궁금한 점이 있으면 언제든지 물어보세요! 😊

¹(https://www.elastic.co/kr/kibana): [Elastic Blog](https://www.elastic.co/kr/kibana)
²(https://www.elastic.co/kr/kibana/features): [Elastic Features](https://www.elastic.co/kr/kibana/features)
³(https://m.blog.naver.com/mu-ze/222065329452): [네이버 블로그](https://m.blog.naver.com/mu-ze/222065329452)

원본: Copilot과의 대화, 2025. 2. 20.
(1) Kibana: 데이터 탐색, 시각화, 발견 | Elastic. https://www.elastic.co/kr/kibana.
(2) Kibana 기능 목록 - Elastic. https://www.elastic.co/kr/kibana/features.
(3) 키바나 : 개념과 기본 쿼리 - 네이버 블로그. https://m.blog.naver.com/mu-ze/222065329452.
