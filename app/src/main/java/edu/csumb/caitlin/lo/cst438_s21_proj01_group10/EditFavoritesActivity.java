package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDAO;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDatabase;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.Favorites;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditFavoritesActivity extends AppCompatActivity {

    private Spinner dropdown;
    private EditText endpoint;
    private Button typeButton;
    private TextView itemList;
    private Button deleteButton;
    private String type;
    private String endpointId;

    private AppDAO appDAO;
    private int userId;
    private Favorites toDelete;

    private NbaApi nbaApi;
    String itemName;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfavorites);

        getUserId();
        getDB();
        connectToDisplay();
        buildRetrofit();

        typeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getDisplayInfo();

                 if (toDelete == null) {
                     itemList.setText("No favorites with type \"" + type + "\" and ID \"" + endpointId + "\"");
                 } else if (toDelete.getType().equals("players")) {
                     getFavoritePlayer(Integer.parseInt(toDelete.getEndpoint()));
                 } else if (toDelete.getType().equals("teams")) {
                     getFavoriteTeam(Integer.parseInt(toDelete.getEndpoint()));
                 }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlert();
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
     * connect elements from display to variables
     */
    private void connectToDisplay() {
        dropdown = findViewById(R.id.edit_fav_type);
        endpoint = findViewById(R.id.edit_fav_endpoint);
        typeButton = findViewById(R.id.edit_fav_type_button);
        String[] types = new String[]{"Players", "Teams"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        dropdown.setAdapter(adapter);
        itemList = findViewById(R.id.edit_fav_list);
        deleteButton = findViewById(R.id.edit_fav_delete);
    }

    /**
     * Get DB
     */
    private void getDB() {
        appDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();
    }

    /**
     * Get user input when button is pressed
     */
    private void getDisplayInfo() {
        type = dropdown.getSelectedItem().toString();
        endpointId = endpoint.getText().toString();
        toDelete = appDAO.getFavoriteByPrimaryKey(userId, type, endpointId);
    }

    /**
     * Build retrofit
     */
    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nbaApi = retrofit.create(NbaApi.class);
    }

    /**
     * get favorite player to be deleted
     */
    private void getFavoritePlayer(Integer playerId) {
        deleteButton.setVisibility(View.INVISIBLE);
        Call<PlayerPost> call = nbaApi.getPlayer(playerId);
        call.enqueue(new Callback<PlayerPost>() {
            @Override
            public void onResponse(Call<PlayerPost> call, Response<PlayerPost> response) {
                if (!response.isSuccessful()) {
                    itemList.setText("Error Code: " + response.code());
                    return;
                }
                PlayerPost player = response.body();
                String info = "";
                itemName = player.getFirst_name() + " " + player.getLast_name();
                info += "\nPlayer Name: " + player.getFirst_name() + " " + player.getLast_name() + "\n";
                if (player.getHeight_inches() != null && player.getHeight_feet() != null) {
                    info += "Height: " + player.getHeight_feet() + "\' " + player.getHeight_inches() + "\"\n";
                }
                info += "Position: " + player.getPosition() + "\n";
                itemList.setText(info);
                deleteButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<PlayerPost> call, Throwable t) {
                itemList.setText(t.getMessage());
            }
        });
    }

    /**
     * get favorite team to be deleted
     */
    private void getFavoriteTeam(Integer teamId) {
        deleteButton.setVisibility(View.INVISIBLE);
        Call<TeamPost> call = nbaApi.getTeams(teamId);
        call.enqueue(new Callback<TeamPost>() {
            @Override
            public void onResponse(Call<TeamPost> call, Response<TeamPost> response) {
                if (!response.isSuccessful()) {
                    itemList.setText("Error Code: " + response.code());
                    return;
                }
                TeamPost team = response.body();
                String info = "";
                itemName = team.getFull_name();
                info += "\nTeam Name: " + team.getFull_name() + "\n";
                info += "City: " + team.getCity() + "\n";
                info += "Abbreviation: " + team.getAbbreviation() + "\n";
                info += "Conference: " + team.getConference() + "\n";
                itemList.setText(info);
                deleteButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<TeamPost> call, Throwable t) {
                itemList.setText(t.getMessage());
            }
        });
    }

    /**
     * Click delete alert dialogue
     */
    private void removeAlert() {
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);
        confirm.setTitle("Confirm deletion of: ");
        confirm.setMessage(itemName);
        confirm.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeFavorite();
            }
        });
        confirm.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        confirm.setCancelable(true);
        confirm.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        confirm.create().show();
    }

    /**
     * Remove favorite from DB
     */
    private void removeFavorite() {
        appDAO.delete(toDelete);
        itemList.setText("");
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
}
