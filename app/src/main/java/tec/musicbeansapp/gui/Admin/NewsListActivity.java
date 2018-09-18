package tec.musicbeansapp.gui.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.News.CreateNewsActivity;

public class NewsListActivity extends AppCompatActivity {

    Button btnAddNew;
    /*TODO:
    * CreateNewsActivity
    * DeleteNewsActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_news_list);

        btnAddNew = (Button) findViewById(R.id.btnAddNew);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsListActivity.this , CreateNewsActivity.class);
                startActivity(intent);
                System.out.println("LOG: Trying to navigate to News List Activity");
            }
        });
    }
}
