package com.example.myapplication.List;

public class TrainerItem {
    String name;
    double distance;
    int resId;

    @Override
    public String toString() {
        return "TrainerItem{" +
                "name='" + name + '\'' +
                ", Distance='" + distance + '\'' +
                '}';
    }

    public TrainerItem(String name, double distance, int resId) {
        this.name = name;
        this.distance = distance;
        this.resId=resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}