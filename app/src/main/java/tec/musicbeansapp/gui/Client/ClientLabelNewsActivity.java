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
import java.util.ArrayList;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class ClientLabelNewsActivity extends AppCompatActivity {

    // Vars
    final ArrayList<String> newsList = new ArrayList<>();
    final ArrayList<String> newsTitles = new ArrayList<>();

    // UI Views
    ListView lsvMusicLabelNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_label_news);

        lsvMusicLabelNews = (ListView) findViewById(R.id.lsvMusicLabelNews);

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

        getMusicLabelNews();

    }

    public void getMusicLabelNews()
    {
        try
        {
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            System.out.println("LOG: Trying to get Music Label news");

            String query = "SELECT  N.ID_NOTICIA, N.TITULO , N.DESCRIPCION FROM [dbo].NOTICIA as N " +
                    "INNER JOIN  [dbo].NOTICIAPORCUENTA as NPC ON (n.ID_NOTICIA = npc.ID_NOTICIA) " +
                    "INNER JOIN [dbo].CUENTA as C ON (npc.USERNAME = c.USERNAME) " +
                    "WHERE  (C.ACCOUNT_TYPE = 'A')";

            PreparedStatement ps = cn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                String id = rs.getString(1);
                String newsTitle = rs.getString(2);
                System.out.println("LOG: Got " + newsTitle + " news");
                String newsDescription = rs.getString(3);
                String all = id+","+newsTitle+","+newsDescription;
                newsTitles.add(newsTitle);
                newsList.add(all);
            }
            rs.close();
            ps.close();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, newsTitles);
        lsvMusicLabelNews.setAdapter(adapter);
        lsvMusicLabelNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("LOG: Clicked on a music labels newsTitles");
                Intent intent = new Intent(ClientLabelNewsActivity.this, ClientSpecificMusicLabelNewsActivity.class);
                intent.putExtra("newsInfo", newsList.get(position));
                //intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });
    }

}
