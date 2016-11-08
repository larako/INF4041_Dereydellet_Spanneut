package com.example.cyril.customcallblocker;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class getBieresServices extends IntentService {

    private static final String ACTION_get_all_bieres = "com.example.cyril.customcallblocker.action.Bieres";
    public static final String tag="GetBieresServices";

    public getBieresServices() {
        super("getBieresServices");
    }


    // TODO: Customize helper method
    public static void startActionBieres(Context context) {
        Intent intent = new Intent(context, getBieresServices.class);
        intent.setAction(ACTION_get_all_bieres);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_get_all_bieres.equals(action)) {
                handleActionBieres();
            }
        }
    }

    private void handleActionBieres() {
        Log.i(tag,"it works");
        URL url =null;
        try {
                url =new URL("HTTP://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK ==conn.getResponseCode()){
                copyInputStreamToFile(conn.getInputStream(),new File(getCacheDir(),"bieres.json"));
            }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyInputStreamToFile(InputStream in, File file){
        try {
            OutputStream out =new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len ;
            while ((len=in.read(buf))>0){
                out.write(buf,0,len);

            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
