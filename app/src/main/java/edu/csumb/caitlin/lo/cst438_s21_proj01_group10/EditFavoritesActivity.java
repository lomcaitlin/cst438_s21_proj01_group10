package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDAO;

public class EditFavoritesActivity extends AppCompatActivity {

    private Spinner dropdown;
    private Button typeButton;

    private AppDAO appDAO;
    private int userId;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfavorites);

        getUserId();
        connectToDisplay();

    }

    /**
     * get user id from intent
     */
    private void getUserId() {
        userId = getIntent().getIntExtra("userId", -1);
    }

    /**
     * connect elements from display to variables
     */
    private void connectToDisplay() {
        dropdown = findViewById(R.id.edit_fav_type);
        typeButton = findViewById(R.id.edit_fav_type_button);
        String[] types = new String[]{"Players", "Teams"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        dropdown.setAdapter(adapter);
    }

    /**
     * factory pattern intent
     * @param context
     * @param userId
     * @return
     */
    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, EditFavoritesActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}
