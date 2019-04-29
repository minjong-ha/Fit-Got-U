# Fit-Got-U
Fitness For you


Pose Estimation For Android 전체구조

CameraActivity.java 에서 시작 Camera2BasicFrgment 객체 호출

Camera2BasicFrgment에서 백그라운드 스레드로 카메라의 이미지를 처리한다. 

	Background Thread에서 RunnableperiodicClassify()를 반복적으로 호출
	
	RunnablePeriodicClassify()는 Classifyframe을 실행
	
	ClassifyFrame은 Camera로 캡쳐한 이미지를 PoseEstimationfloatInception의 classifyframe 으로 넘긴다. 
	
	PoseEstimationfloatInception.classifyframe에서 runinference()를 진행
	
	여기서 mace와 opencv를 통해 이미지를 처리한다. 
	
	여기서 걸린 시간을 측정된다. ( ex) 화면에 표시되는 37ms가 이 것)

mPrintpointArray에 mace를 통해 처리된 결과값이 들어간다.(post process 처리된것)

다시 Camera2BasicFrgment 여기로 돌와와서 이 값을 Drawview객체로 넘긴다. 
	
Drawview객체 내에서 넘겨받은 이 값을 setDrawviewpoint로 화면에 맞게 처리한다. 

좌표 값과 스켈레톤을 여기서 그린다. 

우리가 임시로 출력하는 값은 Camera2BasicFrgment 에서 출력되어 보여준다. 
