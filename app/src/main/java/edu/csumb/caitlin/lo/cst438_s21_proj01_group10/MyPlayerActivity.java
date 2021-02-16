package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MyPlayerActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplayer);


    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, MyPlayerActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}
