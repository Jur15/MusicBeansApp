package tec.musicbeansapp.gui.Admin;

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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.Accounts.BandAccountListActivity;
import tec.musicbeansapp.gui.Admin.Accounts.CreateBandActivity;
import tec.musicbeansapp.gui.Admin.Accounts.DeleteBandActivity;
import tec.musicbeansapp.gui.Admin.News.CreateNewsActivity;
import tec.musicbeansapp.gui.Admin.News.DeleteNewsActivity;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class NewsListActivity extends AppCompatActivity {

    Button btnAddNew;
    ListView newsList;

    final ArrayList<String> options = new ArrayList<>();
    final ArrayList<String> all_info = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_news_list);

        btnAddNew = (Button) findViewById(R.id.btnAddNew);
        newsList = (ListView) findViewById(R.id.lstNewsList);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("News");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Log in Activity");
                finish();
            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsListActivity.this , CreateNewsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewsListActivity.this, DeleteNewsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        obtenerNoticiasDisquera();
    }

    public void obtenerNoticiasDisquera (){
        ArrayList<ArrayList<String>> news = new ArrayList<>();
        try{
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            String query = "SELECT N.TITULO, N.ID_NOTICIA, N.DESCRIPCION FROM [dbo].NOTICIA as N " +
                    "INNER JOIN [dbo].NOTICIAPORCUENTA as NC ON (N.ID_NOTICIA = NC.ID_NOTICIA) " +
                    "INNER JOIN [dbo].CUENTA as C ON (NC.USERNAME = C.USERNAME) " +
                    "WHERE C.ACCOUNT_TYPE = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1,"A");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ArrayList<String> info = new ArrayList<>();
                info.add(rs.getString(1));
                int idNoticia = rs.getInt(2);
                info.add(Integer.toString(idNoticia));
                info.add(rs.getString(3));
                news.add(info);
            }
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!news.isEmpty()){
            for(int i = 0; i < news.size(); i++){
                options.add(news.get(i).get(0));
                String info = news.get(i).get(0) +","+ news.get(i).get(1) + "," + news.get(i).get(2);
                all_info.add(info);
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        newsList.setAdapter(adapter);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(NewsListActivity.this, DeleteNewsActivity.class);
                intent.putExtra("objectName", all_info.get(position));
                startActivity(intent);
                finish();
            }
        });
    }
}
