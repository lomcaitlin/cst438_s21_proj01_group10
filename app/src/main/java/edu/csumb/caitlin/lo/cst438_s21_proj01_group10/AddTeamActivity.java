package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDAO;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDatabase;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.Favorites;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddTeamActivity extends AppCompatActivity {

    private TextView textViewTeamResult;
    private EditText editTextTeamId;
    private Button teamSearchButton;
    private Button addFavorites;
    private int teamId;
    private int userId;
    private AppDAO appDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        getDB();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NbaApi nbaApi = retrofit.create(NbaApi.class);

        getUserId();

        textViewTeamResult = findViewById(R.id.textViewGameResult);
        editTextTeamId = findViewById(R.id.editTextGameId);
        teamSearchButton = findViewById(R.id.gameSearchButton);
        addFavorites = findViewById(R.id.addFavoritesButton);
        addFavorites.setVisibility(View.INVISIBLE);


        teamSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewTeamResult.setText("");

                Call<TeamPost> call = nbaApi.getTeams(getTeamId());

                call.enqueue(new Callback<TeamPost>() {
                    @Override
                    public void onResponse(Call<TeamPost> call, Response<TeamPost> response) {
                        TeamPost post = response.body();

                                String content = "";
                                content += "Full Name: " + post.getFull_name() + "\n";
                                content += "City: " + post.getCity() + "\n";
                                content += "Abbreviation: " + post.getAbbreviation() + "\n";
                                content += "Conference: " + post.getConference() + "\n";
                                textViewTeamResult.append(content);
                                addFavorites.setVisibility(View.VISIBLE);

                        }

                    @Override
                    public void onFailure(Call<TeamPost> call, Throwable t) {

                    }

                });

            }
        });

        addFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites(getTeamId());
            }
        });



    }

    private void addToFavorites(Integer teamId) {
        if (userId < 0) {
            Toast.makeText(AddTeamActivity.this, "Add failure: not signed in", Toast.LENGTH_SHORT).show();
            startActivity(MainActivity.getIntent(getApplicationContext()));
            return;
        } else{
            Favorites addTeam = new Favorites(userId, "teams", teamId.toString());
            appDao.insert(addTeam);
            Toast.makeText(AddTeamActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
        }
    }


    private int getTeamId(){
        String value = editTextTeamId.getText().toString();
        teamId = Integer.parseInt(value);
//        if(editTextTeamId.getText().toString() == null){
//            return -1;
//        }
        return teamId;
    }

    private void getUserId(){
        userId = getIntent().getIntExtra("userId",-1);
    }

    private void getDB() {
        appDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, AddTeamActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}
