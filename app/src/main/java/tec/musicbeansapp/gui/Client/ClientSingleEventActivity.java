package tec.musicbeansapp.gui.Client;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_single_event);

        name = getIntent().getStringExtra("objectName").toString();

        txvClientSingleEventDescription = (TextView) findViewById(R.id.txvClientSingleEventDescription);
        txvClientSingleEventTitle = (TextView) findViewById(R.id.txvClientSingleEventTitle);
        txvClientSingleEventDate = (TextView) findViewById(R.id.txvClientSingleEventDate);


        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Single event");
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
