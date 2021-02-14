package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

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

        textViewTeamResult = findViewById(R.id.textViewTeamResult);
        editTextTeamId = findViewById(R.id.editTextTeamId);
        teamSearchButton = findViewById(R.id.teamSearchButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NbaApi nbaApi = retrofit.create(NbaApi.class);


        teamSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = editTextTeamId.getText().toString();
                teamId = Integer.parseInt(value);

                Call<List<TeamPost>> call = nbaApi.getTeams(teamId);

                call.enqueue(new Callback<List<TeamPost>>() {
                    @Override
                    public void onResponse(Call<List<TeamPost>> call, Response<List<TeamPost>> response) {

                        List<TeamPost> teamPosts = response.body();

                        for(TeamPost post : teamPosts){
                                String content = "";
                                content += "Full Name: " + post.getFull_name() + "\n";
                                textViewTeamResult.append(content);
                        }



                        }

                    @Override
                    public void onFailure(Call<List<TeamPost>> call, Throwable t) {

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
