package com.example.nivetha_zuch508.cashcollect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Amount extends AppCompatActivity {
    EditText amn;
    Button btn;
    String amount,paid,key,status,month,year;
    DatabaseReference databaseref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);
        amn= (EditText)findViewById(R.id.editText);
        btn = (Button) findViewById(R.id.button7);
        amount = getIntent().getStringExtra("amount");
        paid = getIntent().getStringExtra("paid");
        key = getIntent().getStringExtra("id");
        status = getIntent().getStringExtra("status");
        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("year");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(amn.getText().toString())) {

                String amn1 = amn.getText().toString();
                int a1 = Integer.parseInt(amn1);
                int p1 = Integer.parseInt(paid);
                int x = a1 + p1;
                if ((x > Integer.parseInt(amount))) {
                    Toast.makeText(getApplicationContext(), "Invalid amount", Toast.LENGTH_LONG).show();
                } else {
                    p1 = p1 + a1;

                    String paid1 = Integer.toString(p1);
                    if (paid1.equals(amount)) {
                        status = "Paid";
                    }
                    billupdate artist = new billupdate(key, amount, year, status, paid1, month);
                    databaseref = FirebaseDatabase.getInstance().getReference("customer_bill");
                    databaseref.child(key).child(year).child(month).setValue(artist);
                    Toast.makeText(getApplicationContext(), "Paid", Toast.LENGTH_LONG).show();
                    Intent j = new Intent(Amount.this, bill_list.class);
                    setResult(1, j);
                    finish();
                }


            }
            else
                    Toast.makeText(getApplicationContext(), "please enter the amount", Toast.LENGTH_LONG).show();


            }
        });
    }

}
