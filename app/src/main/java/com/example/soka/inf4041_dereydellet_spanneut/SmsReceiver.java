package com.example.soka.inf4041_dereydellet_spanneut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import static java.lang.Thread.sleep;

/**
 * Created by cyril on 15/11/2016.
 */

public  class SmsReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String MSG_TYPE=intent.getAction();
        DatabaseController db;
        db = new DatabaseController(context);
        String app_is_up = db.getSettingByName("block_status");
        app_is_up=app_is_up.intern();
        if(MSG_TYPE.equals("android.provider.Telephony.SMS_RECEIVED")) {
            MainActivity inst = MainActivity.instance();
            if (app_is_up=="stop") {
                System.out.print("ca passe");
                AudioManager recupAudio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int recup=recupAudio.getRingerMode();
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (recup == AudioManager.RINGER_MODE_VIBRATE)
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                if (recup == AudioManager.RINGER_MODE_SILENT)
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                if (recup == AudioManager.RINGER_MODE_NORMAL)
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }


    }
}