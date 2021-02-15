package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class UserSettings extends AppCompatActivity {

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getUserId();
    }

    private void getUserId() {
        userId = getIntent().getIntExtra("userId", -1);
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, UserSettings.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}