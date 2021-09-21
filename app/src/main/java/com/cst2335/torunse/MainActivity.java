package com.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override   //public  static void main(String args[])
                //this is our starting point

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent onCreate()
        setContentView( R.layout.activity_main ); //loads XML on screen

        CheckBox cb =        findViewById(R.id.check);


        RadioButton radio = findViewById(R.id.radio);
        Switch sw = findViewById(R.id.sw);

        sw.setOnCheckedChangeListener( ( btn, onOrOff) -> {
            radio.setChecked(onOrOff);

            Toast.makeText(MainActivity.this, "You clicked on switch", Toast.LENGTH_LONG).show();
      });


        cb.setOnCheckedChangeListener( ( b, c) -> {
            Toast.makeText(MainActivity.this, "You clicked on checkbow", Toast.LENGTH_SHORT).show();
           if(c)
               radio.setChecked(true);
           else
               radio.setChecked(false);
        });

    }
}