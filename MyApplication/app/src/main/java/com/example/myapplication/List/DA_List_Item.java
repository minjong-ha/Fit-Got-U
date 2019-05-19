package com.example.myapplication.List;

import android.view.View;

public class DA_List_Item {
    private String strDate;
    private String strText;
    private View.OnClickListener onClickListener;

    public String getStrDate() {
        return strDate;
    }

    public String getStrText() {
        return strText;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public void setStrText(String strText) {
        this.strText = strText;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
