package com.example.womensafetyapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CDBEmergency extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DMS";

    public CDBEmergency(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//second table
        sqLiteDatabase.execSQL("create table Emergency(eContact text not null primary key ,name text not null, phone text not null, foreign key(phone) references SheSafety(phone))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Emergency");
        onCreate(sqLiteDatabase);
    }

    public void AddContact(CEmergency ce) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("eContact", ce.eContact);
            cv.put("name", ce.name);
            cv.put("phone", ce.phone);
            db.insert("Emergency", null, cv);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValues(String phone) {
        String EContact = "";
        String msg = "";
        String selectQuery = "select eContact from Emergency where phone =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{phone});
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("eContact");
            if (columnIndex >= 0) {
                EContact = cursor.getString(columnIndex);
            }
//            } else {
//                // Handle the case where the column doesn't exist
//                // You can log an error or return a default value, as needed
//                msg ="Error in fetching";
//                return  msg;
//            }

                cursor.close();
            }
            db.close();
            return EContact;
        }
    }


