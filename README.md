# SecurityOauth2
쿠버네티스 클러스터는 컨테이너화된 애플리케이션을 실행하는 컴퓨팅 노드들의 집합입니다. 클러스터는 주로 **컨트롤 플레인(Control Plane)**과 **노드(Node)**로 구성됩니다. 각 구성 요소는 클러스터의 상태를 관리하고 애플리케이션을 실행하는 데 중요한 역할을 합니다.

### 클러스터 구성 요소

1. **컨트롤 플레인(Control Plane)**:
   - **API 서버(kube-apiserver)**: 클러스터의 모든 구성 요소들이 상호 통신할 수 있도록 API를 제공합니다.
   - **etcd**: 클러스터의 모든 데이터를 저장하는 분산 키-값 데이터베이스입니다.
   - **스케줄러(kube-scheduler)**: 새로운 포드를 클러스터 내의 적절한 노드에 배치합니다.
   - **컨트롤러 매니저(kube-controller-manager)**: 클러스터의 상태를 유지하고 관리하는 다양한 컨트롤러를 실행합니다[1](https://seongjin.me/kubernetes-cluster-components/).

2. **노드(Node)**:
   - **kubelet**: 각 노드에서 실행되며, 컨테이너가 정상적으로 동작하도록 관리합니다.
   - **kube-proxy**: 네트워크 프록시와 로드 밸런서를 제공하여 포드 간의 통신을 가능하게 합니다.
   - **컨테이너 런타임**: 컨테이너를 실행하는 소프트웨어입니다. Docker, containerd 등이 있습니다[2](https://aws.amazon.com/ko/what-is/kubernetes-cluster/).

### 예시

#### 클러스터 설정 예시
다음은 kubeadm을 사용하여 쿠버네티스 클러스터를 설정하는 예시입니다:

1. **kubeadm, kubelet, kubectl 설치**:
   ```bash
   sudo apt-get update && sudo apt-get install -y apt-transport-https curl
   curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
   sudo apt-add-repository "deb http://apt.kubernetes.io/ kubernetes-xenial main"
   sudo apt-get update
   sudo apt-get install -y kubelet kubeadm kubectl
   sudo apt-mark hold kubelet kubeadm kubectl
   ```

2. **클러스터 초기화**:
   ```bash
   sudo kubeadm init --pod-network-cidr=192.168.0.0/16
   ```

3. **kubectl 설정**:
   ```bash
   mkdir -p $HOME/.kube
   sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
   sudo chown $(id -u):$(id -g) $HOME/.kube/config
   ```

4. **네트워크 플러그인 설치** (예: Calico):
   ```bash
   kubectl apply -f https://docs.projectcalico.org/v3.14/manifests/calico.yaml
   ```

5. **워커 노드 조인**:
   마스터 노드에서 출력된 `kubeadm join` 명령을 각 워커 노드에서 실행합니다.

이렇게 설정된 클러스터는 다양한 애플리케이션을 실행하고 관리할 수 있습니다. 클러스터의 각 구성 요소는 상호 작용하여 안정적이고 확장 가능한 환경을 제공합니다.

더 궁금한 점이 있으면 언제든지 질문해 주세요! 😊

[1](https://seongjin.me/kubernetes-cluster-components/): https://seongjin.me/kubernetes-cluster-components/
[2](https://aws.amazon.com/ko/what-is/kubernetes-cluster/): https://aws.amazon.com/ko/what-is/kubernetes-cluster/


### 포드(Pod)
포드는 쿠버네티스에서 가장 작은 배포 단위로, 하나 이상의 컨테이너를 포함합니다. 포드는 동일한 네트워크 네임스페이스를 공유하며, 같은 IP 주소와 포트를 사용합니다. 예를 들어, 웹 서버와 데이터베이스 컨테이너를 포함하는 포드는 다음과 같이 정의할 수 있습니다:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: web-server
spec:
  containers:
  - name: web
    image: nginx
  - name: db
    image: mysql
```
여기서 `nginx`는 웹 서버를, `mysql`은 데이터베이스를 나타냅니다. 두 컨테이너는 같은 포드 내에서 실행되며, 서로 간의 통신이 가능합니다.

### 네임스페이스(Namespace)
네임스페이스는 클러스터 내에서 리소스를 분리하고 관리하기 위한 논리적 구획입니다. 여러 팀이나 프로젝트가 동일한 클러스터를 사용할 때 유용합니다. 예를 들어, 개발 환경과 프로덕션 환경을 분리하는 네임스페이스는 다음과 같이 정의할 수 있습니다:
```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: development
```
이렇게 하면 `development` 네임스페이스 내에서 리소스를 관리할 수 있습니다.

### 디플로이먼트(Deployment)
디플로이먼트는 애플리케이션의 선언적 업데이트를 관리하는 쿠버네티스 객체입니다. 디플로이먼트를 사용하면 애플리케이션을 배포하고, 업데이트하고, 롤백할 수 있습니다. 예를 들어, 3개의 복제본을 가진 Nginx 웹 서버 디플로이먼트는 다음과 같이 정의할 수 있습니다:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  replicas: 3
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
        image: nginx:1.14.2
        ports:
        - containerPort: 80
```
여기서 `replicas: 3`은 3개의 Nginx 인스턴스를 실행하도록 지정합니다.

### 서비스(Service)
서비스는 포드의 집합에 대한 네트워크 접근을 제공하는 추상화입니다. 서비스는 클러스터 내에서 안정적인 IP 주소와 DNS 이름을 제공합니다. 예를 들어, 포드에 접근할 수 있는 클러스터 IP 서비스를 정의하는 방법은 다음과 같습니다:
```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-service
spec:
  selector:
    app: MyApp
  ports:
    - protocol: TCP
      port: 80
      targetPort: 9376
```
여기서 `selector`는 `app: MyApp` 레이블을 가진 포드를 선택하고, `port: 80`은 외부에서 접근할 수 있는 포트를 지정합니다.

### 컨피그맵(ConfigMap)
컨피그맵은 애플리케이션 설정 데이터를 저장하고 관리하는 객체입니다. 컨피그맵을 사용하면 애플리케이션의 설정을 코드와 분리할 수 있습니다. 예를 들어, 애플리케이션 설정을 저장하는 컨피그맵은 다음과 같이 정의할 수 있습니다:
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: my-config
data:
  database_url: "jdbc:mysql://db.example.com:3306/mydatabase"
```
여기서 `database_url`은 데이터베이스 연결 문자열을 나타냅니다.

### 시크릿(Secret)
시크릿은 비밀번호, 토큰, SSH 키와 같은 민감한 데이터를 저장하고 관리하는 객체입니다. 시크릿은 암호화되어 저장됩니다. 예를 들어, 베이직 인증 자격 증명을 저장하는 시크릿은 다음과 같이 정의할 수 있습니다:
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: my-secret
type: Opaque
data:
  username: YWRtaW4=
  password: MWYyZDFlMmU2N2Rm
```
여기서 `username`과 `password`는 Base64로 인코딩된 값입니다.

### 인그레스(Ingress)
인그레스는 클러스터 외부에서 내부 서비스로 HTTP 및 HTTPS 트래픽을 라우팅하는 규칙을 정의하는 객체입니다. 인그레스를 사용하면 외부에서 클러스터 내의 서비스에 접근할 수 있습니다. 예를 들어, 외부 트래픽을 내부 서비스로 라우팅하는 인그레스는 다음과 같이 정의할 수 있습니다:
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
spec:
  rules:
  - host: example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: example-service
            port:
              number: 80
```
여기서 `host: example.com`은 외부 도메인 이름을, `service: example-service`는 내부 서비스 이름을 나타냅니다.

### 노드(Node)
노드는 쿠버네티스 클러스터에서 애플리케이션을 실행하는 물리적 또는 가상 머신입니다. 각 노드는 kubelet, kube-proxy, 컨테이너 런타임을 실행합니다. 예를 들어, 클러스터의 워커 노드로 등록된 가상 머신은 다음과 같이 정의할 수 있습니다:
```yaml
apiVersion: v1
kind: Node
metadata:
  name: worker-node-1
```
여기서 `worker-node-1`은 노드의 이름을 나타냅니다.

### kubelet
kubelet은 각 노드에서 실행되며, 컨테이너가 정상적으로 실행되도록 관리하는 에이전트입니다. kubelet은 API 서버와 통신하여 포드의 상태를 보고합니다. 예를 들어, kubelet을 실행하는 명령은 다음과 같습니다:
```bash
kubelet --config /etc/kubernetes/kubelet-config.yaml
```
여기서 `--config`는 kubelet 설정 파일의 경로를 지정합니다.

이 파일은 JSON 또는 YAML 형식으로 작성되며, 다양한 파라미터를 설정할 수 있습니다. 아래는 kubelet 설정 파일의 예시입니다:

```yaml
apiVersion: kubelet.config.k8s.io/v1beta1
kind: KubeletConfiguration
address: "192.168.0.8"
port: 20250
serializeImagePulls: false
evictionHard:
  memory.available: "200Mi"
```

### 주요 파라미터 설명
- **apiVersion**: 설정 파일의 API 버전을 지정합니다. 여기서는 `kubelet.config.k8s.io/v1beta1`을 사용합니다.
- **kind**: 설정 파일의 종류를 지정합니다. 여기서는 `KubeletConfiguration`입니다.
- **address**: kubelet이 바인딩할 IP 주소를 지정합니다. 예시에서는 `192.168.0.8`입니다.
- **port**: kubelet이 수신할 포트를 지정합니다. 예시에서는 `20250`입니다.
- **serializeImagePulls**: 이미지를 병렬로 가져올지 여부를 지정합니다. `false`로 설정하면 병렬로 이미지를 가져옵니다.
- **evictionHard**: 리소스 부족 시 포드를 축출하는 조건을 지정합니다. 예시에서는 사용 가능한 메모리가 `200Mi` 이하로 떨어지면 포드를 축출합니다[1](https://kubernetes.io/ko/docs/tasks/administer-cluster/kubelet-config-file/).

이 설정 파일을 사용하여 kubelet을 시작하려면 `--config` 플래그를 사용하여 설정 파일의 경로를 지정합니다:
```bash
kubelet --config /path/to/kubelet-config.yaml
```

이렇게 하면 kubelet이 설정 파일에서 구성을 불러옵니다. 커맨드 라인 플래그와 설정 파일의 값이 충돌할 경우, 커맨드 라인 플래그가 우선합니다[1](https://kubernetes.io/ko/docs/tasks/administer-cluster/kubelet-config-file/).

[1](https://kubernetes.io/ko/docs/tasks/administer-cluster/kubelet-config-file/): https://kubernetes.io/ko/docs/tasks/administer-cluster/kubelet-config-file/

### kube-proxy
kube-proxy는 각 노드에서 실행되며, 네트워크 프록시와 로드 밸런서를 제공합니다. kube-proxy는 클러스터 내의 네트워크 규칙을 관리하여 포드 간의 통신을 가능하게 합니다. 예를 들어, kube-proxy를 실행하는 명령은 다음과 같습니다:
```bash
kube-proxy --config /var/lib/kube-proxy/config.conf
```
여기서 `--config`는 kube-proxy 설정 파일의 경로를 지정합니다.

proxy 설정 파일의 예시입니다:

```yaml
apiVersion: kubeproxy.config.k8s.io/v1alpha1
kind: KubeProxyConfiguration
bindAddress: "0.0.0.0"
clientConnection:
  kubeconfig: "/var/lib/kube-proxy/kubeconfig.conf"
mode: "iptables"
clusterCIDR: "192.168.0.0/16"
```

### 주요 파라미터 설명
- **apiVersion**: 설정 파일의 API 버전을 지정합니다. 여기서는 `kubeproxy.config.k8s.io/v1alpha1`을 사용합니다.
- **kind**: 설정 파일의 종류를 지정합니다. 여기서는 `KubeProxyConfiguration`입니다.
- **bindAddress**: kube-proxy가 바인딩할 IP 주소를 지정합니다. 예시에서는 `0.0.0.0`으로 모든 인터페이스에서 수신합니다.
- **clientConnection**: kube-proxy가 API 서버와 통신하기 위한 kubeconfig 파일의 경로를 지정합니다.
- **mode**: 트래픽을 처리하는 모드를 지정합니다. 예시에서는 `iptables` 모드를 사용합니다.
- **clusterCIDR**: 클러스터 내의 파드 네트워크 CIDR 범위를 지정합니다. 예시에서는 `192.168.0.0/16`을 사용합니다[1](https://kubernetes.io/ko/docs/reference/command-line-tools-reference/kube-proxy/).

이 설정 파일을 사용하여 kube-proxy를 시작하려면 `--config` 플래그를 사용하여 설정 파일의 경로를 지정합니다:
```bash
kube-proxy --config /path/to/kube-proxy-config.yaml
```

이렇게 하면 kube-proxy가 설정 파일에서 구성을 불러옵니다. 커맨드 라인 플래그와 설정 파일의 값이 충돌할 경우, 커맨드 라인 플래그가 우선합니다[1](https://kubernetes.io/ko/docs/reference/command-line-tools-reference/kube-proxy/).

[1](https://kubernetes.io/ko/docs/reference/command-line-tools-reference/kube-proxy/): https://kubernetes.io/ko/docs/reference/command-line-tools-reference/kube-proxy/

kube-proxy 설정 파일을 적용하는 방법을 단계별로 설명해드릴게요:

### 1. 설정 파일 작성
먼저, kube-proxy 설정 파일을 작성합니다. 예를 들어, `kube-proxy-config.yaml` 파일을 생성하고 아래 내용을 추가합니다:
```yaml
apiVersion: kubeproxy.config.k8s.io/v1alpha1
kind: KubeProxyConfiguration
bindAddress: "0.0.0.0"
clientConnection:
  kubeconfig: "/var/lib/kube-proxy/kubeconfig.conf"
mode: "iptables"
clusterCIDR: "192.168.0.0/16"
```

### 2. 설정 파일 저장
작성한 설정 파일을 적절한 경로에 저장합니다. 예를 들어, `/etc/kubernetes/kube-proxy-config.yaml` 경로에 저장할 수 있습니다.

### 3. kube-proxy 실행
kube-proxy를 설정 파일과 함께 실행합니다. 다음 명령어를 사용하여 kube-proxy를 시작합니다:
```bash
kube-proxy --config /etc/kubernetes/kube-proxy-config.yaml
```

### 4. 시스템 서비스로 설정 (선택 사항)
kube-proxy를 시스템 서비스로 설정하여 자동으로 시작되도록 할 수 있습니다. 예를 들어, systemd를 사용하는 경우 다음과 같이 설정할 수 있습니다:

1. **서비스 파일 생성**:
   `/etc/systemd/system/kube-proxy.service` 파일을 생성하고 아래 내용을 추가합니다:
   ```ini
   [Unit]
   Description=Kubernetes Kube-Proxy
   Documentation=https://kubernetes.io/docs/reference/command-line-tools-reference/kube-proxy/
   After=network.target

   [Service]
   ExecStart=/usr/local/bin/kube-proxy --config /etc/kubernetes/kube-proxy-config.yaml
   Restart=always
   RestartSec=10s

   [Install]
   WantedBy=multi-user.target
   ```

2. **서비스 시작 및 활성화**:
   ```bash
   sudo systemctl daemon-reload
   sudo systemctl start kube-proxy
   sudo systemctl enable kube-proxy
   ```

이렇게 하면 kube-proxy가 설정 파일을 사용하여 실행되며, 시스템 재부팅 시 자동으로 시작됩니다.
