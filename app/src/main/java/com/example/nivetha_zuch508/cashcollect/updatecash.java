package com.example.nivetha_zuch508.cashcollect;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

public class updatecash extends AppCompatActivity {
    EditText mo,amount;
    Button btn;
    String amount1,mobno;
    DatabaseReference databaseref;
    DatabaseReference databaseReference;
    ListView listView;
    List<Cust>custList;
    Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatecash);
        btn = (Button) findViewById(R.id.button5);
        amount = (EditText) findViewById(R.id.amounttext);

           /* @Override
            public void onClick(View v) {


                databaseReference = FirebaseDatabase.getInstance().getReference("customer_details");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                                Cust bill_update = billSnapshot.getValue(Cust.class);
                                String mon = bill_update.getCustmo().toString();
                                   addcash();
                                Toast.makeText(getApplicationContext(),"data"+mon,Toast.LENGTH_LONG).show();
                            }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

      */  btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount1 = amount.getText().toString().trim();
                if (!TextUtils.isEmpty(amount1)) {

                    SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    String admin = sharedPreferences.getString("acc_id","");
                databaseReference = FirebaseDatabase.getInstance().getReference(admin).child("customer_details");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                            Cust bill_update = billSnapshot.getValue(Cust.class);
                            mobno = bill_update.getCustmo().toString();
                            addcash();
                          //  Toast.makeText(getApplicationContext(),"data"+mobno,Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getApplicationContext(),"Bill updated",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(),"Please Enter the Amount",Toast.LENGTH_LONG).show();
            }
        });
        amount.setText("");

    }
    public void addcash(){

        String status = "Pending";
        //checking if the value is provided
        amount1 = amount.getText().toString().trim();
     if (!TextUtils.isEmpty(amount1)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            // String id = databaseArtists.push().getKey();
         SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
         String admin = sharedPreferences.getString("acc_id","");

            //creating an Artist Object
          Long tsLong = System.currentTimeMillis()/1000;
          String ts = tsLong.toString();
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            String paid = "0";
            String year1= Integer.toString(year);
           // String month1= Integer.toString(month);
            String month1 =c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            billupdate artist = new billupdate(mobno,amount1,year1,status,paid,month1,ts);
            databaseref = FirebaseDatabase.getInstance().getReference(admin).child("customer_bill").child(mobno);
            //Saving the Artist
         String tim = ts+month1;
            databaseref.child(year1).child(tim).setValue(artist);

            //setting edittext to blank again


            //displaying a success toast
          //  Toast.makeText(this, "Bill Updated", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter amount", Toast.LENGTH_LONG).show();
        }
    }
}
