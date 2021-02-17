package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDAO;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDatabase;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.Favorites;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPlayer extends AppCompatActivity {

    /* Display variables */
    private AutoCompleteTextView searchPlayer;
    private TextView playerList;
    private Button add;
    private Button search;

    /* API variables */
    private NbaApi nbaApi;
    private HashMap<String,Integer> playersHash;
    private List<String> playerNames;

    private AppDAO appDao;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);

        getDB();
        buildRetrofit();
        getInitialPlayers();
        getUserId();
        connectToDisplay();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPlayerId() < 0) {
                    searchPlayer.requestFocus();
                }
                getPlayerInfo(getPlayerId());
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites(getPlayerId());
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
        add = findViewById(R.id.add_player_fav_button);
        add.setVisibility(View.INVISIBLE);
        search = findViewById(R.id.search_player_button);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, playerNames);
        searchPlayer.setAdapter(adapter);
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
        if (playersHash.get(searchPlayer.getText().toString()) == null) {
            return -1;
        }
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
                    playerList.setText("Error Code: " + response.code());
                    add.setVisibility(View.INVISIBLE);
                    return;
                }
                PlayerPost player = response.body();
                String info = "";
                info += "\nPlayer Name: " + player.getFirst_name() + " " + player.getLast_name() + "\n";
                if (player.getHeight_feet() != null && player.getHeight_inches() != null) {
                    info += "Height: " + player.getHeight_feet() + "\' " + player.getHeight_inches() + "\"\n";
                }
                info += "Position: " + player.getPosition() + "\n";
                playerList.setText(info);
                add.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<PlayerPost> call, Throwable t) {
                playerList.setText(t.getMessage());
                add.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * getAllPlayers and add to hashmap for search
     */
    private void getInitialPlayers() {
        playersHash = new HashMap<>();
        playerNames = new ArrayList<>();
        Call<JSONResponse> call = nbaApi.getPlayers();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if(!response.isSuccessful()) {
                    playerList.setText("Code: " + response.code());
                    return;
                }
                JSONResponse jsonResponse = response.body();
                List<PlayerPost> playerList = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                for (PlayerPost player : playerList) {
                    playersHash.put(player.getFirst_name() + " " + player.getLast_name(), player.getId());
                    playerNames.add(player.getFirst_name() + " " + player.getLast_name());
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                playerList.setText(t.getMessage());
            }
        });

    }

    /**
     * connect to db
     */
    private void getDB() {
        appDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();
    }

    /**
     * add to favorites
     * @param playerId
     */
    private void addToFavorites(Integer playerId) {
        if (userId < 0) {
            Toast.makeText(SearchPlayer.this, "Add failure: not signed in", Toast.LENGTH_SHORT).show();
            startActivity(MainActivity.getIntent(getApplicationContext()));
            return;
        } else if (appDao.getFavoriteByPrimaryKey(userId, "players", playerId.toString()) != null) {
            Toast.makeText(SearchPlayer.this, "Already added to favorites", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Favorites addPlayer = new Favorites(userId, "players", playerId.toString());
            appDao.insert(addPlayer);
            Toast.makeText(SearchPlayer.this, "Added to favorites", Toast.LENGTH_SHORT).show();
        }
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