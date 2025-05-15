**AWS LoadBalancer Controller 및 TargetGroupBinding 기반 ALB 연동 구축 및 운영 절차**

---

## 1. AWS LoadBalancer Controller 및 TargetGroupBinding CRD 설치 [인프라]

### 설명:
AWS LoadBalancer Controller는 Kubernetes 클러스터에서 ALB/NLB를 관리할 수 있도록 해주는 컨트롤러입니다. TargetGroupBinding은 ALB Target Group과 Kubernetes 리소스를 연결하는 CRD입니다.

### 설치 예시:
```bash
# Helm repo 추가
helm repo add eks https://aws.github.io/eks-charts
helm repo update

# IAM OIDC 및 IAM Role for Service Account (IRSA) 설정 필요

# Helm 설치
helm install aws-load-balancer-controller eks/aws-load-balancer-controller \
  -n kube-system \
  --set clusterName=<CLUSTER_NAME> \
  --set serviceAccount.create=false \
  --set serviceAccount.name=aws-load-balancer-controller \
  --set region=<AWS_REGION> \
  --set vpcId=<VPC_ID>
```

---

## 2. 서비스 타입별 Shared ALB 생성 (콘솔 또는 IaC) [인프라]

### 설명:
서비스 타입(servicewas/gfts/mas/ibs)별로 ALB를 미리 생성하고, Ingress 리소스를 사용하지 않고 수동 또는 IaC(Terraform 등)로 관리합니다.

### 예시 (Terraform):
```hcl
resource "aws_lb" "shared_alb_servicewas" {
  name               = "alb-servicewas"
  internal           = false
  load_balancer_type = "application"
  subnets            = var.public_subnets
  security_groups    = [aws_security_group.alb_sg.id]
}
```

---

## 3. 세부 서비스별 Target Group 생성 및 Listener 연결 (Path Rule 포함) [인프라]

### 설명:
각 서비스별로 Target Group을 생성하고, ALB Listener에 Path 기반 라우팅 규칙을 설정합니다.

### 예시 (Terraform):
```hcl
resource "aws_lb_target_group" "svc1_tg" {
  name     = "svc1-tg"
  port     = 80
  protocol = "HTTP"
  vpc_id   = var.vpc_id
  target_type = "ip"
}

resource "aws_lb_listener_rule" "svc1_rule" {
  listener_arn = aws_lb_listener.http.arn
  priority     = 10

  actions {
    type             = "forward"
    target_group_arn = aws_lb_target_group.svc1_tg.arn
  }

  conditions {
    path_pattern {
      values = ["/svc1/*"]
    }
  }
}
```

---

## 4. Helm Chart 수정: Ingress + TargetGroupBinding 병행 지원 [운영-CDS]

### 설명:
기존 Ingress 방식과 TargetGroupBinding 방식을 동시에 지원하도록 Helm Chart를 수정합니다.

### 예시 (`templates/targetgroupbinding.yaml`):
```yaml
{{- if .Values.alb.enabled }}
apiVersion: elbv2.k8s.aws/v1beta1
kind: TargetGroupBinding
metadata:
  name: {{ include "myapp.fullname" . }}
spec:
  serviceRef:
    name: {{ include "myapp.fullname" . }}
    port: 80
  targetGroupARN: {{ .Values.alb.targetGroupArn }}
{{- end }}
```

---

## 5. Argo Application Helm Chart 버전 업데이트 [운영-CDS]

### 설명:
Argo CD에서 사용하는 Helm Chart 버전을 업데이트하여 새로운 리소스가 반영되도록 합니다.

### 예시:
```yaml
spec:
  source:
    repoURL: https://github.com/myorg/helm-charts
    targetRevision: v1.2.0
    path: charts/myapp
```

---

## 6. 서비스 모듈별 `values.yaml` 수정 [운영-모듈별개발담당자]

### 예시:
```yaml
alb:
  enabled: true
  targetGroupArn: arn:aws:elasticloadbalancing:ap-northeast-2:123456789012:targetgroup/svc1-tg/abc123
```

---

## 7. 파이프라인을 통한 TargetGroupBinding 배포 확인 [운영/개발]

### 설명:
CI/CD 파이프라인을 통해 TargetGroupBinding이 정상적으로 배포되는지 확인합니다.

### 확인 방법:
- `kubectl get targetgroupbinding`
- `kubectl describe targetgroupbinding <name>`
- ALB 콘솔에서 Target Group 상태 확인

---

## 8. 신규 ALB → Pod 간 통신 확인 및 트래픽 100% 전환 [인프라]

### 설명:
ALB를 통해 서비스가 정상적으로 Pod에 연결되는지 확인하고, 기존 Ingress에서 트래픽을 전환합니다.

### 확인 방법:
- ALB Listener 로그
- Target Group 상태 (Healthy)
- 실제 서비스 URL 테스트

---

## 9. 기존 Ingress 정보 삭제 및 재배포 [운영-모듈별개발담당자]

### 예시 (`values.yaml`):
```yaml
ingress:
  enabled: false
```

---

## 10. LB Controller를 통한 최종 서비스 점검 [운영-모듈별개발담당자]

### 설명:
최종적으로 AWS LoadBalancer Controller가 모든 리소스를 정상적으로 관리하고 있는지 점검합니다.

### 확인 항목:
- `kubectl logs -n kube-system deployment/aws-load-balancer-controller`
- TargetGroupBinding 상태
- ALB 콘솔에서 Listener/Target Group 상태

---

필요하시면 각 단계별로 Terraform, Helm Chart, Argo CD 설정 예제를 더 자세히 드릴 수 있어요. 어떤 부분을 더 깊이 다뤄볼까요?
