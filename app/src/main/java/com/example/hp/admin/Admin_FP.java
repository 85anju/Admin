package com.example.hp.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_FP extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Intent aa=getIntent();


    String dispcom="",disparr="",dispremain="",dispprice="";

    String  val="";
        private EditText Edcity;
        private  EditText Edcomm;
        private Button Btnsubmit;
        PriceDetail dt;
        private  Button remain,arr,tvprice;
        String [] commd={"Select commodity","kerosene","wheat","sugar","rice"};

        String newfp="",aaa="";

         String mycity="",mycomm="";
        String panipuri="";


        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mFPRef = mRootRef.child("fairprice");

        DatabaseReference mcityRef;
        DatabaseReference mcommref;
        DatabaseReference mvalref;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin__fp);


            Spinner s=(Spinner) findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.State,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);
            s.setOnItemSelectedListener(this);
            Toast.makeText(Admin_FP.this, ""+mycity, Toast.LENGTH_SHORT).show();



            Spinner s1=(Spinner) findViewById(R.id.spinner3);
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,commd);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s1.setAdapter(aa);
            Toast.makeText(Admin_FP.this, ""+mycomm, Toast.LENGTH_SHORT).show();

            s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override

                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(Admin_FP.this, ""+position, Toast.LENGTH_SHORT).show();


                    switch (position)
                    {
                        case 0:break;
                        case 1:mycomm="kerosene"; break;
                        case 2:mycomm="wheat"; break;
                        case 3:mycomm="sugar"; break;
                        case 4:mycomm="rice"; break;
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
          arr=(Button) findViewById(R.id.arr);
            remain=(Button) findViewById(R.id.remain);
            tvprice=(Button) findViewById(R.id.tvprice);

            arr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpupdate("arrived",disparr);

                }
            });


            tvprice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpupdate("price",dispprice);

                }
            });

            remain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpupdate("remained",dispremain);

                }
            });




            Btnsubmit= (Button) findViewById(R.id.Btnsubmit);
            dt = new PriceDetail();

            Btnsubmit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                   // Toast.makeText(Admin_FP.this, mycomm+"in btn submit"+mycity, Toast.LENGTH_SHORT).show();

                    getdata();
                }
            });

        }


        public void getdata(){



            mcityRef = mRootRef.child("fareprice");


            mcityRef.child(mycity).child(mycomm).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot ) {
                    PriceDetail price = dataSnapshot.getValue(PriceDetail.class);

                        dispcom=price.getCommodity();
                        disparr=price.getArrived();
                        dispprice=price.getPrice();
                        dispremain=price.getRemained();

                         arr.setText("ARRIVED    "+disparr);
                         remain.setText("REMAINED    "+dispremain);
                     //    comm.setText("COMMODITY    "+dispcom);
                          tvprice.setText("PRICE    "+dispprice);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}

            });


        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(Admin_FP.this, ""+position, Toast.LENGTH_SHORT).show();

            switch (position)
            {
                case 0:break;
                case 1:mycity="Bihar"; break;
                case 2:mycity="Haryana"; break;
                case 3:mycity="Karnataka"; break;
                case 4:mycity="Kerala"; break;
                case 5:mycity="Maharashtra"; break;
                case 6:mycity="Manipur"; break;
            }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public  void  fpupdate(final String temp , String ccc){

        final EditText fpinput = new EditText(Admin_FP.this);
        fpinput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);

        //Toast.makeText(Admin_FP.this, "temp"+temp, Toast.LENGTH_SHORT).show();


        final AlertDialog.Builder builder=new AlertDialog.Builder(Admin_FP.this);
        builder.setTitle(temp+"   of "+mycomm+"  in "+mycity+"  is  "+ccc)
                .setView(fpinput);
        builder.setMessage("Want to update???")
                .setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                newfp = fpinput.getText().toString();
                                 mRootRef.child("fareprice").child(mycity).child(mycomm).child(temp).setValue(newfp);

                                // objRef.child(taskId).child("Status").setValue("COMPLETED");
                                // mCommodityRef.setValue(newmsp);
                                 }
                        })
                .setNegativeButton("NO",null);
        AlertDialog alert = builder.create();
        alert.show();

    }

}


