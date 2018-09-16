package tec.musicbeansapp.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.AdminHomeActivity;
import tec.musicbeansapp.gui.Band.BandHomeActivity;
import tec.musicbeansapp.gui.Client.ClientHomeActivity;

public class LoginActivity extends AppCompatActivity {

    // Vars
    private String username;
    private String password;

    // UI Views
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogIn;
    TextView txvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // UI View "binding"
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogIn = findViewById(R.id.btnConfirmLogin);
        txvSignUp = findViewById(R.id.txvSignUp);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to log in.");

                String userType = txtUsername.getText().toString();
                switch (userType)
                {
                    case "admin":
                        System.out.println("LOG: Logging in as admin.");
                        Intent intent = new Intent(LoginActivity.this , AdminHomeActivity.class);
                        startActivity(intent);
                        break;

                    case "band":
                        System.out.println("LOG: Logging in as band.");
                        Intent intent2 = new Intent(LoginActivity.this , BandHomeActivity.class);
                        startActivity(intent2);
                        break;

                    case "client":
                        System.out.println("LOG: Logging in as client.");
                        Intent intent3 = new Intent(LoginActivity.this ,ClientHomeActivity.class);
                        startActivity(intent3);
                }
            }
        });



    }

}
