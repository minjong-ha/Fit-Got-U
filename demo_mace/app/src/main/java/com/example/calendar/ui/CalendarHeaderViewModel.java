package com.example.calendar.ui;


import androidx.lifecycle.ViewModel;

import com.example.calendar.etc.TSLiveData;

public class CalendarHeaderViewModel extends ViewModel {
    public TSLiveData<Long> mHeaderDate = new TSLiveData<>();

    public void setHeaderDate(long headerDate) {
        this.mHeaderDate.setValue(headerDate);
    }
}
