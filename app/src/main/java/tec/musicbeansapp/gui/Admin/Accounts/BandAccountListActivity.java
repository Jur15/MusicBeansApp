package tec.musicbeansapp.gui.Admin.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import tec.musicbeansapp.R;

public class BandAccountListActivity extends AppCompatActivity {


    // UI Views
    ListView bandList;
    Button btnAddNewBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_account_list);

        bandList = (ListView) findViewById(R.id.bandList);
        btnAddNewBand = (Button) findViewById(R.id.btnAddNewBand);

        bandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Clicked item number: " + i);
                //TODO: Add Intent to navigate to an Activity to view Band details and enable option to eliminate it.
            }
        });

        btnAddNewBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Tying to create a new Band Account");
            }
        });
    }
}
