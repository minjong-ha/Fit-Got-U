package com.example.myapplication.List;

public class HT3_List_Item {
    private int imageid;
    private int nameid;
    private int descid;

    public HT3_List_Item(int imageid, int nameid, int descid) {
        this.imageid = imageid;
        this.nameid = nameid;
        this.descid = descid;
    }

    public int getImageId() {
        return imageid;
    }

    public int getNameId() {
        return nameid;
    }

    public int getDescId() {
        return descid;
    }
}
