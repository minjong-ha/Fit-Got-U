package com.example.myapplication.calendar.ui;


import androidx.lifecycle.ViewModel;

import com.example.myapplication.calendar.etc.TSLiveData;

public class CalendarHeaderViewModel extends ViewModel {
    public TSLiveData<Long> mHeaderDate = new TSLiveData<>();

    public void setHeaderDate(long headerDate) {
        this.mHeaderDate.setValue(headerDate);
    }
}
