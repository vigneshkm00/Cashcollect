package com.example.nivetha_zuch508.cashcollect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

public class Amount extends AppCompatActivity {
    EditText amn;
    Button btn;
    String amount,paid,key,status,month,year,ts,amn1;
    String y,m;
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
        ts = getIntent().getStringExtra("timest");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(amn.getText().toString())) {

              amn1 = amn.getText().toString();
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
                    billupdate artist = new billupdate(key, amount, year, status, paid1, month,ts);
                    SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    String admin = sharedPreferences.getString("acc_id","");
                    databaseref = FirebaseDatabase.getInstance().getReference(admin).child("customer_bill");
                    y = getIntent().getStringExtra("year");
                    m = getIntent().getStringExtra("timest");
                    databaseref.child(key).child(year).child(y+m+month).setValue(artist);
                    Toast.makeText(getApplicationContext(), "Paid", Toast.LENGTH_LONG).show();
                    Intent j = new Intent(Amount.this, bill_list.class);
                    String y;
                    Calendar c = Calendar.getInstance();
                    int a = c.get(Calendar.YEAR);
                    int b = c.get(Calendar.DATE);
                    int d = c.get(Calendar.HOUR);
                    int e = c.get(Calendar.MINUTE);
                    int f = c.get(Calendar.SECOND);
                    String g = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                    String message = "&message=" + "Recharge of Rs."+amn1+" for "+month+" is successful on "+b+g+","+a+" "+d+":"+e+":"+f+" towards your cable connection with customer ID:"+key+".";
                    Toast.makeText(getApplicationContext(), "Paid:"+message, Toast.LENGTH_LONG).show();
                    y = sendSms();
                    Toast.makeText(getApplicationContext(), "Sms Sent:"+y, Toast.LENGTH_LONG).show();
                    setResult(1, j);
                    finish();
                }


            }
            else
                    Toast.makeText(getApplicationContext(), "please enter the amount", Toast.LENGTH_LONG).show();


            }
        });
    }
    public String sendSms() {
        try {
            // Construct data
            Calendar c = Calendar.getInstance();
            int a = c.get(Calendar.YEAR);
            int b = c.get(Calendar.DATE);
            int d = c.get(Calendar.HOUR);
            int e = c.get(Calendar.MINUTE);
            int f = c.get(Calendar.SECOND);
            String g = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            String apiKey = "apikey=" + "mPIkes096PU-uLrqn7LefDmrwAMG3R0th6HdnEJajq";
            String message = "&message=" + "Recharge of Rs."+amn1+" for the Month:"+month+" "+year+" is successful on "+b+g+","+a+" "+d+":"+e+":"+f+" towards your cable connection with customer ID:"+key+".";
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + "91"+key;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            Toast.makeText(getApplicationContext(),"Error SMS:"+e,Toast.LENGTH_LONG).show();
            return "Error "+e;
        }
      /*  try {
            // Construct data
            String apiKey = "apikey=" + "M75sYdRhU+o-veY4GL4RXoXTyrjyhwao6OQ6Ut6O26";
            String message = "&message=" + "The Bill Amount for Customer ID:"+key+"is Rs."+amount+".paid:"+amn1+" Status:"+status+".";
            String sender = "&sender=" + "vignesh";
            String numbers = "&numbers=" + "91"+key;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }*/
    }
}
