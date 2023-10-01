package com.example.womensafetyapp;

import android.Manifest;


import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;


public class registerActivity extends AppCompatActivity {

    EditText phone;
    String otpmsg, phno;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Intent intent = getIntent();
        phone =(EditText) findViewById(R.id.phone);
    }

    public  void sentOTP(View view){

        if(ContextCompat.checkSelfPermission(registerActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
            OTP();
        }
        else{
            ActivityCompat.requestPermissions(registerActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
        }
    }




    @Override
    public  void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                OTP();

            } else {
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    private  void OTP(){
        otpmsg = generateRandomOTP();
        phno = phone.getText().toString();


        if (!phno.isEmpty()) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                //ArrayList<String> parts = smsManager.divideMessage(message + otpmsg);
                message ="Unlock the power of safety: Your OTP to She Safety app is "+otpmsg;
                String PhoneNumber = phno;
                //smsManager.sendMultipartTextMessage(PhoneNumber, null, parts, null, null);
                    smsManager.sendTextMessage(PhoneNumber,null, message,null,null);
                // Notify the user that OTP has been sent
                Toast.makeText(this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // Handle SMS sending failure
                Toast.makeText(this, "Error sending OTP: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            // Handle the case where the phone number is empty
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
        }
    }

    public void  onNext(View view){
        SharedPreferences sp;
        sp = getSharedPreferences("SD",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("phone",phone.getText().toString());
        ed.commit();


        Intent intent = new Intent(registerActivity.this, register1Activity.class);
        intent.putExtra("OtpMsg", otpmsg);

        startActivity(intent);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Random number between 100000 and 999999
        return String.valueOf(otp);
    }

}