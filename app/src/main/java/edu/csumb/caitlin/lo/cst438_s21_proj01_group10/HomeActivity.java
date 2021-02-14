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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        display();

    }

    private void display(){
        addPlayerButton = findViewById(R.id.addPlayerButton);
        addTeamButton = findViewById(R.id.addTeamButton);
        searchGameButton = findViewById(R.id.searchGameButton);
        userSettingsButton = findViewById(R.id.userSettingsButton);

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddPlayerActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        addTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddTeamActivity.getIntent(getApplicationContext());
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
                Intent intent = UserSettingsActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });


    }


    public static Intent getIntent(Context context, int id) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("id", id);
        return intent;
    }
}