package tec.musicbeansapp.gui.Band.Events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class BandCreateEventsActivity extends AppCompatActivity {

    EditText txtEventDate;
    EditText txtEventPlace;
    EditText txtEventDescripction;
    Button btnPostEvent;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_create_events);

        //Obtains the username from the Intent
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("username");

        txtEventDate = (EditText) findViewById(R.id.txtEventDate_BandCreateEvents);
        txtEventPlace = (EditText) findViewById(R.id.txtEventPlace_BandCreateEvents);
        txtEventDescripction = (EditText) findViewById(R.id.txtEventDescription_BandCreateEvents);
        btnPostEvent = (Button) findViewById(R.id.btnPostEvent_BandCreateEvents);

        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();

        btnPostEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Date date = Date.valueOf(txtEventDate.getText().toString());
                    String place = txtEventPlace.getText().toString();
                    String description = txtEventDescripction.getText().toString();
                    if(!date.equals(null) & !place.equals("") & !description.equals("")) {
                        String query_insert_event = "INSERT INTO [dbo].EVENTO " +
                                "VALUES (?,?,?)";
                        PreparedStatement ps_insert_event = cn.prepareStatement(query_insert_event);
                        ps_insert_event.setDate(1, date);
                        ps_insert_event.setString(2, place);
                        ps_insert_event.setString(3, description);
                        ps_insert_event.execute();
                        ps_insert_event.close();

                        String query_searchEventID = "SELECT [ID_EVENTO] FROM [dbo].EVENTO " +
                                "WHERE DETALLE = ?";
                        PreparedStatement ps_searchEventID = cn.prepareStatement(query_searchEventID);
                        ps_searchEventID.setString(1, description);
                        ResultSet infoEvent = ps_searchEventID.executeQuery();
                        infoEvent.next();
                        int eventID = infoEvent.getInt(1);
                        infoEvent.close();
                        ps_searchEventID.close();

                        String query_insert_E_B = "INSERT INTO [dbo].EVENTOPORBANDA " +
                                "VALUES(?,?)";
                        PreparedStatement ps_insert_E_B = cn.prepareStatement(query_insert_E_B);
                        ps_insert_E_B.setInt(1, eventID);
                        ps_insert_E_B.setString(2, usuario);
                        ps_insert_E_B.execute();
                        ps_insert_E_B.close();
                        Toast.makeText(BandCreateEventsActivity.this, "Evento creado con éxito",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BandCreateEventsActivity.this, "Fecha, lugar y descripción del evento son necesarios",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
