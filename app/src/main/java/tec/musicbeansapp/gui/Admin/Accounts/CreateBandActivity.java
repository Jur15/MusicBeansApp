package tec.musicbeansapp.gui.Admin.Accounts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class CreateBandActivity extends AppCompatActivity {

    EditText bandName;
    EditText bandDescription;
    ImageView bandPicture;
    ImageButton uploadBandPicture;
    Button btnCreateNewBandAccount;
    EditText bandPassword;
    EditText bandConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_band);

        bandName = (EditText) findViewById(R.id.txtBandName);//falta username
        bandDescription = (EditText) findViewById(R.id.txtBandDescription);
        bandPassword = (EditText) findViewById(R.id.txtBandPassword);
        bandConfirmPassword = (EditText) findViewById(R.id.txtBandConfirmPassword);
        bandPicture = (ImageView) findViewById(R.id.imvBandPicture);
        uploadBandPicture = (ImageButton) findViewById(R.id.btnUploadBandPicture);
        btnCreateNewBandAccount = (Button) findViewById(R.id.btnCreateNewBandAccount);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Create new band account");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);

        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateBandActivity.this, BandAccountListActivity.class);
                startActivity(intent);
                finish();
            }
        });



        uploadBandPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to upload band picture");
            }
        });

        btnCreateNewBandAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String band_name = bandName.getText().toString();
                String password = bandPassword.getText().toString();
                String description = bandDescription.getText().toString();
                String confirmPassword = bandConfirmPassword.getText().toString();
                if(!band_name.equals("")){
                    if(!password.equals("") & password.equals(confirmPassword)){
                        try{
                            String query_searchBandname = "SELECT [NOMBRE] FROM [dbo].BANDA " +
                                    "WHERE NOMBRE = ?";
                            PreparedStatement ps_searchBandName = cn.prepareStatement(query_searchBandname);
                            ps_searchBandName.setString(1, band_name);
                            ResultSet rs = ps_searchBandName.executeQuery();
                            if(rs.next()){
                                Toast.makeText(CreateBandActivity.this, "Ya existe una banda con ese nombre",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                String query_insert_account = "INSERT INTO [dbo].CUENTA " +
                                        "VALUES (?,?,?)";
                                PreparedStatement ps_insert_account = cn.prepareStatement(query_insert_account);
                                ps_insert_account.setString(1,band_name);//por el momento, no sé como
                                ps_insert_account.setString(2,password);//agregar el campo de username
                                ps_insert_account.setString(3,"B");
                                ps_insert_account.execute();
                                ps_insert_account.close();

                                String query_insert_band = "INSERT INTO [dbo].BANDA " +
                                        "VALUES (?,?,?)";
                                PreparedStatement ps_insert_band = cn.prepareStatement(query_insert_band);
                                ps_insert_band.setString(1, band_name);
                                ps_insert_band.setString(2, description);
                                ps_insert_band.setFloat(3,5);
                                ps_insert_band.execute();
                                ps_insert_band.close();

                                String query_searchBandID = "SELECT [ID_BANDA] FROM [dbo].BANDA " +
                                        "WHERE NOMBRE = ?";
                                PreparedStatement ps_searchID = cn.prepareStatement(query_searchBandID);
                                ps_searchID.setString(1,band_name);
                                ResultSet infoBanda = ps_searchID.executeQuery();
                                infoBanda.next();
                                int idBanda = infoBanda.getInt(1);
                                infoBanda.close();
                                ps_searchID.close();

                                String query_insertBandAccount = "INSERT INTO [dbo].CUENTAPORBANDA " +
                                        "VALUES(?,?)";
                                PreparedStatement ps_insertBandAccount = cn.prepareStatement(query_insertBandAccount);
                                ps_insertBandAccount.setInt(1,idBanda);
                                ps_insertBandAccount.setString(2,band_name);
                                ps_insertBandAccount.execute();
                                ps_insertBandAccount.close();

                                Toast.makeText(CreateBandActivity.this, "El registro de la banda fue un éxito",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(CreateBandActivity.this, "Las contraseñas no deben estar vacías y deben coincidir",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CreateBandActivity.this, "El nombre de la banda no puede ser nulo",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



}
