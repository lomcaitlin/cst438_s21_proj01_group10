package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.widget.EditText;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Part;

public interface NbaApi {
    @GET("api/v1/teams/{id}")
    Call<TeamPost> getTeams(@Path("id") int teamId);

    @GET("api/v1/games/{id}")
    Call<GamePost> getGames(@Path("id") int gameId);

    @GET("api/v1/players")
    Call<JSONResponse> getPlayers();

    @GET("api/v1/players/{id}")
    Call<PlayerPost> getPlayer(@Path("id") int playerId);
}
