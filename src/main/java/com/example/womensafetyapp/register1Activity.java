package com.example.womensafetyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register1Activity extends AppCompatActivity {
    EditText e1;
String otpSent;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register1);
        Intent intent = getIntent();
        otpSent = intent.getStringExtra("OtpMsg");

        e1 = (EditText) findViewById(R.id.e1);

    }

    public  void onVerify(View view){



        String receivedOpt = e1.getText().toString();
        if(otpSent.equals(receivedOpt)){
            Toast.makeText(this, "Verification successfull", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(register1Activity.this, register2Activity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Verification failed. Please enter the correct OTP.", Toast.LENGTH_LONG).show();
        }

    }
}




