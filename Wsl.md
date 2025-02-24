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
wsl -l -o
```

이 과정을 통해 WSL과 리눅스 배포판을 설치할 수 있습니다. 추가로 궁금한 점이 있으면 언제든지 말씀해 주세요!

¹(https://learn.microsoft.com/ko-kr/windows/wsl/install): [WSL 설치 | Microsoft Learn](https://learn.microsoft.com/ko-kr/windows/wsl/install)

원본: Copilot과의 대화, 2025. 2. 24.
(1) WSL 설치 | Microsoft Learn. https://learn.microsoft.com/ko-kr/windows/wsl/install.

## linux systemd 기반으로 변경하고 싶어
Linux 시스템을 systemd 기반으로 변경하려면 다음 단계를 따르세요. 이 과정은 기존의 init 시스템을 systemd로 대체하는 것을 포함합니다. 주의: 이 작업은 시스템에 중요한 영향을 미칠 수 있으므로, 변경 전에 중요한 데이터를 백업하는 것이 좋습니다.

### 1. systemd 설치
대부분의 최신 Linux 배포판에는 이미 systemd가 설치되어 있습니다. 만약 설치되어 있지 않다면, 다음 명령어를 사용하여 설치할 수 있습니다:

#### Debian/Ubuntu
```sh
sudo apt-get update
sudo apt-get install systemd
```

#### CentOS/RHEL
```sh
sudo yum install systemd
```

### 2. 기본 init 시스템 변경
systemd를 기본 init 시스템으로 설정하려면 다음 단계를 따르세요:

#### Debian/Ubuntu
1. **GRUB 설정 파일 수정**:
   ```sh
   sudo nano /etc/default/grub
   ```
2. **GRUB_CMDLINE_LINUX_DEFAULT** 라인에 `init=/lib/systemd/systemd` 추가:
   ```sh
   GRUB_CMDLINE_LINUX_DEFAULT="quiet splash init=/lib/systemd/systemd"
   ```
3. **GRUB 업데이트**:
   ```sh
   sudo update-grub
   ```

#### CentOS/RHEL
1. **GRUB 설정 파일 수정**:
   ```sh
   sudo nano /etc/default/grub
   ```
2. **GRUB_CMDLINE_LINUX** 라인에 `init=/lib/systemd/systemd` 추가:
   ```sh
   GRUB_CMDLINE_LINUX="rd.lvm.lv=centos/root rd.lvm.lv=centos/swap rhgb quiet init=/lib/systemd/systemd"
   ```
3. **GRUB2 구성 파일 생성**:
   ```sh
   sudo grub2-mkconfig -o /boot/grub2/grub.cfg
   ```

### 3. 시스템 재부팅
변경 사항을 적용하려면 시스템을 재부팅합니다:
```sh
sudo reboot
```

### 4. systemd 확인
시스템이 재부팅된 후, 다음 명령어를 사용하여 systemd가 제대로 작동하는지 확인합니다:
```sh
systemctl --version
```

이 단계를 통해 Linux 시스템을 systemd 기반으로 변경할 수 있습니다¹(https://ko.linux-terminal.com/?p=3557)²(https://ko.linux-console.net/?p=5807). 추가로 궁금한 점이 있으면 언제든지 말씀해 주세요!

¹(https://ko.linux-terminal.com/?p=3557): [Systemd on Linux - 서비스, 실행 수준 및 로그 관리](https://ko.linux-terminal.com/?p=3557)
²(https://ko.linux-console.net/?p=5807): [Systemctl을 사용하여 Systemd 서비스 및 단위를 관리하는 방법](https://ko.linux-console.net/?p=5807)

원본: Copilot과의 대화, 2025. 2. 24.
(1) Systemd on Linux - 서비스, 실행 수준 및 로그 관리. https://ko.linux-terminal.com/?p=3557.
(2) Systemctl을 사용하여 Systemd 서비스 및 단위를 관리하는 방법. https://ko.linux-console.net/?p=5807.

## Wsl linux ubuntu Docker 설치

WSL(Windows Subsystem for Linux)에서 Docker를 설치하는 방법을 안내해드릴게요. 다음 단계를 따라 WSL2 환경에서 Docker를 설치하고 사용할 수 있습니다:

### 1. WSL2 설치 및 설정
먼저, WSL2를 설치하고 설정합니다. 관리자 권한으로 PowerShell을 열고 다음 명령어를 실행하세요:
```sh
wsl --install
wsl --set-default-version 2
```

### 2. 리눅스 배포판 설치
Microsoft Store에서 원하는 리눅스 배포판(예: Ubuntu 22.04 LTS)을 설치합니다. 설치 과정에서 사용자 이름과 비밀번호를 지정합니다.

### 3. Docker 설치
리눅스 배포판에서 Docker를 설치합니다. WSL2에서 Ubuntu를 사용하는 예시입니다:

1. **Docker 패키지 저장소 추가**:
   ```sh
   sudo apt-get update
   sudo apt-get install \
     ca-certificates \
     curl \
     gnupg \
     lsb-release

   sudo mkdir -p /etc/apt/keyrings
   curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
   echo \
     "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
     $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
   ```

2. **Docker 엔진 설치**:
   ```sh
   sudo apt-get update
   sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
   ```

3. **Docker 데몬 시작**:
   ```sh
   sudo service docker start
   ```

4. **Docker 설치 확인**:
   ```sh
   sudo docker run hello-world
   ```

### 4. systemd 활성화 (선택 사항)
WSL2에서 systemd를 활성화하려면 `/etc/wsl.conf` 파일을 수정합니다:
```sh
sudo nano /etc/wsl.conf
```
다음 내용을 추가합니다:
```ini
[boot]
systemd=true
```
변경 사항을 적용하려면 WSL을 다시 시작합니다:
```sh
wsl --shutdown
wsl
```

이 단계를 통해 WSL2 환경에서 Docker를 설치하고 사용할 수 있습니다¹(https://blog.naver.com/PostView.nhn?blogId=ilikebigmac&logNo=222007741507)²(https://www.lainyzine.com/ko/article/how-to-install-docker-on-wsl/). 추가로 궁금한 점이 있으면 언제든지 말씀해 주세요!

¹(https://blog.naver.com/PostView.nhn?blogId=ilikebigmac&logNo=222007741507): [WSL2 설치 및 Docker 환경 구축 - 네이버 블로그](https://blog.naver.com/PostView.nhn?blogId=ilikebigmac&logNo=222007741507)
²(https://www.lainyzine.com/ko/article/how-to-install-docker-on-wsl/): [윈도우 WSL에서 Docker 설치하는 방법 - LainyZine](https://www.lainyzine.com/ko/article/how-to-install-docker-on-wsl/)

원본: Copilot과의 대화, 2025. 2. 24.
(1) WSL2 설치 및 Docker 환경 구축 - 네이버 블로그. https://blog.naver.com/PostView.nhn?blogId=ilikebigmac&logNo=222007741507.
(2) 윈도우 WSL에서 Docker 설치하는 방법 - LainyZine. https://www.lainyzine.com/ko/article/how-to-install-docker-on-wsl/.
