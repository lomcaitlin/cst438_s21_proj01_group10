package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface JsonPlaceHolderApi {
    @GET("players")
    Call<List<Post>> getPlayers();
}
