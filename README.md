# KNU CSE Solve

## KNI CSE Solve v0.0.1

> 경북대학교 컴퓨터학부 커뮤니티 웹 사이트<br> 
> 개발 기간 : 2023.09 ~ 2023.11

## 프로젝트 소개

> KNU CSE Solve는 경북대학교 컴퓨터학부 커뮤니티입니다. 경북대학교 컴퓨터학부는 1000명 이상의 학생 수에 걸맞게 많은 수업이 있고, 하나의 수업에서도 많은 학생 수를 배정할 수 없어 여러 분반으로 나뉘어져 있으며, 수업을 진행하시는 교수님 또한 분반마다 다른 모습을 보입니다. <br> 인원수 1000명이 넘는 대형학부이며 복수전공자가 많은 컴퓨터학부 특성상 혼자서 수업을 듣거나 수업의 내용을 따라가기 힘든 경우가 많습니다. 이러한 불편함을 KNU CSE Solve 커뮤니티 속에서 함께 공유하며, 과제나 시험에 좋은 결과를 얻게 될 수 있을 것으로 기대됩니다.
> 

## 배포 주소


> [https://www.knucsesolve.com/](https://www.knucsesolve.com/)
> 

## 데모 동영상 주소


> [https://youtu.be/3N-cxHPmVwQ?si=2ShA6Jl5NkE4lzyO](https://youtu.be/3N-cxHPmVwQ?si=2ShA6Jl5NkE4lzyO)
>
[![Video Label](http://img.youtube.com/vi/3N-cxHPmVwQ/0.jpg)](https://youtu.be/3N-cxHPmVwQ)

## 개발 환경

```
Spring Boot Version :       v2.7.0
JDK version :               JDK11
Build Tool:                 Gradle 7.6.1
ORM :                       Spring Data JPA
Auth:                       Spirng Security 
Template Engine:            Thymeleaf
Database:                   MySQL community
```

## Stacks

### IDE
![IntelliJ IDEA Ultimate](https://img.shields.io/badge/IntelliJ_IDEA_Ultimate-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![MySQL Workbench](https://img.shields.io/badge/MySQL_Workbench-4479A1?style=for-the-badge&logo=mysql&logoColor=white)


### Development
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005C0F?style=for-the-badge)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)

## 프로젝트 주요 기능 

### 커뮤니티 기능
- 루트 페이지를 통해 실시간으로 등록되는 게시글과 댓글을 확인할 수 있으며, 상위 게시판의 게시글을 한눈에 확인할 수 있습니다.
- 여러 게시판에 게시글을 등록할 수 있으며, 작성한 게시글을 수정, 삭제할 수 있습니다.
- 또한 게시글에 추천을 할 수 있으며, 사용자는 게시글을 스크랩하여 저장하여 조회할 수 있습니다
- 게시글에는 댓글을 작성할 수 있으며, 삭제할 수 있습니다.
- 댓글에는 대댓글을 작성할 수 있고, 댓글을 할 수 있습니다.
- 관리자 권한을 가진 사용자는 모든 사용자의 데이터를 수정/삭제할 수 있습니다.

### 마이페이지 기능
- 사용자는 프로필 사진과 같은 사용자의 정보를 열람하고 수정할 수 있습니다.
- 사용자가 작성한 게시글을 확인할 수 있습니다.
- 사용자가 작성한 댓글을 확인할 수 있습니다.
- 사용자가 스크랩한 게시글을 확인할 수 있습니다.

## 프로젝트 디렉토리 구조
```
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─insung
    │  │          └─knucsesolve
    │  │              ├─config
    │  │              ├─controller
    │  │              │  ├─auth
    │  │              │  │  └─dto
    │  │              │  │      └─rest
    │  │              │  ├─community
    │  │              │  │  └─dto
    │  │              │  │      ├─board
    │  │              │  │      │  ├─rest
    │  │              │  │      │  └─view
    │  │              │  │      ├─comment
    │  │              │  │      │  ├─rest
    │  │              │  │      │  └─view
    │  │              │  │      └─post
    │  │              │  │          ├─rest
    │  │              │  │          └─view
    │  │              │  ├─file
    │  │              │  └─my
    │  │              │      └─dto
    │  │              │          ├─rest
    │  │              │          └─view
    │  │              ├─domain
    │  │              │  ├─board
    │  │              │  ├─comment
    │  │              │  ├─member
    │  │              │  └─post
    │  │              ├─exception
    │  │              ├─handler
    │  │              ├─repository
    │  │              │  ├─board
    │  │              │  ├─comment
    │  │              │  ├─member
    │  │              │  └─post
    │  │              ├─service
    │  │              │  ├─auth
    │  │              │  ├─community
    │  │              │  ├─file
    │  │              │  └─my
    │  │              └─util
```
## 시스템 아키텍처
![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/4b2dbedc-78f7-4ba1-95ba-caab488770b8)

## 화면 구성
| 로그인 페이지 | 회원가입 페이지 | 
| --- | --- |
| ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/b9575320-fb10-444b-8180-e2b793b95d8a) | ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/9ec5faab-8617-4320-8b33-4beee54baf8f) |
| 루트 페이지 | 루트 페이지(2) |
| ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/c466758f-c941-497f-8736-a7986f9c27e9) | ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/c827dd18-d203-47df-b923-590bc212d7de)|
| 게시판 페이지 | 게시판 페이지(2)|
| ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/1c1f08b5-fd47-4bf5-baf3-d511f8fee25d) | ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/77c6b0f3-a5f8-40c7-92fa-9082f6f00e70) |
| 게시판 페이지(3) | 게시판 페이지(4)|
|![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/93bfe95d-e6fd-44c6-af4c-63775aeebf0e)|![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/b7f28438-e262-40d1-8e82-545387e39d7e) |
| 게시글 페이지 | 게시글 페이지 (2)|
|![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/c5127620-cc12-4282-ab15-afcf24a695d9) | ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/aac95cc0-273c-47cf-9e0e-c07400a1b960) |
| 게시글 작성 페이지 | 게시글 수정 페이지 |
|![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/5da542d1-fb10-4f89-bb09-4717a10c334b) | ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/d1988415-3264-442c-a69f-64c83668048d) |
| 마이페이지 | 사용자 작성글 페이지 |
| ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/b959a6df-3a5a-46dc-92d7-2c9606d545fb) | ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/36d5f66b-0c57-43fe-b5a5-b45b27581e8d) |
| 사용자 작성댓글 페이지 | 사용자 스크랩 페이지 |
| ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/c08fb6f9-3d1b-4542-a832-8566a8dd7668) | ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/4a9c6b13-2e7e-4459-ac41-4e8d94284379) |
| 사용자 정보 수정 페이지 | 사용자 비밀번호 변경 페이지|
| ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/70e7051b-eb6f-4e6e-9db2-1a745b2027a7) | ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/18498e52-25ba-4604-ade0-95a66904de35) |
| 사용자 계정 비활성화 페이지 | 모든 게시판 페이지 |
| ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/da3803ae-4b15-4505-af2a-52e39b964790) | ![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/290b6b71-3d2c-4a60-9cb2-c8720f40af19) |

