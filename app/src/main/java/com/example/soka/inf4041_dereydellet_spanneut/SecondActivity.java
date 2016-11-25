package com.example.soka.inf4041_dereydellet_spanneut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;


public class SecondActivity extends AppCompatActivity {
    private ListView nums;
    private ListView names;
    DatabaseController db;
    private HashMap<String, String> data = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        nums = (ListView) findViewById(R.id.nums);
        names = (ListView) findViewById(R.id.names);
        db = new DatabaseController(getApplicationContext());
        data = db.getAllNumbers(); // on recupere tous les nums
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondActivity.this,android.R.layout.simple_list_item_1, Utils.getAllKeysFromHashMap(data) ); //on les met en liste
        nums.setAdapter(adapter); //on affiche la liste

        ArrayAdapter<String> adapter_ = new ArrayAdapter<String>(SecondActivity.this,android.R.layout.simple_list_item_1, Utils.getAllValuesFromHashMap(data) ); //on les met en liste
        names.setAdapter(adapter_);

        nums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "contact retiré", Toast.LENGTH_SHORT).show();
                db = new DatabaseController(getApplicationContext());
                db.removeNumber((String) ((TextView) view).getText()); //on supprime quand le mec clique sur une case
                db.close();

                db = new DatabaseController(getApplicationContext());
                data = db.getAllNumbers(); //et in reaffiche tous les nums
                db.close();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondActivity.this,android.R.layout.simple_list_item_1,Utils.getAllKeysFromHashMap(data));
                nums.setAdapter(adapter);
                ArrayAdapter<String> adapter_ = new ArrayAdapter<String>(SecondActivity.this,android.R.layout.simple_list_item_1, Utils.getAllValuesFromHashMap(data) ); //on les met en liste
                names.setAdapter(adapter_);
            }
        });
    }

}
