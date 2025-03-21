# ArgoCD
Argo CD를 설치하는 방법은 여러 가지가 있지만, 여기서는 가장 일반적인 방법인 `kubectl` 명령어를 사용한 설치 방법을 설명드릴게요.

### Argo CD 설치 단계

1. **네임스페이스 생성**:
   먼저, Argo CD를 배포할 네임스페이스를 생성합니다.
   ```bash
   kubectl create namespace argocd
   ```

2. **Argo CD 설치**:
   공식 Argo CD 설치 파일을 사용하여 Argo CD를 설치합니다.
   ```bash
   kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
   ```

3. **Pod 상태 확인**:
   Argo CD의 Pod들이 정상적으로 생성되었는지 확인합니다.
   ```bash
   kubectl get pods -n argocd
   ```

4. **서비스 노출**:
   Argo CD 서버를 외부에 노출시키기 위해 서비스 타입을 `LoadBalancer`로 변경합니다.
   ```bash
   kubectl patch svc argocd-server -n argocd -p '{"spec": {"type": "LoadBalancer"}}'
   ```

5. **초기 관리자 비밀번호 확인**:
   초기 관리자 계정의 비밀번호를 확인합니다.
   ```bash
   kubectl get secret argocd-initial-admin-secret -n argocd -o jsonpath="{.data.password}" | base64 -d
   ```

6. **Argo CD UI 접속**:
   웹 브라우저에서 Argo CD UI에 접속합니다. 기본 URL은 `http://<ARGOCD_SERVER_IP>`입니다. 로그인 시 사용자 이름은 `admin`이며, 비밀번호는 이전 단계에서 확인한 비밀번호를 사용합니다.

