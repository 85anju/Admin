package com.example.hp.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void nxtFP(View view)
    {
        Intent aa = new Intent(MainActivity.this, Admin_FP.class);
        startActivity(aa);
    }
  public void nxtMSP(View view)
    {
        Intent bb = new Intent(MainActivity.this, Admin_MSP.class);
        startActivity(bb);
    }




}
