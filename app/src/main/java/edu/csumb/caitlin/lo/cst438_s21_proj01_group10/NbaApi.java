package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.widget.EditText;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NbaApi {
    @GET("api/v1/players")
    Call<JSONResponse> getPlayers();

    @GET("api/v1/teams/{id}")
    Call<List<TeamPost>> getTeams(@Path("id") int teamId);
}
