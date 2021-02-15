package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchGameActivity extends AppCompatActivity {

    private TextView textViewGameResult;
    private EditText editTextGameId;
    private Button gameSearchButton;
    private int gameId;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewGameResult = findViewById(R.id.textViewGameResult);
        editTextGameId = findViewById(R.id.editTextGameId);
        gameSearchButton = findViewById(R.id.gameSearchButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NbaApi nbaApi = retrofit.create(NbaApi.class);

        gameSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewGameResult.setText("");
                String value = editTextGameId.getText().toString();
                gameId = Integer.parseInt(value);


                Call<GamePost> call = nbaApi.getGames(gameId);

                call.enqueue(new Callback<GamePost>() {
                    @Override
                    public void onResponse(Call<GamePost> call, Response<GamePost> response) {
                        GamePost post = response.body();

                        String content = "";
                        content += "Date: " + post.getDate() + "\n";
                        content += "Season: " + post.getSeason() + "\n";
                        content += "Home Team Score: " + post.getHome_team_score() + "\n";
                        content += "Visitor Team Score " + post.getVisitor_team_score() + "\n";
                        content += "Home Team: " + post.getHomeName() + "\n";
                        content += "Visitor Team: " + post.getVisitorName() + "\n";
                        textViewGameResult.append(content);
                    }

                    @Override
                    public void onFailure(Call<GamePost> call, Throwable t) {

                    }
                });
            }
        });

    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SearchGameActivity.class);
        return intent;
    }
}
