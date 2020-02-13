package com.example.psl2020;

public class TopBallAttr {
    String name;
    String team;
    Integer wickets;

    public TopBallAttr() {
    }

    public TopBallAttr(String name, String team, Integer wickets) {
        this.name = name;
        this.team = team;
        this.wickets = wickets;
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

    public Integer getWickets() {
        return wickets;
    }

    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }
}
