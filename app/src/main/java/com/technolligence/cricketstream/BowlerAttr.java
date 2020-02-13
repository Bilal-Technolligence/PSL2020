package com.technolligence.cricketstream;

public class BowlerAttr {
    String econ;
    String medians;
    String name;
    String overs;
    String score;
    String wickets;

    public String getEcon() {
        return econ;
    }

    public void setEcon(String econ) {
        this.econ = econ;
    }

    public String getMedians() {
        return medians;
    }

    public void setMedians(String medians) {
        this.medians = medians;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOvers() {
        return overs;
    }

    public void setOvers(String overs) {
        this.overs = overs;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWickets() {
        return wickets;
    }

    public void setWickets(String wickets) {
        this.wickets = wickets;
    }

    public BowlerAttr(String econ, String medians, String name, String overs, String score, String wickets) {
        this.econ = econ;
        this.medians = medians;
        this.name = name;
        this.overs = overs;
        this.score = score;
        this.wickets = wickets;
    }

    public BowlerAttr() {
    }
}
