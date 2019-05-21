package com.example.myapplication.List;

public class TrainerItem {
    long kakaoid;
    String name;
    String profile_image;
    String youtube;
    double distance;

    @Override
    public String toString() {
        return "TrainerItem{" +
                "name='" + name + '\'' +
                ", Distance='" + distance + '\'' +
                '}';
    }
	
    public TrainerItem(long kakaoid, String name, double distance, String profile_image, String youtube) {
        this.kakaoid = kakaoid;
        this.name = name;
        this.distance = distance;
        this.profile_image=profile_image;
        this.youtube=youtube;
    }

    public long getKakaoid() {
        return kakaoid;
    }

    public void setKakaoid(long kakaoid) {
        this.kakaoid = kakaoid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}