package tec.musicbeansapp.gui.Admin.News;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.Accounts.CreateBandActivity;

public class CreateNewsActivity extends AppCompatActivity {

    ImageView newsPicture;
    EditText txtNewsDescription;
    ImageButton btnUploadNewsPicture;
    Button btnPostNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_news);

        newsPicture = (ImageView) findViewById(R.id.imvNewsPicture);
        txtNewsDescription = (EditText) findViewById(R.id.txtNewsDescription);
        btnUploadNewsPicture = (ImageButton) findViewById(R.id.btnUploadNewsPicture);
        btnPostNews = (Button) findViewById(R.id.btnPostNews);

        btnPostNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Attempting to post a recently created News");
                //if success
                Toast.makeText(CreateNewsActivity.this, "Successfully created a new post",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
