package com.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override   //public  static void main(String args[])
                //this is our starting point

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent onCreate()
        //nothing is loaded
//        findViewById()//always fail here


        setContentView( R.layout.activity_main ); //loads XML on screen

        //now your xml is loaded
        TextView topView = findViewById(R.id.helloTextView); //must match XML id
        String oldText = topView.getText().toString();
        topView.setText("Java put this here");

        EditText bottom = findViewById(R.id.bottomtext);

        Button btn = findViewById(R.id.button);
        btn.setText("The view was previously "+ oldText); //on startup
        btn.setOnClickListener(  (  voidsfhguiaerhgkhersi ) ->
        {
                //when  you click
           //text is empty
            topView.setText("Edit text has " +  bottom.getText().toString());

        }   ); //OnCLickListener goes in here

        /*btn.setOnClickListener(new View.OnClickListener() { //anonymous class
            @Override
            public void onClick(View v) {
                topView.setText("Edit text has " + bottom.getText());
            }
        });*/

    }
}