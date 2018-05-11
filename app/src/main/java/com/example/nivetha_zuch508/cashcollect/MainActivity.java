package com.example.nivetha_zuch508.cashcollect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
EditText ed;
Button b1;
TextView t1,t2,t3,t4,t5,t6;
DatabaseReference databaseReference;
ListView listView;
CardView ca1,ca2;
List<billupdate>billupdateList;
    String key;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed= (EditText) findViewById(R.id.editText6);
        b1 = (Button) findViewById(R.id.button2);
        listView = (ListView) findViewById(R.id.bill_list_view);

        billupdateList = new ArrayList<>();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = ed.getText().toString();
                if (!TextUtils.isEmpty(key)) {
                     Toast.makeText(getApplicationContext(),"Search Result",Toast.LENGTH_LONG).show();

                    flag = 0;
                    SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    String admin = sharedPreferences.getString("acc_id","");
                databaseReference = FirebaseDatabase.getInstance().getReference(admin).child("customer_bill").child(key);
                    Query myTopPostsQuery = databaseReference.orderByChild("timestamp");

                    myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        billupdateList.clear();
                        for (DataSnapshot billSnapshot1 : dataSnapshot.getChildren()) {
                            for (DataSnapshot billSnapshot : billSnapshot1.getChildren()) {
                                billupdate bill_update = billSnapshot.getValue(billupdate.class);
                                billupdateList.add(bill_update);
                                String mon = bill_update.getMonth().toString();
                                flag = 1;
                                //   Toast.makeText(getApplicationContext(),"data"+mon,Toast.LENGTH_LONG).show();
                            }
                            bill_list adapter = new bill_list(MainActivity.this, billupdateList);

                            listView.setAdapter(adapter);


                        }
                        if (flag == 1) {
                            setVisi();
                        }
                        if (flag == 0) {
                            t6 = (TextView) findViewById(R.id.nocus);
                            ca1 = (CardView) findViewById(R.id.titlecard);
                            ca2 = (CardView) findViewById(R.id.datacard);
                            t6.setVisibility(View.VISIBLE);
                            ca1.setVisibility(View.INVISIBLE);
                            ca2.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "No Customer Found", Toast.LENGTH_LONG).show();

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(),"please enter the Customer ID",Toast.LENGTH_LONG).show();

                }
        });

    }

    private void setVisi() {
        ca1 =(CardView) findViewById(R.id.titlecard);
        ca2 = (CardView) findViewById(R.id.datacard);
        t1 = (TextView) findViewById(R.id.mth);
        t2 = (TextView) findViewById(R.id.yer);
        t3 = (TextView) findViewById(R.id.amn);
        t4 =(TextView) findViewById(R.id.pa);
        t5 = (TextView) findViewById(R.id.sta);

        t1.setVisibility(View.VISIBLE);
        t2.setVisibility(View.VISIBLE);
        t3.setVisibility(View.VISIBLE);
        t4.setVisibility(View.VISIBLE);
        t5.setVisibility(View.VISIBLE);
        ca1.setVisibility(View.VISIBLE);
        ca2.setVisibility(View.VISIBLE);

    }

}
