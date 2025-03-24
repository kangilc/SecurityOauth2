## CPU 사용률이 70%를 초과하고 2분간 지속될 경우 파드 수를 1대 늘리며, 메모리 사용률이 80%를 초과하고 4분간 지속될 경우 파드를 50%씩 증가시키도록

### 1. **Metrics Server 설치**
Metrics Server는 HPA가 작동하기 위해 필요한 메트릭 데이터를 수집합니다.
```bash
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
```

### 2. **네임스페이스 생성**
HPA와 Deployment를 특정 네임스페이스에 배포합니다.
```bash
kubectl create namespace my-namespace
```

### 3. **Deployment 생성**
Nginx 웹 서버를 배포하는 Deployment를 생성합니다.
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
  namespace: my-namespace
spec:
  replicas: 2
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx
        resources:
          requests:
            cpu: "100m"
            memory: "200Mi"
          limits:
            cpu: "500m"
            memory: "500Mi"
```

### 4. **HPA 설정**
CPU 사용률이 70%를 초과하고 2분간 지속될 경우 파드 수를 1대 늘리며, 메모리 사용률이 80%를 초과하고 4분간 지속될 경우 파드를 50%씩 증가시키도록 설정합니다.
```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: nginx-hpa
  namespace: my-namespace
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
      stabilizationWindowSeconds: 120
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

### 5. **HPA 배포**
HPA 설정 파일을 네임스페이스에 적용합니다.
```bash
kubectl apply -f hpa.yaml -n my-namespace
```

### 6. **HPA 상태 확인 및 테스트**
HPA가 정상적으로 작동하는지 확인합니다.
```bash
kubectl get hpa -n my-namespace
kubectl get pods -n my-namespace
```

### 7. **Metrics Server 오류 해결**
Metrics Server가 노드의 인증서를 검증하지 못해 발생하는 `tls: failed to verify certificate: x509: cannot validate certificate for 192.168.49.2 because it doesn't contain any IP SANs` 오류를 해결하기 위해 다음 단계를 시도해보세요:

1. **Metrics Server 설정 수정**
   Metrics Server의 설정을 수정하여 `--kubelet-insecure-tls` 인수를 추가합니다.
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
           - --kubelet-insecure-tls  # TLS 인증서 검증을 비활성화
           - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
           image: k8s.gcr.io/metrics-server-amd64:v0.3.6
           name: metrics-server
   ```

2. **APIService 상태 확인**
   APIService가 올바르게 등록되어 있는지 확인합니다.
   ```bash
   kubectl get apiservice v1beta1.metrics.k8s.io -o yaml
   ```
   APIService의 상태를 확인하여 `FailedDiscoveryCheck`와 같은 오류가 있는지 확인합니다.

3. **Network 설정 확인**
   Metrics Server가 네트워크 문제로 인해 클러스터 내의 노드와 통신하지 못할 수 있습니다. `hostNetwork: true` 설정을 추가하여 해결할 수 있습니다.
   ```yaml
   spec:
     template:
       spec:
         hostNetwork: true  # Metrics Server가 호스트 네트워크를 사용하도록 설정
         containers:
         - args:
           - --kubelet-insecure-tls
           - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
           image: k8s.gcr.io/metrics-server-amd64:v0.3.6
           name: metrics-server
   ```

이 단계를 통해 Metrics Server가 정상적으로 작동하도록 설정할 수 있습니다.
