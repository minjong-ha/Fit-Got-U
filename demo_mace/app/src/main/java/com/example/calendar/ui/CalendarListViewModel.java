package com.example.calendar.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.Activity.MainActivity;
import com.example.Etc.Util;
import com.example.calendar.etc.Keys;
import com.example.calendar.etc.TSLiveData;
import com.example.calendar.etc.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class CalendarListViewModel extends AndroidViewModel {
    private long mCurrentTime;
    private Activity context;

    public TSLiveData<String> mTitle = new TSLiveData<>();
    public TSLiveData<ArrayList<Object>> mCalendarList = new TSLiveData<>(new ArrayList<>());
    public String date;
    public int posCount=0;

    HashMap<Integer, String> posiToDate = new HashMap<Integer, String>();
    public int mCenterPosition;

    public CalendarListViewModel(@NonNull Application application, AppCompatActivity main) {
        super(application);
        context=main;
    }

    public void setTitle(int position) {
        try {
            Object item = mCalendarList.getValue().get(position);
            if (item instanceof Long) {
                setTitle((Long) item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTitle(long time) {
        mCurrentTime = time;
        mTitle.setValue(DateFormat.getDate(time, DateFormat.CALENDAR_HEADER_FORMAT));
    }


    public void initCalendarList() {
        GregorianCalendar cal = new GregorianCalendar();
        setCalendarList(cal);
    }


    ArrayList<HashMap<String, String>> routine = Util.SelectRoutine((((MainActivity)context)).getKakaoid() + "");

    public void setCalendarList(GregorianCalendar cal) {
        setTitle(cal.getTimeInMillis());

        ArrayList<Object> calendarList = new ArrayList<>();
        for (int i = -2; i < 2; i++) {
            try {
                date=null;
                GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + i, 1, 0, 0, 0);
                if (i == 0) {
                    mCenterPosition = calendarList.size();
                }
                calendarList.add(calendar.getTimeInMillis());
                putPosiDate(posCount,date);

                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; //해당 월에 시작하는 요일 -1 을 하면 빈칸을 구할 수 있겠죠 ?
                int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월에 마지막 요일

                for (int j = 0; j < dayOfWeek; j++) {
                    calendarList.add(Keys.EMPTY);
                    putPosiDate(posCount,date);
                }
                for (int j = 1; j <= max; j++) {
                    date=(calendar.get(Calendar.YEAR))+"-"+(cal.get(Calendar.MONTH) + i + 1)+"-"+j;
                    calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j));
                    putPosiDate(posCount,date);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mCalendarList.setValue(calendarList);
    }

    public void putPosiDate(int posCount, String date){
        posiToDate.put(posCount,date);
        posCount++;
        return ;
    }
}
