package tec.musicbeansapp.gui.Band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Band.Events.BandCreateEventsActivity;
import tec.musicbeansapp.gui.Band.Events.BandDeleteEventsActivity;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class BandEventsListActivity extends AppCompatActivity {

    // Var
    String usuario;
    ArrayList<String> events = new ArrayList<>();
    ArrayList<ArrayList<String>> eventsInfo = new ArrayList<>();
    // UI Views
    ListView eventsList;
    Button btnAddNew;
    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_events_list);
        //Sets Toolbar data
        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Eventos");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Band Home Activity");
                finish();
            }
        });
        //Obtains the username from the Intent
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("username");

        eventsList = (ListView) findViewById(R.id.list_BandEventsList);
        btnAddNew = (Button) findViewById(R.id.btnAddNew_BandEventsList);
        btnRefresh = (Button) findViewById(R.id.btnRefresh_BandEventsList);

        // Button functionality
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to create a new Event post");
                Intent intent = new Intent(BandEventsListActivity.this , BandCreateEventsActivity.class);
                intent.putExtra("username",usuario);
                startActivity(intent);
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateList();
            }
        });

        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BandEventsListActivity.this, BandDeleteEventsActivity.class);
                intent.putExtra("username",usuario);
                intent.putExtra("date",Date.valueOf(eventsInfo.get(position).get(0)));
                intent.putExtra("place",eventsInfo.get(position).get(1));
                intent.putExtra("description",eventsInfo.get(position).get(2));
                intent.putExtra("id",eventsInfo.get(position).get(3));
                startActivity(intent);
            }
        });

        updateList();
    }

    private void updateList() {
        events = new ArrayList<>();
        eventsInfo = new ArrayList<>();
        ArrayList<String> singleEventInfo;
        try {
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            String query = "SELECT E.DETALLE, E.LUGAR, E.FECHA, E.ID_EVENTO FROM [dbo].EVENTO as E " +
                    "INNER JOIN [dbo].EVENTOPORBANDA as EB ON (E.ID_EVENTO = EB.ID_EVENTO) " +
                    "WHERE EB.USERNAME = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                singleEventInfo = new ArrayList<>();
                String detalle = rs.getString(1);
                String lugar = rs.getString(2);
                String date = rs.getDate(3).toString();
                String id = Integer.toString(rs.getInt(4));
                singleEventInfo.add(date);
                singleEventInfo.add(lugar);
                singleEventInfo.add(detalle);
                singleEventInfo.add(id);
                events.add(detalle);
                eventsInfo.add(singleEventInfo);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(BandEventsListActivity.this, "Error al obtener los eventos",
                    Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, events);
        eventsList.setAdapter(adapter);
    }
}
