package tec.musicbeansapp.gui.Band.News;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.BitSet;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class BandDeleteNewsActivity extends AppCompatActivity {

    //Var
    private String titulo;
    private String descripcion;
    private Bitmap imagen;
    //UI Views
    TextView postTitle;
    TextView postDescription;
    ImageView postImage;
    Button deleteNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_delete_news);
        //Obtains data from the Intent
        Bundle bundle = getIntent().getExtras();
        titulo = bundle.getString("title");
        descripcion = bundle.getString("description");
        //imagen = bundle.get("image"); TODO: Get image from intent

        postTitle = (TextView) findViewById(R.id.txvNewsTitle_BandDeleteNews);
        postDescription = (TextView) findViewById(R.id.txvNewsDescription_BandDeleteNews);
        postImage = (ImageView) findViewById(R.id.imvNewsPicture_BandDeleteNews);
        deleteNews = (Button) findViewById(R.id.btnDeleteNews_BandDeleteNews);

        postTitle.setText(titulo);
        postDescription.setText(descripcion);
        //postImage.setImageBitmap(imagen); TODO: Set image from variable

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Deleting news post");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);

        // Button functionality
        deleteNews.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deleteNews();
                finish();
            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Band News List Activity");
                finish();
            }
        });
    }

    private void deleteNews() {
        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();
        try {
            String query_searchNewsID = "SELECT [ID_NOTICIA] FROM [dbo].NOTICIA " +
                    "WHERE TITULO = ?";
            PreparedStatement ps_searchNewsID = cn.prepareStatement(query_searchNewsID);
            ps_searchNewsID.setString(1, titulo);
            ResultSet infoNews = ps_searchNewsID.executeQuery();
            infoNews.next();
            int newsID = infoNews.getInt(1);
            infoNews.close();
            ps_searchNewsID.close();

            String query_delete_N_A = "DELETE FROM [dbo].NOTICIAPORCUENTA " +
                    "WHERE ID_NOTICIA = ?";
            PreparedStatement ps_delete_news_acc = cn.prepareStatement(query_delete_N_A);
            ps_delete_news_acc.setInt(1, newsID);
            ps_delete_news_acc.execute();
            ps_delete_news_acc.close();

            String query_delete_news = "DELETE FROM [dbo].NOTICIA " +
                    "WHERE TITULO = ?";
            PreparedStatement ps_delete_news = cn.prepareStatement(query_delete_news);
            ps_delete_news.setString(1, titulo);
            ps_delete_news.execute();
            ps_delete_news.close();

                Toast.makeText(BandDeleteNewsActivity.this, "Noticia eliminada con éxito",
                        Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
