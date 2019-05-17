package com.example.myapplication.List;

public class TrainerItem {
    String name;
    int resId;
    String youtubechannelurl;
    double distance;

    @Override
    public String toString() {
        return "TrainerItem{" +
                "name='" + name + '\'' +
                ", Distance='" + distance + '\'' +
                '}';
    }
	
    public TrainerItem(String name, double distance, int resId, String youtubechannelurl) {
        this.name = name;
        this.distance = distance;
        this.resId=resId;
        this.youtubechannelurl=youtubechannelurl;
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

    public String getYoutubechannelurl() {
        return youtubechannelurl;
    }
    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}