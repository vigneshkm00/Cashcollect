package com.example.nivetha_zuch508.cashcollect;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        databasecust = FirebaseDatabase.getInstance().getReference("customer_details");
        name = (EditText) findViewById(R.id.nameed);
        mobileno = (EditText) findViewById(R.id.mobed);
        address1 = (EditText) findViewById(R.id.editText2);
        address2 = (EditText) findViewById(R.id.editText3);
        city = (EditText) findViewById(R.id.editText5);
        dob = (EditText) findViewById(R.id.editText7);
        state = (Spinner) findViewById(R.id.spinner);
        Button add = (Button) findViewById(R.id.button3);
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
            databasecust.child(mobno).setValue(artist);

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
