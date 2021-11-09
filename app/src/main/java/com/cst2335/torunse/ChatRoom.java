package com.cst2335.torunse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

// 1080 x 2220
//  width  height

// 2220 x 1080
//  width  height

// smallest width - > smaller number :1080
//This makes it a page in your application
public class ChatRoom extends AppCompatActivity {
//use them anywhere in the class:

    boolean isTablet = false;

    @Override
    public void onCreate(Bundle p){
        super.onCreate(p);
        setContentView(R.layout.empty_layout); //loads the FrameLayouts


        isTablet = findViewById(R.id.detailsRoom) != null; //find the green FrameLayout

        MessageListFragment chatFragment = new MessageListFragment();//create fragment object
        //load XML just a FrameLayout for loading fragments:

        FragmentManager fMgr = getSupportFragmentManager();

        FragmentTransaction tx = fMgr.beginTransaction();

        //load the fragment:
        tx.add(R.id.fragmentRoom, chatFragment);//load chatFragment into FrameLayout with id fragmentRoom
        tx.addToBackStack( null ); //undo the fragmentTransaction instead of startActivity()
        tx.commit(); // now load it
    }

    public void userClickedMessage(MessageListFragment.Message message, int position) {

        MessageDetailsFragment details = new MessageDetailsFragment(message, position);

        if(isTablet)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsRoom, details ).commit(); // now load it
            }
        else //this is a phone
        {
            FragmentManager fMgr = getSupportFragmentManager();

            FragmentTransaction tx = fMgr.beginTransaction();

            //load the fragment:
            tx.add(R.id.fragmentRoom, details );//load chatFragment into FrameLayout with id fragmentRoom
            tx.addToBackStack( null ); //undo the fragmentTransaction instead of startActivity()
            tx.commit();
        }
/*

        FragmentManager fMgr = getSupportFragmentManager();

        FragmentTransaction tx = fMgr.beginTransaction();

        //load the fragment:
        tx.add(R.id.fragmentRoom,  );//load chatFragment into FrameLayout with id fragmentRoom
        tx.addToBackStack( null ); //undo the fragmentTransaction instead of startActivity()
        tx.commit(); // now load it

 */
    }
}
