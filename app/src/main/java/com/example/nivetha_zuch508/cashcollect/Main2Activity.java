package com.example.nivetha_zuch508.cashcollect;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
EditText name,mobileno,address1,address2,city,dob;
Spinner state;
DatabaseReference databasecust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Button add = (Button) findViewById(R.id.button3);

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        final String admin = sharedPreferences.getString("acc_id","");
        databasecust = FirebaseDatabase.getInstance().getReference(admin);
        name = (EditText) findViewById(R.id.nameed);
        mobileno = (EditText) findViewById(R.id.mobed);
        address1 = (EditText) findViewById(R.id.editText2);
        address2 = (EditText) findViewById(R.id.editText3);
        city = (EditText) findViewById(R.id.editText5);
        dob = (EditText) findViewById(R.id.editText7);
        state = (Spinner) findViewById(R.id.spinner);
        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference(admin).child("customer_details");
                   databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren())
                           {
                              String k= dataSnapshot2.getKey();
                              if(k.equals(mobileno.getText().toString()))
                              {
                             //     Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                                  TextView tv = (TextView) findViewById(R.id.alert);
                                  tv.setText("*Mobile no already exist");
                                  add.setEnabled(false);
                              }
                              else {
                                  add.setEnabled(true);
                                  TextView tv = (TextView) findViewById(R.id.alert);
                                  tv.setText("");
                              }
                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });
                    add.setEnabled(false);



            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCust();
            }
        });
        ImageButton ibtn = (ImageButton) findViewById(R.id.imageButton);
        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
    }
    private void addCust() {
        //getting the values to save
        String name1 = name.getText().toString().trim();
        String mobno = mobileno.getText().toString();
        String address = address1.getText().toString()+","+address2.getText().toString();
        String city1 = city.getText().toString();
        String state1 = state.getSelectedItem().toString();
        String dob1 = dob.getText().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name1)&&!TextUtils.isEmpty(mobno)&&!TextUtils.isEmpty(address)&&!TextUtils.isEmpty(city1)&&!TextUtils.isEmpty(state1)&&!TextUtils.isEmpty(dob1)&&!address2.getText().toString().isEmpty()&&!address1.getText().toString().isEmpty()) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
           // String id = databaseArtists.push().getKey();

            //creating an Artist Object
            Cust artist = new Cust(mobno,name1,address,city1,state1,dob1);

            //Saving the Artist
            databasecust.child("customer_details").child(mobno).setValue(artist);

            //setting edittext to blank again
            name.setText("");
            mobileno.setText("");
            address2.setText("");
            address1.setText("");
            city.setText("");
            dob.setText("");

            //displaying a success toast
            Toast.makeText(this, "Customer added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE,dayOfMonth);
        String dob2 = DateFormat.getDateInstance().format(c.getTime());

        dob.setText(dob2);
    }

}
