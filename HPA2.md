## CPU 사용률이 70%를 초과하고 2분간 지속될 경우 파드 수를 1대 늘리며, 메모리 사용률이 80%를 초과하고 4분간 지속될 경우 파드를 50%씩 증가시키도록

### 1. **Metrics Server 설치**
Metrics Server는 HPA가 작동하기 위해 필요한 메트릭 데이터를 수집합니다.
```bash
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
```
배포 상태 확인
```bash
kubectl get deployment metrics-server -n kube-system
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

## 쿠버네티스 클러스터에서 부하 테스트

쿠버네티스 클러스터에서 부하 테스트를 수행하는 방법을 알려드릴게요. 부하 테스트를 통해 HPA 설정이 제대로 작동하는지 확인할 수 있습니다. 여기서는 Apache HTTP 서버 프로젝트에서 제공하는 `ab`(Apache Benchmark) 도구를 사용하여 부하 테스트를 진행하는 방법을 설명하겠습니다.

### 부하 테스트 단계

1. **Apache Benchmark 설치**
   먼저, `ab` 도구를 설치합니다. 대부분의 시스템에서는 `apache2-utils` 패키지에 포함되어 있습니다.
   ```bash
   sudo apt-get install apache2-utils
   ```

2. **부하 테스트 명령어**
   `ab` 명령어를 사용하여 부하 테스트를 수행합니다. 예를 들어, 한 번에 10개의 동시 연결을 사용하여 60초 동안 테스트를 진행합니다.
   ```bash
   ab -c 10 -t 60 http://<IP주소:포트번호>/
   ```
   여기서:
   - `-c 10`: 한 번에 10개의 동시 연결을 사용합니다.
   - `-t 60`: 60초 동안 테스트를 진행합니다.
   - `http://<IP주소:포트번호>/`: 테스트할 서비스의 URL을 입력합니다.

3. **부하 테스트 결과 확인**
   부하 테스트 결과를 확인하여 HPA가 정상적으로 작동하는지 확인합니다. 파드 수가 증가하는지 확인하려면 다음 명령어를 사용합니다.
   ```bash
   kubectl get hpa -n my-namespace
   ```
   ```bash
   kubectl get pods -n my-namespace
   ```

### 예시
```bash
ab -c 10 -t 60 http://192.168.49.2:80/
```

[1](https://m.blog.naver.com/ziippy/222089202921): [쿠버네티스 시작하기, Apache HTTP 부하테스트하기](https://imhamburger.tistory.com/95)

[2](https://m.blog.naver.com/mantechbiz/221468682766): [Kubernetes와 JMeter를 사용한 부하테스트](https://m.blog.naver.com/mantechbiz/221468682766)
