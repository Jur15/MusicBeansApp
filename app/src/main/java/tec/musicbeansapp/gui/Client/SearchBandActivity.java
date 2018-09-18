package tec.musicbeansapp.gui.Client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import tec.musicbeansapp.R;

public class SearchBandActivity extends AppCompatActivity {

    EditText txtBandName;
    ImageButton btnSearchBand;
    ListView lsvBandSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_search_band);

        txtBandName = (EditText) findViewById(R.id.txtBandName);
        btnSearchBand = (ImageButton) findViewById(R.id.btnSearchBand);
        lsvBandSearchResults = (ListView) findViewById(R.id.lsvBandSearchResults);



        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Search band");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Log in Activity");
                finish();
            }
        });

    }
}
