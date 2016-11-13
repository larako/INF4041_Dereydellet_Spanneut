package com.example.soka.inf4041_dereydellet_spanneut;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends Activity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker time;
    private static MainActivity inst;
    private TextView alarmTextView;
    private ToggleButton toggleButton;
    private boolean ispush=false;
    private NotificationManager alarmNotificationManager;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = (TimePicker) findViewById(R.id.alarmTimePicker);
        time.setIs24HourView(true);
        //time.setBackgroundColor(Color.BLUE); pour changer la couleur
        //time.setScaleX(0.50f); pour changer la taille
        //time.setScaleY(0.50f);
         toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void click(View view) {
        if (((ToggleButton) view).isChecked()) {
            setToast("activé");
            Calendar newTime = Calendar.getInstance();
            newTime.set(Calendar.HOUR_OF_DAY, time.getCurrentHour());
            newTime.set(Calendar.MINUTE, time.getCurrentMinute());
            ispush=true;
            createNotification("blocage activé");
            //toggleButton.setText("Stop");
            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
            alarmManager.set(AlarmManager.RTC, newTime.getTimeInMillis(), pendingIntent);
        } else {
            cancel();
        }
    }

    public void cancel(){
        alarmManager.cancel(pendingIntent);
        ispush=false;
        //toggleButton.setText("Start");
        createNotification("END");
        setToast("désactivé");
    }

    public void setToast(String text){
        Toast.makeText(getBaseContext(), text , Toast.LENGTH_SHORT ).show();
    }

    public void createNotification(String message){

        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message);


        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
    }

}