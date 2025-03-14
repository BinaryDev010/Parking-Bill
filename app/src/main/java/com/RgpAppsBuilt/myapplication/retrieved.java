package com.RgpAppsBuilt.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class retrieved extends AppCompatActivity {


    private TextView optext;
    private TextView ot;
    private TextView rd;
    private TextView rg;
    ValueEventListener DatabaseReference;

    RadioButton carr;
    RadioButton biker;
    RadioGroup radioGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_retrieved);
        String user=getIntent().getStringExtra("us");
        //int select=radioGroup.getCheckedRadioButtonId();
        //biker=(RadioButton)findViewById(select);
        //carr=(RadioButton)findViewById(select);

        DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Vehicle price list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                rg.setText(snapshot.child("Location").getValue().toString());
if(user.equals("bike"))
{
    //Toast.makeText(retrieved.this, "biked if", Toast.LENGTH_SHORT).show();
    rd.setText(snapshot.child("Bike Price").getValue().toString());
}
else if (user.equals("car")) {

    //Toast.makeText(retrieved.this, "carif", Toast.LENGTH_SHORT).show();
    rd.setText(snapshot.child("Car Price").getValue().toString());

}

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });





        optext=findViewById(R.id.retname);
        ot=findViewById(R.id.etno);
        rd=findViewById(R.id.rd);
        rg=findViewById(R.id.rg);
        String username=getIntent().getStringExtra("nameuser");
        String vehno=getIntent().getStringExtra("nouser");
        String rbb= getIntent().getStringExtra("rb");
        String rcc= getIntent().getStringExtra("rc");




//        rd.setText(rbb);
//
//        rd.setText(rcc);
        optext.setText(username);
        ot.setText(vehno);

    }



}