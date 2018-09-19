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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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





        /*if(cn != null){
            Log.e("d","Success");
        }else{
            Log.e("c","Failure");
        }*/
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to log in.");
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                ConnectToSQLServer cs = new ConnectToSQLServer();//esto no debe ir aquí, es una prueba
                Connection cn = cs.getConnection();

                if(cn != null){
                    System.out.println("Success");
                }
                else{
                    System.out.println("Error with DB connection");
                }

                String userType = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                String query = "SELECT [USERNAME],[PASSWORD_ACCOUNT],[ACCOUNT_TYPE] "+
                        "FROM [dbo].CUENTA WHERE USERNAME = ? and PASSWORD_ACCOUNT = ?";
                try{
                    PreparedStatement ps;
                    ResultSet rs;
                    ps = cn.prepareStatement(query);
                    ps.setString(1,userType);//la posición de cada signo de pregunta, también
                    ps.setString(2,password);//importa el tipo de parámetro
                    rs = ps.executeQuery();//solo si tiene que retornar algo, si no sería execute()
                    String usuario;
                    String contrasenna;
                    String tipo;
                    if(rs.next()){//verifica si existe algún elemento
                        usuario = rs.getString(1);
                        contrasenna = rs.getString(2);
                        tipo = rs.getString(3);
                        switch (tipo)
                        {
                            case "A":
                                System.out.println("LOG: Logging in as admin.");
                                Intent intent = new Intent(LoginActivity.this , AdminHomeActivity.class);
                                intent.putExtra("username" , usuario);
                                startActivity(intent);
                                break;

                            case "B":
                                System.out.println("LOG: Logging in as band.");
                                Intent intent2 = new Intent(LoginActivity.this , BandHomeActivity.class);
                                intent2.putExtra("username" , usuario);
                                startActivity(intent2);
                                break;

                            case "C":
                                System.out.println("LOG: Logging in as client.");
                                Intent intent3 = new Intent(LoginActivity.this ,ClientHomeActivity.class);
                                intent3.putExtra("username" , usuario);
                                startActivity(intent3);
                        }
                    }else{
                        System.out.println("No existe el usuario o es incorrecta la contraseña");
                    }
                }catch(SQLException e){
                    e.printStackTrace();
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
