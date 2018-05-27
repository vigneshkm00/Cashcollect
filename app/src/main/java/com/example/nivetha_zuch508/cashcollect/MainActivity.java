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
import android.widget.ProgressBar;
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
TextView t1,t2,t3,t4,t5,t6,t7;
DatabaseReference databaseReference;
ListView listView;
CardView ca1,ca2;
List<billupdate>billupdateList;
    String key;
    int flag,flag1;

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

                t6 = (TextView) findViewById(R.id.nocus);
                t7 = (TextView) findViewById(R.id.textView14);
                key = ed.getText().toString();

                if (!TextUtils.isEmpty(key)) {
                    flag1=0;
                    flag=0;
                    ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                    pb.setVisibility(View.VISIBLE);
                     Toast.makeText(getApplicationContext(),"Search Result",Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    String admin = sharedPreferences.getString("acc_id","");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(admin).child("customer_details");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                            {
                                final Cust cust = dataSnapshot1.getValue(Cust.class);
                                ed= (EditText) findViewById(R.id.editText6);
                                key = ed.getText().toString();
                                if(key.equals(cust.getCustmo().toString()))
                                {
                                    flag = 0;
                                    flag1 = 1;
                                    SharedPreferences sharedPreferences1 = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                    String admin1 = sharedPreferences1.getString("acc_id","");
                                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference(admin1).child("customer_bill").child(key);
                                    Query myTopPostsQuery = databaseReference1;
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
                                                CardView cusde = (CardView) findViewById(R.id.cardView4);
                                                 ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                                                pb.setVisibility(View.INVISIBLE);
                                                cusde.setVisibility(View.VISIBLE);
                                                TextView add = (TextView) findViewById(R.id.addressbar);
                                                add.setText(cust.getCustname().toString()+"\n"+cust.getAddress().toString()+"\n"+cust.getCity().toString()+","+cust.getState().toString());
                                                setVisi();
                                            }
                                            if (flag == 0&&flag1==1) {
                                                CardView cusde = (CardView) findViewById(R.id.cardView4);
                                                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                                                pb.setVisibility(View.INVISIBLE);
                                                t7 = (TextView) findViewById(R.id.textView14);
                                                t6 = (TextView) findViewById(R.id.nocus);
                                                ca1 = (CardView) findViewById(R.id.titlecard);
                                                ca2 = (CardView) findViewById(R.id.datacard);
                                                t6.setVisibility(View.INVISIBLE);
                                                t7.setVisibility(View.VISIBLE);
                                                ca1.setVisibility(View.INVISIBLE);
                                                ca2.setVisibility(View.INVISIBLE);
                                                cusde.setVisibility(View.VISIBLE);
                                                TextView add = (TextView) findViewById(R.id.addressbar);
                                                add.setText(cust.getCustname().toString()+"\n"+cust.getAddress().toString()+"\n"+cust.getCity().toString()+","+cust.getState().toString());

                                                //                                             Toast.makeText(getApplicationContext(), "No Bills Found", Toast.LENGTH_LONG).show();

                                            }



                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                            }
                            if (flag ==0&& flag1==0)
                            {
                                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                                pb.setVisibility(View.INVISIBLE);
                                t6 = (TextView) findViewById(R.id.nocus);
                                t7 = (TextView) findViewById(R.id.textView14);
                                ca1 = (CardView) findViewById(R.id.titlecard);
                                ca2 = (CardView) findViewById(R.id.datacard);
                                t6.setVisibility(View.VISIBLE);
                                t7.setVisibility(View.INVISIBLE);
                                ca1.setVisibility(View.INVISIBLE);
                                ca2.setVisibility(View.INVISIBLE);
        //                        Toast.makeText(getApplicationContext(), "No Customer Found", Toast.LENGTH_LONG).show();
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
