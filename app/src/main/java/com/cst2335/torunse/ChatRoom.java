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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


//This makes it a page in your application
public class ChatRoom extends AppCompatActivity {
//use them anywhere in the class:

    //Create an OpenHelper to store data:
    MyOpenHelper myOpener;


    Button submit;
    EditText edit;
    RecyclerView rView;
    MyAdapter theAdapter;   //<<cannot be anonymous<<

    ArrayList<Message> messages = new ArrayList<>();
    SQLiteDatabase theDatabase;

    //need onCreate:
    @Override
    public void onCreate(Bundle p){
        super.onCreate(p);

        //load XML:
        setContentView(R.layout.chatroom);

        //initialize it in onCreate
        myOpener = new MyOpenHelper( this );
        //open the database:
        theDatabase = myOpener.getWritableDatabase();

        //load from the database:
        Cursor results = theDatabase.rawQuery( "Select * from " + MyOpenHelper.TABLE_NAME + ";", null );//no arguments to the query

        //Convert column names to indices:
        int idIndex = results.getColumnIndex( MyOpenHelper.COL_ID );
        int  messageIndex = results.getColumnIndex( MyOpenHelper.COL_MESSAGE);
        int sOrRIndex = results.getColumnIndex( MyOpenHelper.COL_SEND_RECEIVE);
        int timeIndex = results.getColumnIndex( MyOpenHelper.COL_TIME_SENT );

        //cursor is pointing to row -1
        while( results.moveToNext() ) //returns false if no more data
        { //pointing to row 2
            int id = results.getInt(idIndex);
            String message = results.getString( messageIndex );
            int sendOrRecieve = results.getInt(sOrRIndex);
            String time = results.getString( timeIndex);

            //add to arrayList:
            messages.add( new Message( message, time, id ));
        }

        submit = findViewById(R.id.submitButton);
        edit = findViewById(R.id.editText);
        rView = findViewById(R.id.myRecycleView);

        submit.setOnClickListener( click ->{
            String whatIsTyped = edit.getText().toString();
            Date timeNow = new Date(); //when was this code run

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

            String currentDateandTime = sdf.format( timeNow ); //convert date to String

            //insert into database:
            ContentValues newRow = new ContentValues();// like intent or Bundle

            //Message column:
            newRow.put( MyOpenHelper.COL_MESSAGE , whatIsTyped  );

            //Send or receive column:
            newRow.put(MyOpenHelper.COL_SEND_RECEIVE, 1);

            //TimeSent column:
            newRow.put( MyOpenHelper.COL_TIME_SENT, currentDateandTime );

            //now that columns are full, you insert:

            long id = theDatabase.insert( MyOpenHelper.TABLE_NAME, null, newRow ); //returns the id

            Message cm = new Message(whatIsTyped, currentDateandTime, id) ;

            //adding a new message to our history:
            messages.add( cm ); //what is the database id?

            edit.setText("");//clear the text

            //notify that new data was added at a row:
            theAdapter.notifyItemInserted( messages.size() - 1 ); //at the end of ArrayList,
            // call onCreateViewHolder, onBindViewHolder()

        });
         theAdapter = new MyAdapter();
        rView.setAdapter(  theAdapter ) ;
        rView.setLayoutManager(new LinearLayoutManager(this));


    }

    public class MyAdapter extends RecyclerView.Adapter< MyViewHolder > {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            //Load a new row from the layout file:
            LayoutInflater li = getLayoutInflater();
            View thisRow;
            //import layout for a row:

             thisRow = li.inflate(R.layout.sent_message, parent, false);

            return new MyViewHolder( thisRow );
        }


        //initializes a Row at position
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) { //need an ArrayList to hold all the messages.
            //MyViewHolder has time and message textViews

            // What message object is at position:
            Message thisRow = messages.get(position);//

            //                      String object:
            holder.timeView.setText( thisRow.getTimeSent() );//what time goes on row position
            holder.messageView.setText( thisRow.getMessageTyped() );//what message goes on row position
        }

        public int getItemViewType(int position){
            return 5;
//            messages.get(position).getSentOrReceived();//return 1 or 2 depending if it's sent or received
        }

        @Override
        public int getItemCount() {
            return messages.size() ; //how many items in the list
        }
    }

//this holds TextViews on a row:
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView timeView;
        TextView messageView;

        //View will be a ConstraintLayout
        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener( click -> {
                int position = getAbsoluteAdapterPosition();//which row was clicked.
                Message whatWasClicked = messages.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );

                builder.setTitle("Question:")
                        .setMessage("Do you want to delete this:" + whatWasClicked.getMessageTyped())
                        .setNegativeButton("Negative", (dialog, click1)->{ })
                        .setPositiveButton("Positive", (dialog, click2)->{
                            //actually delete something:


                            messages.remove(position);
                            theAdapter.notifyItemRemoved(position);
                            Snackbar.make(submit, "You removed item # " + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", (click4)-> {
                                        messages.add(position, whatWasClicked);
                                        theAdapter.notifyItemInserted(position);
                                        //reinsert into the database
                                        theDatabase.execSQL( String.format( " Insert into %s values (\"%d\", \"%s\", \"%d\", \"%s\" );",
                                          MyOpenHelper.TABLE_NAME      , whatWasClicked.getId()  , whatWasClicked.getMessageTyped() , 1  , whatWasClicked.getTimeSent()));

                                    })
                                    .show();
                            //delete from database:, returns number of rows deleted
                            theDatabase.delete(MyOpenHelper.TABLE_NAME,
                                    MyOpenHelper.COL_ID +" = ?", new String[] { Long.toString( whatWasClicked.getId() )  });
                        }).create().show();
        //                .setNeutralButton("Neutral", (dialog, click3)-> {})

            });

            timeView = itemView.findViewById(R.id.time);
            messageView = itemView.findViewById(R.id.message);
        }
    }

    public class Message{
        String messageTyped;
        String timeSent;
        long id;

        public Message(String messageTyped, String timeSent, long _id) {
            this.messageTyped = messageTyped;
            this.timeSent = timeSent;
            id = _id;
        }

        public String getMessageTyped() {
            return messageTyped;
        }

        public String getTimeSent() {
            return timeSent;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

}
