package tec.musicbeansapp.gui.Admin.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import tec.musicbeansapp.R;

public class CreateBandActivity extends AppCompatActivity {

    EditText bandName;
    EditText bandDescription;
    ImageView bandPicture;
    ImageButton uploadBandPicture;
    Button btnCreateNewBandAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_band);

        bandName = (EditText) findViewById(R.id.txtBandName);
        bandDescription = (EditText) findViewById(R.id.txtBandDescription);
        bandPicture = (ImageView) findViewById(R.id.imvBandPicture);
        uploadBandPicture = (ImageButton) findViewById(R.id.btnUploadBandPicture);
        btnCreateNewBandAccount = (Button) findViewById(R.id.btnCreateNewBandAccount);

        uploadBandPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to upload band picture");
            }
        });

        btnCreateNewBandAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to create a new band acount");
            }
        });
    }



}