package com.example.calendar.ui;


import androidx.lifecycle.ViewModel;

import com.example.calendar.etc.TSLiveData;

import java.util.Calendar;

public class CalendarViewModel extends ViewModel {
    public TSLiveData<Calendar> mCalendar = new TSLiveData<>();

    public void setCalendar(Calendar calendar) {
        this.mCalendar.setValue(calendar);
    }


}
