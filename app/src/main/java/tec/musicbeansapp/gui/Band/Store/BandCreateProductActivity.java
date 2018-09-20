package tec.musicbeansapp.gui.Band.Store;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class BandCreateProductActivity extends AppCompatActivity {

    EditText txtProductName;
    EditText txtProductDescription;
    Button btnAddProduct;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_create_product);

        //Obtains the username from the Intent
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("username");

        txtProductName = (EditText) findViewById(R.id.txtProductName_BandCreateProduct);
        txtProductDescription = (EditText) findViewById(R.id.txtProductDescription_BandCreateProduct);
        btnAddProduct = (Button) findViewById(R.id.btnAddNew_BandCreateProduct);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Agregar producto");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Band Store Activity");
                finish();
            }
        });

        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int idBanda;
                    String name = txtProductName.getText().toString();
                    String description = txtProductDescription.getText().toString();
                    if (!name.equals("") & !description.equals("")) {
                        String query_getBandID = "SELECT ID_BANDA FROM [dbo].CUENTAPORBANDA " +
                                "WHERE USERNAME = ?";
                        PreparedStatement ps_getBandID = cn.prepareStatement(query_getBandID);
                        ps_getBandID.setString(1, usuario);
                        ResultSet rsBandID = ps_getBandID.executeQuery();
                        rsBandID.next();
                        idBanda = rsBandID.getInt(1);
                        rsBandID.close();
                        ps_getBandID.close();

                        String query_searchItem = "SELECT ID_PRODUCTO FROM [dbo].PRODUCTO " +
                                "WHERE ID_BANDA = ? AND NOMBRE = ?";
                        PreparedStatement ps_searchItem = cn.prepareStatement(query_searchItem);
                        ps_searchItem.setInt(1, idBanda);
                        ps_searchItem.setString(2, name);
                        ResultSet rs = ps_searchItem.executeQuery();
                        if (rs.next()) {
                            Toast.makeText(BandCreateProductActivity.this, "Ya existe una producto con ese nombre",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            rs.close();
                            ps_searchItem.close();
                            String query_insert_item = "INSERT INTO [dbo].PRODUCTO " +
                                    "VALUES (?,?,?)";
                            PreparedStatement ps_insert_item = cn.prepareStatement(query_insert_item);
                            ps_insert_item.setString(1, name);
                            ps_insert_item.setString(2, description);
                            ps_insert_item.setInt(3, idBanda);
                            ps_insert_item.execute();
                            ps_insert_item.close();

                            Toast.makeText(BandCreateProductActivity.this, "Producto agregado con éxito",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(BandCreateProductActivity.this, "Nombre y descripción del producto son necesarios",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BandCreateProductActivity.this, "Error al agregar producto",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
