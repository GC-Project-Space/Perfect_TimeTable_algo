# Perfect Table
> 알고리즘 13조 텀프로젝트

**목차**
- [Perfect Table](#perfect-table)
  - [프로젝트 개요](#프로젝트-개요)
    - [왜 해당 프로젝트를 기획 했는가?](#왜-해당-프로젝트를-기획-했는가)
    - [기존의 서비스와의 차별점](#기존의-서비스와의-차별점)
    - [프로젝트의 진행 방식](#프로젝트의-진행-방식)
    - [프로젝트 도구](#프로젝트-도구)
  - [참여 인원](#참여-인원)
  - [Git 컨벤션](#git-컨벤션)
    - [commmit message 요약](#commmit-message-요약)

<br>

## 프로젝트 개요
### 왜 해당 프로젝트를 기획 했는가?

### 기존의 서비스와의 차별점
<기존 서비스>
대학생 커뮤니티 "에브리타임" - 시간표 마법사
<차별점>
1. 공강 시간을 고려하여 시간표를 짠다.
2. 하루 최대 강의시간을 설정할 수 있다.
3. 필수 분야와 선택 분야를 나누어 시간표에 반영할 수 있다.
   
### 프로젝트의 진행 방식
1. 크롤링을 통해 가천대 각 과목 정보를 모은다.
2. 해당 정보를 로컬 DB에 저장
3. 최적의 시간표를 짜주는 알고리즘을 고안
4. 알고리즘을 안드로이드 앱에 적용하여, 최적의 시간표 앱 개발

### 프로젝트 도구
- Android studio
- [Notion](https://radial-morocco-c4c.notion.site/a6871f705c3f48738ec1621ad21e17fa?v=4498f4a8b63c4d9b840d5fa85d887b7c&pvs=4)
- Kakao Work
- Figma

## 참여 인원
<center>

|![이희재](https://avatars.githubusercontent.com/u/83583699?v=4)|![하나/심세원](https://avatars.githubusercontent.com/u/105415118?v=4)|![이지현]()|![박예림]()|![카일로/김현겸]()|
|:---:|:---:|:---:|:---:|:---:|
|[이희재](https://github.com/Heejae-L)|[하나/심세원](https://github.com/ShimFFF)|[이지현]()|[박예림]()|[강현승]()|

</center>

## Git 컨벤션
- [자세한 commit rule](https://github.com/GC-Project-Space/Convention/blob/main/forGithub/commit.md)

### commmit message 요약
깃모지, 태그 : 제목의 형태
- `:sparkles: Feat : 새로운 기능 추가`
- `:bug: Fix : 버그 수정`
- `:memo: Docs : 문서 수정`
- `:white_check_mark: Test : 테스트 코드 추가`
- `:recycle: Refactor : 코드 리팩토링`
- `:art: Style : 코드 의미에 영향을 주지 않는 변경사항(코드 포맷팅, 세미콜론 누락 등..)`
- `:wrench: Chore : 빌드 부분 혹은 패키지 매니저 수정사항`
- `:truck: Rename : 파일, 경로, route를 옮기거나 이름 변경`
- `:fire: Remove : 삭제(파일, 코드)`
