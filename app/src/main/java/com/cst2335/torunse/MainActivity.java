package com.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public final static String TAG ="MainActivity";

    @Override     //first called
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent onCreate()

        Log.i(TAG, "In onCreate, just creating the objects");
       setContentView( R.layout.activity_main ); //loads XML on screen


        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String previous = prefs.getString("USERINPUT", "NONE");


        //now your xml is loaded
        TextView topView = findViewById(R.id.helloTextView); //must match XML id


        EditText editText = findViewById(R.id.bottomtext);
editText.setText(previous);


        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(  (  click ) ->
        {
            String userTyped = editText.getText().toString();
            topView.setText("Edit text has " +  userTyped);


                                        //Where you are     //where we're going
            Intent nextPage = new Intent(MainActivity.this,   SecondActivity.class  );

            nextPage.putExtra("USERINPUT", userTyped);
            nextPage.putExtra("MONTH", 10);
            nextPage.putExtra("OTHER INFO", 3.14);


            //Make the transition:
            startActivity(    nextPage  );


        }   ); //OnCLickListener goes in here

    }

    @Override //screen is visible but not responding
    protected void onStart() {
        super.onStart();



        Log.d(TAG, "In onStart, visible but not responding");
    }

    @Override //screen is visible but not responding
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "In onResume");
    }

    @Override //screen is visible but not responding
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "In onPause");
    }

    @Override //not visible
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "In onStop");
    }

    @Override  //garbage collected
    protected void onDestroy() {
        super.onDestroy();
    }
}