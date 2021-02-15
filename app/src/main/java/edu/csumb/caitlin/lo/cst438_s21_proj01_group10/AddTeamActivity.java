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

public class AddTeamActivity extends AppCompatActivity {

    private TextView textViewTeamResult;
    private EditText editTextTeamId;
    private Button teamSearchButton;
    private int teamId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        textViewTeamResult = findViewById(R.id.textViewGameResult);
        editTextTeamId = findViewById(R.id.editTextGameId);
        teamSearchButton = findViewById(R.id.gameSearchButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NbaApi nbaApi = retrofit.create(NbaApi.class);


        teamSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewTeamResult.setText("");
                String value = editTextTeamId.getText().toString();
                teamId = Integer.parseInt(value);

                Call<TeamPost> call = nbaApi.getTeams(teamId);

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

                        }

                    @Override
                    public void onFailure(Call<TeamPost> call, Throwable t) {

                    }

                });

            }
        });



    }



    public static Intent getIntent(Context context, int id) {
        Intent intent = new Intent(context, AddTeamActivity.class);
        intent.putExtra("id", id);
        return intent;
    }
}
