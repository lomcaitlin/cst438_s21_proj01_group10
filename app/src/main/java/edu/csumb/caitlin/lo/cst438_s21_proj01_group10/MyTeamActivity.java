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

public class MyTeamActivity extends AppCompatActivity {

    private TextView myTeamTextView;
    private AppDAO appDao;
    private int userId;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myteam);

        appDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NbaApi nbaApi = retrofit.create(NbaApi.class);

        getUserId();

        myTeamTextView = findViewById(R.id.my_players_view);

        List<Favorites> favTeam = appDao.getFavoritesByUserId(userId);
        for(Favorites fav : favTeam){
            if(fav.getType().equals("teams")){
                Integer teamId = Integer.parseInt(fav.getEndpoint());

                Call<TeamPost> call = nbaApi.getTeams(teamId);
                call.enqueue(new Callback<TeamPost>() {
                    @Override
                    public void onResponse(Call<TeamPost> call, Response<TeamPost> response) {
                        TeamPost post = response.body();

                        String content = "";
                        content += "Team ID: " + teamId + "\n";
                        content += "Full Name: " + post.getFull_name() + "\n";
                        content += "City: " + post.getCity() + "\n";
                        content += "Abbreviation: " + post.getAbbreviation() + "\n";
                        content += "Conference: " + post.getConference() + "\n";
                        content += "\n";
                        myTeamTextView.append(content);
                    }

                    @Override
                    public void onFailure(Call<TeamPost> call, Throwable t) {

                    }
                });

            }
        }




    }

    private void getUserId(){
        userId = getIntent().getIntExtra("userId",-1);
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, MyTeamActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}
