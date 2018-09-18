package tec.musicbeansapp.gui;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.AdminHomeActivity;
import tec.musicbeansapp.gui.Band.BandHomeActivity;
import tec.musicbeansapp.gui.Client.ClientHomeActivity;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

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
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogIn = (Button) findViewById(R.id.btnConfirmLogin);
        txvSignUp = (TextView) findViewById(R.id.txvSignUp);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        ConnectToSQLServer cs = new ConnectToSQLServer();

        Connection cn = cs.getConnection();

        if(cn != null){
            Log.e("d","Success");
        }else{
            Log.e("c","Failure");
        }
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


        txvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to sign up. ");
                Intent signupIntent = new Intent(LoginActivity.this , SignUpActivity.class);
                startActivity(signupIntent);
            }
        });


    }

}
