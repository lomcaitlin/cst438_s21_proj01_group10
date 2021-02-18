package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDAO;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDatabase;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.User;

public class UserSettings extends AppCompatActivity {

    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText passwordConfEdit;
    private Button save;

    private String username;
    private String password;
    private String passwordConf;

    private int userId;
    private AppDAO appDAO;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getUserId();
        getDB();
        getUserInfo();
        connectToDisplay();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDisplayInfo();
                if (checkUsername()) {
                    saveAlert();
                }
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
     * get db
     */
    private void getDB() {
        appDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();
    }

    /**
     * Get user info from db
     */
    private void getUserInfo() {
        user = appDAO.getUserById(userId);
    }

    /**
     * Connect to display variables
     */
    private void connectToDisplay() {
        usernameEdit = findViewById(R.id.settings_username);
        passwordEdit = findViewById(R.id.settings_password);
        passwordConfEdit = findViewById(R.id.settings_passwordConf);
        usernameEdit.setHint("Username: " + user.getUsername());
        save = findViewById(R.id.settings_button);
    }

    /**
     * check duplicate username
     */
    private boolean checkUsername() {
        if (appDAO.getUserByUsername(username) != null) {
            usernameEdit.requestFocus();
            Toast.makeText(this, "Username taken", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Save changes alert
     */
    private void saveAlert() {
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);
        confirm.setTitle("Confirm changes to account");
        confirm.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateUser();
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
     * update DB
     */
    private void updateUser() {
        if (username.equals("")) {
            username = user.getUsername();
        }
        if (password.equals("")) {
            password = user.getPassword();
            passwordConf = user.getPassword();
        }
        if (password.equals(passwordConf)) {
                user.setUsername(username);
                user.setPassword(password);
                appDAO.update(user);
                Toast.makeText(this, "Updated your account", Toast.LENGTH_SHORT).show();
                usernameEdit.setHint("Username: " + username);
        } else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get display info
     */
    private void getDisplayInfo() {
        username = usernameEdit.getText().toString();
        password = passwordEdit.getText().toString();
        passwordConf = passwordConfEdit.getText().toString();
    }

    /**
     * factory pattern intent
     * @param context
     * @param userId
     * @return
     */
    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, UserSettings.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}