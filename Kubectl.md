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
Kubernetes에서 노드에 Taint가 있는지 확인하려면 `kubectl` 명령어를 사용할 수 있습니다. 다음은 노드의 Taint를 확인하는 방법입니다:

### **1. 모든 노드의 Taint 확인**
모든 노드의 Taint를 확인하려면 다음 명령어를 사용합니다:
```sh
kubectl get nodes -o json | jq '.items[] | {name: .metadata.name, taints: .spec.taints}'
```
이 명령어는 모든 노드의 이름과 Taint를 JSON 형식으로 출력합니다.

### **2. 특정 노드의 Taint 확인**
특정 노드의 Taint를 확인하려면 다음 명령어를 사용합니다:
```sh
kubectl describe node <node-name>
```
이 명령어는 해당 노드의 상세 정보를 출력하며, Taint 정보도 포함됩니다.

### **3. Taint 정보 필터링**
특정 노드의 Taint 정보만 필터링하려면 다음 명령어를 사용합니다:
```sh
kubectl get node <node-name> -o json | jq '.spec.taints'
```
이 명령어는 해당 노드의 Taint 정보를 JSON 형식으로 출력합니다.

### **예시**
```sh
kubectl describe node <node-name>
```
이 명령어를 실행하면 노드의 Taint 정보가 포함된 상세 정보를 확인할 수 있습니다.
