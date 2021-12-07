package com.cst2335.torunse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


/** This page lets the user type in a pass..
 * @author Eric Torunski
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    /** This textView shows the feedback on the screen*/
    TextView feedbackText;

    /** This is the loginButton at the bottom of the screen*/
    Button loginButton;

    EditText passwordText;
    String serverUrl = "https://www.carboninterface.com/api/v1/estimates";//  //"https://api.pexels.com/v1/search?query=%s";

    private SensorManager mSensorManager;
    private Sensor mSensor;


    class StepCountListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            //the reading has changed, taken more steps
                float [] reading = event.values;
                Log.i("Step count:", Float.toString( reading[0] ));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//more for GPS sensors where accuracy changes
        }
    }


    @Override   //public  static void main(String args[])
                //this is our starting point
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent onCreate()

        //connect to the sensors:
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD );//for step counter

mSensorManager.registerListener( new StepCountListener() , mSensor,  SensorManager.SENSOR_DELAY_NORMAL );


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView( R.layout.activity_main ); //loads XML on screen

        loginButton = findViewById(R.id.button);
        passwordText = findViewById(R.id.editText);
        feedbackText = findViewById(R.id.textView);

        loginButton.setOnClickListener( (click) -> {

            Vibrator v = (Vibrator)MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds​
            //v.vibrate(2500);
            int [] amplitudes = new int[] {255, 0,     64,   128}; //max 255
            long [] pattern = new long[] {1500, 1500, 1000, 1000};
v.vibrate(  VibrationEffect.createWaveform(pattern, amplitudes, -1) );

            String cityName = passwordText.getText().toString(); // ""
             Executor newThread = Executors.newSingleThreadExecutor();
                               //Run function
             newThread.execute( ( ) -> {
                 //done on a second processor:
                 try {
                    //encodes the string
                    String fullUrl =  String.format(serverUrl, URLEncoder.encode(cityName, "UTF-8"));

                     //Must be done on other thread:
                     URL url = new URL(fullUrl); //build the server connection
                     HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); //connect to server
                     urlConnection.setRequestMethod("POST");

String distanceFromUser = "50";
String API_KEY = "";
String dataString = String.format("{\"type\":\"vehicle\", \"distance_unit\": \"km\",\"distance_value\":\"%s\",\"vehicle_model_id\":\"%s\"}",
                        distanceFromUser, "8a22e500-10df-49c9-abad-9d84e2156cae");

//CARBON DIOXIDE:
urlConnection.setRequestProperty("Authorization", "Bearer PaMSiTFKUSgZeq4DmPXAmA"  );
urlConnection.setRequestProperty("Content-Type", "application/json"  );
                     OutputStream outStream = urlConnection.getOutputStream();
                     OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
                     outStreamWriter.write(dataString );
                     outStreamWriter.flush();
                     outStreamWriter.close();
                     outStream.close();
//End of CARBON DIOXIDE Exmample


// PEXELS:  urlConnection.setRequestProperty("Authorization", "TOKEN"  );


      //owlbot: urlConnection.setRequestProperty("Authorization", "token 15c881db0d7d5f48aa8ae39ab02a65119e2f7b6d"  );


                     InputStream in = new BufferedInputStream(urlConnection.getInputStream()); //read the information

                     String jsonString = (new BufferedReader(
                             new InputStreamReader(in, StandardCharsets.UTF_8)
                     )).lines()
                             .collect(Collectors.joining("\n"));

                     JSONObject theDocument = new JSONObject( jsonString );

/*
                     //for covid:
                     JSONArray dataArray = theDocument.getJSONArray("data");
                     for(int i = 0; i < dataArray.length(); i++)
                     {
                         JSONObject objAtI = dataArray.getJSONObject(i);

                     }

 */

                     int i = 0;

                  /*   JSONObject mainObj = theDocument.getJSONObject("main");
                     double temp = mainObj.getDouble("temp");
                    JSONArray weatherArray = theDocument.getJSONArray("weather");
                    JSONObject pos0Obj = weatherArray.getJSONObject(0);
                    String icon = pos0Obj.getString( "icon");

                    //can only setText on GUI thread:

                     runOnUiThread( () -> {
                         //running on GUI Thread

                         feedbackText.setText("Temperature is:" + temp);


                         //running on GUI Thread
                     } );

*/
                    }
                     catch(JSONException je){
                         Log.e("JSONException", je.getMessage() );
                     }
                     catch(IOException ioe){
                         Log.e("IOException", ioe.getMessage() );
                     }

                 //end of the thread
             } ); //end of newThread.execute()


        });
    }

    /** This function checks if the password has Upper case, Lower case, Number, and Special Character.
     * Write more details in the longer description that show up at the bottom of the page.
     * gegae
     * aewgaewgheaw
     * rahawreha
     * serhreahser
     * serthjerwah
     *
     * @param password Non-null string of the user typed in the editText.
     * @return True if all conditions are met, false otherwise.
     */
    private boolean checkPasswordComplexity(@NonNull String password) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        //start looping:
        for(int i = 0; i < password.length(); i++)
        {
            char c = password.charAt(i);
            Log.i("Looking at char:", ""+c );
            if(Character.isLowerCase(c))
                foundLowerCase = true;
            else if( isSpecialCharacter( c ) )
            {
                foundSpecial = true;
            }
        }
        if(!foundLowerCase)
            Toast.makeText( MainActivity.this, "Missing lower case character", Toast.LENGTH_LONG).show();
        /*if(!foundSpecial)
            passwordText.setError("Missing special character");*/
        //if anything is false, then it's not in the password;
        return foundLowerCase && foundSpecial ;// && foundNumber && foundUpperCase;
    }

    /** This returns true if c is one of { #$%^&*!@? }, false otherwise
     *
     * @param c The character we are checkin g
     * @return true if c is one of { #$%^&*!@? }, false otherwise
     */
    private boolean isSpecialCharacter(char c) {
        switch(c)
        {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            //unreachable because you return above;
            default:
                return false;
        }
    }
}
