package tec.musicbeansapp.gui.Admin.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tec.musicbeansapp.R;

public class DeleteClientAccountActivity extends AppCompatActivity {

    TextView txvClientName;
    TextView txvClientAge;
    TextView txvClientEmail;
    ImageView imvClientPicture;
    Button btnDeleleteClienteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_client_account);

        txvClientName = (TextView) findViewById(R.id.txvClientName);
        txvClientAge = (TextView) findViewById(R.id.txvClientAge);
        txvClientEmail = (TextView) findViewById(R.id.txvClientEmail);
        imvClientPicture = (ImageView) findViewById(R.id.imvClientPicture);
        btnDeleleteClienteAccount = (Button) findViewById(R.id.btnDeleteClientAccount);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Deleting Client account");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Client Account List Activity");
                finish();
            }
        });



        btnDeleleteClienteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Trying to delete a client account");
                //TODO: Eliminate account with confirmation dialog or something like that.
            }
        });
    }
}
