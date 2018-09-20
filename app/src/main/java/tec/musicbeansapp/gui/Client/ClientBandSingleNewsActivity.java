package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import tec.musicbeansapp.R;

public class ClientBandSingleNewsActivity extends AppCompatActivity {

    TextView title;
    TextView description;
    Button btnSeeComments;
    Button btnCommentNews;

    private String titulo;
    private String descripcion;
    private int idNoticia;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_band_single_news);

        title = (TextView) findViewById(R.id.txvClientNewsTitle);
        description = (TextView) findViewById(R.id.txvClientNewsDescription);
        username = getIntent().getStringExtra("username");
        final String all_info = getIntent().getStringExtra("objectName");
        String[] parts = all_info.split(",");
        idNoticia = Integer.parseInt(parts[0]);
        titulo = parts[1];
        descripcion = parts[2];

        btnSeeComments = (Button) findViewById(R.id.btnClientSeeNewsComments);
        btnCommentNews = (Button) findViewById(R.id.btnClientCommentSingleNews);

        title.setText(titulo);
        description.setText(descripcion);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("News");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientBandSingleNewsActivity.this, ClientGeneralBandNewsActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });

        btnSeeComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientBandSingleNewsActivity.this, ClientSingleNewsCommentsActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("idNoticia",idNoticia);
                intent.putExtra("info",all_info);
                startActivity(intent);
                finish();
            }
        });

        btnCommentNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientBandSingleNewsActivity.this, ClientCommentNewsActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("idNoticia",idNoticia);
                intent.putExtra("objectName",all_info);
                startActivity(intent);
                finish();
            }
        });


    }
}
