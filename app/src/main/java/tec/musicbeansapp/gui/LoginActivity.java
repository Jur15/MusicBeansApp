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
import android.widget.Toast;

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
                ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();//esto no debe ir aquí, es una prueba
                Connection cn = cs.get_Instance_Connection();

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
                    ps.close();
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
                                Intent intent = new Intent(LoginActivity.this , AdminHomeActivity.class);
                                startActivity(intent);
                                break;

                            case "B":
                                Intent intent2 = new Intent(LoginActivity.this , BandHomeActivity.class);
                                startActivity(intent2);
                                break;

                            case "C":
                                Intent intent3 = new Intent(LoginActivity.this ,ClientHomeActivity.class);
                                startActivity(intent3);
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "El nombre de usuario no existe o la contraseña es incorrecta",
                                Toast.LENGTH_SHORT).show();
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
