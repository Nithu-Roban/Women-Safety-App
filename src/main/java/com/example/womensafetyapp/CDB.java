package com.example.womensafetyapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CDB extends SQLiteOpenHelper {

    String phone;

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME= "DMS";

    public CDB(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("create table SheSafety(phone text not null primary key , username text not null, password text not null)");
        sqLiteDatabase.execSQL("create table Emergency(eContact text not null primary key ,name text not null, phone text not null, foreign key(phone) references SheSafety(phone))");



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SheSafety");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Emergency");
        onCreate(sqLiteDatabase);
    }

    //registering new user
    public  void AddTb(CSheSafety cs){

        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv =new ContentValues();
            cv.put("phone",cs.phone);
            cv.put("username", cs.username);
            cv.put("password", cs.password);
            db.insert("SheSafety", null, cv);
            db.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

//validity check
    public int getValues(String phone, String pass){
        String selectQuery ="select Count(*) from SheSafety where phone =? and password =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{phone,pass});
        int count =0;
        if(cursor!= null){
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return count;
    }


}
