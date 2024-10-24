# **출발했어?!**

## 프로젝트 소개
### 왜 이 프로젝트를 만들었는가?
- 친구들과 모임에서 종종 지각하는 사람들이 있었는데, 어떻게 하면 늦지 않도록 습관을 고칠 수 있을까 고민하다가 이 프로젝트를 기획하게 되었습니다.
- 벌칙을 주는 사람과 받는 사람이 서로 기분이 상하지 않는 선에서 벌칙을 지정하여, 지각하는 사람에게 벌칙을 주는 방식으로 진행됩니다. 이를 통해 늦지 않도록 동기를 부여합니다.

## 트러블슈팅
- **동시성 제어 문제**: 다수의 사용자가 동시에 약속에 체크인하는 상황에서 동시성 제어가 필요했습니다. 관련 내용은 [여기](https://velog.io/@seyoungkwon29/posts)에서 확인할 수 있습니다.

## 기술 스택
<div align="center">
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
  <img src="https://img.shields.io/badge/jpa-007393?style=for-the-badge&logo=jpa&logoColor=white">
  <br>
  <br>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white">
  <br>
  <br>
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/ncloud-03C75A?style=for-the-badge&logo=naver&logoColor=white">
  <img src="https://img.shields.io/badge/github actions-2088FF?style=for-the-badge&logo=github actions&logoColor=white">
</div>

## 주요 기능
### 회원 관리
- **회원가입**
  - 회원가입 시 Redis를 활용한 이메일 인증
- **로그인**
  - 일반 로그인: JWT와 Spring Security 필터를 이용한 인증
  - 소셜 로그인: OAuth2.0을 이용한 카카오 로그인

### 약속 관리
- **약속 생성, 수정, 삭제**
  - 위도와 경도를 포함한 약속 장소 저장
- **약속 조회**
  - 남은 약속: 현재 시각을 기준으로 예정된 약속 조회
  - 지난 약속: 지난 약속 조회
- **약속 초대, 거절**
  - 링크를 통해 타인을 초대하고, 수락 시 약속 스페이스에 추가
- **사용자 정의 약속 유형 생성, 조회, 삭제**
  - 기본 약속 유형 외에, 사용자가 커스텀한 약속 유형 생성

### 체크인 및 패널티 기능
- **체크인**
  - 약속 시작 시 각 사용자는 체크인할 수 있습니다.
  - 약속 컨트롤러와 서비스 계층에서 체크인 로직을 처리하며, 별도의 엔티티는 없습니다.
- **패널티**
  - 1등으로 도착한 사용자는 지각자에게 줄 패널티를 지정할 수 있습니다.