## API 설계
### 커뮤니티 관련 api
#### view
| Method | URL | 기능 |
| --- | --- | --- |
| GET | / | RootPage로 이동 |
| GET | /board/{boardId}?page={page} | 게시판의 게시글의 page번째 페이지로 이동 |
| GET | /board/{boardId}?hot=true&page={page} | 게시판의 인기 게시글의 page번째 페이지로 이동 |
| GET | /post/{postId} | 게시글로 이동 |
| GET | /post/{postId}?page={page} | 게시글로 이동 및 게시판의 page번째 페이지를 출력 |
| GET | /write/{boardId} | 게시판에 게시글을 작성하는 페이지로 이동 |
| GET | /rewrite/{postAuthorId}/{postId} | postAuthorId가 작성한 postId에 해당하는 게시글을 수정하는 페이지로 이동 |
| GET | /read-comment/{postId}?page={page} | 게시글의 page번째 댓글 페이지를 출력 |
| GET | /manage-board | 게시판 관리 페이지로 이동 |
| GET | /all-board | 모든 게시판 페이지로 이동 |
| GET | /board-menu | 상위 n개의 게시판 미리보기를 출력 |
#### rest
| Method | URL | 기능 |
| --- | --- | --- |
| POST | /api/save-post | 게시글을 저장 |
| PATCH | /api/update-post | 게시글을 수정 |
| DELETE | /api/delete-post | 게시글을 삭제 |
| POST | /api/save-comment | 댓글을 저장 |
| DELETE | /api/delete-comment | 댓글을 삭제 |
| POST | /api/inc-post-recommend | 게시글의 추천수 증가 및 게시글에 추천한 사용자 등록 |
| POST | /api/inc-comment-recommend | 댓글의 추천수 증가 및 댓글에 추천한 사용자 등록 |
| POST | /api/inc-post-scrap | 게시글의 스크랩 수 증가 및 게시글을 스크랩한 사용자 등록 |
| POST | /api/save-board | 게시판을 저장 |
### 마이페이지 관련 api
#### view
| Method | URL | 기능 |
| --- | --- | --- |
| GET | /my/{memberId} | 사용자의 마이페이지로 이동 |
| GET | /my/post/{memberId}?page={page} | 사용자가 작성한 게시글의 page 번째 페이지로 이동 |
| GET | /my/comment/{memberId}}?page={page} | 사용자가 작성한 게시글의 page 번째 페이지로 이동 |
| GET | /my/scrap/{memberId}}?page={page} | 사용자가 작성한 게시글의 page 번째 페이지로 이동 |
| GET | /my-edit/{memberId} | 사용자의 프로필 수정 페이지로 이동 |
| GET | /my-edit/pw/{memberId} | 사용자의 비밀번호 수정 페이지로 이동 |
| GET | /my-edit/withdraw/{memberId} | 사용자의 계정 활성화 여부 수정 페이지로 이동 |
#### rest
| Method | URL | 기능 |
| --- | --- | --- |
| POST | /api/update-my | 사용자 프로필 정보 수정 |
| DELETE | /api/delete-my-post | 사용자 작성 게시글 삭제 |
| DELETE | /api/delete-my-comment | 사용자 작성 댓글 삭제 |
| POST | /api/delete-my-scrap | 사용자 스크랩 기록 삭제 |
| PATCH | /api/update-my-password | 사용자 비밀번호 수정 |
| PATCH | /api/withdraw | 사용자 계정 비활성화 |
### 사용자 인증 관련 api
#### view
| Method | URL | 기능 |
| --- | --- | --- |
| GET | /login | 로그인 페이지로 이동 |
| GET | /signup | 회원가입 페이지로 이동 |
#### rest
| Method | URL | 기능 |
| --- | --- | --- |
| POST | /login | 스프링 시큐리티 로그인 |
| POST | /logout | 스프링 시큐리티 로그아웃 |
| POST | /api/signup | 회원가입 |

