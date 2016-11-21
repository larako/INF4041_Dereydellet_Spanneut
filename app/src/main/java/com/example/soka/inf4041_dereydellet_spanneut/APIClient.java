package com.example.soka.inf4041_dereydellet_spanneut;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by soka on 21/11/16.
 */

public class APIClient {

    final static String API_URL = "http://api.mobile.crashlab.org";

    public static String getJSON(String _url) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            in.close();
            urlConnection.disconnect();

        }
        catch (MalformedURLException e) {
            Log.e("http", Log.getStackTraceString(e));
        }
        catch (IOException e) {
            Log.e("http", Log.getStackTraceString(e));
        }

        return result.toString();
    }

    /*
        Hash password
     */

    public static String sha1(String str) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte result[] = digest.digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();

            for (byte b : result) {
                sb.append(String.format("%02X", b));
            }

            return sb.toString();

        }catch (NoSuchAlgorithmException e) {
            Log.e("digest", Log.getStackTraceString(e));
        }
        catch (UnsupportedEncodingException e) {
            Log.e("encoding", Log.getStackTraceString(e));
        }

        return "";
    }
}
