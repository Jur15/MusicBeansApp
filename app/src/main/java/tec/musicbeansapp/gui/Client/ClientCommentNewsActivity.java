package tec.musicbeansapp.gui.Client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.Accounts.CreateBandActivity;

public class ClientCommentNewsActivity extends AppCompatActivity {

    // Vars
    int newID = 0; //FIXME: Dummy ID value

    // UI Views
    EditText textClientComment;
    Button btnClientCommentNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_comment_news);

        textClientComment = (EditText) findViewById(R.id.textClientComment);
        btnClientCommentNews = (Button) findViewById(R.id.btnClientCommentNews);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Dummy text");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Log in Activity");
                finish();
            }
        });

        btnClientCommentNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to post a comment on news: " + newID );
                Toast.makeText(ClientCommentNewsActivity.this, "Comment posted",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
