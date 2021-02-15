package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPlayer extends AppCompatActivity {

    /* Display variables */
    private AutoCompleteTextView searchPlayer;
    private TextView playerList;
    private Button search;

    /* API variables */
    private NbaApi nbaApi;
    private HashMap<String,Integer> playersHash;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);

        getUserId();
        buildRetrofit();
        getInitialPlayers();
        connectToDisplay();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int playerId = getPlayerId();
                getPlayerInfo(playerId);
            }
        });
    }

    /**
     * get userId from intent
     */
    private void getUserId() {
        userId = getIntent().getIntExtra("userId", -1);
    }

    /**
     * Connect variables to elements in display & fill autofill text
     */
    private void connectToDisplay() {
        searchPlayer = findViewById(R.id.search_player_autocomplete);
        playerList = findViewById(R.id.search_player_list);
        search = findViewById(R.id.search_player_button);
        List<String> players = new ArrayList<>(playersHash.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, players);
    }

    /**
     * Build retrofit
     */
    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nbaApi = retrofit.create(NbaApi.class);
    }

    /**
     * get player name from autofill text
     */
    private int getPlayerId() {
        return playersHash.get(searchPlayer.getText().toString());
    }

    /**
     * get player info from API using id from hash
     */
    private void getPlayerInfo(int id) {
        Call<PlayerPost> call = nbaApi.getPlayer(id);
        call.enqueue(new Callback<PlayerPost>() {
            @Override
            public void onResponse(Call<PlayerPost> call, Response<PlayerPost> response) {
                if(!response.isSuccessful()) {
                    playerList.setText("Code: " + response.code());
                    return;
                }
                PlayerPost player = response.body();
                playerList.setText("");
            }

            @Override
            public void onFailure(Call<PlayerPost> call, Throwable t) {
                playerList.setText(t.getMessage());
            }
        });
    }

    /**
     * getAllPlayers and add to hashmap for search
     */
    private void getInitialPlayers() {
        playersHash = new HashMap<>();
        Call<List<PlayerPost>> call = nbaApi.getAllPlayers();
        call.enqueue(new Callback<List<PlayerPost>>() {
            @Override
            public void onResponse(Call<List<PlayerPost>> call, Response<List<PlayerPost>> response) {
                if(!response.isSuccessful()) {
                    playerList.setText("Code: " + response.code());
                    return;
                }
                List<PlayerPost> players = response.body();
                for (PlayerPost player : players) {
                    playersHash.put(player.getFirst_name() + " " + player.getLast_name(), player.getId());
                }
            }

            @Override
            public void onFailure(Call<List<PlayerPost>> call, Throwable t) {
                playerList.setText(t.getMessage());
            }
        });
    }

    /**
     * Intent factory to switch to SearchPlayer
     * @param context
     * @return
     */
    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, SearchPlayer.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}