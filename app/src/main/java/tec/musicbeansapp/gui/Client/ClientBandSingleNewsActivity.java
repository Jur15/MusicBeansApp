package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import tec.musicbeansapp.R;

public class ClientBandSingleNewsActivity extends AppCompatActivity {

    TextView title;
    TextView description;

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
        String all_info = getIntent().getStringExtra("objectName");
        String[] parts = all_info.split(",");
        idNoticia = Integer.parseInt(parts[0]);
        titulo = parts[1];
        descripcion = parts[2];

        title.setText(titulo);
        description.setText(descripcion);

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
    }
}
