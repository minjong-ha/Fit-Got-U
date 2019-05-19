package com.example.myapplication.Etc;

public class YoutubeData {
    String videoId;
    String title;
    String desc;
    String imgurl;
    String publishedAt;

    public YoutubeData(String videoId, String title, String desc, String imgurl, String publishedAt) {
        super();
        this.videoId = videoId;
        this.title = title;
        this.desc = desc;
        this.imgurl = imgurl;
        this.publishedAt = publishedAt;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String url) {
        this.imgurl = imgurl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
