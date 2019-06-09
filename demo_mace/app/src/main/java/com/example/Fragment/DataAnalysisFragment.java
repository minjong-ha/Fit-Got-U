package com.example.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.Activity.DataAnalysisPopupActivity;
import com.example.Etc.MySQLiteOpenHelper;
import com.example.List.DA_List_Item;
import com.example.List.DA_List_Item_Adapter;
import com.example.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;


public class DataAnalysisFragment extends Fragment {
    //body이미지 변수선언
    private final int POINT_STANDARD_YELLO_RED = 60; // 노란 또는 빨강 기준
    private final int POINT_STANDARD_YELLO_GREEN = 30;
    private ImageView imageView_l_arm1, imageView_r_arm1, imageView_l_arm2, imageView_r_arm2,
            imageView_l_leg1, imageView_r_leg1, imageView_l_leg2, imageView_r_leg2,
            imageView_m_body, imageView_m_hip, imageView_l_sholder, imageView_r_sholder;

    // view 및 context 변수
    private View view;
    private Context c;

    //list viwe
    private ListView m_oListView = null;

    // time
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");


    public DataAnalysisFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data_analysis, container, false);
        c = view.getContext();

        // sqlite
        final MySQLiteOpenHelper sqliteHelper = new MySQLiteOpenHelper(c, "jelly.db", null, 3);

        //이미지 부분
        int jointValues[] = getJointValue(sqliteHelper);

        for(int i = 0; i <jointValues.length ; i++) {
            if(jointValues[i]<0) jointValues[i] *= -1;
        }
        showImage(jointValues);

        // 점수 부분을 계산
        setBodyTotalPoint(jointValues);

        // 이미지 설명 텍스트
        TextView textViewForImage = (TextView) view.findViewById(R.id.textView);
        textViewForImage.setText(sqliteHelper.analysisText(jointValues));

        //리스트 뷰
        settingListView(sqliteHelper);

        //TestButton
        Button button1 = (Button) view.findViewById(R.id.button1);
        button1.setVisibility(View.INVISIBLE);
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                sqliteHelper.insertTest();
                testJointValue();
            }
            protected void testJointValue() {
                int jointValues[] = new int[12];

                Random generator = new Random();

                for (int i = 0; i < jointValues.length; i++) {
                    jointValues[i] = generator.nextInt(20);
                }

                TextView textViewForImage = (TextView) view.findViewById(R.id.textView);
                textViewForImage.setText(sqliteHelper.analysisText(jointValues));

                showImage(jointValues);

                setBodyTotalPoint(jointValues);

                TextView temp = (TextView) view.findViewById(R.id.textView);
            }
        });

        return view;
    }


    // 이미지 확인
    protected void showImage(int jointValues[]) {
        // 이미지 뷰
        imageView_l_arm1 = (ImageView) view.findViewById(R.id.imageView_l_arm1);
        imageView_r_arm1 = (ImageView) view.findViewById(R.id.imageView_r_arm1);
        imageView_l_arm2 = (ImageView) view.findViewById(R.id.imageView_l_arm2);
        imageView_r_arm2 = (ImageView) view.findViewById(R.id.imageView_r_arm2);
        imageView_l_leg1 = (ImageView) view.findViewById(R.id.imageView_l_leg1);
        imageView_r_leg1 = (ImageView) view.findViewById(R.id.imageView_r_leg1);
        imageView_l_leg2 = (ImageView) view.findViewById(R.id.imageView_l_leg2);
        imageView_r_leg2 = (ImageView) view.findViewById(R.id.imageView_r_leg2);
        imageView_m_body = (ImageView) view.findViewById(R.id.imageView_m_body);
        imageView_m_hip = (ImageView) view.findViewById(R.id.imageView_m_hip);
        imageView_l_sholder = (ImageView) view.findViewById(R.id.imageView_l_sholder);
        imageView_r_sholder = (ImageView) view.findViewById(R.id.imageView_r_sholder);

        // 이미지 로드
        if (jointValues[0] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_l_arm1.setImageDrawable(getResources().getDrawable(R.mipmap.left_arm1_red));
        else if (jointValues[0] >= POINT_STANDARD_YELLO_GREEN)
            imageView_l_arm1.setImageDrawable(getResources().getDrawable(R.mipmap.left_arm1_yellow));
        else
            imageView_l_arm1.setImageDrawable(getResources().getDrawable(R.mipmap.left_arm1_green));

        if (jointValues[1] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_l_arm2.setImageDrawable(getResources().getDrawable(R.mipmap.left_arm2_red));
        else if (jointValues[1] >= POINT_STANDARD_YELLO_GREEN)
            imageView_l_arm2.setImageDrawable(getResources().getDrawable(R.mipmap.left_arm2_yellow));
        else
            imageView_l_arm2.setImageDrawable(getResources().getDrawable(R.mipmap.left_arm2_green));

        if (jointValues[2] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_l_leg1.setImageDrawable(getResources().getDrawable(R.mipmap.left_leg1_red));
        else if (jointValues[2] >= POINT_STANDARD_YELLO_GREEN)
            imageView_l_leg1.setImageDrawable(getResources().getDrawable(R.mipmap.left_leg1_yellow));
        else
            imageView_l_leg1.setImageDrawable(getResources().getDrawable(R.mipmap.left_leg1_green));

        if (jointValues[3] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_l_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.left_leg2_red));
        else if (jointValues[3] >= POINT_STANDARD_YELLO_GREEN)
            imageView_l_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.left_leg2_yellow));
        else
            imageView_l_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.left_leg2_green));

        if (jointValues[4] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_m_body.setImageDrawable(getResources().getDrawable(R.mipmap.middle_body_red));
        else if (jointValues[4] >= POINT_STANDARD_YELLO_GREEN)
            imageView_m_body.setImageDrawable(getResources().getDrawable(R.mipmap.middle_body_yellow));
        else
            imageView_m_body.setImageDrawable(getResources().getDrawable(R.mipmap.middle_body_green));

        if (jointValues[5] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_m_hip.setImageDrawable(getResources().getDrawable(R.mipmap.middle_hip_red));
        else if (jointValues[5] >= POINT_STANDARD_YELLO_GREEN)
            imageView_m_hip.setImageDrawable(getResources().getDrawable(R.mipmap.middle_hip_yellow));
        else
            imageView_m_hip.setImageDrawable(getResources().getDrawable(R.mipmap.middle_hip_green));

        if (jointValues[6] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_r_arm1.setImageDrawable(getResources().getDrawable(R.mipmap.right_arm1_red));
        else if (jointValues[6] >= POINT_STANDARD_YELLO_GREEN)
            imageView_r_arm1.setImageDrawable(getResources().getDrawable(R.mipmap.right_arm1_yellow));
        else
            imageView_r_arm1.setImageDrawable(getResources().getDrawable(R.mipmap.right_arm1_green));

        if (jointValues[7] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_r_arm2.setImageDrawable(getResources().getDrawable(R.mipmap.right_arm2_red));
        else if (jointValues[7] >= POINT_STANDARD_YELLO_GREEN)
            imageView_r_arm2.setImageDrawable(getResources().getDrawable(R.mipmap.right_arm2_yellow));
        else
            imageView_r_arm2.setImageDrawable(getResources().getDrawable(R.mipmap.right_arm2_green));

        if (jointValues[8] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_r_leg1.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg1_red));
        else if (jointValues[8] >= POINT_STANDARD_YELLO_GREEN)
            imageView_r_leg1.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg1_yellow));
        else
            imageView_r_leg1.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg1_green));

        if (jointValues[9] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_r_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg2_red));
        else if (jointValues[9] >= POINT_STANDARD_YELLO_GREEN)
            imageView_r_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg2_yellow));
        else
            imageView_r_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg2_green));

        if (jointValues[9] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_r_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg2_red));
        else if (jointValues[9] >= POINT_STANDARD_YELLO_GREEN)
            imageView_r_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg2_yellow));
        else
            imageView_r_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg2_green));

        if (jointValues[9] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_r_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg2_red));
        else if (jointValues[9] >= POINT_STANDARD_YELLO_GREEN)
            imageView_r_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg2_yellow));
        else
            imageView_r_leg2.setImageDrawable(getResources().getDrawable(R.mipmap.right_leg2_green));

        if (jointValues[10] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_l_sholder.setImageDrawable(getResources().getDrawable(R.mipmap.left_sholder_red));
        else if (jointValues[10] >= POINT_STANDARD_YELLO_GREEN)
            imageView_l_sholder.setImageDrawable(getResources().getDrawable(R.mipmap.left_sholder_yellow));
        else
            imageView_l_sholder.setImageDrawable(getResources().getDrawable(R.mipmap.left_sholder_green));

        if (jointValues[11] >= POINT_STANDARD_YELLO_RED)// Red면
            imageView_r_sholder.setImageDrawable(getResources().getDrawable(R.mipmap.right_sholder_red));
        else if (jointValues[11] >= POINT_STANDARD_YELLO_GREEN)
            imageView_r_sholder.setImageDrawable(getResources().getDrawable(R.mipmap.right_sholder_yellow));
        else
            imageView_r_sholder.setImageDrawable(getResources().getDrawable(R.mipmap.right_sholder_green));

        // 이미지 뷰 end
    }


    protected int[] getJointValue(MySQLiteOpenHelper sqliteHelper) {
        return sqliteHelper.getJointValues(null,null);
    }


       /*리스트뷰 설정*/
    // 리스튜뷰 값 세팅
    private void settingListView(final MySQLiteOpenHelper sqliteHelper) {
        // 리스트 박스 값 세팅
        ArrayList<DA_List_Item> oData = new ArrayList<>();
        String days[] = {"오늘", "1일 전", "2일 전", "3일 전", "4일 전", "5일 전", "6일 전"};


        for (int i = 0; i < 7; ++i) {
            DA_List_Item oItem = new DA_List_Item();
            final String sDay = getTime(i);
            oItem.setStrDate(days[i] + "  (" + sDay.substring(0,10) + ")");

            final int exerciseCount = sqliteHelper.countDayExercise(sDay);
            if(exerciseCount==0){
                oItem.setStrText("운동을 하지 않았습니다.");
            }else{
                oItem.setStrText(String.format("총 %d가지의 운동기록이 있습니다.",exerciseCount));
            }

            oItem.setOnClickListener( new Button.OnClickListener() {
                // 버튼 선택시 상세 운동 정보 팝업을 켬
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(c.getApplicationContext(), DataAnalysisPopupActivity.class);

                    intent.putExtra("date",sDay);
                    startActivityForResult(intent, 101);

                }
            });
            oData.add(oItem);
        }

        // 어뎁터 연결 메소드 호출
        connectAdapter(oData);

    }

    // 리스트뷰 어뎁터 생성 및 연결
    private void connectAdapter(ArrayList<DA_List_Item> oData) {

        // ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView) view.findViewById(R.id.listView);
        final ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        ListAdapter oAdapter = new DA_List_Item_Adapter(oData);
        m_oListView.setAdapter(oAdapter);
        m_oListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    // after_day만큼을 더한 날짜를 리턴(형식 0000-00-00)
    private String getTime(int after_day) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.getTimeInMillis();
        gc.add(Calendar.DATE, -after_day);
        return mFormat.format(gc.getTime());
    }

    // 점수를 계산하고 표시하는 함수
    private void setBodyTotalPoint(int jointValues[]) {

        // Point 기준
        final int RED_POINT = 0;
        final int YELLOW_POINT = 3;
        final int GREEN_POINT = 5;
        int total = jointValues.length * (GREEN_POINT);

        int my_point = 0;

        for (int i = 0; i < jointValues.length; i++) {
            if (jointValues[i] < POINT_STANDARD_YELLO_GREEN)
                my_point += GREEN_POINT;
            else if (jointValues[i] < POINT_STANDARD_YELLO_RED)
                my_point += YELLOW_POINT;
            else
                my_point += RED_POINT;
        }

        TextView textViewBodyPoint = (TextView) view.findViewById(R.id.textViewPoint);
        my_point = my_point * 100 / total;
        String pointString = my_point + "점";
        textViewBodyPoint.setText(pointString);


    }
}
