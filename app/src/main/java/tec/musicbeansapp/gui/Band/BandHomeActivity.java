package tec.musicbeansapp.gui.Band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tec.musicbeansapp.R;

public class BandHomeActivity extends AppCompatActivity {

    // Var
    String usuario;
    // UI Views
    Button btnProfile;
    Button btnManageNews;
    Button btnManageEvents;
    Button btnManageStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Obtains the username from the Intent
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("username");

        setContentView(R.layout.activity_band_home);
        System.out.println("LOG: Created BandHomeActivity.");

        btnProfile = (Button) findViewById(R.id.btnMyProfile);
        btnManageNews = (Button) findViewById(R.id.btnManageNews);
        btnManageEvents = (Button) findViewById(R.id.btnManageEvents);
        btnManageStore = (Button) findViewById(R.id.btnManageStore);

        // Button functionality
        btnManageNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Band clicked MANAGE NEWS button.");
                System.out.println("LOG: Trying to navigate to Band News List Activity");
                //Go to Band News List Activity
                Intent intent = new Intent(BandHomeActivity.this , BandNewsListActivity.class);
                intent.putExtra("username",usuario);
                startActivity(intent);
            }
        });

        btnManageEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Band clicked MANAGE NEWS button.");
                System.out.println("LOG: Trying to navigate to Band News List Activity");
                //Go to Band News List Activity
                Intent intent = new Intent(BandHomeActivity.this , BandEventsListActivity.class);
                intent.putExtra("username",usuario);
                startActivity(intent);
            }
        });

        btnManageStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Band clicked MANAGE STORE button.");
                System.out.println("LOG: Trying to navigate to Band Store Activity");
                //Go to Band Store Activity
                Intent intent = new Intent(BandHomeActivity.this , BandStoreActivity.class);
                intent.putExtra("username",usuario);
                startActivity(intent);
            }
        });
    }
}
