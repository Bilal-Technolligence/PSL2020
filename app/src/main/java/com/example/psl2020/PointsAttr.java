package com.example.psl2020;

public class PointsAttr {
    String id;
    String name;
    Integer points;

    public PointsAttr() {
    }

    public PointsAttr(String id, String name, Integer points) {
        this.id = id;
        this.name = name;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}