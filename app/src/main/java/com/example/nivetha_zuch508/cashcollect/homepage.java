package com.example.nivetha_zuch508.cashcollect;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class homepage extends AppCompatActivity {

    EditText ed1,ed2,ed3,ed4,ed5;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
       /* ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText)findViewById(R.id.amounttext);
        ed4 = (EditText) findViewById(R.id.editText4);
        ed5 = (EditText)findViewById(R.id.editText5);
        b1 = (Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Construct data
                    String data = "";
                    data += "username=" + URLEncoder.encode("vigneshkm00", "ISO-8859-1");
                    data += "&password=" + URLEncoder.encode("Geetha@123", "ISO-8859-1");
                    data += "&message=" + URLEncoder.encode("hello", "ISO-8859-1");
                    data += "&want_report=1";
                    data += "&msisdn=919962426183";// relace with the number

                    // Send data
                    URL url = new URL("http://bulksms.vsms.net:5567/eapi/submission/send_sms/2/2.0");

                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();

                    // Get the response
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        // Print the response output...
                        System.out.println(line);
                        Toast.makeText(getApplicationContext(),"message"+line,Toast.LENGTH_LONG).show();
                    }
                    wr.close();
                    rd.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"eror"+e,Toast.LENGTH_LONG).show();
                }
            }
        });*/
        Button b2 = (Button)findViewById(R.id.button4);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homepage.this,Main2Activity.class);
                startActivity(i);

            }
        });
        Button b3 = (Button)findViewById(R.id.updatecashbtn);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homepage.this,updatecash.class);
                startActivity(i);
            }
        });
        Button b4 = (Button) findViewById(R.id.paybtn);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homepage.this,MainActivity.class);
                startActivity(i);
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}