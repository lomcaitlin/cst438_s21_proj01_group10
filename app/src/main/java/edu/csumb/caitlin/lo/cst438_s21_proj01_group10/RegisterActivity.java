package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDAO;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDatabase;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.User;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword, editTextCnfPassword;
    Button buttonRegister;
    TextView textViewLogin;

    String username;
    String password;
    String passwordConf;

    private AppDAO appDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        connectToDisplay();
        getDB();

        //after clicking login with good credentials will send user to login screen
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.getIntent(getApplicationContext()));
            }
        });



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCredentials();

                /**
                 * empty username or password check
                 */
                if (username.matches("") || password.matches("") || passwordConf.matches("")) {
                    Toast.makeText(RegisterActivity.this, "Empty text field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (appDao.getUserByUsername(username) != null) {
                    editTextUsername.requestFocus();
                    Toast.makeText(RegisterActivity.this, "Username taken", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(passwordConf)) {
                    editTextPassword.requestFocus();
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(username, password);
                    appDao.insert(user);
                    startActivity(MainActivity.getIntent(getApplicationContext()));
                }
            }
        });
    }

    /**
     * Connect display elements to variables
     */
    private void connectToDisplay() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCnfPassword = findViewById(R.id.editTextCnfPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);
    }

    /**
     * connect to AppDAO
     */
    private void getDB() {
        appDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();
    }

    /**
     * get user input
     */
    private void getCredentials() {
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        passwordConf = editTextCnfPassword.getText().toString().trim();
    }

    /**
     * Factory pattern intent
     * @param context
     * @return
     */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }
}