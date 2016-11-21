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

import java.util.List;


public class SecondActivity extends AppCompatActivity {
    private ListView text;
    DatabaseController db;
    private  List<String> nums;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        text= (ListView) findViewById(R.id.text);
        db = new DatabaseController(getApplicationContext());
         nums = db.getAllNumbers(); // on recupere tous les nums
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondActivity.this,android.R.layout.simple_list_item_1,nums); //on les met en liste
        text.setAdapter(adapter); //on affiche la liste

        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "contact retiré", Toast.LENGTH_SHORT).show();
                db.removeNumber((String) ((TextView) view).getText()); //on supprime quand le mec clique sur une case
                nums = db.getAllNumbers(); //et in reaffiche tous les nums
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondActivity.this,android.R.layout.simple_list_item_1,nums);
                text.setAdapter(adapter);
            }
        });
    }

}
