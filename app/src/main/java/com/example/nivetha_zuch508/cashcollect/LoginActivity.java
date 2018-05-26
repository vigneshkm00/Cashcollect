package com.example.nivetha_zuch508.cashcollect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private EditText user_name;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    ConnectionDetector cd;
    String email1,pass;
    private static final String TAG = "Main3Activity";
    private static final int REQUEST_CODE = 2415135;
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar2);
        pb.setVisibility(View.INVISIBLE);
//

//        verifyPermission();

        user_name =(EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        cd = new ConnectionDetector(this);
        Button login = (Button) findViewById(R.id.signbtn);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnected()){
                    pb.setVisibility(View.VISIBLE);
                    email1 = user_name.getText().toString();
                    pass = mPasswordView.getText().toString();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("admin_details").child(email1);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try{
                                Admin_det admin_det=dataSnapshot.getValue(Admin_det.class);
                                String na = admin_det.getName().toString();
                                String pa = admin_det.getPassword().toString();
                                int acc = admin_det.getAccess();
                                if(email1.equals(na)&&pass.equals(pa)&&acc==1)
                                {
                                    pb.setVisibility(View.INVISIBLE);
                                    Intent i = new Intent(LoginActivity.this,homepage.class);
                                    startActivity(i);
                                    Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_LONG);
                                    SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("acc_id",email1);
                                    editor.apply();

                                }
                                else
                                {
                                    pb.setVisibility(View.INVISIBLE);
                                    TextView t = (TextView) findViewById(R.id.textView6);
                                    t.setText("*Invalid credential or Access Denied");
                                }
                                user_name.setText("");
                                mPasswordView.setText("");
                            }
                            catch (Exception e)
                            {
                                pb.setVisibility(View.INVISIBLE);
                                TextView t = (TextView) findViewById(R.id.textView6);
                                t.setText("*Invalid credential");}
                            user_name.setText("");
                            mPasswordView.setText("");
                        }




                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    LinearLayout cns = (LinearLayout) findViewById(R.id.loginlayout);
                    Snackbar snackbar = Snackbar
                            .make(cns, "No internet Connection ", Snackbar.LENGTH_LONG);

                    snackbar.show();
                    //Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_LONG).show();
                }


            }
        });



        // Set up the login form.
    }


}

