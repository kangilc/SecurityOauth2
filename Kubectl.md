Kubernetes 클러스터를 관리할 때 유용한 `kubectl` 명령어들을 모아 보았습니다:

### **Pod 관련 명령어**
- **현재 네임스페이스의 모든 Pod 상태 확인**:
  ```sh
  kubectl get pods
  ```
- **특정 네임스페이스의 모든 Pod 상태 확인**:
  ```sh
  kubectl get pods -n <namespace>
  ```
- **Pod 상세 정보 확인**:
  ```sh
  kubectl describe pod <pod-name> -n <namespace>
  ```
- **Pod 로그 확인**:
  ```sh
  kubectl logs <pod-name> -n <namespace>
  ```
- **Pod 삭제**:
  ```sh
  kubectl delete pod <pod-name> -n <namespace>
  ```

### **네임스페이스 관련 명령어**
- **모든 네임스페이스 목록 확인**:
  ```sh
  kubectl get namespaces
  ```
- **네임스페이스 생성**:
  ```sh
  kubectl create namespace <namespace>
  ```
- **네임스페이스 삭제**:
  ```sh
  kubectl delete namespace <namespace>
  ```

### **서비스 관련 명령어**
- **모든 서비스 목록 확인**:
  ```sh
  kubectl get services
  ```
- **특정 네임스페이스의 모든 서비스 목록 확인**:
  ```sh
  kubectl get services -n <namespace>
  ```
- **서비스 상세 정보 확인**:
  ```sh
  kubectl describe service <service-name> -n <namespace>
  ```

### **디플로이먼트 관련 명령어**
- **모든 디플로이먼트 목록 확인**:
  ```sh
  kubectl get deployments
  ```
- **특정 네임스페이스의 모든 디플로이먼트 목록 확인**:
  ```sh
  kubectl get deployments -n <namespace>
  ```
- **디플로이먼트 상세 정보 확인**:
  ```sh
  kubectl describe deployment <deployment-name> -n <namespace>
  ```
- **디플로이먼트 삭제**:
  ```sh
  kubectl delete deployment <deployment-name> -n <namespace>
  ```

### **기타 유용한 명령어**
- **현재 컨텍스트 확인**:
  ```sh
  kubectl config current-context
  ```
- **컨텍스트 목록 확인**:
  ```sh
  kubectl config get-contexts
  ```
- **컨텍스트 전환**:
  ```sh
  kubectl config use-context <context-name>
  ```
- **클러스터 상태 확인**:
  ```sh
  kubectl cluster-info
  ```
