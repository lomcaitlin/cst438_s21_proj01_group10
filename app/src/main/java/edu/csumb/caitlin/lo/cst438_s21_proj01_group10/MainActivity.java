package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    /* Display Variables */
    private Button login;
    private Button createAcct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectToDisplay();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: redirect to login page
                // Intent loginPage = new Intent(MainActivity.this, LoginActivity.class);
                // startActivity(loginPage);
            }
        });

        createAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: redirect to create acct page
                // Intent createAcct = new Intent(MainActivity.this, CreateAccountActivity.class);
                // startActivity(createAcct);
            }
        });
    }

    /**
     * Connect to elements in layout
     */
    private void connectToDisplay() {
        login = findViewById(R.id.login_pg_bttn);
        createAcct = findViewById(R.id.create_acct_pg_bttn);
    }
}