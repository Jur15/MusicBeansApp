package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tec.musicbeansapp.R;

public class ClientSingleEventActivity extends AppCompatActivity {

    // Vars
    private String name;

    // UI Views
    TextView txvClientSingleEventDescription;
    TextView txvClientSingleEventTitle;
    TextView txvClientSingleEventDate;

    private String lugar;
    private String fecha;
    private String descripcion;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_single_event);

        name = getIntent().getStringExtra("objectName").toString();

        txvClientSingleEventDescription = (TextView) findViewById(R.id.txvClientSingleEventDescription);
        txvClientSingleEventTitle = (TextView) findViewById(R.id.txvClientSingleEventTitle);
        txvClientSingleEventDate = (TextView) findViewById(R.id.txvClientSingleEventDate);

        String info = getIntent().getStringExtra("objectName");
        String[] parts = info.split(",");
        descripcion = parts[0];
        lugar = parts[1];
        fecha = parts[2];

        txvClientSingleEventTitle.setText(lugar);
        txvClientSingleEventDate.setText(fecha);
        txvClientSingleEventDescription.setText(descripcion);

        username = getIntent().getStringExtra("username");


        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Single event");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientSingleEventActivity.this, ClientGeneralBandEventsActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });
    }
}
