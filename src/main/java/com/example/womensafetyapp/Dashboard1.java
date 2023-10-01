package com.example.womensafetyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard1 extends AppCompatActivity {

    SharedPreferences sp, sp1, sp2;
    String loc, phone, username;

    CDBEmergency db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        Intent intent = getIntent();

        sp = getSharedPreferences("SD", MODE_PRIVATE);
        phone = sp.getString("phone", "").toString();

        sp1 = getSharedPreferences("SU", MODE_PRIVATE);
        username = sp1.getString("username","").toString();

        sp2 = getSharedPreferences("SL",MODE_PRIVATE);
        loc = sp2.getString("location" ,"").toString();



        db = new CDBEmergency(this);
    }
    public void onContact(View view){
        Intent intent = new Intent(Dashboard1.this, Contacts.class);
        startActivity(intent);
    }

    public void onTracking(View view){
        Intent intent = new Intent(Dashboard1.this, TrackLocation.class);
        startActivity(intent);

    }

    public void onHelpline(View view){
        Intent intent = new Intent(Dashboard1.this, Helpline.class);
        startActivity(intent);
    }
    public void onPanic(View view){
//        String  EContact =  db.getValues(phone);
//        String message = "DANGER ALERT \n "+username+"is in Danger. Help Her as soon as possible.\n Her Current Location is"+loc;
//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage(EContact, null, message, null, null);
//        Toast.makeText(this,"Alert Message sent successfully", Toast.LENGTH_LONG).show();
//    }

            try {
                //String EContact = db.getValues(phone);
                //Currently added personal Number to check the module
                String EContact ="7736872693";
                String message = "DANGER ALERT\n " + username + " is in Danger. Help Her as soon as possible.\nHer Current Location is " + loc;
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(EContact, null, message, null, null);
                Toast.makeText(this, "Alert Message sent successfully", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // Handle any exceptions that might occur during SMS sending
                Toast.makeText(this, "Error sending panic message: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }
