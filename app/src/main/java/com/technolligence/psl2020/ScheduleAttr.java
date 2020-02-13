package com.technolligence.psl2020;

public class ScheduleAttr {
    String teamOne;
    String teamTwo;
    Integer sid;
    String id;
    String time;
    String date;
    String status;
    String city;
    String winner;
    String note;

    public ScheduleAttr() {
    }

    public ScheduleAttr(String teamOne, String teamTwo, Integer sid, String id, String time, String date, String status, String city, String winner, String note) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.sid = sid;
        this.id = id;
        this.time = time;
        this.date = date;
        this.status = status;
        this.city = city;
        this.winner = winner;
        this.note = note;
    }

    public String getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(String teamOne) {
        this.teamOne = teamOne;
    }

    public String getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(String teamTwo) {
        this.teamTwo = teamTwo;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}