package tec.musicbeansapp.gui.Band.Store;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class BandDeleteProductActivity extends AppCompatActivity {

    //Var
    private String nombre;
    private String descripcion;
    private String usuario;
    //UI Views
    TextView itemName;
    TextView itemDescription;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_delete_product);
        //Obtains data from the Intent
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("username");
        nombre = bundle.getString("name");
        descripcion = bundle.getString("description");

        itemName = (TextView) findViewById(R.id.txtProductName_BandDeleteProduct);
        itemDescription = (TextView) findViewById(R.id.txtProductDescription_BandDeleteProduct);
        delete = (Button) findViewById(R.id.btnDelete_BandDeleteProduct);

        itemName.setText(nombre);
        itemDescription.setText(descripcion);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Remove a product");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);

        // Button functionality
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deleteProduct();
                finish();
            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Navigating back to Band Store Activity");
                finish();
            }
        });
    }

    private void deleteProduct() {
        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();
        try {
            String query_searchProductID = "SELECT [ID_PRODUCTO] FROM [dbo].PRODUCTO AS P " +
                    "INNER JOIN [dbo].CUENTAPORBANDA AS CB ON P.ID_BANDA = CB.ID_BANDA " +
                    "WHERE CB.USERNAME = ? AND P.NOMBRE = ?";
            PreparedStatement ps_searchProductID = cn.prepareStatement(query_searchProductID);
            ps_searchProductID.setString(1, usuario);
            ps_searchProductID.setString(2, nombre);
            ResultSet productInfo = ps_searchProductID.executeQuery();
            productInfo.next();
            int productID = productInfo.getInt(1);
            productInfo.close();
            ps_searchProductID.close();

            String query_delete_product = "DELETE FROM [dbo].PRODUCTO " +
                    "WHERE ID_PRODUCTO = ?";
            PreparedStatement ps_delete_product = cn.prepareStatement(query_delete_product);
            ps_delete_product.setInt(1, productID);
            ps_delete_product.execute();
            ps_delete_product.close();
            Toast.makeText(BandDeleteProductActivity.this, "Producto eliminado con éxito",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(BandDeleteProductActivity.this, "Error al eliminar el producto",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
