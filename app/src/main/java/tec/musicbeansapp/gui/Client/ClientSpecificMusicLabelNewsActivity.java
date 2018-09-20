package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tec.musicbeansapp.R;

public class ClientSpecificMusicLabelNewsActivity extends AppCompatActivity {

    private String newsInfo;
    TextView txvLabelNewsTitle;
    TextView txvLabelNewsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_specific_music_label_news);

        txvLabelNewsTitle = (TextView) findViewById(R.id.txvLabelNewsTitle);
        txvLabelNewsDescription = (TextView) findViewById(R.id.txvLabelNewsDescription);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Dummy text");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Music Label News Activity");
                finish();
            }
        });

        Intent intent = getIntent();
        newsInfo = intent.getExtras().getString("newsInfo");
        System.out.println("LOG: Received " + newsInfo);

        List<String> details = Arrays.asList(newsInfo.split(","));
        System.out.println(details.get(0));
        txvLabelNewsTitle.setText(details.get(1));
        txvLabelNewsDescription.setText(details.get(2));

    }
}
