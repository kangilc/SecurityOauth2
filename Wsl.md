# wsl 리눅스 설치
WSL(Windows Subsystem for Linux)을 사용하여 Windows에 리눅스를 설치하는 방법을 안내해드릴게요. 다음 단계에 따라 WSL과 리눅스 배포판을 설치할 수 있습니다:

### 1. WSL 활성화
먼저, WSL 기능을 활성화해야 합니다. 관리자 권한으로 PowerShell을 열고 다음 명령어를 입력하세요:
```sh
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
```

### 2. Virtual Machine Platform 활성화
WSL 2를 사용하려면 Virtual Machine Platform 기능도 활성화해야 합니다. 다음 명령어를 입력하세요:
```sh
dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
```

### 3. WSL 설치
이제 WSL을 설치합니다. 관리자 권한으로 PowerShell을 열고 다음 명령어를 입력하세요:
```sh
wsl --install
```
이 명령어는 WSL과 기본 리눅스 배포판(Ubuntu)을 설치합니다¹(https://learn.microsoft.com/ko-kr/windows/wsl/install).

### 4. 리눅스 배포판 설치
기본 배포판 외에 다른 리눅스 배포판을 설치하려면 다음 명령어를 사용하세요:
```sh
wsl --install -d <배포판 이름>
```
예를 들어, Debian을 설치하려면:
```sh
wsl --install -d Debian
```

### 5. WSL 2로 설정
WSL 2를 기본 버전으로 설정하려면 다음 명령어를 입력하세요:
```sh
wsl --set-default-version 2
```

### 6. 설치 확인
설치된 리눅스 배포판을 확인하려면 다음 명령어를 입력하세요:
```sh
wsl --list --verbose
```

### 7. 설치 가능 버전 확인(온라인)
설치 가능한(온라인) 리눅스 배포판을 확인하려면 다음 명령어를 입력하세요:
```sh
wsl --l -o
```

이 과정을 통해 WSL과 리눅스 배포판을 설치할 수 있습니다. 추가로 궁금한 점이 있으면 언제든지 말씀해 주세요!

¹(https://learn.microsoft.com/ko-kr/windows/wsl/install): [WSL 설치 | Microsoft Learn](https://learn.microsoft.com/ko-kr/windows/wsl/install)

원본: Copilot과의 대화, 2025. 2. 24.
(1) WSL 설치 | Microsoft Learn. https://learn.microsoft.com/ko-kr/windows/wsl/install.
