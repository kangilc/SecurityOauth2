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

Kubernetes에서 Taint는 특정 노드에 Pod가 배치되지 않도록 하는 메커니즘입니다. Taint는 노드에 적용되며, 해당 노드에 특정 조건을 만족하는 Pod만 배치될 수 있도록 합니다. Taint와 Toleration을 함께 사용하여 노드에 대한 배치 정책을 세밀하게 조정할 수 있습니다.

### **Taint의 주요 요소**
1. **Key**: Taint의 이름을 지정합니다.
2. **Value**: Taint의 값을 지정합니다.
3. **Effect**: Taint가 Pod에 미치는 영향을 지정합니다. 주요 효과는 다음과 같습니다:
   - **NoSchedule**: Taint가 있는 노드에 Pod를 배치하지 않습니다.
   - **PreferNoSchedule**: 가능하면 Taint가 있는 노드에 Pod를 배치하지 않습니다.
   - **NoExecute**: Taint가 있는 노드에 이미 배치된 Pod를 퇴출시키고, 새로운 Pod를 배치하지 않습니다.

### **Taint 추가 방법**
노드에 Taint를 추가하려면 다음 명령어를 사용합니다:
```sh
kubectl taint nodes <node-name> key=value:effect
```
예를 들어, `NoSchedule` 효과를 가진 Taint를 추가하려면 다음과 같이 합니다:
```sh
kubectl taint nodes <node-name> dedicated=tvservice:NoSchedule
```

### **Taint 제거 방법**
노드에서 Taint를 제거하려면 다음 명령어를 사용합니다:
```sh
kubectl taint nodes <node-name> key=value:effect-
```
예를 들어, `NoSchedule` 효과를 가진 Taint를 제거하려면 다음과 같이 합니다:
```sh
kubectl taint nodes <node-name> dedicated=tvservice:NoSchedule-
```

### **Toleration**
Pod가 특정 Taint를 가진 노드에 배치될 수 있도록 하려면 Toleration을 설정해야 합니다. 예를 들어, `TVService`라는 키를 가진 Taint를 허용하는 Toleration을 설정하려면 다음과 같이 합니다:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-pod
spec:
  tolerations:
  - key: "TVService"
    operator: "Equal"
    value: "true"
    effect: "NoSchedule"
```

Taint와 Toleration을 사용하면 Kubernetes 클러스터에서 리소스 배치를 세밀하게 조정할 수 있습니다.
