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

이렇게 하면 Argo CD를 설치하고 기본 설정을 완료할 수 있습니다[1](https://velog.io/@squarebird/Argo-CD-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%84%A4%EC%B9%98)[2](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment)[3](https://bing.com/search?q=Argo+CD+%ec%84%a4%ec%b9%98+%eb%b0%a9%eb%b2%95). 추가로 궁금한 점이 있으면 언제든지 물어보세요! 😊

[1](https://velog.io/@squarebird/Argo-CD-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%84%A4%EC%B9%98): [Argo CD 설치 가이드](https://velog.io/@squarebird/Argo-CD-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%84%A4%EC%B9%98)
[2](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment): [Argo CD 설치 및 설정](https://velog.io/@airoasis/ArgoCD-Kubernetes-Deployment)
[3](https://bing.com/search?q=Argo+CD+%ec%84%a4%ec%b9%98+%eb%b0%a9%eb%b2%95): [Argo CD 설치 방법](https://bing.com/search?q=Argo+CD+%ec%84%a4%ec%b9%98+%eb%b0%a9%eb%b2%95)
