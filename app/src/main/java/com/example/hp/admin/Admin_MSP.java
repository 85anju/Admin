package com.example.hp.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Admin_MSP extends AppCompatActivity implements View.OnClickListener {

    Intent bb=getIntent();
    private ImageButton BtnGram,BtnPaddy,BtnWheat,BtnSugarcane;

    private TextView tvdisp;

    int newmspint;

    String  TAG="msp";
    String obj="";
    int msp;
    private String newmsp = "";


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mMSPRef = mRootRef.child("msp");
    DatabaseReference mPriceRef=mMSPRef.child("price");/*
    DatabaseReference mAddressRef=mMSPRef.child("address");
    DatabaseReference mRegionRef;*/
    DatabaseReference mCommodityRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__msp);
         tvdisp=(TextView)findViewById(R.id.tvdisp);

        BtnGram = (ImageButton) findViewById(R.id.BtnGram);
        BtnPaddy = (ImageButton) findViewById(R.id.BtnPaddy);
        BtnWheat = (ImageButton) findViewById(R.id.BtnWheat);
        BtnSugarcane = (ImageButton) findViewById(R.id.BtnSugarcane);

        BtnGram.setOnClickListener(this);
        BtnSugarcane.setOnClickListener(this);
        BtnWheat.setOnClickListener(this);
        BtnPaddy.setOnClickListener(this);


    }
    @Override
       public void onClick (View v){

        switch(v.getId()) {
            case R.id.BtnGram:
                 obj="Gram";
                break;
            case R.id.BtnPaddy:
                obj="Paddy";

                break;
            case R.id.BtnWheat:
                obj="Wheat";
                break;
            case R.id.BtnSugarcane:
                 obj="Sugarcane";
                break;


        }


        mCommodityRef=mPriceRef.child(obj);

        mCommodityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                msp=dataSnapshot.getValue(Integer.class);

                Log.d(TAG, Integer.toString(msp));

                tvdisp.setText(obj+"---> ₹"+msp+"/quintal");

                update();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public  void  update(){

        final EditText input = new EditText(Admin_MSP.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);

        final AlertDialog.Builder builder=new AlertDialog.Builder(Admin_MSP.this);
        builder.setTitle("MSP of "+obj+"  is  "+msp)
                .setView(input);
        builder.setMessage("Want to update???")
                .setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                newmsp = input.getText().toString();
                                newmspint=Integer.parseInt(newmsp);
                            // objRef.child(taskId).child("Status").setValue("COMPLETED");
                                mPriceRef.child(obj).setValue(newmspint);

                            }
                        })
                .setNegativeButton("NO",null);
        AlertDialog alert = builder.create();
        alert.show();

    }
}
