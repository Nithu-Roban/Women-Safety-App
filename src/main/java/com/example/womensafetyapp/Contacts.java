package com.example.womensafetyapp;




import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    private static final int CONTACT_PICKER_REQUEST = 1;
    private final int REQUEST_CODE=99;

    ListView listView;
    private Button selectButton;
     EditText contactNameTextView;
     EditText contactNumberTextView;
    CDBEmergency db;
    SharedPreferences sp;
    String phone1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        Intent intent = getIntent();

        listView = findViewById(R.id.ListView); // Initialize the ListView here
        selectButton = findViewById(R.id.button4);
        contactNameTextView = findViewById(R.id.contactNameTextView);
        contactNumberTextView = findViewById(R.id.contactNumberTextView);

        db = new CDBEmergency(this);

        sp = getSharedPreferences("SD", MODE_PRIVATE);
        phone1 = sp.getString("phone", "").toString();
    }

    public void selectContact(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_REQUEST);

    }

    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CONTACT_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Cursor cursor = null;
//                String contactId = null;
//                try {
//                    String contactName = null;
//                    String contactPhoneNumber = null;
//
//                    // Get the contact's data
//                    Uri contactUri = data.getData();
//                    cursor = getContentResolver().query(contactUri, null, null, null, null);
//
//                    if (cursor != null && cursor.moveToFirst()) {
//                        // Get the contact's name
//
//                        int displayNameColumnIndex =cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
//                        if (displayNameColumnIndex != -1) {
//                            contactName = cursor.getString(displayNameColumnIndex);
//                        }
//                        // Get the contact's ID
//                        int displayNumberColumnIndex =cursor.getColumnIndex(ContactsContract.Contacts._ID);
//                        if(displayNumberColumnIndex!=-1) {
//                             contactId = cursor.getString(displayNumberColumnIndex);
//                        }
//                        // Fetch the contact's phone numbers
//                        Cursor phoneCursor = getContentResolver().query(
//                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                                null,
//                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                                new String[]{contactId},
//                                null
//                        );
//
//                        if (phoneCursor != null && phoneCursor.moveToFirst()) {
//                            // Assuming you want to get the first phone number
//                            int phoneNumberColumnIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                            if(phoneNumberColumnIndex!=-1) {
//                                contactPhoneNumber = phoneCursor.getString(phoneNumberColumnIndex);
//                            }
//                            phoneCursor.close();
//                        }
//                    }
//
//                    // Update the EditText fields
//                    contactNameTextView.setText(contactName);
//                    contactNumberTextView.setText(contactPhoneNumber);
//
//                } finally {
//                    if (cursor != null) {
//                        cursor.close();
//                    }
//                }
//            }
//        }
//    }
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        int NumIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
                        String contactId = c.getString(NumIndex);
                        int hasNumIndex = c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                        String hasNumber = c.getString(hasNumIndex);
                        String num = "";
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (numbers.moveToNext()) {
                                int Colindex = numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                                num = numbers.getString(Colindex);
//                                contactNumberTextView.setText(num.toString());
//                                contactNameTextView.setText("");
                            }
                        }
                    }
                    break;
                }
        }
    }

    public void  onSave(View view){

        CEmergency rec = new CEmergency();
        String cname = contactNameTextView.getText().toString();
        String cnum = contactNumberTextView.getText().toString();


        //saving to db emergency;
        rec.eContact = cnum.toString();
        rec.name = cname.toString();
        rec.phone = phone1.toString();
        db.AddContact(rec);
        contactNumberTextView.setText("");
        contactNameTextView.setText("");
        try {
            String ContactCheck = db.getValues(phone1);
            if (cnum.equals(ContactCheck)) {
                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Contacts.this, Dashboard1.class);
                startActivity(intent);
            }
        }catch (Exception e){
            Toast.makeText(this, "Error Inserting into db " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

}





