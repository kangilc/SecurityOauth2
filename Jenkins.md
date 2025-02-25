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
