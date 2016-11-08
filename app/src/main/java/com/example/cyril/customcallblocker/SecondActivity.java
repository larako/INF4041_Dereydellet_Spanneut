package com.example.cyril.customcallblocker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBieresServices.startActionBieres(this);
        setContentView(R.layout.activity_second);
    }
}
