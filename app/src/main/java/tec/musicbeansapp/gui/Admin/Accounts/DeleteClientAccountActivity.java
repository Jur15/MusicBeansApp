package tec.musicbeansapp.gui.Admin.Accounts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class DeleteClientAccountActivity extends AppCompatActivity {

    TextView txvClientName;
    TextView txvClientAge;
    TextView txvClientEmail;
    ImageView imvClientPicture;
    Button btnDeleleteClienteAccount;

    private String name;
    private String username;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_client_account);

        txvClientName = (TextView) findViewById(R.id.txvClientName);
        txvClientAge = (TextView) findViewById(R.id.txvClientAge);
        txvClientEmail = (TextView) findViewById(R.id.txvClientEmail);
        imvClientPicture = (ImageView) findViewById(R.id.imvClientPicture);
        btnDeleleteClienteAccount = (Button) findViewById(R.id.btnDeleteClientAccount);

        String info_user = getIntent().getStringExtra("objectName").toString();
        String[] parts = info_user.split(" , ");

        name = parts[0];
        username = parts[1];

        txvClientName.setText(name);
        txvClientAge.setText(username);


        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();

        try{
            String query = "SELECT CL.EMAIL " +
                    "FROM CLIENTE as CL INNER JOIN CUENTAPORCLIENTE as CCL ON (CL.ID_CLIENTE = CL.ID_CLIENTE) " +
                    "INNER JOIN CUENTA as C ON (CCL.USERNAME = C.USERNAME) WHERE C.USERNAME = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            email = rs.getString(1);
            txvClientEmail.setText(email);
            rs.close();
            ps.close();

        }catch(Exception e){
            e.printStackTrace();
        }



        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Deleting Client account");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteClientAccountActivity.this, ClientAccountListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeleleteClienteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String query_delete = "DELETE FROM [dbo].CUENTA WHERE USERNAME = ?";
                    PreparedStatement ps_delete = cn.prepareStatement(query_delete);
                    ps_delete.setString(1, username);
                    ps_delete.execute();
                    ps_delete.close();
                    Intent intent = new Intent(DeleteClientAccountActivity.this, ClientAccountListActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
