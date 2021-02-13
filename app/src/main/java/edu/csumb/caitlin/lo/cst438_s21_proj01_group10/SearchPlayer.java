package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SearchPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);



    }

    /**
     * Intent factory to switch to SearchPlayer
     * @param context
     * @return
     */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SearchPlayer.class);
        // intent.putExtra();
        return intent;
    }
}