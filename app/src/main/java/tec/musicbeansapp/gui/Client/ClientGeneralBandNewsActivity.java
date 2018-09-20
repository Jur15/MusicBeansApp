package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.Accounts.DeleteBandActivity;
import tec.musicbeansapp.gui.Admin.News.DeleteNewsActivity;
import tec.musicbeansapp.gui.Admin.NewsListActivity;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class ClientGeneralBandNewsActivity extends AppCompatActivity {

    ListView lsvNewsForClient;
    final ArrayList<String> newsList = new ArrayList<>();
    final ArrayList<String> options = new ArrayList<>();
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_general_band_news);

        lsvNewsForClient = (ListView) findViewById(R.id.lsvNewsForClient);
        username = getIntent().getStringExtra("username");

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Band news");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Client Home Activity");
                finish();
            }
        });

        lsvNewsForClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("LOG: Clicked item: " + i);

            }
        });
        obtenerNoticias();
    }

    public void obtenerNoticias (){
        ArrayList<String> news = new ArrayList<>();
        try{
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            String query = "SELECT N.ID_NOTICIA, N.TITULO, N.DESCRIPCION FROM [dbo].NOTICIA as N " +
                    "INNER JOIN [dbo].NOTICIAPORCUENTA as NC ON (N.ID_NOTICIA = NC.ID_NOTICIA) " +
                    "INNER JOIN [dbo].CUENTA as C ON (NC.USERNAME = C.USERNAME) " +
                    "INNER JOIN [dbo].BANDAPORCLIENTE as BC ON (C.USERNAME = BC.BANDA) " +
                    "WHERE BC.CLIENTE = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String descripcion = rs.getString(3);
                String titulo = rs.getString(2);
                String id = Integer.toString(rs.getInt(1));
                String all = id+","+titulo+","+descripcion;
                news.add(titulo);
                newsList.add(all);
            }
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!news.isEmpty()){
            for(int i = 0; i < news.size(); i++){
                options.add(news.get(i));
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        lsvNewsForClient.setAdapter(adapter);
        lsvNewsForClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClientGeneralBandNewsActivity.this, ClientBandSingleNewsActivity.class);
                intent.putExtra("objectName", newsList.get(position));
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });
    }
}
