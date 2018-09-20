package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.Accounts.DeleteBandActivity;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class ClientGeneralBandEventsActivity extends AppCompatActivity {

    ListView lsvClientBandGeneralEvents;

    final ArrayList<String> eventsNames = new ArrayList<>();
    final ArrayList<String> all_info = new ArrayList<>();

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_general_band_events);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);

        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Log in Activity");
                finish();
            }
        });
        username = getIntent().getStringExtra("username");
        toolBarText.setText(username);
        lsvClientBandGeneralEvents = (ListView) findViewById(R.id.lsvClientBandGeneralEvents);

        lsvClientBandGeneralEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        obtenerEventos();
    }

    public void obtenerEventos (){
        ArrayList<String> events = new ArrayList<>();
        try{
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            String query = "SELECT E.DETALLE, E.LUGAR, E.FECHA, E.ID_EVENTO FROM [dbo].EVENTO as E " +
                    "INNER JOIN [dbo].EVENTOPORBANDA as EB ON (E.ID_EVENTO = EB.ID_EVENTO) " +
                    "INNER JOIN [dbo].CUENTA as C ON (EB.USERNAME = C.USERNAME) " +
                    "INNER JOIN [dbo].BANDAPORCLIENTE as BC ON (C.USERNAME = BC.BANDA) " +
                    "WHERE BC.CLIENTE = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            Format formatter = new SimpleDateFormat("dd-MM-yyyy");
            while(rs.next()){
                String detalle = rs.getString(1);
                String lugar = rs.getString(2);
                String s = formatter.format(rs.getDate(3));
                String id = Integer.toString(rs.getInt(4));
                String all = detalle+","+lugar+","+s+","+id;
                events.add(detalle);
                all_info.add(all);
            }
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!events.isEmpty()){
            for(int i = 0; i < events.size(); i++){
                eventsNames.add(events.get(i));
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, eventsNames);
        lsvClientBandGeneralEvents.setAdapter(adapter);
        lsvClientBandGeneralEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClientGeneralBandEventsActivity.this, ClientSingleEventActivity.class);
                intent.putExtra("objectName", all_info.get(position));
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });
    }
}
