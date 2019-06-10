package com.example.PoseEstimation;

import android.content.Context;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.Activity.MainActivity;
import com.example.Etc.MySQLiteOpenHelper;
import com.example.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by a1 on 09/05/2019.
 */


public class Calculate {
    static String info;
    static double minAng1 = 360; //init
    static double minAng2 = 360; //init
    static double minHead = 0; //init
    static double tempAng1, tempAng2;

    static ArrayList<Integer> left = new ArrayList<Integer>();
    static ArrayList<Integer> right = new ArrayList<Integer>();

    static ArrayList<PointF> headPoint = new ArrayList<PointF>();

    static boolean isExercise = false;
    static ArrayList<Double> tmpList1 = new ArrayList<Double>();
    static ArrayList<Double> tmpList2 = new ArrayList<Double>();
    static int exerciseCount = 0;
    static int validCount = 0;
    static int uppperCount = 0;
    static int lowerCount = 0;
    static int index = -1;

    static double rtAngle = 0;

    static CopyOnWriteArrayList<PointF> lastPoints = new CopyOnWriteArrayList<>();

    static Context context = null;

    static String status = null;

    public static void main(CopyOnWriteArrayList<PointF> Points) {

        //=====
        if (Calculate.lastPoints.isEmpty() == true)
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


        Log.d("errorcheck", "1");
        if (info.compareTo("스쿼트") == 0) {
            /*if (isPointFValidate(Calculate.lastPoints.get(11), p11)
                    && isPointFValidate(Calculate.lastPoints.get(12), p12)
                    && isPointFValidate(Calculate.lastPoints.get(13), p13)
                    && isPointFValidate(Calculate.lastPoints.get(8), p8)
                    && isPointFValidate(Calculate.lastPoints.get(9), p9)
                    && isPointFValidate(Calculate.lastPoints.get(10), p10))*/
            {
                double leftAng = Cal_Angle(p11, p12, p13); //left
                double rightAng = Cal_Angle(p8, p9, p10);   //right

                Log.d("errorcheck", "2");
                //Log.d("checkangle", Double.toString(leftAng) + " " + Double.toString(rightAng));

                if (DrawView.isAllDone == true) {
                    squatAngleCheck2(leftAng, rightAng, Points);
                    Log.d("errorcheck", "3");
                }

            }

        }
        if (info.compareTo("런지") == 0) {
            double leftAng = Cal_Angle(p11, p12, p13); //left
            double rightAng = Cal_Angle(p8, p9, p10);   //right

            if (DrawView.isAllDone == true) {
                lungeAngleCheck(leftAng, rightAng, Points);
            }
        }


    }


