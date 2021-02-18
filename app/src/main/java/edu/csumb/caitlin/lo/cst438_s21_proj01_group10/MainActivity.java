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

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;

    String username;
    String password;

    private AppDAO appDao;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectToDisplay();
        getDB();


        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterActivity.getIntent(getApplicationContext()));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCredentials();
                user = appDao.getUserByUsername(username);
                
                /**
                 * empty username or password check
                 */
                if (username.matches("") || password.matches("")) {
                    Toast.makeText(MainActivity.this, "Empty text field", Toast.LENGTH_SHORT).show();
                    return;
                }

                //checking if there is a user
                if (user != null) {
                    if (validatePassword()) {
                        startActivity(HomeActivity.getIntent(getApplicationContext(),user.getUserId()));
                    } else {
                        editTextPassword.requestFocus();
                        Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    editTextUsername.requestFocus();
                    Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Connect elements to variables
     */
    private void connectToDisplay() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
    }

    /**
     * Connect to db
     */
    private void getDB() {
        appDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAppDAO();
    }

    /**
     * Get user input
     */
    private void getCredentials() {
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
    }

    /**
     * Validate user input with password from db
     */
    private boolean validatePassword() {
        return password.equals(user.getPassword());
    }

    /**
     * Factory pattern intent
     * @param context
     * @return
     */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}
