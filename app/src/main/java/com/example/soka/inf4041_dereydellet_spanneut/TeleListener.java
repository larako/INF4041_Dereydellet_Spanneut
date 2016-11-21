package com.example.soka.inf4041_dereydellet_spanneut;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.android.internal.telephony.ITelephony;
/**
 * Created by cyril on 14/11/2016.
 */

public class TeleListener extends PhoneStateListener {

    private TelephonyManager tel;
    private  AudioManager audioManager;
    private Context context;
    private DatabaseController db;
    private List<String> nums;

    public TeleListener(TelephonyManager tel, AudioManager audioManager, Context context){
        this.tel=tel;
        this.audioManager= audioManager;
        this.context=context;
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
                db = new DatabaseController(context);
                nums = db.getAllNumbers();

                Class c = null;
                    try {
                        for (String num: nums) {
                            num=num.intern();
                            incomingNumber=incomingNumber.intern();
                            if (incomingNumber != num) { //ici on mettra les numéros enregistrés
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