이렇게 하면 Argo CD를 설치하고 기본 설정을 완료할 수 있습니다[1](https://velog.io/@squarebird/Argo-CD-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%84%A4%EC%B9%98)[2](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment)[3](https://bing.com/search?q=Argo+CD+%ec%84%a4%ec%b9%98+%eb%b0%a9%eb%b2%95).

[1](https://velog.io/@squarebird/Argo-CD-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%84%A4%EC%B9%98): [Argo CD 설치 가이드](https://velog.io/@squarebird/Argo-CD-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%84%A4%EC%B9%98)
[2](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment): [Argo CD 설치 및 설정](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment)
[3](https://bing.com/search?q=Argo+CD+%ec%84%a4%ec%b9%98+%eb%b0%a9%eb%b2%95): [Argo CD 설치 방법](https://bing.com/search?q=Argo+CD+%ec%84%a4%ec%b9%98+%eb%b0%a9%eb%b2%95)

Argo CD 설치 후 초기 설정 방법을 안내해드릴게요.

### 초기 설정 단계

1. **Argo CD CLI 설치**:
   Argo CD CLI를 설치하여 명령줄에서 Argo CD를 관리할 수 있습니다.
   ```bash
   curl -sSL -o /usr/local/bin/argocd https://github.com/argoproj/argo-cd/releases/latest/download/argocd-linux-amd64
   chmod +x /usr/local/bin/argocd
   ```

2. **Argo CD 서버에 로그인**:
   Argo CD 서버에 로그인합니다. 먼저, Argo CD 서버의 외부 IP 주소를 확인합니다.
   ```bash
   kubectl get svc argocd-server -n argocd
   ```
   그런 다음, 로그인 명령어를 사용합니다.
   ```bash
   argocd login <ARGOCD_SERVER_IP>
   ```
   기본 사용자 이름은 `admin`이며, 비밀번호는 설치 시 확인한 초기 비밀번호입니다.

3. **초기 관리자 비밀번호 변경**:
   보안을 위해 초기 관리자 비밀번호를 변경합니다.
   ```bash
   argocd account update-password
   ```

4. **애플리케이션 등록**:
   Git 리포지토리에 있는 애플리케이션을 Argo CD에 등록합니다.
   ```bash
   argocd app create <APP_NAME> \
   --repo <REPO_URL> \
   --path <APP_PATH> \
   --dest-server https://kubernetes.default.svc \
   --dest-namespace <NAMESPACE>
   ```

5. **애플리케이션 동기화**:
   등록된 애플리케이션을 동기화하여 Kubernetes 클러스터에 배포합니다.
   ```bash
   argocd app sync <APP_NAME>
   ```

6. **웹 UI 설정**:
   웹 브라우저에서 Argo CD UI에 접속하여 애플리케이션 상태를 모니터링하고 관리할 수 있습니다. 기본 URL은 `http://<ARGOCD_SERVER_IP>`입니다.

이렇게 하면 Argo CD의 초기 설정을 완료할 수 있습니다[1](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment)[2](https://blog.pages.kr/3061)[3](https://bing.com/search?q=Argo+CD+%ec%b4%88%ea%b8%b0+%ec%84%a4%ec%a0%95+%eb%b0%a9%eb%b2%95). 

[1](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment): [Argo CD 설치 및 설정](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment)
[2](https://blog.pages.kr/3061): [Argo CD 사용자 계정 및 기본 설정](https://blog.pages.kr/3061)
[3](https://bing.com/search?q=Argo+CD+%ec%b4%88%ea%b8%b0+%ec%84%a4%ec%a0%95+%eb%b0%a9%eb%b2%95): [Argo CD 설치 방법](https://bing.com/search?q=Argo+CD+%ec%b4%88%ea%b8%b0+%ec%84%a4%ec%a0%95+%eb%b0%a9%eb%b2%95)

Argo CD의 보안 설정은 매우 중요합니다. 여기 몇 가지 주요 보안 설정 방법을 안내해드릴게요.

### 1. Role-Based Access Control (RBAC) 설정
RBAC를 사용하여 사용자와 그룹에 대한 권한을 세밀하게 관리할 수 있습니다. Argo CD는 Kubernetes의 RBAC를 활용하여 접근 제어를 설정합니다.

- **ClusterRole 및 Role 생성**:
  ```yaml
  apiVersion: rbac.authorization.k8s.io/v1
  kind: ClusterRole
  metadata:
    name: argocd-admin
  rules:
    - apiGroups: ["*"]
      resources: ["*"]
      verbs: ["*"]
  ```

- **RoleBinding 및 ClusterRoleBinding 생성**:
  ```yaml
  apiVersion: rbac.authorization.k8s.io/v1
  kind: ClusterRoleBinding
  metadata:
    name: argocd-admin-binding
  subjects:
    - kind: User
      name: <USERNAME>
      apiGroup: rbac.authorization.k8s.io
  roleRef:
    kind: ClusterRole
    name: argocd-admin
    apiGroup: rbac.authorization.k8s.io
  ```

### 2. 외부 인증 제공자 통합
LDAP, SAML, OIDC 등 외부 인증 제공자를 사용하여 Argo CD에 대한 접근을 관리할 수 있습니다. 이를 통해 조직의 기존 인증 시스템과 통합할 수 있습니다.

- **OIDC 설정 예시**:
  ```yaml
  apiVersion: v1
  kind: ConfigMap
  metadata:
    name: argocd-cm
    namespace: argocd
  data:
    oidc.config: |
      name: Okta
      issuer: https://<OKTA_DOMAIN>/oauth2/default
      clientID: <CLIENT_ID>
      clientSecret: $oidc.okta.clientSecret
      requestedScopes: ["openid", "profile", "email"]
  ```

### 3. TLS 설정
TLS를 사용하여 Argo CD 서버와 클라이언트 간의 통신을 암호화합니다.

- **TLS 인증서 설정**:
  ```yaml
  apiVersion: v1
  kind: Secret
  metadata:
    name: argocd-tls
    namespace: argocd
  type: kubernetes.io/tls
  data:
    tls.crt: <BASE64_ENCODED_CERT>
    tls.key: <BASE64_ENCODED_KEY>
  ```

### 4. 네트워크 정책 설정
네트워크 정책을 사용하여 Argo CD의 네트워크 트래픽을 제어합니다.

- **네트워크 정책 예시**:
  ```yaml
  apiVersion: networking.k8s.io/v1
  kind: NetworkPolicy
  metadata:
    name: argocd-network-policy
    namespace: argocd
  spec:
    podSelector:
      matchLabels:
        app: argocd
    policyTypes:
      - Ingress
      - Egress
    ingress:
      - from:
          - podSelector:
              matchLabels:
                app: argocd
    egress:
      - to:
          - podSelector:
              matchLabels:
                app: argocd
  ```

이러한 설정을 통해 Argo CD의 보안을 강화할 수 있습니다[1](https://blog.pages.kr/3061)[2](https://www.sktenterprise.com/bizInsight/blogDetail/dev/2606)[3](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment). 

[1](https://blog.pages.kr/3061): [Argo CD 사용자 계정 및 기본 설정](https://blog.pages.kr/3061)
[2](https://www.sktenterprise.com/bizInsight/blogDetail/dev/2606): [AWS EKS-연재8: Argo CD 설치 및 설정](https://www.sktenterprise.com/bizInsight/blogDetail/dev/2606)

### Filebeat와 Kibana를 통합

1. **Elasticsearch 설정**: Filebeat가 수집한 로그 데이터를 Elasticsearch에 전송하도록 설정합니다. Filebeat 설정 파일(`filebeat.yml`)에서 `output.elasticsearch` 섹션을 구성합니다.
    ```yaml
    output.elasticsearch:
      hosts: ["http://localhost:9200"]
    ```

2. **Kibana 설정**: Kibana가 Elasticsearch 데이터를 시각화할 수 있도록 설정합니다. Kibana 설정 파일(`kibana.yml`)에서 `elasticsearch.hosts`를 지정합니다.
    ```yaml
    elasticsearch.hosts: ["http://localhost:9200"]
    ```

3. **Filebeat 시작**: Filebeat를 시작하여 로그 데이터를 수집하고 Elasticsearch로 전송합니다.
    ```bash
    sudo service filebeat start
    ```

4. **Kibana에서 데이터 확인**: Kibana 웹 인터페이스에 접속하여 `Discover` 탭에서 Filebeat가 전송한 로그 데이터를 확인합니다.

이 과정을 통해 Filebeat와 Kibana를 통합하여 로그 데이터를 시각화할 수 있습니다¹(https://www.elastic.co/kr/blog/whats-new-elasticsearch-kibana-cloud-7-16-0)²(https://pyromaniac.me/entry/Kibana-%EC%82%AC%EC%9A%A9%EB%B2%95-2-ELK-%ED%86%B5%ED%95%A9-%EC%97%B0%EB%8F%99-%EB%B0%8F-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%B6%84%EC%84%9D).

¹(https://www.elastic.co/kr/blog/whats-new-elasticsearch-kibana-cloud-7-16-0): [Elastic Blog](https://www.elastic.co/kr/blog/whats-new-elasticsearch-kibana-cloud-7-16-0)
²(https://pyromaniac.me/entry/Kibana-%EC%82%AC%EC%9A%A9%EB%B2%95-2-ELK-%ED%86%B5%ED%95%A9-%EC%97%B0%EB%8F%99-%EB%B0%8F-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%B6%84%EC%84%9D): [Pyromaniac Blog](https://pyromaniac.me/entry/Kibana-%EC%82%AC%EC%9A%A9%EB%B2%95-2-ELK-%ED%86%B5%ED%95%A9-%EC%97%B0%EB%8F%99-%EB%B0%8F-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%B6%84%EC%84%9D)

원본: Copilot과의 대화, 2025. 2. 20.
(1) Elasticsearch, Kibana, Elastic Cloud 7.16: 통합 및 실행 가능한 인사이트. https://www.elastic.co/kr/blog/whats-new-elasticsearch-kibana-cloud-7-16-0.
(2) Kibana 사용법 #2 (ELK 통합 연동 및 데이터 분석). https://pyromaniac.me/entry/Kibana-%EC%82%AC%EC%9A%A9%EB%B2%95-2-ELK-%ED%86%B5%ED%95%A9-%EC%97%B0%EB%8F%99-%EB%B0%8F-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%B6%84%EC%84%9D.
[3](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment): [Argo CD 설치 및 설정](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment)

## 아르고 시디에서 파드를 늘리는 방법을 알고 싶어
Argo CD를 사용하여 파드를 늘리는 방법은 GitOps 방식을 통해 Kubernetes 리소스를 선언적으로 관리하는 것입니다. 이를 위해 Deployment 매니페스트 파일을 수정하여 파드 수를 조정할 수 있습니다. 다음은 그 과정에 대한 예시입니다:

### 1. Deployment 매니페스트 파일 수정
먼저, Git 리포지토리에 있는 Deployment 매니페스트 파일을 수정하여 `replicas` 값을 원하는 파드 수로 변경합니다. 예를 들어, 파드 수를 10개로 늘리려면 다음과 같이 수정합니다:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: example-deployment
spec:
  replicas: 10  # 원하는 파드 수
  selector:
    matchLabels:
      app: example
  template:
    metadata:
      labels:
        app: example
    spec:
      containers:
      - name: example-container
        image: nginx:1.14.2
        ports:
        - containerPort: 80
```

### 2. Git 리포지토리에 변경 사항 커밋
수정한 매니페스트 파일을 Git 리포지토리에 커밋하고 푸시합니다:

```sh
git add deployment.yaml
git commit -m "Increase replicas to 10"
git push origin main
```

### 3. Argo CD에서 동기화
Argo CD는 Git 리포지토리의 변경 사항을 감지하고 Kubernetes 클러스터에 적용합니다. Argo CD UI 또는 CLI를 사용하여 애플리케이션을 동기화할 수 있습니다.

#### Argo CD UI에서 동기화
1. Argo CD UI에 로그인합니다.
2. 변경된 애플리케이션을 선택합니다.
3. "Sync" 버튼을 클릭하여 변경 사항을 동기화합니다.

#### Argo CD CLI에서 동기화
Argo CD CLI를 사용하여 동기화할 수도 있습니다:
```sh
argocd app sync example-app
```

이 과정을 통해 Argo CD가 Git 리포지토리의 변경 사항을 Kubernetes 클러스터에 적용하여 파드 수를 늘리게 됩니다¹(https://velog.io/@squarebird/Argo-CD-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%84%A4%EC%B9%98)²(https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment).

¹(https://velog.io/@squarebird/Argo-CD-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%84%A4%EC%B9%98): [Argo CD 개념 및 설치 - 벨로그](https://velog.io/@squarebird/Argo-CD-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%84%A4%EC%B9%98)
²(https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment): [Argo CD 설치 및 설정 - 벨로그](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment)

원본: Copilot과의 대화, 2025. 2. 24.
(1) Argo CD 개념 및 설치 - 벨로그. https://velog.io/@squarebird/Argo-CD-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%84%A4%EC%B9%98.
(2) Argo CD 설치 및 설정 - 벨로그. https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment.

이 문제는 여러 가지 원인으로 발생할 수 있습니다. 다음 해결 방법들을 시도해 보세요:

1. **비밀번호 재확인**:
   - 비밀번호를 다시 한 번 확인해 보세요. 비밀번호를 복구할 때 사용한 명령어가 정확한지 확인합니다:
     ```bash
     kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d
     ```

2. **Pod 이름 확인**:
   - 비밀번호가 초기 관리자 비밀번호가 아닌 경우, ArgoCD 서버 Pod 이름을 사용해 보세요:
     ```bash
     kubectl exec -n argocd argocd-server-<pod-name> -- argocd admin initial-password
     ```

3. **비밀번호 재설정**:
   - 비밀번호를 재설정하는 방법도 있습니다. 다음 명령어를 사용하여 비밀번호를 재설정해 보세요:
     ```bash
     kubectl -n argocd patch secret argocd-secret -p '{"stringData": { "admin.password": "<new-password>", "admin.passwordMtime": "'$(date +%FT%T%Z)'" }}'
     ```

4. **로그인 시도**:
   - 사용자 이름을 `admin`으로 설정하고, 복구한 비밀번호를 사용하여 로그인해 보세요
   -
   - [1](https://www.unixcloudfusion.in/2023/04/solved-argocd-invalid-username-and.html)[2](https://stackoverflow.com/questions/68297354/what-is-the-default-password-of-argocd).

### argocd account update-password FATA[0000] Argo CD server address unspecified

`Argo CD server address unspecified` 오류는 ArgoCD 서버 주소를 지정하지 않았기 때문에 발생합니다. 다음 방법들을 시도해 보세요:

1. **서버 주소 지정**:
   - `argocd login` 명령어를 사용하여 ArgoCD 서버에 로그인할 때 서버 주소를 지정합니다:
     ```bash
     argocd login <ARGOCD_SERVER_ADDRESS>
     ```

2. **환경 변수 설정**:
   - 환경 변수를 설정하여 서버 주소를 지정할 수 있습니다:
     ```bash
     export ARGOCD_SERVER=<ARGOCD_SERVER_ADDRESS>
     ```

3. **명령어에 서버 주소 포함**:
   - `argocd account update-password` 명령어에 서버 주소를 포함하여 실행합니다:
     ```bash
     argocd account update-password --server <ARGOCD_SERVER_ADDRESS>
     ```

[1](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment)
[2](https://argo-cd.readthedocs.io/en/stable/operator-manual/server-commands/argocd-server/)
[3](https://argo-cd.readthedocs.io/en/release-3.0/user-guide/environment-variables/).
