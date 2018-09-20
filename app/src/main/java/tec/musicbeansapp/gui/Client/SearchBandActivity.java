package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class SearchBandActivity extends AppCompatActivity {

    EditText txtBandName;
    Button btnSearchBand;
    ListView lsvBandSearchResults;
    final ArrayList<String> options = new ArrayList<>();
    final ArrayList<String> info_bands = new ArrayList<>();

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_search_band);

        txtBandName = (EditText) findViewById(R.id.txtBandName);
        btnSearchBand = (Button) findViewById(R.id.btnSearchBand);
        lsvBandSearchResults = (ListView) findViewById(R.id.lsvBandSearchResults);

        username = getIntent().getStringExtra("username");

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Search band");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Log in Activity");
                finish();
            }
        });

        btnSearchBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entryValue = txtBandName.getText().toString();
                if(!entryValue.equals("")){
                    options.clear();
                    info_bands.clear();
                    obtenerInfoBandas(entryValue);
                }
            }
        });

    }

    public void obtenerInfoBandas(String nombreSimilar){
        ArrayList<String> infoBands = new ArrayList<>();
        ArrayList<String> bandNames = new ArrayList<>();
        try{
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            String query = "SELECT B.ID_BANDA, B.NOMBRE, B.DESCRIPCION, B.PROMEDIO_CALIFICACION, C.USERNAME " +
                    "FROM BANDA as B INNER JOIN CUENTAPORBANDA as CB ON (B.ID_BANDA = CB.ID_BANDA) " +
                    "INNER JOIN CUENTA as C ON (CB.USERNAME = C.USERNAME) " +
                    "WHERE B.NOMBRE LIKE ";
            query +="'%"+nombreSimilar+"%'";
            PreparedStatement ps = cn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                String s = rs.getString(3);
                float promedio = rs.getFloat(4);
                String banda = rs.getString(5);
                String all = Integer.toString(id)+","+nombre+","+s+","+Float.toString(promedio)+","+banda;
                infoBands.add(all);
                bandNames.add(nombre);
            }
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!bandNames.isEmpty()){
            for(int i = 0; i < bandNames.size(); i++){
                info_bands.add(infoBands.get(i));
                options.add(bandNames.get(i));
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        lsvBandSearchResults.setAdapter(adapter);
        lsvBandSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchBandActivity.this, ClientBandProfileViewActivity.class);
                intent.putExtra("objectName", info_bands.get(position));
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });
    }
}
