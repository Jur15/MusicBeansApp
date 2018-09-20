package tec.musicbeansapp.gui.Band;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Band.News.BandCreateNewsActivity;
import tec.musicbeansapp.gui.Band.News.BandDeleteNewsActivity;
import tec.musicbeansapp.gui.Band.Store.BandCreateProductActivity;
import tec.musicbeansapp.gui.Band.Store.BandDeleteProductActivity;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class BandStoreActivity extends AppCompatActivity {

    // Var
    String usuario;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<ArrayList<String>> itemsInfo = new ArrayList<>();
    // UI Views
    ListView itemsList;
    Button btnAddNew;
    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_store);
        //Obtains the username from the Intent
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("username");
        //Sets Toolbar data
        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Tienda");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Band Home Activity");
                finish();
            }
        });
        itemsList = (ListView) findViewById(R.id.list_BandStore);
        btnAddNew = (Button) findViewById(R.id.btnAddNew_BandStore);
        btnRefresh = (Button) findViewById(R.id.btnRefresh_BandStore);

        // Button functionality
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Trying to add a new item to the Store");
                Intent intent = new Intent(BandStoreActivity.this , BandCreateProductActivity.class);
                intent.putExtra("username",usuario);
                startActivity(intent);
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateList();
            }
        });

        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BandStoreActivity.this, BandDeleteProductActivity.class);
                intent.putExtra("username",usuario);
                intent.putExtra("id", itemsInfo.get(position).get(0));
                intent.putExtra("name", itemsInfo.get(position).get(1));
                intent.putExtra("description", itemsInfo.get(position).get(2));
                startActivity(intent);
            }
        });

        updateList();
    }

    private void updateList() {
        items = new ArrayList<>();
        itemsInfo = new ArrayList<>();
        ArrayList<String> singleItemInfo;
        try {
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            String query = "SELECT P.ID_PRODUCTO, P.NOMBRE, P.DESCRIPCION FROM [dbo].PRODUCTO as P " +
                    "INNER JOIN [dbo].CUENTAPORBANDA as CB ON (P.ID_BANDA = CB.ID_BANDA) " +
                    "WHERE CB.USERNAME = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                singleItemInfo = new ArrayList<>();
                String id = Integer.toString(rs.getInt(1));
                String nombre = rs.getString(2);
                String descripcion = rs.getString(3);
                singleItemInfo.add(id);
                singleItemInfo.add(nombre);
                singleItemInfo.add(descripcion);
                items.add(nombre);
                itemsInfo.add(singleItemInfo);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(BandStoreActivity.this, "Error al obtener los productos",
                    Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);
    }
}
