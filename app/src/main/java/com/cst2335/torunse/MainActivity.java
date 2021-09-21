package com.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override   //public  static void main(String args[])
                //this is our starting point

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent onCreate()
        setContentView( R.layout.activity_main ); //loads XML on screen

        ImageButton btn = findViewById(R.id.button3);
        btn.setOnClickListener( (vw) -> {
            int width = btn.getWidth();
            int height = vw.getHeight();

        });
    }
}