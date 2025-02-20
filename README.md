<p align="center">
  <img src="https://readme-typing-svg.herokuapp.com/?font=Fira+Code&size=30&duration=3000&color=F74B00&center=true&vCenter=true&width=500&height=50&lines=🔥+Welcome+to+Tenstagram!" />
</p>

# 📰 뉴스피드
이 프로젝트는 `Spring Boot`와 `MySQL`을 사용하여 개발된 게시판 관리 API 입니다.<br>
`JWT` 방식의 인증 방법을 사용하고 있으며, `JPA`를 활용한 `MySQL DB`를 사용하고 있습니다.<br>
각종 상황에 따른 예외 처리를 제공하고 있습니다.<br>


![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-green?style=for-the-badge&logo=springboot&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-Hibernate%20ORM-%236DB33F?style=for-the-badge&logo=hibernate&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Authentication-%233A3A3A?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-6.6.5.Final-%236DB33F?style=for-the-badge&logo=hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange?style=for-the-badge&logo=mysql&logoColor=white)
[![GitHub](https://img.shields.io/badge/GitHub-Organization-black?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Tenstagram)

## 📌 주요 기능
### 1. 사용자 관리
   - **회원가입**: 새로운 사용자를 등록합니다.
   - **로그인**: 사용자 인증을 위한 JWT 기반 로그인입니다.
   - **사용자 정보 조회**: 사용자 정보를 확인할 수 있습니다.
   - **사용자 정보 수정**: 사용자 정보를 업데이트할 수 있습니다.
   - **사용자 탈퇴**: Soft Delet 방식의 회원 탈퇴 기능입니다.
### 2. 게시물 관리  
   - **게시글 작성**: 새로운 게시글을 생성합니다.
   - **게시글 전체 조회**: 필터링 및 페이징을 지원하는 게시글 목록 조회기능 입니다. 또한 차단한 사용자의 게시글을 숨김 처리할 수 있습니다.
   - **특정 게시글 조회**: 선택한 게시글의 상세 정보를 할 수 있습니다.
   - **친구 게시글 조회** : 서로 친구이거나 내가 친구 추가를 한 사용자의 게시글을 최신순으로 조회할 수 있습니다.
   - **특정 게시글 수정**: 선택한 게시글의 정보를 수정할 수 있습니다.
   - **특정 게시글 삭제**: Soft Delet 방식의 게시글 삭제 기능입니다.
### 3. 댓글 관리
   - **댓글 작성**: 게시글에 댓글을 추가합니다.
   - **특정 게시글의 댓글 전체 조회**: 특정 게시글의 댓글을 페이징 처리하여 조회합니다.
   - **특정 게시글의 베스트 댓글 조회**: 인기 있는 베스트 댓글을 확인합니다.
   - **특정 댓글 수정**: 선택한 댓글의 상세 정보를 할 수 있습니다.
   - **특정 댓글 삭제**: Soft Delet 방식의 댓글 삭제 기능입니다.
### 4. 좋아요 기능
   - **특정 게시글/댓글 좋아요 및 취소**: 각 게시글 및 댓글에 좋아요를 추가하거나 취소할 수 있습니다.
### 5. 관계 기능
   - **특정 사용자 친구 요청**: 사용자 간 친구 요청을 보낼 수 있습니다.
   - **특정 사용자 친구 요청 수락 및 거절**: 사용자 간 친구 요청에 대해 수락하거나 거절할 수 있습니다.
   - **팔로워 및 팔로잉 목록 조회** : 나를 친구 추가한 사용자와 내가 친구 추가한 사용자, 또는 맞팔로우 목록을 조회합니다.
   - **특정 사용자 차단**: 특정 사용자를 차단하여 관계를 관리할 수 있습니다.
   - **차단 목록 조회** : 내가 차단한 사용자의 목록을 조회합니다.
   - **특정 사용자 차단 해제** : 차단한 사용자의 차단 상태를 해제합니다.

## 🛠️ 기술 스택
### Backend
- Java 17+
- Spring Boot 3.4.2
- Spring Data JPA
- JPA Auditing

### Database
- MySQL 8.0

### Security
- JWT (JSON Web Token)
- BCrypt (`at.favre.lib:bcrypt:0.10.2`)

### Utilities
- Lombok


## 📆 개발 기간
2025-02-14 ~ 2025-02-20


## 📂 프로젝트 구조
```
src
└── main
    └── java/com/example/project
        ├── domain/
        │   ├── member/                # 사용자(Member) 도메인
        │   │   ├── controller/        # MemberController
        │   │   ├── service/           # MemberService
        │   │   ├── repository/        # MemberRepository
        │   │   ├── dto/               # MemberDTO
        │   │   ├── command/           # MemberCommand
        │   │   ├── entity/            # Member (JPA 엔티티)
        │   │   ├── exception/         # 사용자 관련 예외 처리
        │   │   └── util/              # 사용자 도메인 관련 유틸 클래스
        │   ├── relationship/          # 관계(Relationship) 도메인(친구요청,수락,차단)
        │   │   ├── controller/        # RelationshipController
        │   │   ├── service/           # RelationshipService
        │   │   ├── repository/        # RelationshipRepository
        │   │   ├── dto/               # RelationshipDTO
        │   │   ├── entity/            # Relationship (JPA 엔티티)
        │   │   ├── exception/         # 관계 관련 예외 처리
        │   │   └── util/              # 관계 도메인 관련 유틸 클래스
        │   ├── like/                  # 좋아요 도메인
        │   │   ├── controller/        # LikeController
        │   │   ├── service/           # LikeService
        │   │   ├── repository/        # LikeRepository
        │   │   ├── dto/               # LikeDTO
        │   │   ├── entity/            # Like (JPA 엔티티)
        │   │   ├── exception/         # 좋아요 관련 예외 처리
        │   │   └── util/              # 좋아요 도메인 관련 유틸 클래스
        │   ├── post/                  # 게시물(Post) 도메인
        │   │   ├── controller/        # PostController
        │   │   ├── service/           # PostService
        │   │   ├── repository/        # PostRepository
        │   │   ├── dto/               # PostDTO
        │   │   ├── entity/            # Post (JPA 엔티티)
        │   │   ├── exception/         # 게시물 관련 예외 처리
        │   │   └── util/              # 게시물 도메인 관련 유틸 클래스
        │   ├── comment/               # 댓글 도메인 (필요 시)
        │   │   ├── controller/        # CommentController
        │   │   ├── service/           # CommentService
        │   │   ├── repository/        # CommentRepository
        │   │   ├── dto/               # CommentDTO
        │   │   ├── entity/            # Comment (JPA 엔티티)
        │   │   ├── exception/         # 댓글 관련 예외 처리
        │   │   └── util/              # 댓글 도메인 관련 유틸 클래스
        ├── config/                    # 설정 (Security, CORS, DB 등)
        ├── exception/                  # 글로벌 예외 처리 (GlobalExceptionHandler)
        ├── util/                       # 공통 유틸리티 클래스
        ├── security/                   # 인증 및 권한 관련 클래스
        └── application.properties      # 환경설정 파일
```

## 🖼️ 와이어프레임
![image](https://github.com/user-attachments/assets/a50ffbb0-569f-48e8-8b3b-073c240cdfbb)
![image](https://github.com/user-attachments/assets/56c57806-bf62-49f0-bde4-1b398b5a8f9e)


## 💡 ERD
![image](https://github.com/user-attachments/assets/e17c07a9-c5ac-4801-9252-ce407b11a474)


## 📖 API 명세
자세한 API 명세는 API 문서를 통해 확인할 수 있습니다.

[API 문서 보기](https://www.notion.so/teamsparta/19a2dc3ef5148097a28fc04896650ae9?v=de53936dbb8a41539072c9374af6dc32&pvs=4)

## 📺 시연 영상
프로젝트 시연 영상은 영상 링크를 통해 확인할 수 있습니다.

[시연 영상 보기](https://drive.google.com/file/d/1IsLlN3AgyoCeHCh2FOPmvX2WIWExwAhE/view?usp=drive_link)


## ✨ 팀원
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/KimKiHong-1111">
        <img src="https://avatars.githubusercontent.com/u/188948297?v=4" width="100px;" alt=""/>
        <br /><b>김기홍</b>
      </a>
      <br /><small>사용자, 인증/인가</small>
    </td>
    <td align="center">
      <a href="https://github.com/bopeep934">
        <img src="https://avatars.githubusercontent.com/u/191215415?v=4" width="100px;" alt=""/>
        <br /><b>송윤정</b>
      </a>
      <br /><small>게시물</small>
    </td>
    <td align="center">
      <a href="https://github.com/3uomlkh">
        <img src="https://avatars.githubusercontent.com/u/86907076?v=4" width="100px;" alt=""/>
        <br /><b>이채원</b>
      </a>
      <br /><small>팔로우/차단</small>
    </td>
    <td align="center">
      <a href="https://github.com/uyr83157">
        <img src="https://avatars.githubusercontent.com/u/192572219?v=4" width="100px;" alt=""/>
        <br /><b>정의용</b>
      </a>
      <br /><small>댓글</small>
    </td>
  </tr>
</table>
