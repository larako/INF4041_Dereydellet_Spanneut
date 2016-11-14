package com.android.internal.telephony;

/**
 * Created by cyril on 14/11/2016.
 */

public interface ITelephony {
    boolean endCall();

    void answerRingingCall();

    void silenceRinger();
}
