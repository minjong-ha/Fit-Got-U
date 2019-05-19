package com.example.myapplication.List;

public class HT2_List_Item {
    private String image;
    private int nameid;
    private String desc;

    public HT2_List_Item(String image, int nameid, String desc) {
        this.image = image;
        this.nameid = nameid;
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public int getNameId() {
        return nameid;
    }

    public String getDesc() {
        return desc;
    }
}
