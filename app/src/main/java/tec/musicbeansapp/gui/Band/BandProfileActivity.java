package tec.musicbeansapp.gui.Band;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class BandProfileActivity extends AppCompatActivity {

    private TextView bandName;
    private TextView bandDescription;
    private RatingBar bandRating;
    private String usuario, nombre, descripcion;
    private float promedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_profile);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Perfil de la banda");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bandName = (TextView) findViewById(R.id.txvBandName_BandProfile);
        bandDescription = (TextView) findViewById(R.id.txvBandDescription_BandProfile);
        bandRating = (RatingBar) findViewById(R.id.rbRating_BandProfile);
        usuario = getIntent().getStringExtra("username");

        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();
        try {
            String query = "SELECT B.NOMBRE, B.DESCRIPCION, B.PROMEDIO_CALIFICACION " +
                    "FROM BANDA as B INNER JOIN CUENTAPORBANDA as CB ON (B.ID_BANDA = CB.ID_BANDA) " +
                    "WHERE CB.USERNAME = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            rs.next();
            nombre = rs.getString(1);
            descripcion = rs.getString(2);
            promedio = rs.getFloat(3);
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        bandName.setText(nombre);
        bandDescription.setText(descripcion);
        bandRating.setRating(promedio);
    }
}
