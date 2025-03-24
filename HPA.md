## CPU 사용률에 따른 Pod 수량 1개 증가, Memory 사용률에 따른 Pod % 단위 증가 설정

### HPA 안정화 창 수정 방법

HPA 설정에서 `behavior` 섹션의 `scaleUp` 및 `scaleDown` 속성에 `stabilizationWindowSeconds` 값을 추가하여 안정화 창을 설정할 수 있습니다.

### 예시 설정

```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: nginx-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: nginx-deployment
  minReplicas: 2
  maxReplicas: 10
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70
  - type: Resource
    resource:
      name: memory
      target:
        type: Utilization
        averageUtilization: 80
  behavior:
    scaleUp:
      stabilizationWindowSeconds: 120  # CPU 사용률 조건의 안정화 창 설정
      policies:
      - type: Pods
        value: 1
        periodSeconds: 120
      - type: Percent
        value: 50
        periodSeconds: 240  # 메모리 사용률 조건의 시간 설정을 4분으로 변경
    scaleDown:
      stabilizationWindowSeconds: 300  # 다운스케일 안정화 창을 5분으로 설정
      policies:
      - type: Pods
        value: 1
        periodSeconds: 300
```

[1](https://kubernetes.io/ko/docs/tasks/run-application/horizontal-pod-autoscale/): [Horizontal Pod Autoscaling - Kubernetes](https://kubernetes.io/ko/docs/tasks/run-application/horizontal-pod-autoscale/)

[2](https://jennifersoft.com/ko/blog/kubernetes/2023-10-11-jennifer-kubernetes-7/): [07. 파드 오토스케일링 – Metrics-Server & HPA – 제니퍼소프트](https://jennifersoft.com/ko/blog/kubernetes/2023-10-11-jennifer-kubernetes-7/)

## Metrics-server Ready가 되지 않는 경우
```bash
kubectl get deployment metrics-server -n kube-system
```

NAME READY UP-TO-DATE AVAILABLE AGE
metrics-server 0/1 1 0 46m

Metrics Server가 정상적으로 작동하지 않는 경우, 몇 가지 일반적인 문제와 해결 방법을 확인해볼 수 있습니다:

### 1. **Metrics Server 로그 확인**
먼저, Metrics Server의 로그를 확인하여 오류 메시지를 찾습니다.
```bash
kubectl logs -n kube-system deployment/metrics-server
```

### 2. **Deployment 설정 확인**
Metrics Server의 Deployment 설정을 확인하여 필요한 인수(args)가 올바르게 설정되어 있는지 확인합니다. 예를 들어, `--kubelet-insecure-tls` 및 `--kubelet-preferred-address-types` 인수가 포함되어 있는지 확인합니다.
```bash
kubectl edit deployment metrics-server -n kube-system
```
다음과 같이 설정되어 있는지 확인합니다:
```yaml
spec:
  template:
    spec:
      containers:
      - args:
        - --cert-dir=/tmp
        - --secure-port=4443
        - --kubelet-insecure-tls
        - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
        image: k8s.gcr.io/metrics-server-amd64:v0.3.6
        name: metrics-server
```

### 3. **APIService 상태 확인**
APIService가 올바르게 등록되어 있는지 확인합니다.
```bash
kubectl get apiservice v1beta1.metrics.k8s.io -o yaml
```
APIService의 상태를 확인하여 `FailedDiscoveryCheck`와 같은 오류가 있는지 확인합니다[1](https://stackoverflow.com/questions/59969791/metrics-server-not-working-in-kubernetes-cluster).

### 4. **Network 설정 확인**
Metrics Server가 네트워크 문제로 인해 클러스터 내의 노드와 통신하지 못할 수 있습니다. `hostNetwork: true` 설정을 추가하여 해결할 수 있습니다.
```yaml
spec:
  template:
    spec:
      hostNetwork: true
      containers:
      - args:
        - --kubelet-insecure-tls
        - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
        image: k8s.gcr.io/metrics-server-amd64:v0.3.6
        name: metrics-server
```

### 5. **RBAC 설정 확인**
RBAC(Role-Based Access Control) 설정이 올바르게 되어 있는지 확인합니다. Metrics 권한을 가지고 있는지 확인합니다.
```bash
kubectl get clusterrolebinding metrics-server -o yaml
```

이 단계를 통해 Metrics Server가 정상적으로 작동하도록 설정할 수 있습니다[3](https://stackoverflow.com/questions/57137683/how-to-troubleshoot-metrics-server-on-kubeadm)[2](https://labex.io/tutorials/kubernetes-how-to-diagnose-metrics-server-problems-418973). 문제가 계속될 경우, 추가적인 로그와 설정을 공유해주시면 더 자세히 도와드릴 수 있습니다.

혹시 더 궁금한 점이 있으신가요? 😊

[1](https://stackoverflow.com/questions/59969791/metrics-server-not-working-in-kubernetes-cluster): [Metrics server not working in Kubernetes cluster](https://stackoverflow.com/questions/59969791/metrics-server-not-working-in-kubernetes-cluster)

[3](https://stackoverflow.com/questions/57137683/how-to-troubleshoot-metrics-server-on-kubeadm): [How to troubleshoot metrics-server on kubeadm?](https://stackoverflow.com/questions/57137683/how-to-troubleshoot-metrics-server-on-kubeadm)

[2](https://labex.io/tutorials/kubernetes-how-to-diagnose-metrics-server-problems-418973): [How to diagnose Metrics Server problems](https://labex.io/tutorials/kubernetes-how-to-diagnose-metrics-server-problems-418973)

```bash
status:
 conditions:
 - lastTransitionTime: "2025-03-24T06:57:04Z"
 message: endpoints for service/metrics-server in "kube-system" have no addresses
 with port name "https"
 reason: MissingEndpoints
 status: "False"
 type: Available
```

`MissingEndpoints` 오류는 Metrics Server 서비스에 연결된 엔드포인트가 없음을 나타냅니다. 이 문제를 해결하기 위해 다음 단계를 시도해보세요:

### 1. **서비스 및 엔드포인트 확인**
먼저, Metrics Server 서비스와 엔드포인트가 올바르게 설정되어 있는지 확인합니다.
```bash
kubectl get svc -n kube-system
kubectl get endpoints -n kube-system
```
서비스와 엔드포인트가 올바르게 설정되어 있는지 확인하고, 엔드포인트가 비어 있지 않은지 확인합니다.

### 2. **Metrics Server 재배포**
Metrics Server를 삭제하고 다시 배포해보세요.
```bash
kubectl delete deployment metrics-server -n kube-system
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
```

### 3. **Deployment 설정 확인**
Metrics Server의 Deployment 설정을 확인하여 필요한 인수(args)가 올바르게 설정되어 있는지 확인합니다. 예를 들어, `--kubelet-insecure-tls` 및 `--kubelet-preferred-address-types` 인수가 포함되어 있는지 확인합니다.
```bash
kubectl edit deployment metrics-server -n kube-system
```
다음과 같이 설정되어 있는지 확인합니다:
```yaml
spec:
  template:
    spec:
      containers:
      - args:
        - --cert-dir=/tmp
        - --secure-port=4443
        - --kubelet-insecure-tls
        - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
        image: k8s.gcr.io/metrics-server-amd64:v0.3.6
        name: metrics-server
```

### 4. **APIService 상태 확인**
APIService가 올바르게 등록되어 있는지 확인합니다.
```bash
kubectl get apiservice v1beta1.metrics.k8s.io -o yaml
```
APIService의 상태를 확인하여 `FailedDiscoveryCheck`와 같은 오류가 있는지 확인합니다[1](https://github.com/kubernetes-sigs/metrics-server/issues/525).

### 5. **Network 설정 확인**
Metrics Server가 네트워크 문제로 인해 클러스터 내의 노드와 통신하지 못할 수 있습니다. `hostNetwork: true` 설정을 추가하여 해결할 수 있습니다.
```yaml
spec:
       hostNetwork: true
      containers:
      - args:
        - --kubelet-insecure-tls
        - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
        image: k8s.gcr.io/metrics-server-amd64:v0.3.6
        name: metrics-server
```

이 단계를 통해 Metrics Server가 정상적으로 작동하도록 설정할 수 있습니다[2](https://stackoverflow.com/questions/57739870/how-to-enable-kube-system-metrics-server-from-status-false-missingendpoints)[3](https://discuss.kubernetes.io/t/k8s-metrics-server-has-an-error-false-faileddiscoverycheck-failing-or-missing-response-from-https-10-88-0-180-10250-apis-metrics-k8s-io-v1beta1-get-https-ip-10250-apis-metrics-k8s-io-v1beta1-dial-tcp-ip-connect-no-route/27829). 문제가 계속될 경우, 추가적인 로그와 설정을 공유해주시면 더 자세히 도와드릴 수 있습니다.

혹시 더 궁금한 점이 있으신가요? 😊

[1](https://github.com/kubernetes-sigs/metrics-server/issues/525): Metrics server not working in Kubernetes cluster

[2](https://stackoverflow.com/questions/57739870/how-to-enable-kube-system-metrics-server-from-status-false-missingendpoints): [How to troubleshoot metrics-server on kubeadm?](https://stackoverflow.com/questions/57137683/how-to-troubleshoot-metrics-server-on-kubeadm)

[3](https://discuss.kubernetes.io/t/k8s-metrics-server-has-an-error-false-faileddiscoverycheck-failing-or-missing-response-from-https-10-88-0-180-10250-apis-metrics-k8s-io-v1beta1-get-https-ip-10250-apis-metrics-k8s-io-v1beta1-dial-tcp-ip-connect-no-route/27829): How to diagnose Metrics Server problems


kube-system metrics-server log에서 다음과 같은 내용이 나왔다
scraper.go:149] "Failed to scrape node" err="Get \"https://192.168.49.2:10250/metrics/resource\": tls: failed to verify certificate: x509: cannot validate certificate for 192.168.49.2 because it doesn't contain any IP SANs" node="minikube"

이 오류는 Metrics Server가 노드의 인증서를 검증하지 못해 발생합니다. 이를 해결하기 위해 몇 가지 방법을 시도해볼 수 있습니다:

### 1. **Metrics Server 설정 수정**
Metrics Server의 설정을 수정하여 `--kubelet-insecure-tls` 인수를 추가합니다. 이 인수는 TLS 인증서를 검증하지 않도록 설정합니다.
```bash
kubectl edit deployment metrics-server -n kube-system
```
다음과 같이 설정합니다:
```yaml
spec:
  template:
    spec:
      containers:
      - args:
        - --cert-dir=/tmp
        - --secure-port=4443
        - --kubelet-insecure-tls
        - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
        image: k8s.gcr.io/metrics-server-amd64:v0.3.6
        name: metrics-server
```

### 2. **APIService 상태 확인**
APIService가 올바르게 등록되어 있는지 확인합니다.
```bash
kubectl get apiservice v1beta1.metrics.k8s.io -o yaml
```
APIService의 상태를 확인하여 `FailedDiscoveryCheck`와 같은 오류가 있는지 확인합니다[1](https://github.com/kubernetes-sigs/metrics-server/issues/1141).

### 3. **Network 설정 확인**
Metrics Server가 네트워크 문제로 인해 클러스터 내의 노드와 통신하지 못할 수 있습니다. `hostNetwork: true` 설정을 추가하여 해결할 수 있습니다.
```yaml
spec:
  template:
    spec:
      hostNetwork: true
     :
        - --kubelet-insecure-tls
        - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
        image: k8s.gcr.io/metrics-server-amd64:v0.3.6
        name: metrics-server
```

### 4. **RBAC 설정 확인**
RBAC(Role-Based Access Control) 설정이 올바르게 되어 있는지 확인합니다. Metrics 권한을 가지고 있는지 확인합니다.
```bash
kubectl get clusterrolebinding metrics-server -o yaml
```

[1](https://github.com/kubernetes-sigs/metrics-server/issues/1141): Metrics server not working in Kubernetes cluster

[2](https://github.com/kubernetes/kops/issues/14708): How to troubleshoot metrics-server on kubeadm?

[3](https://stackoverflow.com/questions/52893272/kubernetes-metrics-server-unable-to-fully-scrape-metrics): How to diagnose Metrics Server problems
