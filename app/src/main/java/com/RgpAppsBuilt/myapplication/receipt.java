package com.RgpAppsBuilt.myapplication;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class receipt extends AppCompatActivity {

    private TextInputEditText ownname ,vehno;

    private RadioButton biker,carr;
    private Button print;
    private RadioGroup radioGroup;
    TextInputEditText mblnol;
    String bp,cp;
    private Object DatabaseReference;
    private String bikeprice;
    private String carprice;
    private  String loca;

    String user;

    private String date;
    private String time;

    private BroadcastReceiver broadcastReceiver;

    TextView allotno;

    int value=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_receipt);

        broadcastReceiver = new networkBroardcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        ownname = findViewById(R.id.ownameData);
        vehno = findViewById(R.id.vehnoData);
        biker=(RadioButton)findViewById(R.id.radiobike);
        carr=(RadioButton)findViewById(R.id.radiocar);
        print=findViewById(R.id.print);
        setRadioGroup((RadioGroup)findViewById(R.id.radio));
        mblnol=findViewById(R.id.mblnoData);

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mblnol.getText().toString().isEmpty())
                {

                    Toast.makeText(receipt.this,"!!! Please enter mobile number",Toast.LENGTH_SHORT).show();
                    mblnol.requestFocus();
                }
                else if(mblnol.getText().toString().length()>10 || mblnol.getText().toString().length()<10)
                {

                    Toast.makeText(receipt.this,"!!! Invalid mobile number",Toast.LENGTH_SHORT).show();
                    mblnol.requestFocus();
                }
                else if(vehno.getText().toString().isEmpty())
                {

                    Toast.makeText(receipt.this,"!!! Please enter vehicle number",Toast.LENGTH_SHORT).show();
                    vehno.requestFocus();
                }
                else if(vehno.getText().toString().length()> 10)
                {

                    Toast.makeText(receipt.this,"!!! Invalid Vehicle Number",Toast.LENGTH_SHORT).show();
                    vehno.requestFocus();

                }
                else if (ownname.getText().toString().isEmpty())
                {
                    Toast.makeText(receipt.this,"!!! Please enter vehicle owner number",Toast.LENGTH_SHORT).show();
                    ownname.requestFocus();
                }
                else if(!biker.isChecked() && !carr.isChecked())
                {
                    Toast.makeText(receipt.this,"!!! Please select vehicle type",Toast.LENGTH_SHORT).show();
                    radioGroup.requestFocus();
                }

                else{

                    error();
                }

            }
        });



    }

    private  void  error()
    {
            // to store data in mysql
        String url="http://192.168.136.150/test/test.php";
        RequestQueue myrequest= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mblnol.setText("");
                ownname.setText("");
                vehno.setText("");
                //user="";
            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mblnol.setText("");
                        ownname.setText("");
                        vehno.setText("");
                        //user="";
                        //Toast.makeText(getApplicationContext(),error.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<String,String>();
                map.put("Mobile_number",mblnol.getText().toString());
                map.put("owner_name",ownname.getText().toString());
                map.put("vehicle_number",vehno.getText().toString());
                map.put("vehicle_type",user.toString());
                map.put("location",loca.toString());
                map.put("time",time.toString());
                map.put("date",date.toString());
                return map;
            }

        };
        myrequest.add(stringRequest);
        // mysql data store ends here

        rrt(); // method of database retrive

        String o=ownname.getText().toString();
        String e=vehno.getText().toString();
        String b=biker.getText().toString();
        String c=carr.getText().toString();
        //String user1 = user;
        String m=mblnol.getText().toString();


        String mblnod = mblnol.getText().toString();
        String ownnamed = ownname.getText().toString();
        String vehnod = vehno.getText().toString();


        datee();
        timee();



        if(ContextCompat.checkSelfPermission(receipt.this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(receipt.this,new String[]{Manifest.permission.SEND_SMS},1);
        }

        Intent intent = new Intent(receipt.this,retrieved.class);
        if(biker.isChecked()){
            intent.putExtra("rb",b);
            user="bike";
            intent.putExtra("us",user);
            String dum="PARKING BOOK"+"\nowner name: "+o+"\n"+"vehicle number: "+e+"\n"+"vehicle: "+user+"\n"+"price: "+bikeprice+"\n"+"location: "+loca;
            SmsManager.getDefault().sendTextMessage(m,null,dum,null,null);
            Toast.makeText(receipt.this, "sent", Toast.LENGTH_SHORT).show();
        }
        if(carr.isChecked()){
            intent.putExtra("rc",c);
            user="car";
            intent.putExtra("us",user);
            String dum="PARKING BOOK"+"\nowner name: "+o+"\n"+"vehicle number: "+e+"\n"+"vehicle: "+user+"\n"+"price: "+carprice+"\n"+"location: "+loca;
            SmsManager.getDefault().sendTextMessage(m,null,dum,null,null);
            Toast.makeText(receipt.this, "sent", Toast.LENGTH_SHORT).show();
        }

        intent.putExtra("nameuser",o);
        intent.putExtra("nouser",e);


        startActivity(intent);
    }



    private String datee() { // to get system date
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            date=new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(new Date());
        }
        return date;
    }












    private String timee() { // to get system time
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             time=new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        }
        return time;
    }











    // data base retrieve
    private void rrt() {
        DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Vehicle price list").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //biker.setText(snapshot.child("Location").getValue().toString());


                //Toast.makeText(retrieved.this, "biked if", Toast.LENGTH_SHORT).show();
                bikeprice=(snapshot.child("Bike Price").getValue().toString());



                //Toast.makeText(retrieved.this, "carif", Toast.LENGTH_SHORT).show();
                carprice=(snapshot.child("Car Price").getValue().toString());

                loca = (snapshot.child("Location")).getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    // database retrieve ends here










    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }





}  //main