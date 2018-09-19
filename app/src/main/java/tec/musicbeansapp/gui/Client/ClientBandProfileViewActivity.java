package tec.musicbeansapp.gui.Client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import tec.musicbeansapp.R;

public class ClientBandProfileViewActivity extends AppCompatActivity {

    // Vars
    String bandName = "Dummy band name";
    Boolean isInFavorites = false;
    Boolean isRated = false;
    float bandRating = 0;

    // UI Views
    ImageView imvBandClientProfilePic;
    TextView txvClientBrandDescription;

    RatingBar rbClientBandRatingBar;

    Button btnClientBandEvents;
    Button btnClientBandNews;
    Button btnClientBandStore;
    Button btnClientRateBand;
    Button btnClientChangeRating;
    Button btnClientBandAddToFavorites;
    Button btnClientBandRemoveFromFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_band_profile_view);

        imvBandClientProfilePic = (ImageView) findViewById(R.id.imvClientBandProfilePic);
        txvClientBrandDescription = (TextView) findViewById(R.id.txvClientBrandDescription);
        rbClientBandRatingBar = (RatingBar) findViewById(R.id.rbClientBandRatingBar);

        btnClientBandEvents = (Button) findViewById(R.id.btnClientBandEvents);
        btnClientBandNews = (Button) findViewById(R.id.btnClientBandNews);
        btnClientBandStore = (Button) findViewById(R.id.btnClientBandStore);

        btnClientRateBand = (Button) findViewById(R.id.btnClientRateBand);
        btnClientChangeRating = (Button) findViewById(R.id.btnClientChangeRating);

        btnClientBandAddToFavorites = (Button) findViewById(R.id.btnClientBandAddToFavorites);
        btnClientBandRemoveFromFavorites = (Button) findViewById(R.id.btnClientBandRemoveFromFavorites);

        btnClientBandRemoveFromFavorites.setVisibility(View.GONE);
        btnClientChangeRating.setVisibility(View.GONE);

        if (isInFavorites)
        {
            btnClientBandAddToFavorites.setVisibility(View.GONE);
            btnClientBandRemoveFromFavorites.setVisibility(View.VISIBLE);
        }

        if (isRated)
        {
            rbClientBandRatingBar.setVisibility(View.GONE);
            btnClientRateBand.setVisibility(View.GONE);
        }

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText(bandName);
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Search Band Activity");
                finish();
            }
        });

        btnClientRateBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRated= true; //TODO: Change attribute in DB as well
                btnClientRateBand.setVisibility(View.GONE);
                btnClientChangeRating.setVisibility(View.VISIBLE);
                System.out.println("LOG: Rating " + bandName + " with: " + rateBand());
            }
        });

        btnClientChangeRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Change attribute in DB as well
                System.out.println("LOG: Changing " + bandName + " rating to: " + rateBand());
            }
        });

        btnClientBandAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInFavorites = true; //TODO: Change attribute in DB as well
                btnClientBandAddToFavorites.setVisibility(View.GONE);
                btnClientBandRemoveFromFavorites.setVisibility(View.VISIBLE);
                System.out.println("LOG: Adding " + bandName + " to favorites bands");

            }
        });

        btnClientBandRemoveFromFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Change attribute in DB as well
                isInFavorites = false;
                btnClientBandRemoveFromFavorites.setVisibility(View.GONE);
                btnClientBandAddToFavorites.setVisibility(View.VISIBLE);
            }
        });

    }

    public float rateBand()
    {
        float bandRating = 0;
        bandRating = rbClientBandRatingBar.getRating();
        return  bandRating;
    }


}
