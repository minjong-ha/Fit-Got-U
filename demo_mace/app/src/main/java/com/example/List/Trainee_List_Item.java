package com.example.List;

import android.view.View;

public class Trainee_List_Item {
    private String subid;
    private String traineeID;
    private String name;
    private String weight;
    private String tall;
    private String address;
    private String sex; //남 또는 여
    private String isAccept;
    private View.OnClickListener onClickListenerDetail;
    private View.OnClickListener onClickListenerAccept;
    private View.OnClickListener onClickListenerDeny;

    public Trainee_List_Item(){}

    // get

    public String getSubid() {
        return subid;
    }
    public String getTraineeID() {
        return traineeID;
    }
    public String getTall() {
        return tall;
    }
    public String getName() {
        return name;
    }
    public String getWeight() {
        return weight;
    }
    public String getAddress() {
        return address;
    }
    public String getSex() {
        return sex;
    }
    public View.OnClickListener getOnClickListenerDetail() {
        return onClickListenerDetail;
    }
    public String getIsAccept() {
        return isAccept;
    }
    public View.OnClickListener getOnClickListenerAccept() {
        return onClickListenerAccept;
    }
    public View.OnClickListener getOnClickListenerDeny() {
        return onClickListenerDeny;
    }

    // set

    public void setSubid(String subid) {
        this.subid = subid;
    }
    public void setTraineeID(String traineeID) {
        this.traineeID = traineeID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setTall(String tall) {
        this.tall = tall;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setOnClickListenerDetail(View.OnClickListener onClickListener) {
        this.onClickListenerDetail = onClickListener;
    }
    public void setIsAccept(String isAccept) {
        this.isAccept = isAccept;
    }
    public void setOnClickListenerAccept(View.OnClickListener onClickListenerAccept) {
        this.onClickListenerAccept = onClickListenerAccept;
    }
    public void setOnClickListenerDeny(View.OnClickListener onClickListenerDeny) {
        this.onClickListenerDeny = onClickListenerDeny;
    }

}
