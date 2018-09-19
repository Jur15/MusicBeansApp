package tec.musicbeansapp.gui.Client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import tec.musicbeansapp.R;

public class ClientBandEventsActivity extends AppCompatActivity {

    //Vars
    String bandName = "Dummy band name"; //FIXME

    // UI Views
    ListView lsvClientBandNews;
    TextView txvClientBandNameNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_band_events);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Admin");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Client Band Profile Activity");
                finish();
            }
        });

        lsvClientBandNews = (ListView) findViewById(R.id.lsvClientBandNews);
        txvClientBandNameNews = (TextView) findViewById(R.id.txvClientBandNameNews);

        txvClientBandNameNews.setText(bandName);
    }
}
