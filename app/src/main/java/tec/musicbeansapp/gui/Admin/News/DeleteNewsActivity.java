package tec.musicbeansapp.gui.Admin.News;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.Accounts.BandAccountListActivity;
import tec.musicbeansapp.gui.Admin.Accounts.DeleteBandActivity;
import tec.musicbeansapp.gui.Admin.NewsListActivity;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class DeleteNewsActivity extends AppCompatActivity {

    TextView titulo;
    TextView descripcion;
    Button btnDelete;
    private int idNoticia;
    private String title;
    private String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_news);

        titulo = (TextView) findViewById(R.id.txvNewsTitleFromAdminDelete);
        descripcion = (TextView) findViewById(R.id.txvNewsDescriptionFromAdminDelete);
        btnDelete = (Button) findViewById(R.id.btnDeleteNewsFromAdmin);

        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();

        String info = getIntent().getStringExtra("objectName");
        String[] parts = info.split(",");
        title = parts[0];
        description = parts[2];
        idNoticia = Integer.parseInt(parts[1]);

        titulo.setText(title);
        descripcion.setText(description);

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Admin");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteNewsActivity.this, NewsListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String query_delete = "DELETE FROM [dbo].NOTICIAPORCUENTA WHERE ID_NOTICIA = ?";
                    PreparedStatement ps_delete = cn.prepareStatement(query_delete);
                    ps_delete.setInt(1, idNoticia);
                    ps_delete.execute();
                    ps_delete.close();

                    query_delete = "DELETE FROM [dbo].COMENTARIOPORNOTICIA WHERE ID_NOTICIA = ?";
                    ps_delete = cn.prepareStatement(query_delete);
                    ps_delete.setInt(1, idNoticia);
                    ps_delete.execute();
                    ps_delete.close();

                    query_delete = "DELETE FROM [dbo].NOTICIA WHERE ID_NOTICIA = ?";
                    ps_delete = cn.prepareStatement(query_delete);
                    ps_delete.setInt(1, idNoticia);
                    ps_delete.execute();
                    ps_delete.close();

                    Intent intent = new Intent(DeleteNewsActivity.this, NewsListActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
