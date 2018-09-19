package tec.musicbeansapp.gui.Band.Events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class BandDeleteEventsActivity extends AppCompatActivity {

    //Var
    private Date fecha;
    private String lugar;
    private String descripcion;
    //UI Views
    TextView eventDate;
    TextView eventPlace;
    TextView eventDescription;
    Button deleteEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_delete_events);
        //Obtains data from the Intent
        Bundle bundle = getIntent().getExtras();
        fecha = (Date) bundle.get("date");
        lugar = bundle.getString("place");
        descripcion = bundle.getString("description");

        eventDate = (TextView) findViewById(R.id.txvEventDate_BandDeleteEvents);
        eventPlace = (TextView) findViewById(R.id.txvEventPlace_BandDeleteEvents);
        eventDescription = (TextView) findViewById(R.id.txvEventDescription_BandDeleteEvents);
        deleteEvent = (Button) findViewById(R.id.btnDeleteEvent_BandDeleteEvents);

        eventDate.setText(fecha.toString());
        eventPlace.setText(lugar);
        eventDescription.setText(descripcion);
        //Toolbar
        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Deleting news post");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        // Button functionality
        deleteEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deleteEvent();
                finish();
            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Band Event List Activity");
                finish();
            }
        });
    }

    private void deleteEvent() {
        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();
        try {
            String query_searchEventID = "SELECT [ID_EVENTO] FROM [dbo].EVENTO " +
                    "WHERE DETALLE = ?";
            PreparedStatement ps_searchEventID = cn.prepareStatement(query_searchEventID);
            ps_searchEventID.setString(1, descripcion);
            ResultSet infoEvent = ps_searchEventID.executeQuery();
            infoEvent.next();
            int eventID = infoEvent.getInt(1);
            infoEvent.close();
            ps_searchEventID.close();

            String query_delete_E_B = "DELETE FROM [dbo].EVENTOPORBANDA " +
                    "WHERE ID_EVENTO = ?";
            PreparedStatement ps_delete_event_band = cn.prepareStatement(query_delete_E_B);
            ps_delete_event_band.setInt(1, eventID);
            ps_delete_event_band.execute();
            ps_delete_event_band.close();

            String query_delete_event = "DELETE FROM [dbo].EVENTO " +
                    "WHERE ID_EVENTO = ?";
            PreparedStatement ps_delete_event = cn.prepareStatement(query_delete_event);
            ps_delete_event.setInt(1, eventID);
            ps_delete_event.execute();
            ps_delete_event.close();

            Toast.makeText(BandDeleteEventsActivity.this, "Evento eliminado con éxito",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
