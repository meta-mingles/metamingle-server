<div align="center">

# 🦄 Meta Mingle 메타밍글 : Backend

<br>

<img src="https://github.com/meta-mingles/.github/assets/88484476/6c623e90-4758-423b-b493-56f548f5b6d2" width="500"/>

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fmeta-mingles%2Fmetamingle-server&count_bg=%23FFA49F&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=views&edge_flat=false)](https://hits.seeyoufarm.com)

`MINGLE : 돌아다니다, 섞이다, 어우러지다`

 **메타 밍글은 숏폼, 인터랙티브 무비를 통한 문화 교류 커뮤니티 메타버스 플랫폼입니다.**

### **언어로 소통하고, 문화로 연결하며, 창작으로 재미를 더하는 MetaMingle!**

</div>

<br>

## 👨‍👩‍👧‍👦 주요 기능 소개
### 크리에이터 툴

**`1️⃣ 웹캠 - Unity 실시간 모션트래킹을 통해 아바타 동영상 제작 `**

**`2️⃣ 사용자가 입력한 경험담으로 AI 대본, 배경 이미지, BGM 생성 `**

**`3️⃣ 사용자가 만든 영상에 한/영 자막 자동 생성 `**

### 플랫폼

**`1️⃣ 사용자 경험담으로 AI가 만들어주는 퀴즈`**

**`2️⃣ 다중 사용자 환경 & 채팅`**

**`3️⃣ 크리에이터들이 제작한 동영상 감상`**

<br>

## 시연 영상
[![Video Label](https://github.com/meta-mingles/.github/assets/88484476/e7527860-f455-41a6-a82c-7cd4f2e8c83b)](https://youtu.be/TtD8kEjEgMs)


<br>

## 👋 팀원 소개
<table>
  <tr>
    <td align="center"><a href="https://github.com/Dylan-SonJungin"><img src="https://avatars.githubusercontent.com/silverpoodle" width="150px;" alt="">
    <td align="center"><a href="https://github.com/numerical43"><img src="https://avatars.githubusercontent.com/numerical43" width="150px;" alt="">
  </tr>
  <tr>
    <td align="center"><a href="https://github.com/silverpoodle"><b>손정인</b></td>
    <td align="center"><a href="https://github.com/numerical43"><b>강수의</b></td>
  </tr>
    <tr>
    <td align="center"><strong>회원, 아바타, 대본 스트리밍, AI 배경&BGM</strong></td>
    <td align="center"><strong>숏폼(영상), 인터렉티브 무비(영상), 좋아요, 배포 자동화</strong></td>
  </tr>
</table>

<br>

## ⚒️ 기술 스택
<img src="https://img.shields.io/badge/Java-5382a1?style=for-the-badge&logo=Java&logoColor=f89820"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/node.js-339933?style=for-the-badge&logo=Node.js&logoColor=white"> 
<br>
<img src="https://img.shields.io/badge/firebase-1B3A57?style=for-the-badge&logo=firebase&logoColor=F5820D"> <img src="https://img.shields.io/badge/Redis-c93131?style=for-the-badge&logo=Redis&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-f29111?style=for-the-badge&logo=MySQL&logoColor=00758f"> <img src="https://img.shields.io/badge/MongoDB-001E2B?style=for-the-badge&logo=MongoDB&logoColor=00ED64">
<br>
<img src="https://img.shields.io/badge/Jenkins-181717?style=for-the-badge&logo=Jenkins&logoColor=white"> <img src="https://img.shields.io/badge/Docker-384d54?style=for-the-badge&logo=Docker&logoColor=0db7ed">

<br>

## 🧱 아키텍처
![Server](https://github.com/meta-mingles/metamingle-server/assets/61495627/457e4e29-16ba-450f-9d78-80998e7bea35)


<br>

## ✨ 협업 도구
<img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white"/> <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/>  <img src="https://img.shields.io/badge/Miro-F7DF1E?style=for-the-badge&logo=Miro&logoColor=black"/>

<br>

## 📌 컨벤션
### 1️⃣ 커뮤니케이션 컨벤션
- 모여서 회의가 불가능할 경우 **Discord**를 통해 회의합니다.
- 논의 사항과 Trouble Shooting은 **Git Discussion**에 정리합니다.
- 협업 툴 : **Github**, **Discord**

<br>

### 2️⃣ 코드 컨벤션
#### 📝 클래스 명칭
```
🐤 [ 도메인 이름 ] + [ Command / Query ] + [ Domain / Infra ] + [ Controller / Service / Repository]
```
#### 📝 메소드 명칭

메소드 명은 🐫(Camel Case)로 표기할 것!

```
C : create + [ 명사 ]

R : find + [ 명사 ]

U : update + [ 명사 ]

D : delete + [ 명사 ]
```

#### 📝 API 응답

응답의 형태는 Common 패키지에서 공용으로 사용되며 공통 양식을 유지할 것!
> 출처 : https://wildeveloperetrain.tistory.com/m/240

![image](https://github.com/cca-ffodregamdi/running-hi-back/assets/115992753/4dd76c8d-dcc3-486d-830c-cda93a5ecb39)


#### 📝 DTO
DTO(Data Transfer Object)를 request와 response로 나누어 제작할 것!

<br>

### 3️⃣ GitHub : PR & Commit  컨벤션

#### ✅ **Git Convention**
| **Convention**  | **내용**                                                         |
|-----------------|----------------------------------------------------------------|
| **Feat**        | 새로운 기능 추가                                                      |
| **BugFix**         | 버그 수정                                                          |
| **Test**        | 테스트 코드, 리펙토링 테스트 코드 추가, Production Code(실제로 사용하는 코드) 변경 없음     |
| **Comment**     | 필요한 주석 추가 및 변경                                                 |
| **Rename**      | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우                                   |
| **Remove**      | 파일을 삭제하는 작업만 수행한 경우                                            |
| **Refactor** | 프로덕션 코드 리팩토링                                                   |
| **API** | 서버 API 통신                                                   |
| **Deploy** | 배포 관련                                                   |
| **Setting** | 개발환경 세팅                                                   |

#### ✅ **Branch 명칭**
```
🧸 [ Commit 이름 ] / [ 도메인 이름 ] / [ 구현 기능 ]
```

#### ✅ **PR & Commit 규칙**

- main branch에 바로 push 금지! develop branch로 Pull requests 하기.
- git convention을 지키기.
- PR에 적극적으로 코드 리뷰 남기기.
