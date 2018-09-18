package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.Accounts.ClientAccountListActivity;

public class ClientHomeActivity extends AppCompatActivity {

    Button btnMyProfile;
    Button btnSearchBandFromClientHome;
    Button btnNews;
    Button btnBandEvents;
    Button btnMusicLabels; //Do not know what this is used for


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        btnMyProfile = (Button) findViewById(R.id.btnMyProfile);
        btnSearchBandFromClientHome = (Button) findViewById(R.id.btnSearchBandFromClientHome);
        btnNews = (Button) findViewById(R.id.btnNews);
        btnBandEvents = (Button) findViewById(R.id.btnBandEvents);
        btnMusicLabels = (Button) findViewById(R.id.btnMusicLabels);

        btnSearchBandFromClientHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to navigate to Search Band Activity");
                Intent intent = new Intent(ClientHomeActivity.this , SearchBandActivity.class);
                startActivity(intent);
            }
        });


        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to navigate to Client News Activity");
                Intent intent = new Intent(ClientHomeActivity.this , ClientNewsActivity.class);
                startActivity(intent);
            }
        });
    }
}
