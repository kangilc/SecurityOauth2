AWS EKS 클러스터를 Kubernetes 1.30 버전으로 업그레이드할 때 주의해야 할 사항은 다음과 같습니다:

1. **사전 준비**:
   - 클러스터의 모든 노드가 컨트롤 플레인과 동일한 버전을 실행 중인지 확인합니다[1](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/update-cluster.html).
   - 런치 템플릿을 업데이트하여 클러스터와 노드의 AMI 버전을 맞춥니다[2](https://velog.io/@zionedoha/AWS-EKS-update1.28-1.29).

2. **업그레이드 과정**:
   - 클러스터를 업그레이드한 후에는 이전 버전으로 다운그레이드할 수 없습니다[1](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/update-cluster.html).
   - 클러스터의 API 서버 엔드포인트에 항상 액세스할 수 있도록 가용성 높은 Kubernetes 컨트롤 플레인을 제공하고, API 서버 인스턴스의 롤링 업데이트를 수행합니다[1](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/update-cluster.html).

3. **애플리케이션 테스트**:
   - 새 Kubernetes 버전에 대해 애플리케이션의 동작을 테스트하는 것이 중요합니다[1](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/update-cluster.html).
   - 지속적인 통합 워크플로를 구축하여 애플리케이션의 동작을 테스트합니다[1](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/update-cluster.html).

4. **네트워크 설정**:
   - 클러스터를 생성할 때 지정한 서브넷에 사용 가능한 IP 주소가 충분한지 확인합니다[1](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/update-cluster.html).
   - 필요한 클러스터 통신을 허용하는 보안 그룹 규칙이 있는지 확인합니다[1](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/update-cluster.html).

5. **업그레이드 후 확인**:
   - 클러스터와 애플리케이션이 정상적으로 동작하는지 확인합니다[2](https://velog.io/@zionedoha/AWS-EKS-update1.28-1.29).
   - 필요한 경우, 지속적인 통합 워크플로를 통해 애플리케이션의 동작을 테스트합니다[1](https://docs.aws.amazon.com/ko_kr/eks/latest/userguide/update-cluster.html).
