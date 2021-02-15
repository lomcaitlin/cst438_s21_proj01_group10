package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GamePost {
    private String date;
    private int home_team_score;
    private int visitor_team_score;
    private String season;
    @SerializedName("home_team")
    @Expose
    private HomeTeam homeTeam;
    @SerializedName("visitor_team")
    @Expose
    private VisitorTeam visitorTeam;

    public String getVisitorName(){
        return visitorTeam==null? "" :
                visitorTeam.getFull_name();
    }

    public String getHomeName(){
        return homeTeam==null? "" :
                homeTeam.getFull_name();
    }

    public HomeTeam getHomeTeam() {
        return homeTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHome_team_score() {
        return home_team_score;
    }

    public void setHome_team_score(int home_team_score) {
        this.home_team_score = home_team_score;
    }

    public int getVisitor_team_score() {
        return visitor_team_score;
    }

    public void setVisitor_team_score(int visitor_team_score) {
        this.visitor_team_score = visitor_team_score;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

}
