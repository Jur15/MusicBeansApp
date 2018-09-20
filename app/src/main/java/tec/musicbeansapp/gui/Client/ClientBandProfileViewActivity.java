package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class ClientBandProfileViewActivity extends AppCompatActivity {

    // Vars
    private String bandName;
    private String description;
    private String username;
    private String band_username;
    private int idCalificacion;
    private int idBanda;
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

        username = getIntent().getStringExtra("username");

        String all = getIntent().getStringExtra("objectName");

        String[] parts = all.split(",");
        idBanda = Integer.parseInt(parts[0]);
        bandName = parts[1];
        description = parts[2];
        bandRating = Float.parseFloat(parts[3]);
        band_username = parts[4];

        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();

        try{
            String query = "SELECT [BANDA] FROM [dbo].BANDAPORCLIENTE " +
                    "WHERE CLIENTE = ? AND BANDA = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1,username);
            ps.setString(2,band_username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                isInFavorites = true;
            }
            rs.close();
            ps.close();

            String query_search_calification = "SELECT CA.ID_CALIFICACION, CA.CALIFICACION FROM [dbo].CALIFICACION " +
                    "as CA WHERE CLIENTE = ? AND BANDA = ?";
            PreparedStatement ps_s_c = cn.prepareStatement(query_search_calification);
            ps_s_c.setString(1,username);
            ps_s_c.setString(2,band_username);
            ResultSet rs_s_c = ps_s_c.executeQuery();
            if(rs_s_c.next()){
                idCalificacion = rs.getInt(1);
                float calificacion = rs.getFloat(2);
                isRated = true;
                bandRating = calificacion;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

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
        txvClientBrandDescription.setText(description);
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientBandProfileViewActivity.this, SearchBandActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
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
                try{
                    String query_add_favorites = "INSERT INTO [dbo].BANDAPORCLIENTE values(?,?)";
                    PreparedStatement ps_add_favorites = cn.prepareStatement(query_add_favorites);
                    ps_add_favorites.setString(1,username);
                    ps_add_favorites.setString(2,band_username);
                    ps_add_favorites.execute();
                    isInFavorites = true;
                    btnClientBandAddToFavorites.setVisibility(View.GONE);
                    btnClientBandRemoveFromFavorites.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnClientBandRemoveFromFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String query_delete_favorites = "DELETE FROM [dbo].BANDAPORCLIENTE " +
                            "WHERE CLIENTE = ? AND BANDA = ?";
                    PreparedStatement ps_delete_favorites = cn.prepareStatement(query_delete_favorites);
                    ps_delete_favorites.setString(1,username);
                    ps_delete_favorites.setString(2,band_username);
                    ps_delete_favorites.execute();
                    isInFavorites = false;
                    btnClientBandRemoveFromFavorites.setVisibility(View.GONE);
                    btnClientBandAddToFavorites.setVisibility(View.VISIBLE);
                }catch (Exception e) {
                    e.printStackTrace();
                }
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
