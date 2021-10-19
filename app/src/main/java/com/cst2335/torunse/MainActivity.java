package com.cst2335.torunse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/** This page lets the user type in a pass...
 *
 * @author Eric Torunski
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    /** This textView shows the feedback on the screen*/
    TextView feedbackText;

    /** This is the loginButton at the bottom of the screen*/
    Button loginButton;

    EditText passwordText;

    @Override   //public  static void main(String args[])
                //this is our starting point
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent onCreate()
        setContentView( R.layout.activity_main ); //loads XML on screen

        loginButton = findViewById(R.id.button);
        passwordText = findViewById(R.id.editText);
        feedbackText = findViewById(R.id.textView);

        loginButton.setOnClickListener( (click) -> {
            String password = passwordText.getText().toString(); // ""

            if( checkPasswordComplexity( password ) )
                feedbackText.setText("Your password is complex enough");

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
        if(!foundSpecial)
            passwordText.setError("Missing special character");
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