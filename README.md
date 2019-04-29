# Fit-Got-U
Fitness For you

2019-03-21
그냥 기본인 com.example.myapplication 으로 만들었음.
SDK는 26(Oreo)를 이용.
자바는 JDK 8 이용.

디자인 없이 뼈대만 만들고 있으며 버그 등은 아직 고려하지 않았음.
현재 상태는 아래와 같음.

MainActivity : 아무것도 안 만듬. 기본적으로 여기서 어플이 시작되기 때문에 StartPageActivity를 볼 수는 없을 것임.

StartPageActivity : 시작 화면임(나중에). User login 버튼과 Fitness Login 버튼, 회원가입 버튼이 존재. login 버튼을 누르면 who 정보를 user 또는 fitness로 저장해 LoginActivity로 전달함.

LoginActivity : 아이디와 비밀번호를 입력하는 창이 나옴. 데이터 베이스는 이용하지 않아서 아래 login 버튼을 누르면 그냥 MainActivity로 이동함.

JoinActivity : 아직 안 만듬.

nanamare는 포기 edvardHua로 시도중

2019-04-29
UI 도안 제작, 현재 안드로이드 스튜디오로 개발 진행 중.

팀원별로 맡은 부분에 대해서 sample application을 개발 중.

PoseEstimationForMobile/edvardhua 소스 코드 분석 / 다른 기능과 merge 시도 중


