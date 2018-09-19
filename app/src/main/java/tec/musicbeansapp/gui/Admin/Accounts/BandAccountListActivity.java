package tec.musicbeansapp.gui.Admin.Accounts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class BandAccountListActivity extends AppCompatActivity {


    // UI Views
    ListView bandList;
    Button btnAddNewBand;
    private ListView list;
    final ArrayList<String> options = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_account_list);

        bandList = (ListView) findViewById(R.id.bandList);
        btnAddNewBand = (Button) findViewById(R.id.btnAddNewBand);
        list = (ListView) findViewById(R.id.bandList);

        bandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Clicked item number: " + i);
                //TODO: Add Intent to navigate to an Activity to view Band details and enable option to eliminate it.
            }
        });

        btnAddNewBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BandAccountListActivity.this, CreateBandActivity.class);
                startActivity(intent);
            }
        });
        prueba();
    }
    public void prueba (){
        ArrayList<String> bandsNames = new ArrayList<>();
        try{
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            String query = "SELECT [NOMBRE] FROM [dbo].BANDA";
            PreparedStatement ps = cn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                bandsNames.add(rs.getString(1));
            }
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!bandsNames.isEmpty()){
            for(int i = 0; i < bandsNames.size(); i++){
                options.add(bandsNames.get(i));
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(BandAccountListActivity.this, DeleteBandActivity.class);
                intent.putExtra("objectName", options.get(position));//aqui hace los parametros que quiera pasar al otro activity
                startActivity(intent);
                finish();
            }
        });
    }
}
