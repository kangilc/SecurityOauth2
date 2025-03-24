
### 클러스터 내에서 MySQL에 접근하기
1. **루트 비밀번호 가져오기**:
   ```bash
   MYSQL_ROOT_PASSWORD=$(kubectl get secret --namespace default my-mysql -o jsonpath="{.data.mysql-root-password}" | base64 --decode; echo)
   ```

2. **Ubuntu 파드 실행**:
   ```bash
   kubectl run -i --tty ubuntu --image=ubuntu:16.04 --restart=Never -- bash -il
   ```

3. **MySQL 클라이언트 설치**:
   파드 내에서 다음 명령어를 실행합니다:
   ```bash
   apt-get update && apt-get install mysql-client -y
   ```

4. **MySQL에 연결**:
   MySQL 클라이언트를 사용하여 데이터베이스에 연결합니다:
   ```bash
   mysql -h my-mysql -p
   ```
   비밀번호를 입력할 때, 위에서 가져온 루트 비밀번호를 사용합니다.

### 클러스터 외부에서 MySQL에 접근하기
1. **포트 포워딩 설정**:
   ```bash
   kubectl port-forward svc/my-mysql 3306
   ```

2. **MySQL 클라이언트를 사용하여 연결**:
   로컬 머신에서 다음 명령어를 실행합니다:
   ```bash
   mysql -h 127.0.0.1 -P3306 -u root -p${MYSQL_ROOT_PASSWORD}
   ```

