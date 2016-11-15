package com.example.soka.inf4041_dereydellet_spanneut;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.android.internal.telephony.ITelephony;
/**
 * Created by cyril on 14/11/2016.
 */

public class TeleListener extends PhoneStateListener {

    private TelephonyManager tel;
    private  AudioManager audioManager;

    public TeleListener(TelephonyManager tel,AudioManager audioManager ){
        this.tel=tel;
        this.audioManager= audioManager;
    }

    public void onCallStateChanged(int state, String incomingNumber) {
        MainActivity inst = MainActivity.instance();

        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                // CALL_STATE_IDLE;

                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                // CALL_STATE_OFFHOOK;

                break;
            case TelephonyManager.CALL_STATE_RINGING:
                // CALL_STATE_RINGING

                Class c = null;
                    try {
                     if (incomingNumber!=null) { //ici on mettra les numéros enregistrés
                        // audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                         c = Class.forName(tel.getClass().getName());
                         Method method = c.getDeclaredMethod("getITelephony");
                         method.setAccessible(true);
                         ITelephony telService = (ITelephony) method.invoke(tel);
                         telService = (ITelephony) method.invoke(tel);
                         telService.silenceRinger();
                         telService.endCall();
                         inst.setToast(incomingNumber);
                     }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
}
