package tec.musicbeansapp.gui.Admin.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tec.musicbeansapp.R;

public class DeleteBandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_band);


        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Deleting Band account");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Band Account List Activity");
                finish();
            }
        });


    }
}
