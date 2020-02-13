package com.technolligence.cricketstream;

public class PlayerAttributes {
    private String id;
    private String Image_url;
    private String Name;
    private String Age;
    private String Team;
    private String Type;
    private String Hand;
    private String Innings;
    private Integer BestScore;
    private Integer TotalScore;
    private Integer CurrentPSLScore;
    private String AvgScore;
    private String Strike;
    private Integer Thirty;
    private Integer Fifty;
    private Integer Hundred;
    private Integer BestBScore;
    private Integer BestBWicket;
    private Integer TWickets;
    private Integer CWickets;

    public PlayerAttributes() {
    }

    public PlayerAttributes(String id, String image_url, String name, String age, String team, String type, String hand, String innings, Integer bestScore, Integer totalScore, Integer currentPSLScore, String avgScore, String strike, Integer thirty, Integer fifty, Integer hundred, Integer bestBScore, Integer bestBWicket, Integer TWickets, Integer CWickets) {
        this.id = id;
        Image_url = image_url;
        Name = name;
        Age = age;
        Team = team;
        Type = type;
        Hand = hand;
        Innings = innings;
        BestScore = bestScore;
        TotalScore = totalScore;
        CurrentPSLScore = currentPSLScore;
        AvgScore = avgScore;
        Strike = strike;
        Thirty = thirty;
        Fifty = fifty;
        Hundred = hundred;
        BestBScore = bestBScore;
        BestBWicket = bestBWicket;
        this.TWickets = TWickets;
        this.CWickets = CWickets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return Image_url;
    }

    public void setImage_url(String image_url) {
        Image_url = image_url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String team) {
        Team = team;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getHand() {
        return Hand;
    }

    public void setHand(String hand) {
        Hand = hand;
    }

    public String getInnings() {
        return Innings;
    }

    public void setInnings(String innings) {
        Innings = innings;
    }

    public Integer getBestScore() {
        return BestScore;
    }

    public void setBestScore(Integer bestScore) {
        BestScore = bestScore;
    }

    public Integer getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(Integer totalScore) {
        TotalScore = totalScore;
    }

    public Integer getCurrentPSLScore() {
        return CurrentPSLScore;
    }

    public void setCurrentPSLScore(Integer currentPSLScore) {
        CurrentPSLScore = currentPSLScore;
    }

    public String getAvgScore() {
        return AvgScore;
    }

    public void setAvgScore(String avgScore) {
        AvgScore = avgScore;
    }

    public String getStrike() {
        return Strike;
    }

    public void setStrike(String strike) {
        Strike = strike;
    }

    public Integer getThirty() {
        return Thirty;
    }

    public void setThirty(Integer thirty) {
        Thirty = thirty;
    }

    public Integer getFifty() {
        return Fifty;
    }

    public void setFifty(Integer fifty) {
        Fifty = fifty;
    }

    public Integer getHundred() {
        return Hundred;
    }

    public void setHundred(Integer hundred) {
        Hundred = hundred;
    }

    public Integer getBestBScore() {
        return BestBScore;
    }

    public void setBestBScore(Integer bestBScore) {
        BestBScore = bestBScore;
    }

    public Integer getBestBWicket() {
        return BestBWicket;
    }

    public void setBestBWicket(Integer bestBWicket) {
        BestBWicket = bestBWicket;
    }

    public Integer getTWickets() {
        return TWickets;
    }

    public void setTWickets(Integer TWickets) {
        this.TWickets = TWickets;
    }

    public Integer getCWickets() {
        return CWickets;
    }

    public void setCWickets(Integer CWickets) {
        this.CWickets = CWickets;
    }
}