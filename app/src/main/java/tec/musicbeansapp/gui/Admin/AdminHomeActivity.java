package tec.musicbeansapp.gui.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tec.musicbeansapp.R;

public class AdminHomeActivity extends AppCompatActivity {

    // Vars

    // UI Views
    Button btnMangeAccount;
    Button btnManageNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        System.out.println("LOG: Created AdminHomeActivity.");

        btnMangeAccount = (Button) findViewById(R.id.btnManageAccount);
        btnManageNews = (Button) findViewById(R.id.btnManageNews);

        btnManageNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Admin clicked MANAGE NEWS button.");
            }
        });
    }
}
