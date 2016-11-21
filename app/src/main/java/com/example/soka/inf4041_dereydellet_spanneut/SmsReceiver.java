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
        if(MSG_TYPE.equals("android.provider.Telephony.SMS_RECEIVED")) {
            if (app_is_up=="stop") {
                System.out.print("ca passe");
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                AudioManager recupAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (recupAudioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE)
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                if (recupAudioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT)
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                if (recupAudioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT)
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        }


    }
}