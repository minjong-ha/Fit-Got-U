package com.example;

import android.graphics.PointF;
import android.util.Log;


import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * Created by a1 on 09/05/2019.
 */


public class Calculate {
    static String info;
    double initAng1 = 360; //init
    double initAng2 = 360; //init
    double tempAng1, tempAng2;

    public void main(CopyOnWriteArrayList<PointF> Points) {

        PointF p0 = Points.get(0); //top
        PointF p1 = Points.get(1); //neck
        PointF p2 = Points.get(2); //r_shoulder
        PointF p3 = Points.get(3); //r_elbow
        PointF p4 = Points.get(4); //r_wrist
        PointF p5 = Points.get(5); //l_shoulder
        PointF p6 = Points.get(6); //l_elbow
        PointF p7 = Points.get(7); //l_wrist
        PointF p8 = Points.get(8); //r_hip
        PointF p9 = Points.get(9); //r_knee
        PointF p10 = Points.get(10); //r_ankle
        PointF p11 = Points.get(11); //l_hip
        PointF p12 = Points.get(12); //l_knee
        PointF p13 = Points.get(13); //l_ankle

        if(info.compareTo("스쿼트") == 0) {
            double leftAng = Cal_Angle(p11,p12,p13); //left
            double rightAng = Cal_Angle(p8,p9,p10);   //right

            squatAngleCheck(leftAng, rightAng);
        }

        else if(info.compareTo("팔벌리기") == 0)
            Cal_Angle(p3,p4,p5);
    }

    public double Cal_Angle(PointF top, PointF center, PointF bottom) {

        double x1 = top.x - center.x;
        double y1 = top.y - center.y;
        double x2 = bottom.x - center.x;
        double y2 = bottom.y - center.y;

        double Angle = (Math.atan(y1 / x1) - Math.atan(y2 / x2)) * 180 / Math.PI;
        if(Angle < 0)
            Angle += 180;

        String result = Double.toString(Angle);

        Log.d("Angle", result);

        if(Angle < 0)
            return Angle + 180;

        return Angle;
    }

    public void PointCheck(){
        //좌표로 오류 체크
    }

    public void squatAngleCheck(double Angle1, double Angle2){

        double bestAngle1, bestAngle2 = 90;

        if(this.initAng1 == 360 || this.initAng2 == 360) {
            this.initAng1 = Angle1;
            this.initAng2 = Angle2;
        }

        else {
            //값 유효값 처리.
            //Angle의 유효값 처리 best angle, 오차범위내 angle, 오류 angle 3가지 분류
            //tempAngle과 minAngle 2개의 변수
            //tempAngle은 일단 모든 angle을 계산 거기서 가장 작은 각도를 minAngle로 저장
            //여기서 minAngle이 어느 범위에 있는지 계산하고 피드백

            double minAngle1 = 360;
            double minAngle2 = 360;

            tempAng1 = Angle1; tempAng2 = Angle2;

            if (tempAng1 < minAngle1 || tempAng2 <  minAngle2){
                minAngle1 = tempAng1;
                minAngle2 = tempAng2;
            }
        }
    }
}