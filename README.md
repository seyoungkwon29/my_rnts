# **출발했어?!** 백엔드

## 프로젝트 소개
### 왜 이 프로젝트를 만들었는가?
- 친구들과 모임에서 종종 지각하는 사람들이 있었는데, 어떻게 하면 늦지 않도록 습관을 고칠 수 있을까 고민하다가 이 프로젝트를 기획하게 되었습니다.
- 벌칙을 주는 사람과 받는 사람이 서로 기분이 상하지 않는 선에서 벌칙을 지정하여, 지각하는 사람에게 벌칙을 주는 방식으로 진행됩니다. 이를 통해 늦지 않도록 동기를 부여합니다.

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

## 기술 스택
- **Java**: 백엔드 로직 구현
- **Spring Boot**: 프로젝트의 핵심 프레임워크
- **JPA**: 데이터베이스 ORM 처리
- **Redis**: 이메일 인증을 위한 캐시 서버
- **Naver Cloud**: 클라우드 인프라 서비스
- **Docker**: 컨테이너화된 배포 환경 구축
- **GitHub Actions**: CI/CD 파이프라인

## 인프라 아키텍처
Users → Naver Cloud → Spring Boot Application
                      ↓
                  Redis (Caching)
                      ↓
                Docker (Deployment)
                      ↓
               GitHub Actions (CI/CD)

## 트러블슈팅
- **동시성 제어 문제 해결**: 다수의 사용자가 동시에 약속에 체크인하는 상황에서 동시성 제어가 필요했습니다. 관련 내용은 [여기](https://velog.io/@seyoungkwon29/posts)에서 확인할 수 있습니다.