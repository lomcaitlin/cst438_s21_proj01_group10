package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisitorTeam {
    @SerializedName("full_name")
    @Expose
    private String full_name;

    public String getFull_name() {
        return full_name;
    }
}
