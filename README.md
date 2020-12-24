# 나도 내가 무섭조(서울 2반 11조)

![](README.assets/logo.jpg)


## 분노의민원
#### 원 버튼 간편 교통 민원 DB 플랫폼
차량 주행 중 한번의 버튼 클릭으로 관련 영상 및 정보가 웹 서버에 전송되어 민원 신고에 간편한 서비스 및 본인의 데이터를 관리 기능 제공

## 서비스 목적
#### 쾌적한 주행 환경 및 에티켓 조성
안전하고 편리하게 위반 상황을 기록하고, 간편한 민원 신고를 통해 사고와 위반의 발생을 줄이고, 올바른 마인드셋 형성

## 핵심 기능

#### 데이터 기록
- 카메라 : 지난 20초의 영상
- GPS : 현재 위치의 좌표
- 기타 : 현재 날짜 및 시간
- 리모콘 : 데이터 전송

#### 데이터 보관 및 관리
- Reverse Geocoding : 좌표를 도로명 주소로 변환하여 관리
- Naver ID Login :  네이버 아이디로 간편하게 보관 및 관리 개인화
- JWT Token : 인증된 사용자만 데이터에 접근 가능하여 기밀성 보장
- Kakao Maps : 데이터를 시각적으로 관리 및 접근

#### 반응형 웹 사이트
- 사건 발생 시 모바일에서 즉시 이용 가능
- 데스크 탑 또한 이용 가능

# 프로젝트 사용법
### Import
```bash
git clone https://lab.ssafy.com/s03-webmobile3-sub2/s03p12a211.git
```

### Vue.js
```bash
cd s03p12a211/frontend
npm install
npm run serve
```

### 현재 인스톨된 NPM 패키지 리스트 만들기

```bash
$ npm shrinkwrap
```

- 현재 경로에 npm-shrinkwrap.json 작성
  
- shrinkwrap: 현재 인스톨된 npm 패키지 json 목록으로 작성하는 패키지
  
- 해당 npm을 공유받는 사람은 

  ```bash
  $ npm install
  ```

  다음의 명령어로 작성된 패키지 리스트대로 설치 가능

#### Install NGINX
```bash
sudo apt-get update
sudo apt-get upgrade
sudo apt-get install nginx
```

#### Environment Setting
```bash
cd /etc/nginx/sites-available
vi default
```

```linux
# Front-End Setting
root <Front 빌드 파일 위치>;
<index 파일명>;
server_name <서버 도메인>;

# Back-End Setting
proxy_pass <웹 사이트 URL>
```

#### Nginx Start
```bash
sudo service nginx start
or
sudo systemctl start nginx
```

#### Deploy
##### Front-End
```json
// package.json
"script": {
    "build": "vue-cli-service build"
}
```
```bash
npm run build
```
dist 폴더 생성 => index.html => src 경로 앞에 . 추가

##### Back-End
``` xml
<!-- pom.xml-->
<packaging>jar</packaging>
```
Run as -> Maven clean > install > package 순으로 실행

Project -> target -> .jar 파일 생성

##### Local -> AWS File Send & Service Run
```bash
scp -i <pem 파일> <파일> ubuntu@<URL>:<파일>
sudo mv <파일> /var/www/html
sudo java -jar <파일>.jar
sudo service nginx restart
```

## IoT

```bash
$ sudo python button1.py &

$ sudo python gps.py &

$ sudo python testing.py &
```

명령어를 etc/profile 마지막에 추가하여 자동실행


### button1.py 

- 기존의 남아있는 잉여 데이터 삭제
- 리모컨 버튼 입력을 받았을 때 h264 파일을 mp4 파일로 변환
- 만들어진 mp4 파일을 알맞게 하나의 파일로 만듦
- Toaws.py를 실행

```python
def make_movie():

    subprocess.call('rm -r sample*.mp4 -y', shell=True)
    #print('movie change')
    subprocess.call('MP4Box -fps 30 -add sample0.h264 sample0.mp4', shell=True)
    subprocess.call('MP4Box -fps 30 -add sample1.h264 sample1.mp4', shell=True)
    subprocess.call('MP4Box -fps 30 -add sample2.h264 sample2.mp4', shell=True)
    subprocess.call('MP4Box -fps 30 -add sample3.h264 sample3.mp4', shell=True)
    subprocess.call('MP4Box -fps 30 -add sample4.h264 sample4.mp4', shell=True)
    subprocess.call('MP4Box -fps 30 -add sample5.h264 sample5.mp4', shell=True)
    #print 'sum movie'
    subprocess.call('ffmpeg -f concat -i input.txt -codec copy output.mp4 -y', shell=True)
    #print 'send'
    subprocess.call('python Toaws.py', shell=True)
    #print 'btn complete'

```



### gps.py 

- gps모듈을 이용하여 gps 정보를 받아옴
- serial로 port를 받아오고, pynmea2 라이브러리를 사용



