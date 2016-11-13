package com.example.soka.inf4041_dereydellet_spanneut;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //Si on arrive ici c'est que l'alarme est finit
        MainActivity inst = MainActivity.instance();
        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        inst.cancel(); //on enleve
    }
}