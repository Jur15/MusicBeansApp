package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tec.musicbeansapp.R;

public class ClientHomeActivity extends AppCompatActivity {

    Button btnSearchBandFromClientHome;
    Button btnNews;
    Button btnBandEvents;
    Button btnMusicLabels; //Do not know what this is used for

    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        btnSearchBandFromClientHome = (Button) findViewById(R.id.btnSearchBandFromClientHome);
        btnNews = (Button) findViewById(R.id.btnNews);
        btnBandEvents = (Button) findViewById(R.id.btnBandEvents);
        btnMusicLabels = (Button) findViewById(R.id.btnMusicLabels);

        username = getIntent().getStringExtra("username");

        btnSearchBandFromClientHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to navigate to Search Band Activity");
                Intent intent = new Intent(ClientHomeActivity.this , SearchBandActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });


        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to navigate to Client General Band News Activity");
                Intent intent = new Intent(ClientHomeActivity.this , ClientGeneralBandNewsActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        btnBandEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to navigate to Client General Band Events Activity");
                Intent intent = new Intent(ClientHomeActivity.this , ClientGeneralBandEventsActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        btnMusicLabels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to navigate to Client Label News Activity");
                Intent intent = new Intent(ClientHomeActivity.this , ClientLabelNewsActivity.class);
                startActivity(intent);
            }
        });
    }
}
