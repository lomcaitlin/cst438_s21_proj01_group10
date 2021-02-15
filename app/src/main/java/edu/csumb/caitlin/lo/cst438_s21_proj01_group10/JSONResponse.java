package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

public class JSONResponse {
    private PlayerPost[] data;
    private TeamPost[] teamData;

    public TeamPost[] getTeamData() {
        return teamData;
    }

    public PlayerPost[] getData() {
        return data;
    }

    public void setData(PlayerPost[] data) {
        this.data = data;
    }
}
