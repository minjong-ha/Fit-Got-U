package com.example.myapplication.Etc;

public class YoutubeData {
    String videoId;
    String title;
    String desc;
    String url;
    String publishedAt;

    public YoutubeData(String videoId, String title, String desc, String url, String publishedAt) {
        super();
        this.videoId = videoId;
        this.title = title;
        this.desc = desc;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
