package tec.musicbeansapp.gui;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class SignUpActivity extends AppCompatActivity {

    // Vars

    // UI Views
    EditText txtFullName;
    EditText txtEmail;
    EditText txtUsername;
    EditText txtPassword;
    EditText txtConfirmPassword;
    Button btnSignUp;
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtFullName = (EditText) findViewById(R.id.txtFullName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();//esto no debe ir aquí, es una prueba
        final Connection cn = cs.get_Instance_Connection();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtUsername.getText().toString();
                if(!user.equals("")){
                    String query_search_user = "SELECT [USERNAME] FROM [dbo].CUENTA " +
                            "WHERE USERNAME = ?";
                    try{
                        PreparedStatement ps = cn.prepareStatement(query_search_user);
                        ps.setString(1,user);
                        ResultSet rs = ps.executeQuery();
                        if(rs.next()){
                            Toast.makeText(SignUpActivity.this, "Ya existe ese usuario",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            String password = txtPassword.getText().toString();
                            String email = txtEmail.getText().toString();
                            if(!password.equals("") & password.equals(txtConfirmPassword.getText().toString())){
                                String query_search_email = "SELECT [EMAIL] FROM [dbo].CLIENTE " +
                                        "WHERE EMAIL = ?";
                                PreparedStatement ps_email = cn.prepareStatement(query_search_email);
                                ps_email.setString(1, email);
                                ResultSet rs_email = ps_email.executeQuery();
                                if(rs_email.next()){
                                    Toast.makeText(SignUpActivity.this, "Ya existe alguien con ese email.",
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    String name = txtFullName.getText().toString();
                                    if(!name.equals("")){
                                        String query_sign_up = "INSERT INTO [dbo].CUENTA " +
                                                "VALUES (?,?,?)";
                                        PreparedStatement ps_insert = cn.prepareStatement(query_sign_up);
                                        ps_insert.setString(1,user);
                                        ps_insert.setString(2,password);
                                        ps_insert.setString(3,"C");
                                        ps_insert.execute();
                                        ps_insert.close();

                                        String query_insert_client = "INSERT INTO [dbo].CLIENTE " +
                                                "VALUES(?,?)";
                                        PreparedStatement ps_insert_client = cn.prepareStatement(query_insert_client);
                                        ps_insert_client.setString(1,name);
                                        ps_insert_client.setString(2,email);
                                        ps_insert_client.execute();
                                        ps_insert_client.close();

                                        String query_get_idClient = "SELECT [ID_CLIENTE] FROM [dbo].CLIENTE " +
                                                "WHERE EMAIL = ?";
                                        PreparedStatement ps_get_idClient = cn.prepareStatement(query_get_idClient);
                                        ps_get_idClient.setString(1,email);
                                        ResultSet rs_idClient = ps_get_idClient.executeQuery();
                                        rs_idClient.next();
                                        int idClient = rs_idClient.getInt(1);
                                        ps_get_idClient.close();

                                        String query_final_insert = "INSERT INTO [dbo].CUENTAPORCLIENTE " +
                                                "VALUES (?,?)";
                                        PreparedStatement ps_final_insert = cn.prepareStatement(query_final_insert);
                                        ps_final_insert.setInt(1,idClient);
                                        ps_final_insert.setString(2,user);
                                        ps_final_insert.execute();
                                        ps_final_insert.close();
                                        Toast.makeText(SignUpActivity.this, "Registro exitoso",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent( SignUpActivity.this , LoginActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(SignUpActivity.this, "El nombre no puede ser vacío.",
                                                Toast.LENGTH_SHORT).show();
                                        System.out.println("");
                                    }
                                }
                            }else{
                                Toast.makeText(SignUpActivity.this, "Las contraseñas deben ser iguales y no vacías.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(SignUpActivity.this, "El nombre de usuario no puede ser vacío",
                            Toast.LENGTH_SHORT).show();
                }
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
