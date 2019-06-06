/*
 * Copyright 2018 Zihua Zeng (edvard_hua@live.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.concurrent.CopyOnWriteArrayList;


public class DrawView extends View {

    static boolean isShapeValid = false;
    static int shapeCount = 0;
    static boolean isAllDone = false;

    public static final int COLOR_TOP = Color.parseColor("#980000");
    public static final int COLOR_NECK = Color.parseColor("#ff0000");
    public static final int COLOR_RSHOULDER = Color.parseColor("#ff9900");
    public static final int COLOR_RELBOW = Color.parseColor("#ffff00");
    public static final int COLOR_RWRIST = Color.parseColor("#00ff00");
    public static final int COLOR_LSHOULDER = Color.parseColor("#00ffff");
    public static final int COLOR_LELBOW = Color.parseColor("#4a86e8");
    public static final int COLOR_LWRIST = Color.parseColor("#0000ff");
    public static final int COLOR_RHIP = Color.parseColor("#9900ff");
    public static final int COLOR_RKNEE = Color.parseColor("#274e13");
    public static final int COLOR_RANKLE = Color.parseColor("#e6b8af");
    public static final int COLOR_LHIP = Color.parseColor("#0c343d");
    public static final int COLOR_LKNEE = Color.parseColor("#1c4587");
    public static final int COLOR_LANKLE = Color.parseColor("#073763");
    public static final int COLOR_BACKGROUND = Color.parseColor("#20124d");

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;

    //private final CopyOnWriteArrayList<PointF> mDrawPoint = new CopyOnWriteArrayList<>();
    public final CopyOnWriteArrayList<PointF> mDrawPoint = new CopyOnWriteArrayList<>();
    private int mWidth, mHeight;
    private float mRatioX, mRatioY;
    private int mImgWidth, mImgHeight;

    private final int[] mColorArray = new int[]{
            COLOR_TOP, COLOR_NECK,
            COLOR_RSHOULDER, COLOR_RELBOW, COLOR_RWRIST,
            COLOR_LSHOULDER, COLOR_LELBOW, COLOR_LWRIST,
            COLOR_RHIP, COLOR_RKNEE, COLOR_RANKLE,
            COLOR_LHIP, COLOR_LKNEE, COLOR_LANKLE,
            COLOR_BACKGROUND
    };
    private Paint mPaint;

    //=====
    private Paint validPaint;
    //=====

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImgSize(int width, int height) {
        mImgWidth = width;
        mImgHeight = height;
        requestLayout();
    }

    /**
     * 输入的为92*92的图,然后按照比例放大
     * 先按ratio放大,再按机器实际尺寸放大
     *
     * @param point 2*14
     */
    public void setDrawPoint(float[][] point, float ratio) {
        mDrawPoint.clear();

        float tempX, tempY;
        for (int i = 0; i < 14; i++) {
            tempX = point[0][i] / ratio / mRatioX;
            tempY = point[1][i] / ratio / mRatioY;
            mDrawPoint.add(new PointF(tempX, tempY));
        }
    }



    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that is,
     * calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
        }

        canvas.drawColor(Color.TRANSPARENT);

        int colorIndex = 0;
        for (PointF pointF : mDrawPoint) {
//            if (pointF.x == 0 && pointF.y == 0) {
//                colorIndex++;
//                continue;
//            }
            mPaint.setColor(mColorArray[colorIndex++]);
            canvas.drawCircle(pointF.x, pointF.y, 8, mPaint);
        }

        mPaint.setColor(Color.parseColor("#6fa8dc"));
        mPaint.setStrokeWidth(5);*/

        if (mDrawPoint.size() <= 0)
            return;

        //keyPonint 총 13개
        PointF p0 = mDrawPoint.get(0); //top
        PointF p1 = mDrawPoint.get(1); //neck
        PointF p2 = mDrawPoint.get(2); //r_shoulder
        PointF p3 = mDrawPoint.get(3); //r_elbow
        PointF p4 = mDrawPoint.get(4); //r_wrist
        PointF p5 = mDrawPoint.get(5); //l_shoulder
        PointF p6 = mDrawPoint.get(6); //l_elbow
        PointF p7 = mDrawPoint.get(7); //l_wrist
        PointF p8 = mDrawPoint.get(8); //r_hip
        PointF p9 = mDrawPoint.get(9); //r_knee
        PointF p10 = mDrawPoint.get(10); //r_ankle
        PointF p11 = mDrawPoint.get(11); //l_hip
        PointF p12 = mDrawPoint.get(12); //l_knee
        PointF p13 = mDrawPoint.get(13); //l_ankle

       /* //0-1 top - neck
        canvas.drawLine(p0.x, p0.y, p1.x, p1.y, mPaint);

        //1-2 neck - r_shoulder
        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, mPaint);

        //2-3 r_shoulder - r_elbow
        canvas.drawLine(p2.x, p2.y, p3.x, p3.y, mPaint);

        //3-4 r_elbow - r_wrist
        canvas.drawLine(p3.x, p3.y, p4.x, p4.y, mPaint);

        //1-5 neck - l_shoulder
        canvas.drawLine(p1.x, p1.y, p5.x, p5.y, mPaint);

        //5-6 l_shoulder - l_elbow
        canvas.drawLine(p5.x, p5.y, p6.x, p6.y, mPaint);

        //6-7 l_elbow - l_wrist
        canvas.drawLine(p6.x, p6.y, p7.x, p7.y, mPaint);

        //1-11 neck - l_hip
        canvas.drawLine(p1.x, p1.y, p11.x, p11.y, mPaint);

        //11-12 l_hip - l_knee
        canvas.drawLine(p11.x, p11.y, p12.x, p12.y, mPaint);

        //12-13 l_knee - l_ankle
        canvas.drawLine(p12.x, p12.y, p13.x, p13.y, mPaint);

        //1-8 neck - r_hip
        canvas.drawLine(p1.x, p1.y, p8.x, p8.y, mPaint);

        //8-9 r_hip - r_knee
        canvas.drawLine(p8.x, p8.y, p9.x, p9.y, mPaint);

        //9-10 r_knee - r_ankle
        canvas.drawLine(p9.x, p9.y, p10.x, p10.y, mPaint);*/


        Paint paint = new Paint();

        validPaint = new Paint();

        Log.d("checkangle", p2.toString());

        //여기가 오버레이
        if(DrawView.shapeCount > 20) {
            //paint.setAlpha(0); // you can change number to change the transparency level
            DrawView.isAllDone = true;

            if(Calculate.info.compareTo("스쿼트") == 0){

                /*
                validPaint.setColor(Color.GREEN);
                validPaint.setStrokeWidth(15);
                //최적의 운동 각도 보여주는 부분
                    //11-12 l_hip - l_knee
                    canvas.drawLine(630, 900, 461, 960, validPaint);

                    //12-13 l_knee - l_ankle
                    canvas.drawLine(461, 960, 540, 1140, validPaint);
                    */


                //현재 사용자의 각도 보여주는 부분
                    //0-1 top - neck
                    canvas.drawLine(p0.x, p0.y, p1.x, p1.y, mPaint);

                    //1-11 neck - l_hip
                    canvas.drawLine(p1.x, p1.y, p11.x, p11.y, mPaint);

                    //11-12 l_hip - l_knee
                    canvas.drawLine(p11.x, p11.y, p12.x, p12.y, mPaint);

                    //12-13 l_knee - l_ankle
                    canvas.drawLine(p12.x, p12.y, p13.x, p13.y, mPaint);

                    //1-8 neck - r_hip
                    canvas.drawLine(p1.x, p1.y, p8.x, p8.y, mPaint);

                    //8-9 r_hip - r_knee
                    canvas.drawLine(p8.x, p8.y, p9.x, p9.y, mPaint);

                    //9-10 r_knee - r_ankle
                    canvas.drawLine(p9.x, p9.y, p10.x, p10.y, mPaint);

            }
            if(Calculate.info.compareTo("런지") == 0){

                validPaint.setColor(Color.GREEN);
                validPaint.setStrokeWidth(15);
                //최적의 운동 각도 보여주는 부분
                //11-12 l_hip - l_knee
                canvas.drawLine(630, 900, 461, 960, validPaint);

                //12-13 l_knee - l_ankle
                canvas.drawLine(461, 960, 540, 1140, validPaint);


                //현재 사용자의 각도 보여주는 부분
                //0-1 top - neck
                canvas.drawLine(p0.x, p0.y, p1.x, p1.y, mPaint);

                //1-11 neck - l_hip
                canvas.drawLine(p1.x, p1.y, p11.x, p11.y, mPaint);

                //11-12 l_hip - l_knee
                canvas.drawLine(p11.x, p11.y, p12.x, p12.y, mPaint);

                //12-13 l_knee - l_ankle
                canvas.drawLine(p12.x, p12.y, p13.x, p13.y, mPaint);

                //1-8 neck - r_hip
                canvas.drawLine(p1.x, p1.y, p8.x, p8.y, mPaint);

                //8-9 r_hip - r_knee
                canvas.drawLine(p8.x, p8.y, p9.x, p9.y, mPaint);

                //9-10 r_knee - r_ankle
                canvas.drawLine(p9.x, p9.y, p10.x, p10.y, mPaint);

            }
            Calculate.main(mDrawPoint);
        }
        else {
            paint.setAlpha(150);

            if (mPaint == null) {
                mPaint = new Paint();
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setAntiAlias(true);
            }

            canvas.drawColor(Color.TRANSPARENT);

            int colorIndex = 0;
            for (PointF pointF : mDrawPoint) {
//            if (pointF.x == 0 && pointF.y == 0) {
//                colorIndex++;
//                continue;
//            }
                mPaint.setColor(mColorArray[colorIndex++]);
                canvas.drawCircle(pointF.x, pointF.y, 8, mPaint);
            }

            mPaint.setColor(Color.parseColor("#6fa8dc"));
            mPaint.setStrokeWidth(5);

            //0-1 top - neck
            canvas.drawLine(p0.x, p0.y, p1.x, p1.y, mPaint);

            //1-2 neck - r_shoulder
            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, mPaint);

            //2-3 r_shoulder - r_elbow
            canvas.drawLine(p2.x, p2.y, p3.x, p3.y, mPaint);

            //3-4 r_elbow - r_wrist
            canvas.drawLine(p3.x, p3.y, p4.x, p4.y, mPaint);

            //1-5 neck - l_shoulder
            canvas.drawLine(p1.x, p1.y, p5.x, p5.y, mPaint);

            //5-6 l_shoulder - l_elbow
            canvas.drawLine(p5.x, p5.y, p6.x, p6.y, mPaint);

            //6-7 l_elbow - l_wrist
            canvas.drawLine(p6.x, p6.y, p7.x, p7.y, mPaint);

            //1-11 neck - l_hip
            canvas.drawLine(p1.x, p1.y, p11.x, p11.y, mPaint);

            //11-12 l_hip - l_knee
            canvas.drawLine(p11.x, p11.y, p12.x, p12.y, mPaint);

            //12-13 l_knee - l_ankle
            canvas.drawLine(p12.x, p12.y, p13.x, p13.y, mPaint);

            //1-8 neck - r_hip
            canvas.drawLine(p1.x, p1.y, p8.x, p8.y, mPaint);

            //8-9 r_hip - r_knee
            canvas.drawLine(p8.x, p8.y, p9.x, p9.y, mPaint);

            //9-10 r_knee - r_ankle
            canvas.drawLine(p9.x, p9.y, p10.x, p10.y, mPaint);
        }

        Bitmap imageForCorrect = BitmapFactory.decodeResource(getResources(), R.drawable.sil_correct);
        Bitmap imageForWrong = BitmapFactory.decodeResource(getResources(), R.drawable.sil_wrong);
        Bitmap imageForSquat = BitmapFactory.decodeResource(getResources(), R.drawable.squat_sill);
        Bitmap imageForLunge = BitmapFactory.decodeResource(getResources(), R.drawable.lunge_sil);

        //DisplayMetrics metrics = getBaseContext().getResources().getDisplayMetrics();
        int width = mWidth;
        int height = mHeight;

        if(DrawView.isAllDone == false) {
            DrawView.isShapeValid = establishBody(p0, p13, p10);
        }

        if(DrawView.isShapeValid == true && DrawView.isAllDone == false) {
            //green image
            canvas.drawBitmap(imageForCorrect, null, new RectF(0, 0, width, height), paint);
        }
        else if(DrawView.isShapeValid == false) {
            canvas.drawBitmap(imageForWrong, null, new RectF(0, 0, width, height), paint);
        }
        else if(DrawView.isAllDone == true && Calculate.info.compareTo("스쿼트") == 0) {
            paint.setAlpha(150);
            canvas.drawBitmap(imageForSquat, null, new RectF(0, 0, width, height), paint);
        }
        else if(DrawView.isAllDone == true && Calculate.info.compareTo("런지") == 0) {
            paint.setAlpha(150);
            canvas.drawBitmap(imageForLunge, null, new RectF(0, 0, width, height), paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * mRatioWidth / mRatioHeight) {
                mWidth = width;
                mHeight = width * mRatioHeight / mRatioWidth;
            } else {
                mWidth = height * mRatioWidth / mRatioHeight;
                mHeight = height;
            }
        }

        setMeasuredDimension(mWidth, mHeight);

        mRatioX = ((float) mImgWidth) / mWidth;
        mRatioY = ((float) mImgHeight) / mHeight;
    }

    public boolean establishBody(PointF p0, PointF p13, PointF p10){
         float HeadX = p0.x;
         float HEadY = p0.y;
         PointF Head = p0;
         PointF LFoot = p13;
         PointF RFoot = p10;

        Log.d("HEAD & ANKLE POINT", Head.toString() + ", " + LFoot.toString() + " ," + RFoot.toString());

        if(p0.y < 700 && p13.x < 650 && p13.y > 1000 && p10.x > 460 && p10.y > 1000) {
            if(DrawView.isShapeValid == true) {
                DrawView.shapeCount++;
            }
            return true;
        }
        else {
            DrawView.shapeCount = 0;
            return false;
        }
    }

}
