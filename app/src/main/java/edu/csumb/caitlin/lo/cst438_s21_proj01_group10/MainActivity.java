package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

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

//import com.example.project02.data.UserDAO;
//import com.example.project02.data.UserDatabase;
//import com.example.project02.model.User;
//
//import static com.example.project02.HomeActivity.ACTIVE_USER_KEY;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;
    private AppDAO db;
    AppDatabase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        textViewRegister = findViewById(R.id.textViewRegister);

        dataBase = Room.databaseBuilder(this, AppDatabase.class, "cst438_group10.db")
                .allowMainThreadQueries()
                .build();

        db = dataBase.getAppDAO();


        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inputs from login screen
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                //predefined users
                User admin = new User("admin","admin");
               // User testuser1 = new User("testuser1","testuser1");
                db.insert(admin);
                //db.insert(testuser1);
                //getting user from login
                User user = db.getUserByUsername(username);

                //checking if there is a user
                if (user != null) {
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    //i.putExtra(ACTIVE_USER_KEY, user);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Unregistered user, or incorrect password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
