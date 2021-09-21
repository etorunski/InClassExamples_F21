package com.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Switch;

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
      });


        cb.setOnCheckedChangeListener( ( b, c) -> {

           if(c)
               radio.setChecked(true);
           else
               radio.setChecked(false);
        });

    }
}