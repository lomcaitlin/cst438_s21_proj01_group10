package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDAO;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDatabase;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.Favorites;

public class EditFavoritesActivity extends AppCompatActivity {

    private Spinner dropdown;
    private Button typeButton;

    private String type;

    private EditText favID;
    private Button deleteButton;

    private AppDAO appDao;
    private int userId;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfavorites);

        getUserId();
        connectToDisplay();
        getDB();

        typeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = dropdown.getSelectedItem().toString();
                favID.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromFavorites(favID);
            }
        });

    }

    /**
     * get user id from intent
     */
    private void getUserId() {
        userId = getIntent().getIntExtra("userId", -1);
    }

    /**
     * connect to the db
     */
    private void getDB() {
         appDao= Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();
    }

    /**
     * connect elements from display to variables
     */
    private void connectToDisplay() {
        dropdown = findViewById(R.id.edit_fav_type);
        typeButton = findViewById(R.id.edit_fav_type_button3);
        String[] types = new String[]{"players", "teams"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        dropdown.setAdapter(adapter);

        favID = findViewById(R.id.fav_id);
        deleteButton = findViewById(R.id.delete_fav_type_button);
        favID.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);

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

    /**
     * remove from favorites
     * @param id
     */
    private void removeFromFavorites(EditText id) {
        if (userId < 0) {
            Toast.makeText(EditFavoritesActivity.this, "Add failure: not signed in", Toast.LENGTH_SHORT).show();
            startActivity(MainActivity.getIntent(getApplicationContext()));
            return;
        } else if (appDao.getFavoriteByPrimaryKey(userId, type, id.getText().toString()) == null) {
            Toast.makeText(EditFavoritesActivity.this, "Not in favorites ", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Favorites remove = appDao.getFavoriteByPrimaryKey(userId, type, id.getText().toString());
            appDao.delete(remove);
            Toast.makeText(EditFavoritesActivity.this, "Removed from favorites ", Toast.LENGTH_SHORT).show();
        }
    }

}
