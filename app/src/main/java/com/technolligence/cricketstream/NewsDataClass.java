package com.technolligence.cricketstream;

public class NewsDataClass {
    private String imgUrl;
    private String newsTitle;
    private String newsDetails;
    private String newsDatetime;

    public NewsDataClass() {
    }

    public NewsDataClass(String imgUrl, String newsTitle, String newsDetails, String newsDatetime) {
        this.imgUrl = imgUrl;
        this.newsTitle = newsTitle;
        this.newsDetails = newsDetails;
        this.newsDatetime = newsDatetime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDetails() {
        return newsDetails;
    }

    public void setNewsDetails(String newsDetails) {
        this.newsDetails = newsDetails;
    }

    public String getNewsDatetime() {
        return newsDatetime;
    }

    public void setNewsDatetime(String newsDatetime) {
        this.newsDatetime = newsDatetime;
    }
}
