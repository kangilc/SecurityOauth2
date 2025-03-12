# Dockerfile 내용 분석

이 Dockerfile은 OpenJDK 17을 기반으로 하는 Alpine Linux 이미지를 사용하여 Spring Boot 애플리케이션을 실행하는 설정입니다. 각 명령어의 역할을 설명드릴게요:

### Dockerfile 설명
1. **베이스 이미지 설정**:
   ```dockerfile
   FROM openjdk:17-alpine
   ```
   - OpenJDK 17을 기반으로 하는 경량의 Alpine Linux 이미지를 사용합니다.

2. **타임존 설정**:
   ```dockerfile
   RUN apk --no-cache add tzdata && cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime
   ```
   - `tzdata` 패키지를 설치하고, 타임존을 `Asia/Seoul`로 설정합니다.

3. **JAR 파일 경로 설정**:
   ```dockerfile
   ARG JAR_FILE=./build/libs/sdp-rdx-normal-0.0.1-SNAPSHOT.jar
   ```
   - 빌드된 JAR 파일의 경로를 설정하는 빌드 인수를 정의합니다.

4. **JAR 파일 복사**:
   ```dockerfile
   COPY ${JAR_FILE} rdx-normal.jar
   ```
   - 지정된 경로의 JAR 파일을 컨테이너 내부의 `rdx-normal.jar`로 복사합니다.

5. **파일 권한 설정**:
   ```dockerfile
   RUN chmod 755 rdx-normal.jar
   ```
   - JAR 파일에 실행 권한을 부여합니다.

6. **포트 노출**:
   ```dockerfile
   EXPOSE 7050
   ```
   - 컨테이너가 외부와 통신할 포트 7050을 노출합니다.

7. **애플리케이션 실행**:
   ```dockerfile
   ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","/rdx-normal.jar"]
   ```
   - Spring Boot 애플리케이션을 `dev` 프로파일로 실행합니다.

이 Dockerfile을 사용하면 Spring Boot 애플리케이션을 컨테이너화하여 쉽게 배포할 수 있습니다.

## OpenJDK 17을 기반으로 하는 Alpine Linux 이미지

**OpenJDK 17**을 기반으로 하는 **Alpine Linux**는 경량의 Java 런타임 환경을 제공하는 Docker 이미지입니다. 이를 통해 Java 애플리케이션을 효율적으로 배포할 수 있습니다. 각 구성 요소를 설명드릴게요:

### OpenJDK 17
- **OpenJDK**: Java Development Kit(JDK)의 오픈 소스 구현입니다. Java 애플리케이션을 개발하고 실행하는 데 필요한 도구와 라이브러리를 포함합니다.
- **버전 17**: Java의 최신 장기 지원(LTS) 버전 중 하나로, 새로운 기능과 성능 개선이 포함되어 있습니다.

### Alpine Linux
- **Alpine Linux**: 보안과 경량화를 목표로 설계된 Linux 배포판입니다. 일반적인 Linux 배포판보다 훨씬 작은 크기를 자랑하며, 컨테이너 환경에서 많이 사용됩니다.
- **장점**: 작은 이미지 크기, 빠른 부팅 시간, 높은 보안성 등이 있습니다.

### 결합의 장점
- **경량화**: Alpine Linux의 작은 크기 덕분에 Docker 이미지의 크기가 줄어들어 배포와 전송이 빠릅니다.
- **보안성**: Alpine Linux는 보안에 중점을 두고 설계되어, 더 안전한 환경을 제공합니다.
- **최신 Java 기능**: OpenJDK 17을 사용하여 최신 Java 기능과 성능 개선을 활용할 수 있습니다.

이 조합은 특히 클라우드 환경에서 Java 애플리케이션을 배포할 때 유용합니다. 추가적인 도움이 필요하면 언제든지 말씀해 주세요! 😊
