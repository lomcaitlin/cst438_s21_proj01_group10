package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private int id;
    RecyclerView recyclerView;
    List<PlayerPost> playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        recyclerView = findViewById(R.id.recyclerView);
        playerList = new ArrayList<>();

        //TextView welcomeMsg = findViewById(R.id.welcomeMsg);
        id = getIntent().getIntExtra("id", 0);
        //welcomeMsg.setText("Welcome Subject " + id);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NbaApi nbaApi = retrofit.create(NbaApi.class);

        Call<JSONResponse> call = nbaApi.getPlayers();

        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                playerList = new ArrayList<>(Arrays.asList(jsonResponse.getData()));

                for(PlayerPost post : playerList){
                    if(id == post.getId()){
                        PutDataIntoRecyclerView(playerList);
                    }
                }
                //PutDataIntoRecyclerView(playerList);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }

    private void PutDataIntoRecyclerView(List<PlayerPost> playerList){
        Adaptery adaptery = new Adaptery(this, playerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptery);
    }

    public static Intent getIntent(Context context, int id) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("id", id);
        return intent;
    }
}