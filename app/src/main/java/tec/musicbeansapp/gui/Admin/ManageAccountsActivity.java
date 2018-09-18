package tec.musicbeansapp.gui.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.Accounts.BandAccountListActivity;

public class ManageAccountsActivity extends AppCompatActivity {

    Button btnBandAccounts;
    Button btnClienteAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_accounts);
        System.out.println("LOG: Created ManageAccountActivity ");

        btnBandAccounts = (Button) findViewById(R.id.btnBandAccounts);
        btnClienteAccounts = (Button) findViewById(R.id.btnClientAccounts);

        btnBandAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating to the bands accounts");
                Intent intent = new Intent(ManageAccountsActivity.this , BandAccountListActivity.class);
                startActivity(intent);
            }
        });

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Admin");
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
