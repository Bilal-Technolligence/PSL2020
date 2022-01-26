package com.technolligence.cricketstream;

public class TopBatAttr {
    String name;
    String team;
    Integer score;

    public TopBatAttr() {
    }

    public TopBatAttr(String name, String team, Integer score) {

        this.name = name;
        this.team = team;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
