package com.example.soka.inf4041_dereydellet_spanneut;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;


import java.lang.reflect.Method;
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
    private  TelephonyManager tel;
    TeleListener listen;
    //private TelephonyManager tel;
    static final int PICK_CONTACT_REQUEST = 1;  // The request code

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
         tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        listen=new TeleListener(tel,ispush);
         toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void clickStart(View view) { //permet de start l'appli
        if (((ToggleButton) view).isChecked()) {
            setToast("activé");
            Calendar newTime = Calendar.getInstance(); // pour recuperer lheure
            newTime.set(Calendar.HOUR_OF_DAY, time.getCurrentHour());
            newTime.set(Calendar.MINUTE, time.getCurrentMinute());
            ispush=true;
            //TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            tel.listen(listen, TeleListener.LISTEN_CALL_STATE); //bloque
            createNotification("blocage activé");
            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class); //permet d'acceder a la classe alarmReceiver
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0); // permet de decaler le moment d'appeler la fct
            alarmManager.set(AlarmManager.RTC, newTime.getTimeInMillis(), pendingIntent); //cree l'alarme uand c'est finit

        } else {
            cancel();
        }
    }

    public void cancel(){ // ce qui va annuler l'alarme
        alarmManager.cancel(pendingIntent);
        ispush=false;
        //TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tel.listen(listen, TeleListener.LISTEN_NONE); //debloque
        //toggleButton.setText("Start");
        createNotification("END");
        setToast("désactivé");
    }

    public void clickContact(View v){ //permet de selectionner un contact
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){ //des que la personne selectionne des contacts
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                setToast("contact ajouté");
                    //et la on ajoute ce numero à la liste des gens autorisés
            }
        }
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