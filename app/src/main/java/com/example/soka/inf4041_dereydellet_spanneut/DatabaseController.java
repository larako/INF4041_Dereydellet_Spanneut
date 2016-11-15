package com.example.soka.inf4041_dereydellet_spanneut;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by soka on 11/14/16.
 *
 * WARNING: This class has not been tested
 */

public class DatabaseController {

    private final String DB_NAME = "settings.sqlite";
    private SQLiteDatabase db;

    public DatabaseController() {
        this.db = SQLiteDatabase.openDatabase(DB_NAME, null, 0);
    }

    public String getSettingByName(String setting_name) {
        SQLiteStatement stmt = db.compileStatement("SELECT setting_value FROM settings WHERE setting_name = ?");
        stmt.bindString(1, setting_name);
        return stmt.simpleQueryForString();
    }

    public void updateSettingByName(String setting_name, String value) {
        SQLiteStatement stmt = db.compileStatement("UPDATE settings SET setting_value = ? WHERE setting_name = ?");
        stmt.bindString(1, value);
        stmt.bindString(2, setting_name);
        stmt.execute();
    }
}
