package com.example.nivetha_zuch508.cashcollect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {
    List<String[]> data = new ArrayList<String[]>();
    EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Button b5 = (Button) findViewById(R.id.custdet);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customer_details");
                data.add(new String[]{"Name","mob_no","address","state","city","dob"});
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                            Cust bill_update = billSnapshot.getValue(Cust.class);
                            String mobno = bill_update.getCustmo().toString();
                            String name = bill_update.getCustname().toString();
                            String addr = bill_update.getAddress().toString();
                            String state = bill_update.getState().toString();
                            String city = bill_update.getCity().toString();
                            String dob = bill_update.getDob().toString();
                            data.add(new String[]{name,mobno,addr,state,city,dob});

                            //  Toast.makeText(getApplicationContext(),"data"+mobno,Toast.LENGTH_LONG).show();
                        }
                        createcsv("customer_det");
                        //Toast.makeText(getApplicationContext(),"Bill updated",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        Button b6 = (Button) findViewById(R.id.custbill);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customer_bill");
                data.add(new String[]{"Mobile_No","Month","Year","Amount","Paid","Status"});
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot billSnapshot1 : billSnapshot.getChildren())
                            {
                                for (DataSnapshot billSnapshot2 : billSnapshot1.getChildren()){
                                billupdate bill_update = billSnapshot2.getValue(billupdate.class);
                                String mobno = bill_update.getId().toString();
                                String month = bill_update.getMonth().toString();
                                String year = bill_update.getYear().toString();
                                String amount = bill_update.getAmount().toString();
                                String paid = bill_update.getPaid().toString();
                                String status = bill_update.getStatus().toString();
                                data.add(new String[]{mobno,month,year,amount,paid,status});
                            }}
                            //  Toast.makeText(getApplicationContext(),"data"+mobno,Toast.LENGTH_LONG).show();
                        }
                        createcsv("bill_report");
                        //Toast.makeText(getApplicationContext(),"Bill updated",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }

    private void createcsv(String f_name) {
        String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        CSVWriter writer = null;
        try {
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            writer = new CSVWriter(new FileWriter("/sdcard/"+f_name+ts+".csv"), ',');
            writer.writeAll(data);
            writer.close();
            data.clear();
            Toast.makeText(getApplicationContext(),"CSV File Generated view it on Internal Storage ",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
