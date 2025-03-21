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

물론이죠! 지금까지의 Kubernetes 관련 질문과 답변을 정리한 보고서를 준비해드릴게요. 😊

### Kubernetes 관련 질문 및 답변 보고서

#### 1. Toleration 설정 방법
- **질문**: Kubernetes에서 Toleration 설정 방법은?
- **답변**: Toleration은 특정 노드에 설정된 Taint를 파드가 용인할 수 있도록 해주는 설정입니다. 이를 통해 특정 노드에만 파드를 스케줄링할 수 있습니다. 예시:
  ```yaml
  apiVersion: v1
  kind: Pod
  metadata:
    name: my-pod
  spec:
    containers:
    - name: my-container
      image: nginx
    tolerations:
    - key: "key1"
      operator: "Equal"
      value: "value1"
      effect: "NoSchedule"
  ```

#### 2. 노드의 `cordon` 상태 해제
- **질문**: uncordon
- **답변**: 노드의 `cordon` 상태를 해제하려면 `kubectl uncordon <노드 이름>` 명령어를 사용합니다.

#### 3. 네임스페이스 삭제 후 복구 방법
- **질문**: kubectl로 namespace를 삭제 했을 경우 복구 방법
- **답변**: 네임스페이스를 삭제한 경우 직접 복구는 불가능하지만, `Terminating` 상태에서 멈춘 경우 `finalizers`를 제거하여 강제로 삭제할 수 있습니다.

#### 4. 네임스페이스와 노드의 관계
- **질문**: namespace 안에 node가 구성이 되는지?
- **답변**: 노드는 네임스페이스에 속하지 않으며, 클러스터 전체에서 관리되는 리소스입니다.

#### 5. 네임스페이스에 포함될 수 있는 서비스
- **질문**: namespace에는 어떤 서비스가 들어가는가
- **답변**: 네임스페이스에는 ClusterIP, NodePort, LoadBalancer, ExternalName 등의 서비스가 포함될 수 있습니다.

#### 6. 네임스페이스와 파드, 서비스의 관계
- **질문**: namespace와 pod service의 관계
- **답변**: 네임스페이스는 파드와 서비스를 포함하는 논리적 그룹입니다. 파드와 서비스는 네임스페이스 내에서 관리됩니다.

#### 7. 인증 문제 해결
- **질문**: couldn't get current server API group list: the server has asked for the client to provide credentials
- **답변**: 인증 문제는 Kubeconfig 파일 확인, 클러스터 컨텍스트 확인, 네트워크 연결 확인, 권한 확인 등을 통해 해결할 수 있습니다.

#### 8. 네임스페이스 삭제 후 Jenkins 재기동 오류
- **질문**: namespace 삭제 후 jenkins 재기동 오류가 생길 수 있는지
- **답변**: 네임스페이스 삭제로 인해 Jenkins 재기동 시 오류가 발생할 수 있습니다. 남아있는 리소스 확인 및 삭제, Finalizers 제거, Jenkins 설정 확인 등을 통해 해결할 수 있습니다.

#### 9. AWS EKS kubeconfig 업데이트
- **질문**: aws eks update-kubeconfig --region <region> --name <cluster_name>
- **답변**: 이 명령어는 AWS EKS 클러스터의 kubeconfig 파일을 업데이트하여 클러스터에 접근할 수 있도록 설정합니다.

#### 10. 네임스페이스 삭제로 인한 권한 문제
- **질문**: namespace 삭제로 인한 계정에 kubectl 권한이 없어짐
- **답변**: Kubeconfig 파일 확인 및 업데이트, RBAC 설정 확인, 네임스페이스 상태 확인, 클러스터 관리자에게 문의 등을 통해 해결할 수 있습니다.

#### 11. Airflow에서 이미지 pull 401 오류
- **질문**: Airflow에서 dag 실행 시 권한 문제로 이미지 pull 이 안되고 401 오류가 밸생한다. 이것도 네임스페이스 삭제의 영향인가
- **답변**: 네임스페이스 삭제로 인해 인증 정보나 권한 설정이 손상되었을 가능성이 있습니다. 이미지 레지스트리 인증 정보 확인 및 재설정, RBAC 설정 확인, 네트워크 정책 확인 등을 통해 해결할 수 있습니다.

#### 12. Docker 레지스트리 시크릿 생성 및 확인
- **질문**: docker 레지스트리 시크릿 확인 방법
- **답변**: 시크릿 목록 확인, 특정 시크릿의 세부 정보 확인, 시크릿의 데이터 디코딩 등을 통해 Docker 레지스트리 시크릿을 확인할 수 있습니다.
