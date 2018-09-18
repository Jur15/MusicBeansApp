package tec.musicbeansapp.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import tec.musicbeansapp.R;

public class SignUpActivity extends AppCompatActivity {

    // Vars

    // UI Views
    EditText txtFullName;
    EditText txtEmail;
    EditText txtUsername;
    EditText txtPassword;
    EditText txtConfirmPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        System.out.println("LOG: Created SignUpActivity. ");

        txtFullName = (EditText) findViewById(R.id.txtFullName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: User " + txtFullName.getText().toString() + " trying to sign up");
                Intent intent = new Intent( SignUpActivity.this , LoginActivity.class);
                startActivity(intent);
            }
        });

        TextView toolbarText = (TextView) findViewById(R.id.txtToolbarText);
        toolbarText.setText("Sign Up");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Log in Activity");
                finish();
            }
        });
    }
}
