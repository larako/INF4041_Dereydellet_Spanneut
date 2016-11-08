package com.example.cyril.customcallblocker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text= (TextView)findViewById(R.id.textView);
        setContentView(R.layout.activity_main);
    }

    public void click(View v){
       Toast.makeText(getApplicationContext(),getString(R.string.message),Toast.LENGTH_LONG).show();
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle("notif")
                .setContentText("This is a test notification")
                .setSmallIcon(R.mipmap.ic_launcher);
        window();

    }


    public void window(){
        Intent i =new Intent(MainActivity.this,SecondActivity.class);
        startActivity(i);
    }
}
