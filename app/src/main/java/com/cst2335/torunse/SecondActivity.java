package com.cst2335.torunse;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

ImageButton profileButton;


    ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();


                        File whereAmI = getFilesDir();//where your app is installed

                        Bitmap thumbnail = data.getParcelableExtra("data");


                        try {
                            FileOutputStream file = openFileOutput("ProfilePict.png", Context.MODE_PRIVATE);

                            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, file);
                            file.flush();
                            file.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        catch(IOException ioe){
                            Log.w("IOException", "Can't output PNG");
                        }


                        Log.i("Got bitmap", "image");
                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED)
                        Log.i("Got bitmap", "User refused the image");
                }
            } );




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Intent fromPrevious = getIntent();
        String input = fromPrevious.getStringExtra("USERINPUT"); //if "USERINPUT" is not found, return null
        int month = fromPrevious.getIntExtra("MONTH", 0); //if "MONTH" is not found, return 0
        double other = fromPrevious.getDoubleExtra("OTHER INFO", 0.0);//if "OTHERINFO" is not found, return 0.0

        //save data from first page:
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor writer = prefs.edit();
writer.putString("USERINPUT", input);
writer.putInt("MONTH", month);
writer.putFloat("OTHER INFO", (float)other);

writer.apply(); //save to disk

        Button welcome = findViewById(R.id.button2);
        welcome.setOnClickListener( click -> {
            //button was clicked
            Intent next = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);



            cameraResult.launch( next ); //start the transition



        } );

    }
}