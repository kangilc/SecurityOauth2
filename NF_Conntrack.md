## NF_Conntrack

`nf_conntrack`는 Linux 커널에서 네트워크 연결을 추적하는 기능을 제공합니다. 이 기능은 네트워크 연결의 상태를 기록하고 관리하는 데 사용됩니다. 여기에는 다음과 같은 주요 기능이 포함됩니다:

1. **연결 상태 추적**: 각 네트워크 연결의 상태를 추적하고 기록합니다. 예를 들어, TCP 연결의 생성, 유지, 종료 상태를 관리합니다[1](https://www.kernel.org/doc/html/latest/networking/nf_conntrack-sysctl.html).
2. **패킷 필터링**: Netfilter 프레임워크를 통해 데이터 패킷을 필터링하고 필요한 경우 수정합니다[2](https://cloud.tencent.com/developer/article/1761367).
3. **NAT 지원**: 네트워크 주소 변환(NAT)을 지원하여 내부 네트워크와 외부 네트워크 간의 통신을 가능하게 합니다[2](https://cloud.tencent.com/developer/article/1761367).
4. **성능 최적화**: 효율적인 해시 테이블을 사용하여 성능을 최적화합니다[1](https://www.kernel.org/doc/html/latest/networking/nf_conntrack-sysctl.html).

이 기능은 Kubernetes, Docker 네트워크, 방화벽 등 다양한 네트워크 애플리케이션에서 중요한 역할을 합니다[2](https://cloud.tencent.com/developer/article/1761367).
