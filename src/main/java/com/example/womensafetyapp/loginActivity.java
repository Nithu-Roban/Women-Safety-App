package com.example.womensafetyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    EditText et1, et2;
    SharedPreferences sp;
    String CheckPhone;
    CDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        db = new CDB(this);

        sp = getSharedPreferences("SD", MODE_PRIVATE);
        CheckPhone = sp.getString("phone", "").toString();

    }

    public  void onLogin(View view){

        String phone = et1.getText().toString().trim();
        String pass = et2.getText().toString().trim();

        if(phone.equals("") || pass.equals("")){
            Toast.makeText(this, "Please fill the required Field!", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                if(CheckPhone.toString().equals(phone)){

                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this, Dashboard1.class);
                    startActivity(intent);
                    et1.setText("");
                    et2.setText("");

                }
                else{
                    Toast.makeText(this, "No Such User Exists", Toast.LENGTH_SHORT).show();
                    et1.setText("");
                    et2.setText("");
                }
            }catch (Exception e){
                Toast.makeText(this, "An error occurred during login:", Toast.LENGTH_SHORT).show();
            }
               

        }

    }

}