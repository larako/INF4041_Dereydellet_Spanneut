package com.example.soka.inf4041_dereydellet_spanneut;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import android.util.Log;

/**
 * Created by soka on 11/14/16.
 *
 * WARNING: This class has not been tested
 */

public class DatabaseController {

    private MyDbHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseController(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public String getSettingByName(String setting_name) {
        SQLiteStatement stmt = this.db.compileStatement("SELECT setting_value FROM settings WHERE setting_name = ?");
        stmt.bindString(1, setting_name);
        return stmt.simpleQueryForString();
    }

    public void updateSettingByName(String setting_name, String value) {
        try {
            db.beginTransaction();
            SQLiteStatement stmt = this.db.compileStatement("UPDATE settings SET setting_value = ? WHERE setting_name = ?");
            Log.w("SQLITE", " UPDATE OK");
            stmt.bindString(1, value);
            stmt.bindString(2, setting_name);
            stmt.executeInsert();
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            Log.w("SQLITE", "Exception " + e);
        }
        finally {
            db.endTransaction();
        }
    }
}
