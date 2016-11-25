package com.example.soka.inf4041_dereydellet_spanneut;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by soka on 23/11/16.
 */

public class Utils {

    // retrieve all keys in hashmap at index i
    public static ArrayList<String> getAllKeysFromHashMap(HashMap<String, String> map) {
        ArrayList<String> list = new ArrayList<String>();
        int n;
        for (Map.Entry<String, String> entry: map.entrySet()) {
            list.add(entry.getKey());
            Log.d("CONTACT", "Num: " + entry.getKey());
        }
        return list;
    }

    // retrieve all values in hashmap at index i
    public static ArrayList<String> getAllValuesFromHashMap(HashMap<String, String> map) {
        ArrayList<String> list = new ArrayList<String>();
        int n;
        for (Map.Entry<String, String> entry: map.entrySet()) {
            list.add(entry.getValue());
            Log.d("CONTACT", "Name: " + entry.getValue());
        }
        return list;
    }
}
