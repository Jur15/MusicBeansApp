package tec.musicbeansapp.gui.Admin.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import tec.musicbeansapp.R;

public class ClientAccountListActivity extends AppCompatActivity {

    ListView clientAccountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_client_account_list);

        clientAccountList = (ListView) findViewById(R.id.clientAccountList);

        clientAccountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("LOG: Clicked item: " + i);
                //TODO: Add intent to navigate to view where the admin can delete Client Accounts
            }
        });
    }
}
