package com.example.soka.inf4041_dereydellet_spanneut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by cyril on 15/11/2016.
 */

public  class SmsReceiver extends BroadcastReceiver {
    private HashMap<String, String> contact_info = new HashMap<String, String>(); // (num, name)
    private List<String> nums;
    private String senderNum;
    private boolean ok=false;

    @Override
    public void onReceive(Context context, Intent intent) {
        String MSG_TYPE=intent.getAction();
        DatabaseController db;
        db = new DatabaseController(context);
        String app_is_up = db.getSettingByName("block_status");
        app_is_up=app_is_up.intern();
        db.close();
        if(MSG_TYPE.equals("android.provider.Telephony.SMS_RECEIVED")) {
            MainActivity inst = MainActivity.instance();
            if (app_is_up=="stop") {
                final Bundle bundle = intent.getExtras();
                try {
                    if (bundle != null) {
                        final Object[] pdusObj = (Object[]) bundle.get("pdus");
                        for (int i = 0; i < pdusObj.length; i++) {
                            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                            senderNum = currentMessage.getDisplayOriginatingAddress();
                            senderNum = senderNum.substring(3);
                            Log.i("SmsReceiver", "senderNum: " + senderNum);
                        }
                    }
                } catch (Exception e) {
                    Log.e("SmsReceiver", "Exception smsReceiver" + e);
                }

                db = new DatabaseController(context);
                contact_info = db.getAllNumbers();
                nums = Utils.getAllKeysFromHashMap(contact_info);
                db.close();
                for (String num : nums) {
                    num = num.substring(1);
                    num = num.intern();
                    senderNum = senderNum.intern();
                    Log.i("Sendernum", senderNum);
                    Log.i("num", num);
                    if (senderNum == num) {
                        ok = true;
                    }
                }
                if (ok == false) {
                    System.out.print("ca passe");
                    AudioManager recupAudio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    int recup = recupAudio.getRingerMode();
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
}