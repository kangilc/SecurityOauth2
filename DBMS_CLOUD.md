# Oracle 프로시저에서 **AWS S3 파일 복사** 및 **Validation 체크**를 구현

---

## ✅ **1. Oracle에서 AWS S3 연동 방식**
### **옵션 A: DBMS_CLOUD 패키지 사용 (Oracle 19c 이상)**
- **DBMS_CLOUD**는 Oracle에서 클라우드 스토리지(AWS S3 포함)와 직접 연동할 수 있는 패키지입니다.
- 주요 기능:
  - `DBMS_CLOUD.PUT_OBJECT` → S3 업로드
  - `DBMS_CLOUD.GET_OBJECT` → S3 다운로드
  - `DBMS_CLOUD.COPY_DATA` → S3 → Oracle 테이블로 데이터 로드
  - `DBMS_CLOUD.CREATE_EXTERNAL_TABLE` → S3 데이터를 외부 테이블로 연결
- **예제**:
  ```sql
  BEGIN
    DBMS_CLOUD.CREATE_CREDENTIAL(
      credential_name => 'AWS_CRED',
      username => 'AWS_ACCESS_KEY',
      password => 'AWS_SECRET_KEY'
    );

    DBMS_CLOUD.PUT_OBJECT(
      credential_name => 'AWS_CRED',
      object_uri => 'https://s3.amazonaws.com/my-bucket/myfile.csv',
      directory_name => 'DATA_PUMP_DIR',
      file_name => 'myfile.csv'
    );
  END;
  /
  ```
