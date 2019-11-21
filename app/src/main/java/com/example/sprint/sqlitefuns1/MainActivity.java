package com.example.sprint.sqlitefuns1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "SQLiteFunTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database: like an excel workbook
        //each sheet is like a database table
        //each row has a unique id(primary key for a table)
        //each column has an identifier as well (fields)

        //2 classes to know
        //1. SQLiteOpenHelper: subclass this and define some database operations
        //2. SQLiteDatabase: reference to the database(read or write)

        ListView listView = new ListView(this);
        setContentView(listView);

        ContactOpenHelper openHelper = new ContactOpenHelper(this);
        Contact contact = new Contact("Spike the Bulldog", "509-509-5095");
        openHelper.insertContact(contact);

        Contact newContact = new Contact("SPIKE", "208-208-2082");
        openHelper.updateContactById(1, newContact);

//        openHelper.deleteAllContacts();

        //DEBUG ONLY
//        List<Contact> contactList = openHelper.getSelectAllContactsList();
//        Log.d(TAG, "onCreate: " + contactList);

        //create a simple cursor adapter to wire our list view to our database (e.g. cursor)
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                openHelper.getSelectAllContactsCursor(),
                //parallel arrays
                new String[] {ContactOpenHelper.NAME},  //names of columns to get data from
                new int[] {android.R.id.text1}, //and insert it into the textView with id text1
                0
        );
        listView.setAdapter(cursorAdapter);
    }
}
