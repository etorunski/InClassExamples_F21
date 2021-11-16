package com.cst2335.torunse;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    String temp, minTemp, maxTemp, icon;

    EditText passwordText;
    String serverUrl = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";

    @Override   //public  static void main(String args[])
                //this is our starting point
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent onCreate()
        setContentView( R.layout.activity_main ); //loads XML on screen

        loginButton = findViewById(R.id.button);
        passwordText = findViewById(R.id.editText);
        feedbackText = findViewById(R.id.textView);


        temp = minTemp= maxTemp= icon= "";


        loginButton.setOnClickListener( (click) -> {

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
                     InputStream in = new BufferedInputStream(urlConnection.getInputStream()); //read the information

                     //XML Pull Parser :

                     XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                     factory.setNamespaceAware(false);
                     XmlPullParser xpp = factory.newPullParser();
                     xpp.setInput( in  , "UTF-8");

                    //results = rawQuery()
                    /*
                    while(results.next()){
                    get data from columns:
                    }
                    //read all the SQL data

                     */
                     //loop over the document:
                     int nextEvent;
                     while( (nextEvent = xpp.next())  != xpp.END_DOCUMENT ) //still data to read
                     {
                         switch(nextEvent) //this can be 1 of 5 things:
                         {
                             case XmlPullParser.START_TAG:
                                    String tagName = xpp.getName();
                                    if(tagName.equals(  "temperature"))
                                    { //currently in <temperature>
                                         temp = xpp.getAttributeValue(null, "value");
                                         minTemp = xpp.getAttributeValue(null, "min");
                                         maxTemp = xpp.getAttributeValue(null, "max");
                                    }
                                    else if(tagName.equals("weather"))
                                    { //currently in <weather>
                                         icon = xpp.getAttributeValue(null, "icon");
                                    }
                                 break;
                             case XmlPullParser.TEXT:

                                 break;
                             case XmlPullParser.END_TAG:

                                 break;

                         }
                     }
                     //can only setText on GUI thread:

                     runOnUiThread( () -> {
                         //running on GUI Thread
//need temp, icon, max, min, declare outside the loop:

                         feedbackText.setText(
                                 String.format("Temperature is:%s \n Max temp: %s, \n Min Temp:%s", temp, maxTemp, minTemp));


                         //running on GUI Thread
                     } );


                    }
                     catch(org.xmlpull.v1.XmlPullParserException je){
                         Log.e("XML pull exception", je.getMessage() );
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
