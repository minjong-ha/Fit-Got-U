package com.example;

import android.app.Activity;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * Created by a1 on 09/05/2019.
 */


public class Calculate {
    static String info;
    static double minAng1 = 360; //init
    static double minAng2 = 360; //init
    static double tempAng1, tempAng2;

    static ArrayList<Integer> left = new ArrayList<Integer>();
    static ArrayList<Integer> right = new ArrayList<Integer>();

    static boolean isExercise = false;
    static ArrayList<Double> tmpList1 = new ArrayList<Double>();
    static ArrayList<Double> tmpList2 = new ArrayList<Double>();
    static int exerciseCount = 0;
    static int validCount = 0;
    static int uppperCount = 0;
    static int lowerCount = 0;
    static int index = 0;

    static ArrayList<Double> minL = new ArrayList<Double>();
    static ArrayList<Double> minR = new ArrayList<Double>();

    static CopyOnWriteArrayList<PointF> lastPoints = new CopyOnWriteArrayList<>();

    public static void main(CopyOnWriteArrayList<PointF> Points){

        //=====
        if(Calculate.lastPoints.isEmpty() == true)
            Calculate.lastPoints = Points;
        //=====

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
//            if(isPointFValidate(Calculate.lastPoints.get(11), p11)
//                    && isPointFValidate(Calculate.lastPoints.get(12), p12)
//                    && isPointFValidate(Calculate.lastPoints.get(13), p13)
//                    && isPointFValidate(Calculate.lastPoints.get(8), p8)
//                    && isPointFValidate(Calculate.lastPoints.get(9), p9)
//                    && isPointFValidate(Calculate.lastPoints.get(10), p10)) {
                double leftAng = Cal_Angle(p11, p12, p13); //left
                double rightAng = Cal_Angle(p8, p9, p10);   //right

                //Log.d("checkangle", Double.toString(leftAng) + " " + Double.toString(rightAng));

            if(DrawView.isAllDone == true) {
                squatAngleCheck2(leftAng, rightAng);
            }
                //Calculate.left.add((int) leftAng);
                //squatAngleCheck(leftAng, rightAng);

                //lastPoints = Points;
           // }
            /*else {
                //do nothing
            }*/
        }

        else if(info.compareTo("팔벌리기") == 0)
            Cal_Angle(p3,p4,p5);
    }

    public static double Cal_Angle(PointF top, PointF center, PointF bottom) {

        double x1 = top.x - center.x;
        double y1 = top.y - center.y;
        double x2 = bottom.x - center.x;
        double y2 = bottom.y - center.y;

        double Angle = Math.abs((Math.atan2(y2, x2) - Math.atan2(y1, x1)) * 180 / Math.PI);

        if (Angle >= 180)
            Angle = 360 - Angle;

        return Angle;
    }

    public void PointCheck(){
        //좌표로 오류 체크
    }

    public static void squatAngleCheck(double Angle1, double Angle2){

        double bestAngle1, bestAngle2 = 90;
        int count = 0;
        int checkcount = 1;
        float correct=0, lower=0, upper = 0;
        int bestAngle = 90;

        if( (Angle1 > 0 && Angle1 <= 180) && (Angle2 > 0 && Angle2 <= 180) ) {
            if (index == 0) {
                minL.add(Angle1);
                minR.add(Angle2);
                index++;
                Log.d("index", Integer.toString(index));
            } else if (((minL.get(index - 1) > Angle1) && (minL.get(index - 1) * 0.95 <= Angle1)) && ((minL.get(index - 1) > Angle2) && (minL.get(index - 1) * 0.95 <= Angle2))) {
                minL.add(Angle1);
                minR.add(Angle2);

                Log.d("LminAngle", Double.toString(Angle1));

                index++;
            }

            //Log.d("LminAngle", minL.get(index - 1).toString());
        }
    }

    public static void squatAngleCheck2(double Angle1, double Angle2) {

        int limitUpAngle = 150;
        int limitDownAngle = 60;
        int lowestAngle = 80; // for proper
        int highestAngle = 100; // for proper

        if((Angle1 < limitUpAngle && Angle1 > limitDownAngle)) {
            Calculate.isExercise = true;
        }

        if(Calculate.isExercise == true && (Angle1 < limitUpAngle && Angle1 > limitDownAngle)) {
            Calculate.tmpList1.add(Angle1);
            Calculate.tmpList2.add(Angle2);
        }

        if(Calculate.tmpList1.isEmpty() == false && Angle1 >= limitUpAngle) {
            Calculate.isExercise = false;

            Calculate.minAng1 = minList(tmpList1);
            Calculate.minAng2 = minList(tmpList2);

            Calculate.left.add((int)Calculate.minAng1);

            Log.d("checkangle", Double.toString(minAng1));

            //feedback if needed
            if(Calculate.minAng1 >= highestAngle) {
                Calculate.uppperCount++;
                Log.d("error", "hello world");
                //squatUpperFeedback();
            }
            else if(Calculate.minAng1 <= lowestAngle) {
                Calculate.lowerCount++;
                Log.d("error", "HELLO WORLD");
                //squatLowerFeedback();
            }
            else {
                //successFeedback();
            }


            Calculate.tmpList1.clear();
            Calculate.tmpList2.clear();

            Calculate.exerciseCount++;
            Calculate.validCount = Calculate.exerciseCount - (Calculate.uppperCount + Calculate.lowerCount);

        }

    }
    public void squatLowerFeedback() {

    }

    public void squatUpperFeedback() {

    }

    public void successFeedback() {

    }

    public static double minList (ArrayList<Double> list) {
        double min = 9999;
        Log.d("checkangle", Calculate.tmpList1.toString());
        for(int i = 0; i < list.size(); i++) {
            if(min > list.get(i)) {
                min = list.get(i);
            }
        }
        Log.d("checkangle", Double.toString(min));
        return min;
    }

    public static void varInit() {

        minAng1 = 360; //init
        minAng2 = 360; //init;

        left.clear();
        right.clear();
        tmpList1.clear();
        tmpList2.clear();
        lastPoints.clear();

        isExercise = false;
        exerciseCount = 0;
        validCount = 0;
        lowerCount = 0;
        uppperCount = 0;

        DrawView.isAllDone = false;
        DrawView.shapeCount = 0;
        DrawView.isShapeValid = false;
    }

    public double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(Math.abs(x2-x1), 2) + Math.pow(Math.abs(y2-y1), 2));
    }

    public boolean isPointFValidate(PointF last, PointF current) {
        int limit = 5;

        if(getDistance(last.x, last.y, current.x, current.y) <= limit)
            return true;

        return false;
    }
}