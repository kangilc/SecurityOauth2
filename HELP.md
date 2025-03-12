## java 실행 명령어 Help
```sh
nohup java -XX:+UseG1GC -Xlog:gc -XX:+PrintFlagsFinal -Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector -Djava.net.preferIPv4Stack=true -jar sdp-rdx-normal.jar 1>/logs001/abc/abc-normal.log 2>&1 &
```

이 명령어는 `nohup`을 사용하여 백그라운드에서 Java 애플리케이션을 실행하는 방법을 보여줍니다. 각 옵션의 역할을 설명드릴게요:

### 명령어 설명
- **nohup**: 터미널 세션이 종료되어도 프로세스가 계속 실행되도록 합니다.
- **java**: Java 애플리케이션을 실행하는 명령어입니다.
- **-XX:+UseG1GC**: G1 가비지 컬렉터를 사용하도록 설정합니다. G1 가비지 컬렉터는 대규모 힙 메모리를 효율적으로 관리하는 데 유리합니다.
- **-Xlog:gc**: 가비지 컬렉션 로그를 출력합니다.
- **-XX:+PrintFlagsFinal**: JVM이 시작될 때 모든 JVM 옵션의 최종 값을 출력합니다.
- **-Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector**: Log4j2의 비동기 로거 컨텍스트 선택기를 설정합니다. 이를 통해 로그 처리가 비동기적으로 이루어져 성능이 향상됩니다.
- **-Djava.net.preferIPv4Stack=true**: IPv4 스택을 우선 사용하도록 설정합니다.
- **-jar abc-normal.jar**: `abc-normal.jar` 파일을 실행합니다.
- **1>/logs001/abc/abc-normal.log**: 표준 출력을 `/logs001/rdx/rdx-normal.log` 파일로 리다이렉트합니다.
- **2>&1**: 표준 오류 출력을 표준 출력과 동일한 파일로 리다이렉트합니다.
- **&**: 명령어를 백그라운드에서 실행합니다.

이 명령어를 사용하면 로그 파일에 가비지 컬렉션 로그와 애플리케이션 로그가 기록되며, 터미널 세션이 종료되어도 애플리케이션이 계속 실행됩니다. 추가적인 도움이 필요하면 언제든지 말씀해 주세요! 😊
