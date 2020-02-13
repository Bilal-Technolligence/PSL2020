package com.technolligence.cricketstream;

public class BatsmanAttr {
    String balls;
    String fours;
    String name;
    String rr;
    String score;
    String sixs;

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getFours() {
        return fours;
    }

    public void setFours(String fours) {
        this.fours = fours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSixs() {
        return sixs;
    }

    public void setSixs(String sixs) {
        this.sixs = sixs;
    }

    public BatsmanAttr(String balls, String fours, String name, String rr, String score, String sixs) {
        this.balls = balls;
        this.fours = fours;
        this.name = name;
        this.rr = rr;
        this.score = score;
        this.sixs = sixs;
    }

    public BatsmanAttr() {
    }
}
