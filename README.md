# 💻 프로젝트 소개

### ♾️ 팀 플로우(Team Flow)

<img src="https://github.com/user-attachments/assets/ebe0e049-b97a-48d3-a3be-30a03f17323f" width="300" height="300"/>


#### 🟣 Team Flow는 개발자 간 원활한 협업을 지원하기 위해 설계된 종합 협업툴입니다. 
#### 🟣 코드 작성부터 프로젝트 관리, 커뮤니케이션까지 개발 생태계 전반을 아우르는 기능을 제공하여 팀워크와 생산성을 극대화합니다.

#### 

## 👨‍💻 팀 소개

- #### **취업하조** <br>
  ![스크린샷 2024-12-30 174530](https://github.com/user-attachments/assets/4b14f68c-fa10-459b-aeb4-38cff71a0d2c)
  
 | name  | role |                    구현 기능                    |     Github      |
  |:-----:|:----:|:-------------------------------------------:|:---------------:|
  |  천경환  |  팀장  |      유저, 멤버 및 역할 관리(인가), 알림, 배포 & CICD      |  <a href="https://github.com/GyeonghwanCheon"><img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white"></a>    |
  |  한지연  |  팀원  |             워크스페이스, 보드, 보드 리스트              |    <a href="https://github.com/j-hann"><img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white"></a>    |
  |  김세원  |  팀원  | 카드, 댓글, 첨부파일(S3), 검색, 스프링 시큐리티(인증), 최적화 | <a href="https://github.com/taketheking"><img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white"></a>  |
  
  <br>

## 🚀 개발 기간

> 2024.12.23 - 2024.12.30

## 🛠️기술 스택

### 🌱 프로그래밍 언어

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">

### 🌱 개발 환경

- **IDE** : IntelliJ
- **JDK** : openjdk version '17.0.2'
- **Framework** : springframework.boot version '3.4.0', Spring Data JPA
- **Library** : Lombok, Bcrypt, Junit, QueryDSL
- **Build Tool** : Gradle, Docker
- **Database** : MySQL version '8.0.40'
- **Infra** : AWS EC2, Amazon S3, AWS RD (MySQL '8.0.40')
- **Tool** : Miro, ERD Cloud, Slack, Github & git, Postman

## 🪧 커밋 컨벤션

🎉 Begin: 프로젝트 시작 <br>
✨ Feat : 새로운 기능 추가, 구현<br>
📝 Docs : 문서 파일 추가 및 수정<br>
🔧 Add :  파일 추가 & 코드 수정<br>
✏️ Typos : 단순 오타 수정<br>
🐛 Fix : 버그 수정<br>
✅ Test : 테스트 코드 작성 & 수정<br>
🚚 Rename : 파일, 경로를 옮기거나 이름 변경<br>
🎨 Rename : 코드의 구조, 형태 개선<br>
♻️ Refactor : 코드 리팩토링<br>
💡 Comment : 주석 추가, 변경<br>
🔥 Remove : 파일, 코드 삭제<br>
🔀 Branch : 브랜치 추가, 병합 등<br>
🏗️ Chore : 빌드 업무 수정, 패키지 매니저 수정, 패키지 관리자 구성 등

## 🪐구현 기능

#### **✨ 유저**

* 회원가입
* 회원탈퇴
* 로그인
* 로그아웃

#### **✨ 멤버 및 역할관리**
* 유저 권한 (일반 유저, 관리자)
* 멤버 역할(워크스페이스, 보드, 읽기 전용)

#### **✨ 워크스페이스**
* 워크스페이스 생성
* 워크스페이스 멤버 초대
* 워크스페이스 조회
* 워크스페이스 수정
* 워크스페이스 삭제 (포함된 보드 삭제)

#### **✨ 보드**
* 보드 생성 (배경색 또는 이미지 첨부 가능)
* 보드 수정
* 보드 조회 (보드 리스트와 카드 함께 조회)
* 보드 삭제 (포함된 보드 리스트 삭제)

#### **✨ 보드 리스트**
* 보드 리스트 생성
* 보드 리스트 수정 (보드 내에서 순서 변경)
* 보드 리스트 조회
* 보드 리스트 삭제 (포함된 카드 삭제)

#### **✨ 카드**
* 카드 생성 (마감일, 담당자 멤버 포함)
* 카드 수정
* 카드 조회 (댓글 포함 조회)
* 카드 삭제

#### **✨ 댓글**
* 댓글 생성 (이모지 포함)
* 댓글 수정
* 댓글 조회
* 댓글 삭제

#### **✨ 검색**
* 카드 검색 기능 (페이징)

#### **✨ 첨부파일**
* 첨부파일 추가 (이미지, 문서)
* 첨부파일 조회
* 첨부파일 삭제

#### **✨ 알림**
* 실시간 알림 (슬랙 API)

#### **✨ 최적화**
* 카드 검색 인덱스

#### **✨ 배포 & CI/CD**

#### **✨ 스프링 시큐리티**
* 로그인 인증

## 📅 와이어 프레임

<details>
<summary>와이어 프레임</summary>

- [Miro link](https://miro.com/app/board/uXjVL0f5P2o=/?passwordless_invite=)

![image](https://github.com/user-attachments/assets/fa6c064e-1e0f-4ecf-9b04-50a95322b1bc)

![image](https://github.com/user-attachments/assets/dc0d963c-1786-428b-a9d3-169002686415)

![image](https://github.com/user-attachments/assets/59638684-dda9-4663-9a49-f652e8a5992e)

</details>

## ⚙️ ERD

<details>
<summary>ERD</summary>

- [ERD Cloud link](https://www.erdcloud.com/d/6oBfZTL4uPCb937h4)

![image](https://github.com/user-attachments/assets/f8cb409a-e954-4ca6-9500-29217b51a669)
</details>

## 📑 API 명세서

<details>
<summary>API 명세서</summary>

- 유저

</details>

## 🌟 실행 화면

> postman API Test & MySQL Workbench
<details>
<summary>🙋 </summary>

#### ⭐ 회원가입

- DB 조회


</details>

## 🎙️ 프로젝트 소감
![image](https://github.com/user-attachments/assets/2e1e2faa-79b9-4594-b048-74b011671a12)





