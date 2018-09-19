package tec.musicbeansapp.gui.Admin.Accounts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class ClientAccountListActivity extends AppCompatActivity {

    ListView clientAccountList;
    final ArrayList<String> options = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_client_account_list);

        clientAccountList = (ListView) findViewById(R.id.clientAccountList);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Clients");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Manage Account Activity");
                finish();
            }
        });


        clientAccountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("LOG: Clicked item: " + i);
                //TODO: Add intent to navigate to view where the admin can delete Client Accounts
            }
        });
        obtenerClientes();
    }

    public void obtenerClientes (){

        ArrayList<String[]> infoClients = new ArrayList<>();
        try{
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            String query = "SELECT CL.NOMBRE, C.USERNAME FROM [dbo].CLIENTE as CL " +
                    "INNER JOIN [dbo].CUENTAPORCLIENTE as CCL ON (CL.ID_CLIENTE = CCL.ID_CLIENTE) " +
                    "INNER JOIN [dbo].CUENTA as C ON (CCL.USERNAME = C.USERNAME)";
            PreparedStatement ps = cn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            do{
                String[] infoUser = new String[2];
                infoUser[0] = rs.getString(1);
                infoUser[1] = rs.getString(2);
                infoClients.add(infoUser);
            }while(rs.next());
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!infoClients.isEmpty()){
            System.out.println(infoClients.size());
            for(int i = 0; i < infoClients.size(); i++){
                String info = infoClients.get(i)[0] + " , " + infoClients.get(i)[1];
                options.add(info);
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        clientAccountList.setAdapter(adapter);
        clientAccountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ClientAccountListActivity.this, DeleteClientAccountActivity.class);
                intent.putExtra("objectName", options.get(position));//aqui hace los parametros que quiera pasar al otro activity
                startActivity(intent);
                finish();
            }
        });
    }
}
