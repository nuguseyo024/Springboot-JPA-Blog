# Springboot-JPA-Blog
## 메타코딩 - Springboot - 나만의 블로그 만들기 강좌
##### Reference
https://www.youtube.com/watch?v=6bhF5o4gAOs&list=PL93mKxaRDidECgjOBjPgI3Dyo8ka6Ilqm&index=1

1. Board, Reply, User 로 구성
2. Board. 게시판 
- 게시글 CRUD
- 자신이 작성한 게시글만 수정, 삭제 가능
- 게시글 페이징 처리
- summernote 라이브러리 사용 

2. Reply. 댓글
- 댓글 작성, 삭제
- 자신이 작성한 댓글만 삭제 가능

3. User
- 회원가입 및 로그인
- 스프링 시큐리티 로그인
- 카카오 로그인
  - 기존에 카카오로 가입된 정보인지 확인 후 정보가 없을 시 바로 카카오 회원가입 후 로그인 처리
  - 카카오로 회원 가입한 유저는 정보 수정 불가
- 회원 정보 수정 시 시큐리티 세션도 변경되도록 함

3-1 스프링 시큐리티 로그인 
- 로그인 요청 시 시큐리티가 username과 password를 가로채서 내부적으로 로그인을 진행하고, 
- 로그인이 완료되면 시큐리티가 들고 있는 시큐리티 세션으로 유저 정보를 등록한다.
- 유저 정보는 IoC 된 정보로, 필요할 때 가져다 쓸 수 있음
- 일반적인 int, String 값은 시큐리티에 등록할 수 없고, 해시값으로 변환해야 함

3-2 카카오 로그인 Oauth (라이브러리 사용x)
- 카카오 소셜 로그인일 경우 기존 회원가입과 구별하기 위해 oauth 컬럼에 kakao 추가
- 사용자가 blog로 로그인 요청 -> <카카오 로그인 버튼 클릭시> -> blog에서 카카오로 로그인 요청
- 카카오 API 서버에서 Access Token을 전달해줌
  - 사용자 정보에 접근할 수 있는 권한 부여됨 
  
  
 ---
 ### 회원가입 화면
 <img width="560" alt="스크린샷 2022-09-26 오후 7 13 52" src="https://user-images.githubusercontent.com/72964859/192251703-ef4692b3-87fb-4f9d-b66c-61a896171554.png">

### 로그인 화면
<img width="1106" alt="image" src="https://user-images.githubusercontent.com/72964859/192251792-a253ba00-15b9-47ad-bbb7-cbe82133b06b.png">

 ### 회원 정보 수정
 <img width="558" alt="image" src="https://user-images.githubusercontent.com/72964859/192252189-b613487b-56a5-4164-b06b-afa74952d06f.png">

 ### 글 쓰기 화면
<img width="1111" alt="스크린샷 2022-09-26 오후 7 12 48" src="https://user-images.githubusercontent.com/72964859/192251440-ef12a0e7-eb83-4fc8-a898-a0a73b4b42ec.png">

### 댓글 / 게시글 상세
<img width="1102" alt="image" src="https://user-images.githubusercontent.com/72964859/192252043-c4aab801-7640-40f1-b505-d5c02e408e19.png">
