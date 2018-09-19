package tec.musicbeansapp.gui.Admin.News;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class CreateNewsActivity extends AppCompatActivity {

    ImageView newsPicture;
    EditText txtNewsDescription;
    ImageButton btnUploadNewsPicture;
    Button btnPostNews;
    EditText txtNewsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_news);

        newsPicture = (ImageView) findViewById(R.id.imvNewsPicture);
        txtNewsDescription = (EditText) findViewById(R.id.txtNewsDescription);
        btnUploadNewsPicture = (ImageButton) findViewById(R.id.btnUploadNewsPicture);
        btnPostNews = (Button) findViewById(R.id.btnPostNews);
        txtNewsTitle = (EditText) findViewById(R.id.tituloNoticia);

        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();

        btnPostNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String title = txtNewsTitle.getText().toString();
                    String description = txtNewsDescription.getText().toString();
                    if(!title.equals("") & !description.equals("")){
                        String query_searchNews = "SELECT [TITULO] FROM [dbo].NOTICIA " +
                                "WHERE TITULO = ?";
                        PreparedStatement ps_searchNews = cn.prepareStatement(query_searchNews);
                        ps_searchNews.setString(1,title);
                        ResultSet rs = ps_searchNews.executeQuery();
                        if(rs.next()){
                            Toast.makeText(CreateNewsActivity.this, "Ya existe una noticia con ese título",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            rs.close();
                            ps_searchNews.close();
                            String query_insert_news = "INSERT INTO [dbo].NOTICIA " +
                                    "VALUES (?,?)";
                            PreparedStatement ps_insert_news = cn.prepareStatement(query_insert_news);
                            ps_insert_news.setString(1,title);
                            ps_insert_news.setString(2,description);
                            ps_insert_news.execute();
                            ps_insert_news.close();

                            String query_searchNewsID = "SELECT [ID_NOTICIA] FROM [dbo].NOTICIA " +
                                    "WHERE TITULO = ?";
                            PreparedStatement ps_searchNewsID = cn.prepareStatement(query_searchNewsID);
                            ps_searchNewsID.setString(1,title);
                            ResultSet infoNews = ps_searchNewsID.executeQuery();
                            infoNews.next();
                            int newsID = infoNews.getInt(1);
                            infoNews.close();
                            ps_searchNewsID.close();

                            String query_insert_N_A = "INSERT INTO [dbo].NOTICIAPORCUENTA " +
                                    "VALUES(?,?)";
                            PreparedStatement ps_insert_N_A = cn.prepareStatement(query_insert_N_A);
                            ps_insert_N_A.setInt(1,newsID);
                            ps_insert_N_A.setString(2,"Ricardo");//esto se debe cambiar, es el
                            //usuario que está activo en el momento
                            ps_insert_N_A.execute();
                            ps_insert_N_A.close();
                            Toast.makeText(CreateNewsActivity.this, "Successfully created a new post",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CreateNewsActivity.this, "Título y descripción de la noticia son necesarios",
                                Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
