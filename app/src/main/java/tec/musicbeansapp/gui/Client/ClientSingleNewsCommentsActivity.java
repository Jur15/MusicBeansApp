package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.Accounts.ClientAccountListActivity;
import tec.musicbeansapp.gui.Admin.Accounts.DeleteClientAccountActivity;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class ClientSingleNewsCommentsActivity extends AppCompatActivity {

    final ArrayList<String> options = new ArrayList<>();

    ListView comments;
    private String username;
    private int idNoticia;
    private String infoNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_single_news_comments);

        comments = (ListView) findViewById(R.id.commentList);
        username = getIntent().getStringExtra("username");
        idNoticia = getIntent().getIntExtra("idNoticia",0);
        infoNoticia = getIntent().getStringExtra("info");
        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText(username);
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientSingleNewsCommentsActivity.this, ClientBandSingleNewsActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("objectName",infoNoticia);
                startActivity(intent);
                finish();
            }
        });
        obtenerComentarios();
    }

    public void obtenerComentarios(){
        ArrayList<String> infoComments = new ArrayList<>();
        try{
            ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
            Connection cn = cs.get_Instance_Connection();

            String query = "SELECT USERNAME, COMENTARIO FROM [dbo].COMENTARIOPORNOTICIA " +
                    "WHERE ID_NOTICIA = ?";
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setInt(1,idNoticia);
            ResultSet rs = ps.executeQuery();
            rs.next();
            do{
                String comment = "Posted by " + rs.getString(1)+"\n"+" "+ rs.getString(2);
                infoComments.add(comment);
            }while(rs.next());
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!infoComments.isEmpty()){
            for(int i = 0; i < infoComments.size(); i++){
                options.add(infoComments.get(i));
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        comments.setAdapter(adapter);
    }
}
