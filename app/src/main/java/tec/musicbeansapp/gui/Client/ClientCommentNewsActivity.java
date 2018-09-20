package tec.musicbeansapp.gui.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tec.musicbeansapp.R;
import tec.musicbeansapp.gui.Admin.Accounts.CreateBandActivity;
import tec.musicbeansapp.gui.utils.ConnectToSQLServer;

public class ClientCommentNewsActivity extends AppCompatActivity {

    // Vars

    // UI Views
    EditText textClientComment;
    Button btnClientCommentNews;

    private String username;
    private int idNoticia;
    private String infoNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_comment_news);

        textClientComment = (EditText) findViewById(R.id.textClientComment);
        btnClientCommentNews = (Button) findViewById(R.id.btnClientCommentNews);

        ConnectToSQLServer cs = ConnectToSQLServer.get_CTSQL_instance();
        final Connection cn = cs.get_Instance_Connection();

        username = getIntent().getStringExtra("username");
        idNoticia = getIntent().getIntExtra("idNoticia",0);
        infoNews = getIntent().getStringExtra("objectName");

        TextView toolBarText = (TextView) findViewById(R.id.txtToolbarText);
        toolBarText.setText("Comment news");
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientCommentNewsActivity.this, ClientBandSingleNewsActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("objectName",infoNews);
                startActivity(intent);
                finish();
            }
        });

        btnClientCommentNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entryValue = textClientComment.getText().toString();
                if(!entryValue.equals("")){
                    String query_search_comment = "SELECT COMENTARIO FROM [dbo].COMENTARIOPORNOTICIA " +
                            "WHERE USERNAME = ? AND ID_NOTICIA = ?";
                    try {
                        PreparedStatement ps_search_comment = cn.prepareStatement(query_search_comment);
                        ps_search_comment.setString(1,username);
                        ps_search_comment.setInt(2,idNoticia);
                        ResultSet rs_search_Comment = ps_search_comment.executeQuery();
                        if(rs_search_Comment.next()){
                            String update_comment = "UPDATE [dbo].COMENTARIOPORNOTICIA " +
                                    "SET COMENTARIO = ? " +
                                    "WHERE USERNAME = ? AND ID_NOTICIA = ?";
                            PreparedStatement ps_update_comment = cn.prepareStatement(update_comment);
                            ps_update_comment.setString(1,entryValue);
                            ps_update_comment.setString(2,username);
                            ps_update_comment.setInt(3,idNoticia);
                            ps_update_comment.execute();
                            ps_update_comment.close();
                            Toast.makeText(ClientCommentNewsActivity.this, "Se ha actualizado el comentario",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ClientCommentNewsActivity.this, ClientBandSingleNewsActivity.class);
                            intent.putExtra("username",username);
                            intent.putExtra("objectName",infoNews);
                            startActivity(intent);
                            finish();
                        }else{
                            String insert_comment = "INSERT INTO [dbo].COMENTARIOPORNOTICIA " +
                                    "VALUES (?,?,?)";
                            PreparedStatement ps_insert_comment = cn.prepareStatement(insert_comment);
                            ps_insert_comment.setInt(1, idNoticia);
                            ps_insert_comment.setString(2, username);
                            ps_insert_comment.setString(3, entryValue);
                            ps_insert_comment.execute();
                            ps_insert_comment.close();
                            Toast.makeText(ClientCommentNewsActivity.this, "Se ha guardado el comentario",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ClientCommentNewsActivity.this, ClientBandSingleNewsActivity.class);
                            intent.putExtra("username",username);
                            intent.putExtra("objectName",infoNews);
                            startActivity(intent);
                            finish();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(ClientCommentNewsActivity.this, "Ha sucedido un error en el sistema, intente más tarde",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ClientCommentNewsActivity.this, ClientBandSingleNewsActivity.class);
                        intent.putExtra("username",username);
                        intent.putExtra("objectName",infoNews);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Toast.makeText(ClientCommentNewsActivity.this, "El comentario no puede estar vacío",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
