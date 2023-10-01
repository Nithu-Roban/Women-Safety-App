package com.example.womensafetyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register2Activity extends AppCompatActivity {


    EditText et1, pass1, pass2;
    String phone;
    SharedPreferences sp, sp1;
    CDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);


        et1 = (EditText) findViewById(R.id.et1);
        pass1 = (EditText) findViewById(R.id.et2);
        pass2 = (EditText) findViewById(R.id.et3);

        db = new CDB(this);

        //using shared preferences accessing the phone number
        sp = getSharedPreferences("SD", MODE_PRIVATE);
        phone = sp.getString("phone", "").toString();
    }

    public void onRegister(View view) {
        CSheSafety rec = new CSheSafety();
        String pass = pass1.getText().toString();
        if (et1.equals("") || pass1.equals("") || pass2.equals("")) {
            Toast.makeText(this, "Please fill the Required field", Toast.LENGTH_LONG).show();
        } else {
            if (pass1.getText().toString().equals(pass2.getText().toString())) {

                // checking if user already exists
                int count = db.getValues(phone,pass);
                if(count > 0){
                    Toast.makeText(this, "User already exists, Please Login", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        // add elements into db
                        rec.phone = phone.toString();
                        rec.username = et1.getText().toString();
                        rec.password = pass1.getText().toString();
                        db.AddTb(rec);
                    }catch (Exception e){
                        Toast.makeText(this, "error in registering:"+e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                        et1.setText("");
                        pass1.setText("");
                        pass2.setText("");
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(register2Activity.this, Dashboard1.class);
                        startActivity(intent);
//adding username into shared preference
                    SharedPreferences sp1;
                    sp1 = getSharedPreferences("SU",MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp1.edit();
                    ed.putString("username",et1.getText().toString());
                    ed.commit();
                }
            }
                //if password doesn't match
            else {
                Toast.makeText(getApplicationContext(), " password doesn't match", Toast.LENGTH_LONG).show();
                pass1.setText("");
                pass2.setText("");
            }


        }
    }
}