    static private String todayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH) + 1;
        int date = cal.get(cal.DATE);

        return year+"-"+dateFormat(month+"")+"-"+dateFormat(date+"");
    }

    static private String dateFormat(String raw_date) {
        if (raw_date.length() == 1)
            return "0" + raw_date;
        return raw_date;
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

    public void PointCheck() {
        //좌표로 오류 체크
    }

    public static void squatAngleCheck(double Angle1, double Angle2) {

        double bestAngle = 65;
        double upperAngle = 80;
        double lowerAngle = 52;
        if (index == -1 && Angle2 == 0.0) {
            index = 0;
        } else if (index == 0 && Angle2 > 0) {
            rtAngle = Angle2;
            index = 1;
        } else if (index == 2) {
            rtAngle = 170;
            if (Angle2 >= rtAngle)
                index = 0;
        } else if (index == 1 && Angle2 > 0) {
            //if((minL.get(minL.size()-1) > Angle2) && (minL.get(minL.size()-1) * 0.95 <= Angle2)){
            if ((rtAngle > Angle2) && (rtAngle * 0.75 <= Angle2)) {
                rtAngle = Angle2;
            } else if (Angle2 > rtAngle * 1.5) {
                if (rtAngle < lowerAngle)
                    //Log.d("LOW Angle", Double.toString(rtAngle));
                    Calculate.lowerCount++;
                else if (rtAngle > upperAngle)
                    //Log.d("UP Angle", Double.toString(rtAngle));
                    Calculate.uppperCount++;
                else if (rtAngle >= lowerAngle && rtAngle <= upperAngle)
                    //Log.d("VALID Angle", Double.toString(rtAngle));
                    Calculate.validCount++;

                index = 2;
                rtAngle = 0;

                Calculate.exerciseCount++;
                Calculate.validCount = Calculate.exerciseCount - (Calculate.uppperCount + Calculate.lowerCount);
            }
        }
    }

    public static void squatAngleCheck2(double Angle1, double Angle2, CopyOnWriteArrayList<PointF> Points) {

        int limitUpAngle = 150;
        int limitDownAngle = 60;
        int lowestAngle = 80; // for proper
        int highestAngle = 100; // for proper

        if ((Angle1 < limitUpAngle && Angle1 > limitDownAngle)) {
            Calculate.isExercise = true;
        }

        if (Calculate.isExercise == true && (Angle1 < limitUpAngle && Angle1 > limitDownAngle)) {
            Calculate.tmpList1.add(Angle1);
            Calculate.tmpList2.add(Angle2);
            Calculate.headPoint.add(Points.get(0));
        }

        if (Calculate.tmpList1.isEmpty() == false && Angle1 >= limitUpAngle) {
            Calculate.isExercise = false;

            Calculate.minAng1 = minList(tmpList1);
            Calculate.minAng2 = minList(tmpList2);
            int index = minIndex(tmpList1);
            double minHead_y = Calculate.headPoint.get(index).y;

            Calculate.left.add((int) Calculate.minAng1);

            if (minHead_y > 450) {
                //feedback if needed
                if (Calculate.minAng1 >= highestAngle) {
                    Calculate.uppperCount++;
                    Calculate.status = "up";
                    MySQLiteOpenHelperForPose.insertDescribe(MySQLiteOpenHelperForPose.getRecordID(),"좌측 다리 상박",(int)Calculate.minAng1);
                    MySQLiteOpenHelperForPose.insertDescribe(MySQLiteOpenHelperForPose.getRecordID(),"우측 다리 상박",(int)Calculate.minAng1);

                    //==
                    ass.execute(Calculate.status);
                    //ass.cancel(true);
                    //==

                    //squatUpperFeedback();
                } else if (Calculate.minAng1 <= lowestAngle) {
                    Calculate.lowerCount++;
                    Calculate.status = "down";
                    MySQLiteOpenHelperForPose.insertDescribe(MySQLiteOpenHelperForPose.getRecordID(),"좌측 다리 상박",-1*(int)Calculate.minAng1);
                    MySQLiteOpenHelperForPose.insertDescribe(MySQLiteOpenHelperForPose.getRecordID(),"우측 다리 상박",-1*(int)Calculate.minAng1);

                    MySQLiteOpenHelperForPose.insertDescribe(MySQLiteOpenHelperForPose.getRecordID(),"좌측 다리 상박",-1*(int)Calculate.minAng1);
                    MySQLiteOpenHelperForPose.insertDescribe(MySQLiteOpenHelperForPose.getRecordID(),"우측 다리 상박",-1*(int)Calculate.minAng1);

                    Runnable r = new VoiceFeedback();
                    Thread thread = new Thread(r);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //squatLowerFeedback();
                } else {
                    Calculate.status = "right";

                    Runnable r = new VoiceFeedback();
                    Thread thread = new Thread(r);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //successFeedback();
                }

                Calculate.exerciseCount++;
            }

            Calculate.tmpList1.clear();
            Calculate.tmpList2.clear();
            Calculate.headPoint.clear();

            Calculate.validCount = Calculate.exerciseCount - (Calculate.uppperCount + Calculate.lowerCount);

        }
    }

    public static void lungeAngleCheck(double Angle1, double Angle2, CopyOnWriteArrayList<PointF> Points) {

        int limitUpAngle = 150;
        int limitDownAngle = 60;
        int lowestAngle = 80; // for proper
        int highestAngle = 100; // for proper

        if ((Angle1 < limitUpAngle && Angle1 > limitDownAngle) || (Angle2 < limitUpAngle && Angle2 > limitDownAngle)) {
            Calculate.isExercise = true;
        }

        if (Calculate.isExercise == true && (Angle1 < limitUpAngle && Angle1 > limitDownAngle) || (Angle2 < limitUpAngle && Angle2 > limitDownAngle)) {
            Calculate.tmpList1.add(Angle1);
            Calculate.tmpList2.add(Angle2);
            Calculate.headPoint.add(Points.get(0));
        }

        if (Calculate.tmpList1.isEmpty() == false && (Angle1 >= limitUpAngle && Angle2 >= limitUpAngle)) {
            Calculate.isExercise = false;

            Calculate.minAng1 = minList(tmpList1);
            Calculate.minAng2 = minList(tmpList2);
            int index = minIndex(tmpList1);
            double minHead_y = Calculate.headPoint.get(index).y;

            Calculate.left.add((int) Calculate.minAng1);

            if (minHead_y > 450) {
                //feedback if needed
                if (Calculate.minAng1 >= highestAngle || Calculate.minAng2 >= highestAngle) {
                    Calculate.uppperCount++;
                    Calculate.status = "up";
                    ass.execute(Calculate.status);
                    MySQLiteOpenHelperForPose.insertDescribe(MySQLiteOpenHelperForPose.getRecordID(),"좌측 다리 상박",(int)Calculate.minAng1);
                    MySQLiteOpenHelperForPose.insertDescribe(MySQLiteOpenHelperForPose.getRecordID(),"우측 다리 상박",(int)Calculate.minAng1);

                    //squatUpperFeedback();
                } /*else if (Calculate.minAng1 <= lowestAngle || Calculate.minAng2 <= lowestAngle) {
                    Calculate.lowerCount++;
                    Calculate.status = "down";

      MySQLiteOpenHelperForPose.insertDescribe(MySQLiteOpenHelperForPose.getRecordID(),"좌측 다리 상박",-1*(int)Calculate.minAng1);
                    MySQLiteOpenHelperForPose.insertDescribe(MySQLiteOpenHelperForPose.getRecordID(),"우측 다리 상박",-1*(int)Calculate.minAng1);

                    Runnable r = new VoiceFeedback();
                    Thread thread = new Thread(r);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //squatLowerFeedback();
                }*/ else {
                    Calculate.status = "right";

                    Runnable r = new VoiceFeedback();
                    Thread thread = new Thread(r);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //successFeedback();
                }

                Calculate.exerciseCount++;
            }

            Calculate.tmpList1.clear();
            Calculate.tmpList2.clear();
            Calculate.headPoint.clear();

            Calculate.validCount = Calculate.exerciseCount - (Calculate.uppperCount + Calculate.lowerCount);

        }

    }


    public void squatLowerFeedback() {

    }

    public void squatUpperFeedback() {

    }

    public void successFeedback() {

    }

    public static double minList(ArrayList<Double> list) {
        double min = 9999;
        double index = 0;

        Log.d("checkangle", Calculate.tmpList1.toString());
        for (int i = 0; i < list.size(); i++) {
            if (min > list.get(i)) {
                min = list.get(i);
                index = i;
            }
        }
        Log.d("checkangle", Double.toString(min));
        return min;
    }

    public static int minIndex(ArrayList<Double> list) {
        double min = 9999;
        int index = 0;

        Log.d("checkangle", Calculate.tmpList1.toString());
        for (int i = 0; i < list.size(); i++) {
            if (min > list.get(i)) {
                min = list.get(i);
                index = i;
            }
        }
        Log.d("checkangle", Double.toString(min));
        return index;
    }

    public static void varInit() {

        minAng1 = 360; //init
        minAng2 = 360; //init;
        minHead = 0;

        left.clear();
        right.clear();
        tmpList1.clear();
        tmpList2.clear();
        lastPoints.clear();
        headPoint.clear();

        isExercise = false;
        exerciseCount = 0;
        validCount = 0;
        lowerCount = 0;
        uppperCount = 0;
        rtAngle = 0;
        index = -1;


        status = null;

        DrawView.isAllDone = false;
        DrawView.shapeCount = 0;
        DrawView.isShapeValid = false;
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2));
    }

    public static boolean isPointFValidate(PointF last, PointF current) {
        int limit = 5;

        if (getDistance(last.x, last.y, current.x, current.y) <= limit)
            return true;

        return false;
    }

    public static void setContext(Context context) {
        Calculate.context = context;
    }
}


class VoiceFeedback implements Runnable {
    @Override
    public void run() {
        MediaPlayer up = MediaPlayer.create(Calculate.context, R.raw.too_low);
        MediaPlayer down = MediaPlayer.create(Calculate.context, R.raw.too_high);
        MediaPlayer good = MediaPlayer.create(Calculate.context, R.raw.well_done);

        if (Calculate.status.equals("down")) {
            up.start();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (Calculate.status.equals("up")) {
            down.start();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (Calculate.status.equals("right")) {
            good.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        up.stop();
        down.stop();
        good.stop();
        up.release();
        down.release();
        good.release();
    }


}