## ERD 
![image](https://github.com/2insung/KNU-CSE-SOLVE/assets/84179188/83f45676-f7a1-47da-a181-2e2c1c684318)


## 엔티티 설계
### member - 회원

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| is_deleted | boolean | not null | 비활성화 여부 |
| created_at | timestamp | not null | 가입 시간 |

### member_auth - 회원 인증 정보

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| member_id | int | Foreign Key | 회원 아이디 |
| username | varchar(50) | unique not null | 유저네임 |
| password | varchar(200) | not null | 비밀번호 |
| role | varchar(20) | not null | 권한 |

### member_detail - 회원 프로필 정보

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| member_id | int | Foreign Key | 회원 아이디 |
| nickname | varchar(20) | unique not null | 회원 닉네임 |
| profile_image | varchar(255) |  | 프로필 이미지 |
| description | varchar(100) |  | 자기소개 |
| grade | varchar(1) |  | 학년 |
| admission_year | varchar(4) |  | 입학년도 |
| department | varchar(20) |  | 학과 |

### board - 게시판
| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| alias | varchar(20) | unique not null | 게시판 이름 |
| description | varchar(100) | not null | 게시판 설명 |
| category | varchar(20) | not null | 게시판 카테고리 |

### board_stat - 게시판 통계

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| board_id | varchar(50) | Foreign Key | 게시판 아이디  |
| post_count | varchar(20) | not null | 게시글 수 |
| hot_post_count | varchar(100) | not null | 인기 게시글 수 |

### post - 게시글

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| board_id | varchar(50) | Foreign key | 게시판 아이디 |
| member_id | varchar(20) | Foreign key | 작성자 아이디 |
| priority | varchar(100) | not null | 게시글 우선순위 |
| is_notice | varchar(20) | not null | 공지사항 여부 |
| is_hot | boolean | not null | 인기글 여부 |
| is_deleted | boolean | not null | 삭제 여부 |
| created_at | timestamp | not null | 생성 시간 |
| hot_registered_at | timestamp |  | 인기글 등록시간 |
| category | varchar(20) | not null | 카테고리 |

### post_content - 게시글 내용

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| post_id | int | Foreign Key | 게시글 아이디  |
| title | varchar(50) | not null | 게시글 제목 |
| body | text | not null | 게시판 본문 |
| summary | varchar(100) | not null | 게시판 요약 |
| thumbnail | varchar(200) |  | 게시글 썸네일 |
| updated_at | timestamp |  | 게시글 수정시간 |

### post_stat - 게시글 통계

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| post_id | int | Foreign Key | 게시글 아이디 |
| hit_count | int | not null | 조회 수 |
| recommend_count | int | not null | 추천 수 |
| comment_count | int | not null | 댓글 수 |
| total_comment_count | int | not null | 전체 댓글 수 |
| scrap_count | int | not null | 스크랩 수 |

### post_recommend_member - 게시글에 추천한 회원

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| post_id | int | Foreign Key | 게시글 아이디 |
| member_id | int | Foreign Key | 추천한 회원 아이디 |

### post_scrap_member - 게시글에 스크랩한 회원

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| post_id | int | Foreign Key | 게시글 아이디 |
| member_id | int | Foreign Key | 스크랩한 회원 아이디 |
| scrapped_at | timestamp | not null | 스크랩 등록 시간 |

### comment - 댓글

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| member_id | varchar(50) | unique not null | 댓글 작성자 아이디  |
| post_id | varchar(20) | unique not null | 게시글 아이디 |
| parent_member_id | varchar(100) | not null | 부모 댓글 작성자 아이디 |
| root_comment_id | varchar(20) | not null | 루트 댓글 아이디 |
| is_post_author | boolean | not null | 게시글 작성자의 댓글 여부 |
| is_root | boolean | not null | 루트 댓글 여부 |
| is_root_child | boolean | not null | 루트 댓글의 자식 여부 |
| is_deleted | boolean | not null | 삭제 여부 |
| body | varchar(500) | not null | 댓글 본문 |
| created_at | timestamp | not null | 생성 시간 |
| group_created_at | timestamp | not null | 댓글 그룹 생성 시간 |
| child_count | int | not null | 자식 댓글 수 |

### comment_stat - 댓글 통계

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| comment_id | int | Foreign Key | 댓글 아이디  |
| recommend_count | int | not null | 추천 수 |

### comment_recommend_member - 댓글에 추천한 회원

| 컬럼명 | 데이터 타입 | 조건 | 설명 |
| --- | --- | --- | --- |
| id | int | Primary Key | 아이디 |
| comment_id | int | Foreign Key | 댓글 아이디 |
| member_id | int | Foreign Key | 추천한 회원 아이디 |
