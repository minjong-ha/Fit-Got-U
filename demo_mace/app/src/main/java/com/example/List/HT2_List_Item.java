package com.example.List;


public class HT2_List_Item {
    private int image;
    private int nameid;
    private String desc;

    public HT2_List_Item(int image, int nameid, String desc) {
        this.image = image;
        this.nameid = nameid;
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public int getNameId() {
        return nameid;
    }

    public String getDesc() {
        return desc;
    }
}