- **Validation**: `DBMS_CLOUD.VALIDATE_EXTERNAL_TABLE` 또는 `DBMS_CLOUD.LIST_OBJECTS`로 파일 존재 여부 확인 가능[1](https://docs.oracle.com/ko/cloud/paas/autonomous-database/dedicated/adbdm/index.html)[2](https://aws.amazon.com/blogs/database/use-the-dbms_cloud-package-in-amazon-rds-custom-for-oracle-for-direct-amazon-s3-integration/)

---

### **옵션 B: PL/SQL + AWS Signature V4 구현**
- GitHub의 [plsql-aws-s3 패키지](https://github.com/cmoore-sp/plsql-aws-s3) 활용 가능
- 기능:
  - S3 객체 업로드/다운로드
  - HTTPS + AWS4 서명 방식 지원
- **장점**: 온프레미스 Oracle에서도 가능
- **단점**: 구현 복잡, 유지보수 부담[3](https://github.com/cmoore-sp/plsql-aws-s3)

---

## ✅ **2. AWS S3 파일 복사 및 Validation**
- **파일 복사**: AWS CLI 또는 SDK에서 `aws s3 cp` 또는 `aws s3 sync` 사용
  ```bash
  aws s3 cp s3://source-bucket/file.csv s3://target-bucket/file.csv
  ```
- **무결성 검증(Validation)**:
  - **ETag 비교**: 업로드 후 `aws s3api head-object`로 ETag 확인
  - **Checksum**: S3는 SHA256, MD5, CRC32 등 체크섬 지원
    ```bash
    aws s3api head-object --bucket my-bucket --key myfile.csv --query ChecksumSHA256
    ```
  - **멀티파트 업로드 시**: 후행 체크섬(Trailing checksum) 사용 가능[4](https://docs.aws.amazon.com/ko_kr/AmazonS3/latest/userguide/checking-object-integrity-upload.html)[5](https://docs.aws.amazon.com/ko_kr/AmazonS3/latest/userguide/checking-object-integrity.html)

---

## ✅ **3. Oracle 프로시저에서 Validation 로직**
- **방법**:
  1. `DBMS_CLOUD.LIST_OBJECTS`로 S3에 파일 존재 여부 확인
  2. 파일 크기(`content_length`)와 체크섬(`etag`) 비교
  3. 필요 시 `DBMS_CLOUD.VALIDATE_EXTERNAL_TABLE`로 데이터 포맷 검증

---

### **추천 아키텍처**
- **Oracle Autonomous DB / RDS**: `DBMS_CLOUD` 또는 `rdsadmin_s3_tasks` 사용
- **온프레미스 Oracle**: PL/SQL + HTTPS API 구현 or AWS CLI 호출 (DBMS_SCHEDULER로 Shell Script 실행)

---

👉 원하면 **PL/SQL 프로시저 샘플 코드**(`DBMS_CLOUD` 기반)와  
**AWS CLI 기반 Validation 스크립트**를 같이 만들어줄까요?  
아니면 **전체 프로세스(Oracle → S3 업로드 → Validation) 플로우 다이어그램**도 추가할까요?

# **Oracle PL/SQL 프로시저**에서 **AWS S3 파일 업로드 및 Validation 체크**를 수행하는 샘플 코드입니다. 이 예제는 **DBMS_CLOUD 패키지**를 활용합니다 (Oracle 19c 이상, Autonomous DB 또는 DBMS_CLOUD 설치 환경 필요).

---

## ✅ **1. AWS Credential 생성**
```sql
BEGIN
  DBMS_CLOUD.CREATE_CREDENTIAL(
    credential_name => 'AWS_CRED',
    username        => 'AWS_ACCESS_KEY_ID',
    password        => 'AWS_SECRET_ACCESS_KEY'
  );
END;
/
```

---

## ✅ **2. S3로 파일 업로드**
```sql
BEGIN
  DBMS_CLOUD.PUT_OBJECT(
    credential_name => 'AWS_CRED',
    object_uri      => 'https://s3.amazonaws.com/my-bucket/myfile.csv',
    directory_name  => 'DATA_PUMP_DIR',
    file_name       => 'myfile.csv'
  );
END;
/
```

---

## ✅ **3. Validation 체크 (파일 존재 여부 및 메타데이터 확인)**
```sql
DECLARE
  l_result CLOB;
BEGIN
  l_result := DBMS_CLOUD.LIST_OBJECTS(
    credential_name => 'AWS_CRED',
    location_uri    => 'https://s3.amazonaws.com/my-bucket/'
  );
  DBMS_OUTPUT.PUT_LINE(l_result);
END;
/
```
- `LIST_OBJECTS` 결과에서 `myfile.csv` 존재 여부, `content_length`(파일 크기) 확인 가능.

---

## ✅ **4. 데이터 포맷 검증 (CSV → 외부 테이블)**
```sql
BEGIN
  DBMS_CLOUD.CREATE_EXTERNAL_TABLE(
    table_name      => 'EXT_MYFILE',
    credential_name => 'AWS_CRED',
    file_uri_list   => 'https://s3.amazonaws.com/my-bucket/myfile.csv',
    format          => json_object('type' value 'csv', 'skipheaders' value '1')
  );
END;
/
```
- 이후 `SELECT COUNT(*) FROM EXT_MYFILE;`로 데이터 로드 가능 여부 확인.

---

## ✅ **5. Validation 로직 예시**
```sql
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_count FROM EXT_MYFILE;
  IF v_count > 0 THEN
    DBMS_OUTPUT.PUT_LINE('Validation OK: ' || v_count || ' rows found.');
  ELSE
    DBMS_OUTPUT.PUT_LINE('Validation FAILED: No data found.');
  END IF;
END;
/
```

---

### ✅ **추가 가능 기능**
- **파일 업로드 후 Checksum 비교** (AWS CLI 또는 SDK 호출)
- **DBMS_SCHEDULER로 자동화**
- **에러 발생 시 로깅 및 알림**

---

# DBMS_CLOUD에서 **AWS S3 인증**을 설정하는 방법 **Credential 객체**를 생성하는 방식이지만, 환경에 따라 추가 옵션이 있습니다:

---

### ✅ **1. 기본 인증 방식**
- `DBMS_CLOUD.CREATE_CREDENTIAL` 사용:
  ```sql
  BEGIN
    DBMS_CLOUD.CREATE_CREDENTIAL(
      credential_name => 'AWS_CRED',
      username        => 'AWS_ACCESS_KEY_ID',
      password        => 'AWS_SECRET_ACCESS_KEY'
    );
  END;
  /
  ```
- 이후 `DBMS_CLOUD.PUT_OBJECT`, `GET_OBJECT`, `COPY_DATA` 등에서 `credential_name`으로 참조[1](https://aws.amazon.com/blogs/database/use-the-dbms_cloud-package-in-amazon-rds-custom-for-oracle-for-direct-amazon-s3-integration/)[2](https://www.pythian.com/blog/technical-track/oracle-dbms_cloud-package-with-aws-s3).

---

### ✅ **2. 추가 인증 옵션**
- **IAM Role 기반 인증** (Amazon RDS for Oracle):
  - RDS에서 S3 연동 시 **IAM Role**을 DB 인스턴스에 연결 가능
  - Oracle DB는 IAM Role을 통해 S3 접근 권한을 위임받음
  - 이 경우 DBMS_CLOUD에서 별도의 Access Key/Secret Key 없이 IAM Role 인증 사용[3](https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/oracle-s3-integration.preparing.html)
  
- **SSL Wallet 기반 인증**:
  - DBMS_CLOUD는 HTTPS 통신을 위해 **SSL Wallet** 설정 필요
  - Oracle 문서(MOS Note 2748362.1)에 따르면, 인증서 기반 SSL Wallet을 구성하고 DBMS_CLOUD가 이를 참조하도록 설정 가능
  - 특히 프록시 환경이나 보안 요구사항이 높은 경우 필수[4](https://support.oracle.com/knowledge/Oracle%20Cloud/2748362_1.html)

---

### ✅ **3. 보안 모델**
- DBMS_CLOUD는 **Credential 객체**를 데이터베이스 내부에 저장
- 권한 제어:
  - `GRANT EXECUTE ON DBMS_CLOUD TO <user>`
  - `GRANT CREATE CREDENTIAL TO <user>`
- 필요 시 **Access Control Entries (ACE)** 설정으로 네트워크 접근 제어 가능[5](https://docs.oracle.com/en/database/oracle/oracle-database/19/arpls/DBMS_CLOUD.html)

---

👉 원하면 **IAM Role 기반 인증을 사용하는 RDS 예제**와  
**SSL Wallet 설정 절차**를 정리해줄까요?  
아니면 **DBMS_CLOUD 인증 방식별 비교표**를 만들어줄까요?
