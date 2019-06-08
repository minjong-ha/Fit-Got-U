package com.example.List;

import android.view.View;

public class Trainee_List_Item {
    private String traineeID;
    private String name;
    private String weight;
    private String tall;
    private String address;
    private String sex; //남 또는 여
    private View.OnClickListener onClickListener;

    public Trainee_List_Item(){}

    // get

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
    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    // set

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
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    //

}