### testing.py

- 영상을 6개씩 계속해서 찍는다(7이상은 0번부터 다시 덮어씌워 무한반복, i = i%6)
- 약 5초의 영상을 촬영
- 각 번호의 영상에 해당하는 번호를 저장하여 앞의 3개와 눌린 시점 이후 5초가 촬영된 영상을 따로 저장



### Toaws.py

- 상황정보(gps, 일시, video이름 등)을 백 서버로 전송해준다.
- S3 서버에 저장될 영상을 업로드해준다.
- 전송할 영상의 이름은 중복 시에 에러를 발생시키므로 random 함수로 이름을 난수 배정

# 



# 서비스 설명

```bash
저희 서비스는 자동차에 설치되는 블랙박스와 개인이 업로드한 블랙박스 영상을 저장하고 관리할 수 있는 웹페이지로 이루어져 있으며,
차량 운행중에 교통법규 위반사례를 목격했다면 블랙박스와 연결된 버튼을 눌러 지난 20초 상황에 대해서만 영상을 본인 웹페이지 계정으로 전송합니다.
```

![](README.assets/블랙박스사진.jpg)
![](README.assets/홈페이지사진1.PNG)
![](README.assets/카드첫클릭.PNG)

```bash
보내온 영상 썸내일을 클릭하게 되면 블랙박스 내 센서로 영상 및 영상정보들이 자동으로 저장되어진 것을 확인할 수 있습니다.
```
![](README.assets/HowToUse1.png)
```bash
초기 상태는 신고 미접수로 아직 스마트 국민제보 사이트에 민원을 접수하기 전 단계입니다
```
![](README.assets/HowToUse3.png)
```bash
제보 상세보기에서는 다음의 빈칸들을 작성 후 저장하기를 누릅니다
```
![](README.assets/HowToUse4.png)
```bash
영상을 다운로드 받은 후 하단의 스마트 국민제보로 신고하러 가기 버튼을 누릅니다.
```
![](README.assets/HowToUse5.png)
```bash
스마트 국민제보 사이트에서 회원가입 후 로그인을 합니다
```
![](README.assets/HowToUse6.png)
```bash
메인화면에 보이는 교통위반 버튼을 누릅니다.
```
![](README.assets/HowToUse7.png)
```bash
분노의 민원 제보목록에 저장된카드 를 보면서 1~8번 순서대로 교통위반신고 항목을 기입하고 신고하기 버튼을 누릅니다.
```
![](README.assets/HowToUse8.png)
```bash
신고가 완료됐다면 분노의 민원 사이트로 돌아와 사진처럼 신고 미접수 상태를 접수 완료 상태로 바꿔줍니다.
```

# 라이센스
[MIT License 2.0](https://lab.ssafy.com/s03-webmobile3-sub2/s03p12a211/blob/develop/LICENSE)

# 기타(개발 일정,  자랑하고 싶은 것, 변화된 이슈 등)

# 개발 규칙

## GIT Branch 변경

```bash
$ git checkout <브랜치 이름>
```

## GIT 특정 Branch 클론

```bash
$ git clone -b <브랜치 이름> --single-branch <레퍼지토리 주소>
```
```bash
$ git clone -b develop --single-branch https://lab.ssafy.com/s03-webmobile3-sub2/s03p12a211.git
```

## GIT Workflow

#### Git Bash의 브랜치 상태를 통해 항상 선택된 브랜치를 확인합니다.

1. (Master) 혹은 (develop)일 때, 특정 브랜치 생성 명령어 `branch` (feature-구현하는 기능)
	```bash
   $ git branch feature-mainpage
   ```
	이미 생성된 브랜치가 있는 경우의 브랜치 변경 명령어는 `checkout` 입니다.
	
	```bash
	$ git checkout feature-mainpage
	```
	
2. 작업 완료 후 평소와 같이 push 워크플로우를 진행합니다.

   ```bash
   $ git add <작업한 디렉토리 경로>
   $ git commit -m "S03P12A211 | Front-end > Layout > Sidebar Modified"
   $ git push
   ```

3. Git Lab 프로젝트로 이동하여 Develop 브랜치와 Merge합니다.

# 팀 소개
### 나도내가무섭조
- 팀장 : 조원빈 (Front-End)
  
    Git : https://lab.ssafy.com/cwb5116
- 팀원
    - 김남규 (Back-End)
      
        Git : https://lab.ssafy.com/kngking22
    - 김보성 (IoT)
    
        Git : https://lab.ssafy.com/bosong111
    - 박세종 (Front-End)
    
        Git : https://lab.ssafy.com/tpwhd95
    - 최재빈 (Back-End)
    
        Git : https://lab.ssafy.com/justin613
    - 최정원 (IoT & Front-End)
    
        Git : https://lab.ssafy.com/jungwon9156

