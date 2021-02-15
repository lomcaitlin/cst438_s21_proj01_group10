package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.User;


public class HomeActivity extends AppCompatActivity {

    private Button addPlayerButton;
    private Button addTeamButton;
    private Button searchGameButton;
    private Button userSettingsButton;
    private Button myPlayerButton;
    private Button myTeamButton;
    private Button editFavButton;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userId = getIntent().getIntExtra("userId", -1);

        addPlayerButton = findViewById(R.id.addPlayerButton);
        addTeamButton = findViewById(R.id.addTeamButton);
        searchGameButton = findViewById(R.id.searchGameButton);
        userSettingsButton = findViewById(R.id.userSettingsButton);
        myPlayerButton = findViewById(R.id.myPlayerButton);
        myTeamButton = findViewById(R.id.myTeamButton);
        editFavButton = findViewById(R.id.editFavButton);

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchPlayer.getIntent(getApplicationContext(), userId);
                startActivity(intent);
            }
        });

        addTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddTeamActivity.getIntent(getApplicationContext(),userId);
                startActivity(intent);
            }
        });

        searchGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchGameActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        userSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserSettings.getIntent(getApplicationContext(), userId);
                startActivity(intent);
            }
        });

        myPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MyPlayerActivity.getIntent(getApplicationContext(),userId);
                startActivity(intent);
            }
        });

        myTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MyTeamActivity.getIntent(getApplicationContext(),userId);
                startActivity(intent);
            }
        });

        editFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditFavoritesActivity.getIntent(getApplicationContext(),userId);
                startActivity(intent);
            }
        });

    }




    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}