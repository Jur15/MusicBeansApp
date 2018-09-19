package tec.musicbeansapp.gui.Band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Band.News.BandCreateNewsActivity;

public class BandNewsListActivity extends AppCompatActivity {

    // Var
    String usuario;
    // UI Views
    ListView newsList;
    Button btnAddNew;
    private ListView list;
    final ArrayList<String> options = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_news_list);
        //Obtains the username from the Intent
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("username");

        newsList = (ListView) findViewById(R.id.list_BandNewsList);
        btnAddNew = (Button) findViewById(R.id.btnAddNew_BandNewsList);
        list = (ListView) findViewById(R.id.list_BandNewsList);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Clicked item number: " + i);
                /*
                Intent intent = new Intent(BandNewsListActivity.this, BandDeleteNewsActivity.class);
                Intent.putExtra("title", TODO: Get title from selected post );
                Intent.putExtra("description", TODO: Get description from selected post );
                Intent.putExtra("image", TODO: Get image from selected post );
                startActivity(intent);
                 */
                updateList();
            }
        });

        // Button functionality
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to create a new News post");
                Intent intent = new Intent(BandNewsListActivity.this , BandCreateNewsActivity.class);
                intent.putExtra("username",usuario);
                startActivity(intent);
                updateList();
            }
        });
        updateList();
    }

    //TODO: Add method to update list's content
    private void updateList() {

    }
}
