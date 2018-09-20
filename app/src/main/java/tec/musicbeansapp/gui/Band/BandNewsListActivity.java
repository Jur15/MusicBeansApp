package tec.musicbeansapp.gui.Band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Band.News.BandCreateNewsActivity;
import tec.musicbeansapp.gui.Band.News.BandDeleteNewsActivity;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class BandNewsListActivity extends AppCompatActivity {

    // Var
    String usuario;
    ArrayList<String> news = new ArrayList<>();
    ArrayList<ArrayList<String>> newsInfo = new ArrayList<>();
    // UI Views
    ListView newsList;
    Button btnAddNew;
    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_news_list);
        //Obtains the username from the Intent
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("username");
        //Sets Toolbar data
        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Noticias");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Band Home Activity");
                finish();
            }
        });
        newsList = (ListView) findViewById(R.id.list_BandNewsList);
        btnAddNew = (Button) findViewById(R.id.btnAddNew_BandNewsList);
        btnRefresh = (Button) findViewById(R.id.btnRefresh_BandNewsList);

        // Button functionality
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to create a new News post");
                Intent intent = new Intent(BandNewsListActivity.this , BandCreateNewsActivity.class);
                intent.putExtra("username",usuario);
                startActivity(intent);
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateList();
            }
        });

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BandNewsListActivity.this, BandDeleteNewsActivity.class);
                intent.putExtra("username",usuario);
                intent.putExtra("id",newsInfo.get(position).get(0));
                intent.putExtra("title", newsInfo.get(position).get(1));
                intent.putExtra("description",newsInfo.get(position).get(2));
                startActivity(intent);
            }
        });

        updateList();
    }

    private void updateList() {
        news = new ArrayList<>();
        newsInfo = new ArrayList<>();
        ArrayList<String> singleNewsInfo;
        try {
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            String query = "SELECT N.ID_NOTICIA, N.TITULO, N.DESCRIPCION FROM [dbo].NOTICIA as N " +
                    "INNER JOIN [dbo].NOTICIAPORCUENTA as NC ON (N.ID_NOTICIA = NC.ID_NOTICIA) " +
                    "WHERE NC.USERNAME = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                singleNewsInfo = new ArrayList<>();
                String id = Integer.toString(rs.getInt(1));
                String titulo = rs.getString(2);
                String descripcion = rs.getString(3);
                singleNewsInfo.add(id);
                singleNewsInfo.add(titulo);
                singleNewsInfo.add(descripcion);
                news.add(titulo);
                newsInfo.add(singleNewsInfo);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(BandNewsListActivity.this, "Error al obtener las noticias",
                    Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, news);
        newsList.setAdapter(adapter);
    }
}
