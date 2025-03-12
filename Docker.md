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
