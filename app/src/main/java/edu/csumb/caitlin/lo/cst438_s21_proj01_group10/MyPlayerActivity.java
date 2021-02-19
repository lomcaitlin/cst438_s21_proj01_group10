package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDAO;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDatabase;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.Favorites;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPlayerActivity extends AppCompatActivity {

    private TextView myPlayers;

    private AppDAO appDAO;
    private NbaApi nbaApi;
    private int userId;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplayer);

        getUserId();
        getDB();
        connectToDisplay();
        buildRetrofit();
        getFavoritePlayers();

    }

    /**
     * get the userID from intent extras
     */
    private void getUserId() {
        userId = getIntent().getIntExtra("userId", -1);
    }

    /**
     * connect to the db
     */
    private void getDB() {
        appDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();
    }

    /**
     * Connect display elements to variables
     */
    private void connectToDisplay() {
        myPlayers = findViewById(R.id.my_players_view);
    }

    /**
     * build retrofit to connect to api
     */
    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nbaApi = retrofit.create(NbaApi.class);
    }

    /**
     * Get list of favorite players from DB and call getPlayerInfo
     */
    private void getFavoritePlayers() {
        List<Favorites> favPlayers = appDAO.getFavoritesByUserId(userId);
        for (Favorites fav : favPlayers) {
            if (fav.getType().equals("players")) {
                Integer playerId = Integer.parseInt(fav.getEndpoint());
                getPlayerInfo(playerId);
            }
        }
    }

    /**
     * Get specific player from API and append to myPlayer textview
     * @param playerId
     */
    private void getPlayerInfo(Integer playerId) {
        Call<PlayerPost> call = nbaApi.getPlayer(playerId);
        call.enqueue(new Callback<PlayerPost>() {
            @Override
            public void onResponse(Call<PlayerPost> call, Response<PlayerPost> response) {
                if (!response.isSuccessful()) {
                    myPlayers.setText("Error Code: " + response.code());
                    return;
                }
                PlayerPost player = response.body();
                String info = "";
                info += "\nPlayer ID: " + player.getId() + "\nPlayer Name: " + player.getFirst_name() + " " + player.getLast_name() + "\n";
                if (player.getHeight_inches() != null && player.getHeight_feet() != null) {
                    info += "Height: " + player.getHeight_feet() + "\' " + player.getHeight_inches() + "\"\n";
                }
                info += "Position: " + player.getPosition() + "\n";
                myPlayers.append(info);
            }

            @Override
            public void onFailure(Call<PlayerPost> call, Throwable t) {
                myPlayers.setText(t.getMessage());
            }
        });
    }

    /**
     * factory pattern intent
     * @param context
     * @param userId
     * @return
     */
    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, MyPlayerActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}
