## Jenkins에서 dev, qa, prd 환경별로 빌드 권한

1. **플러그인 설치**:
   - Jenkins 관리 > Plugin Manager로 이동합니다.
   - `Role-based Authorization Strategy` 플러그인을 검색하여 설치합니다.

2. **글로벌 보안 설정**:
   - Jenkins 관리 > Configure Global Security로 이동합니다.
   - Authorization 항목에서 `Role-Based Strategy`를 선택하고 저장합니다.

3. **역할 생성 및 권한 할당**:
   - Jenkins 관리 > Manage and Assign Roles > Manage Roles로 이동합니다.
   - `dev`, `qa`, `prd` 역할을 추가하고, 각 역할에 빌드 관련 권한을 할당합니다. 예를 들어, 다음과 같이 설정할 수 있습니다:
     - Overall: Read
     - Job: Build, Read

4. **프로젝트별 역할 할당**:
   - Jenkins 관리 > Manage and Assign Roles > Assign Roles로 이동합니다.
   - 각 프로젝트에 대해 `dev`, `qa`, `prd` 역할을 할당합니다. 예를 들어, `dev` 프로젝트에는 `dev` 역할을, `qa` 프로젝트에는 `qa` 역할을 할당합니다.

이렇게 설정하면 각 환경(dev, qa, prd)에 속한 사용자만 해당 환경에서 빌드를 실행할 수 있게 됩니다¹(https://www.bearpooh.com/115)²(https://velog.io/@sangyeon217/jenkins-authorization-strategy).

¹(https://www.bearpooh.com/115): [곰탱푸닷컴](https://www.bearpooh.com/115)
²(https://velog.io/@sangyeon217/jenkins-authorization-strategy): [벨로그](https://velog.io/@sangyeon217/jenkins-authorization-strategy)

원본: Copilot과의 대화, 2025. 2. 25.
(1) Jenkins 권한 관리와 설정 방법 - :::: 곰탱푸닷컴. https://www.bearpooh.com/115.
(2) Jenkins 권한 관리 - 벨로그. https://velog.io/@sangyeon217/jenkins-authorization-strategy.

## 권한별 역할
Jenkins에서 각 권한 역할은 다음과 같습니다:

1. **Overall**:
   - Jenkins 전체에 대한 권한을 관리합니다. 예를 들어, 시스템 설정, 사용자 관리, 플러그인 설치 등의 권한을 포함합니다.

2. **Credentials**:
   - Jenkins에서 자격 증명(예: API 키, 비밀번호 등)을 관리하는 권한입니다. 자격 증명을 생성, 수정, 삭제할 수 있습니다.

3. **Agent**:
   - Jenkins 에이전트(노드)를 관리하는 권한입니다. 에이전트를 추가, 삭제, 구성할 수 있습니다.

4. **Job**:
   - Jenkins 작업(프로젝트)을 관리하는 권한입니다. 작업을 생성, 수정, 삭제할 수 있습니다.

5. **Run**:
   - Jenkins 작업을 실행하고, 실행된 작업의 로그를 볼 수 있는 권한입니다. 작업을 수동으로 트리거할 수 있습니다.

6. **View**:
   - Jenkins 뷰를 관리하는 권한입니다. 뷰를 생성, 수정, 삭제할 수 있습니다.

7. **SCM**:
   - 소스 코드 관리(SCM) 관련 권한입니다. 예를 들어, Git, SVN 등의 소스 코드 저장소와의 연동을 관리할 수 있습니다.

8. **Metrics**:
   - Jenkins의 메트릭스를 조회하고 관리하는 권한입니다. 시스템 성능, 빌드 통계 등의 데이터를 볼 수 있습니다¹(https://velog.io/@sangyeon217/jenkins-authorization-strategy)²(https://www.bearpooh.com/115).

이 권한들을 적절히 설정하여 사용자별로 필요한 권한만 부여할 수 있습니다.

¹(https://velog.io/@sangyeon217/jenkins-authorization-strategy): [곰탱푸닷컴](https://www.bearpooh.com/115)
²(https://www.bearpooh.com/115): [벨로그](https://velog.io/@sangyeon217/jenkins-authorization-strategy)

원본: Copilot과의 대화, 2025. 2. 25.
(1) Jenkins 권한 관리 - 벨로그. https://velog.io/@sangyeon217/jenkins-authorization-strategy.
(2) Jenkins 권한 관리와 설정 방법 - :::: 곰탱푸닷컴. https://www.bearpooh.com/115.
