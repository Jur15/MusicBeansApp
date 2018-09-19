package tec.musicbeansapp.gui.Admin.Accounts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import tec.musicbeansapp.R;

public class BandAccountListActivity extends AppCompatActivity {


    // UI Views
    ListView bandList;
    Button btnAddNewBand;
    private ListView list;
    final ArrayList<String> options = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_account_list);

        bandList = (ListView) findViewById(R.id.bandList);
        btnAddNewBand = (Button) findViewById(R.id.btnAddNewBand);
        list = (ListView) findViewById(R.id.bandList);

        bandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Clicked item number: " + i);
                //TODO: Add Intent to navigate to an Activity to view Band details and enable option to eliminate it.
            }
        });

        btnAddNewBand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("LOG: Tying to create a new Band Account");
                Intent intent = new Intent(BandAccountListActivity.this, CreateBandActivity.class);
                startActivity(intent);
            }
        });
        prueba();
    }
    public void prueba (){
        options.add("Prueba");//aqui va lo que tire la consulta
        options.add("Hola");
        options.add("Prueba");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(BandAccountListActivity.this, DeleteBandActivity.class);
                intent.putExtra("objectName", options.get(position));//aqui hace los parametros que quiera pasar al otro activity
                //intent.putExtra("objectPlace", optionsEvent.get(position).getPlace().toString());
                //intent.putExtra("objectDate", optionsEvent.get(position).getDate().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}
