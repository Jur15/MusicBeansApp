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

public class DeleteBandActivity extends AppCompatActivity {

    TextView bandName;
    TextView bandDescription;
    private String name;
    private  String username;
    private String descripcion;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_band);

        bandName = (TextView) findViewById(R.id.txvBandName);
        bandDescription = (TextView) findViewById(R.id.txvBandDescription);
        name=getIntent().getStringExtra("objectName").toString();
        btnDelete = (Button) findViewById(R.id.btnDeleteBandAccout);

        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();

        try{
            String query = "SELECT B.DESCRIPCION, C.USERNAME " +
                    "FROM BANDA as B INNER JOIN CUENTAPORBANDA as CB ON (B.ID_BANDA = CB.ID_BANDA) " +
                    "INNER JOIN CUENTA as C ON (CB.USERNAME = C.USERNAME) WHERE B.NOMBRE = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            descripcion = rs.getString(1);
            username = rs.getString(2);
            rs.close();
            ps.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        bandName.setText(name);
        bandDescription.setText(descripcion);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Deleting Band account");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteBandActivity.this, BandAccountListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String query_delete = "DELETE FROM [dbo].CUENTA WHERE USERNAME = ?";
                    PreparedStatement ps_delete = cn.prepareStatement(query_delete);
                    ps_delete.setString(1, username);
                    ps_delete.execute();
                    ps_delete.close();
                    Intent intent = new Intent(DeleteBandActivity.this, BandAccountListActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
