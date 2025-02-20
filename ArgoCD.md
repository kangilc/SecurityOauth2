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
[3](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment): [Argo CD 설치 및 설정](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment)
