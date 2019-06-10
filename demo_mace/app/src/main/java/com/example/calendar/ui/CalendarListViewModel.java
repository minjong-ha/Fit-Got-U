package com.example.calendar.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;

import com.example.Activity.MainActivity;
import com.example.Etc.Util;
import com.example.calendar.etc.DateFormat;
import com.example.calendar.etc.Keys;
import com.example.calendar.etc.TSLiveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class CalendarListViewModel extends AndroidViewModel {
    private long mCurrentTime;

    public TSLiveData<String> mTitle = new TSLiveData<>();
    public TSLiveData<ArrayList<Object>> mCalendarList = new TSLiveData<>(new ArrayList<>());
    public String date;
    public int posCount=0;
    private ArrayList<Integer> setDate = new ArrayList<>();
    private ArrayList<String> setBreak = new ArrayList<>();
    private ArrayList<HashMap<String, String>> routine;

    HashMap<String, Integer> posiToDate = new HashMap<>();
    public int mCenterPosition;

    public CalendarListViewModel(@NonNull Application application) {
        super(application);
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


    public void initCalendarList(String kakaoid) {
        GregorianCalendar cal = new GregorianCalendar();
        setCalendarList(cal,kakaoid);
    }

    public void setCalendarList(GregorianCalendar cal, String kakaoid) {
        setTitle(cal.getTimeInMillis());

        routine = Util.SelectRoutine(kakaoid);
        posCount=0;

        String date_mes;

        ArrayList<Object> calendarList = new ArrayList<>();
        for (int i = -2; i < 2; i++) {
            try {
                date = null;
                GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + i, 1, 0, 0, 0);
                if (i == 0) {
                    mCenterPosition = calendarList.size();
                }
                putPosiDate();
                calendarList.add(calendar.getTimeInMillis());

                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; //해당 월에 시작하는 요일 -1 을 하면 빈칸을 구할 수 있겠죠 ?
                int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월에 마지막 요일

                for (int j = 0; j < dayOfWeek; j++) {
                    putPosiDate();
                    calendarList.add(Keys.EMPTY);
                }
                for (int j = 1; j <= max; j++) {
                    if((cal.get(Calendar.MONTH) + i + 1)<10) {
                        date = (calendar.get(Calendar.YEAR)) + "-0" + (cal.get(Calendar.MONTH) + i + 1) + "-" + j;
                    } else date = (calendar.get(Calendar.YEAR)) + "-" + (cal.get(Calendar.MONTH) + i + 1) + "-" + j;
                    putPosiDate();
                    for(HashMap<String, String> t : routine) {
                        date_mes = t.get("date_message");
                        if(date.equals(date_mes)){
                            setDate.add(posiToDate.get(date));
                            setBreak.add(date);
                        }
                    }
                    calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j));
                }
            } catch (Exception e) {
                e.printStackTrace();
                }
            mCalendarList.setValue(calendarList);
        }
    }

    public void putPosiDate(){
        posiToDate.put(date, posCount);
        posCount++;
        return ;
    }

    public ArrayList<Integer> getSetDate(){
        return this.setDate;
    }
    public ArrayList<String> getSetBreak(){
        return this.setBreak;
    }
    public HashMap<String, Integer> getPosiToDate(){
        return this.posiToDate;
    }
}
