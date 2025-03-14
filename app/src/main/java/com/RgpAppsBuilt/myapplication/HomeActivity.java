package com.RgpAppsBuilt.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {


    private Button print,hist,sett;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        //img= findViewById(R.id.tilak);
        print = findViewById(R.id.filldetail);
        hist = findViewById(R.id.btnhisto);
        sett = findViewById(R.id.btnsetti);


        print.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               Intent i =new Intent(HomeActivity.this,receipt.class);
                startActivity(i);
            }
       });

//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(HomeActivity.this, "|| जय श्री नारायण ||", Toast.LENGTH_LONG).show();
//            }
//        });


        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(HomeActivity.this,history.class);
                startActivity(i);
            }
        });

        sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(HomeActivity.this,settings.class);
                startActivity(i);
            }
        });

    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.optionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.contact)
        {
            startActivity(new Intent(HomeActivity.this, contact.class));
        }
//        if (id==R.id.set){
//            startActivity(new Intent(HomeActivity.this,settings.class));
//        }
//
//        if(id==R.id.history){
//            startActivity(new Intent(HomeActivity.this, history.class));
//        }



//        if (id==R.id.logout){
//            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
//            builder.setTitle("Logout");
//            builder.setMessage("Are you sure you want to logout ?");
//            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    finish();
//                }
//            });
//            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            });
//            builder.show();
//        }
        return super.onOptionsItemSelected(item);
    }



}