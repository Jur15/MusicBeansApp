package tec.musicbeansapp.gui.Band;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import tec.musicbeansapp.R;

public class BandHomeActivity extends AppCompatActivity {

    // Var

    // UI Views
    Button btnProfile;
    Button btnManageNews;
    Button btnManageEvents;
    Button btnManageStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_home);
        System.out.println("LOG: Created BandHomeActivity.");

        btnProfile = findViewById(R.id.btnMyProfile);
        btnManageNews = findViewById(R.id.btnManageNews);
        btnManageEvents = findViewById(R.id.btnManageEvents);
        btnManageStore = findViewById(R.id.btnManageStore);
    }
}
