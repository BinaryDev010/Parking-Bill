package com.RgpAppsBuilt.myapplication;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class settings extends AppCompatActivity {

    Button save;
    TextInputEditText  bikep,carp,loc;
   // int bikepr,carpr;


    private  FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Vehicle price list");

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);


        broadcastReceiver = new networkBroardcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        bikep=findViewById(R.id.setbikepData);
        carp=findViewById(R.id.setcarpData);
        loc=findViewById(R.id.setlocdata);

        save = findViewById(R.id.save);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bikep.getText().toString().isEmpty()) {
                    Toast.makeText(settings.this, "!!!data cannot be empty...", Toast.LENGTH_SHORT).show();
                    bikep.requestFocus();
                } else if (carp.getText().toString().isEmpty()) {
                    Toast.makeText(settings.this, "!!!data cannot be empty...", Toast.LENGTH_SHORT).show();
                    carp.requestFocus();
                } else if (loc.getText().toString().isEmpty()) {
                    Toast.makeText(settings.this, "!!!data cannot be empty...", Toast.LENGTH_SHORT).show();
                    loc.requestFocus();
                } else {
                    String bikeprice = bikep.getText().toString();
                    String carprice = carp.getText().toString();
                    String locc = loc.getText().toString();

                    HashMap<String, String> usermap = new HashMap<>();

                    usermap.put("Bike Price", bikeprice);
                    usermap.put("Car Price", carprice);
                    usermap.put("Location", locc);

                    root.setValue(usermap);

                    Toast.makeText(settings.this, "Saved", Toast.LENGTH_SHORT).show();


                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}