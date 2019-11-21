package com.example.sprint.sqlitefuns1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ContactOpenHelper extends SQLiteOpenHelper{
    static final String TAG = "SQLiteFunTag";

    static final String DATABASE_NAME = "contactsDatabase";
    static final int DATABASE_VERSION = 1;

    static final String TABLE_CONTACTS = "tableContacts";
    static final String ID = "_id"; //name id this way for use with adapters later
    static final String NAME = "name";
    static final String PHONE_NUMBER = "phoneNumber";
    static final String IMAGE_RESOURCE = "imageResource";

    public ContactOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION); //what sql needs to open our database
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //this is where we create our database tables
        // this method is only called once... called after
        //  first call to getWriteableDatabase()
        //we consrtuct strings to represent SQL(structured query language)
        // commands/statements

        //CREATE TABLE tableContacts(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phoneNumber TEXT, imageResource INTEGER)
        String sqlCreate = "CREATE TABLE " + TABLE_CONTACTS + "( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " + PHONE_NUMBER + " TEXT, " +
                IMAGE_RESOURCE + " INTEGER)";
        Log.d(TAG, "onCreate: " + sqlCreate);
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertContact(Contact contact){
        //INSERT INTO tableContacts VALUES(null, 'Spike the Bulldog', '509-509-5095', -1)
        String sqlInsert = "INSERT INTO " + TABLE_CONTACTS + " VALUES(null, '" + contact.getName() + "', '" +
                contact.getPhoneNumber() + "', " + contact.getImageResourceId() + ")";
        //get a reference to the database that is writable
        SQLiteDatabase db = getWritableDatabase();
        Log.d(TAG, "insertContact: " + sqlInsert);
        db.execSQL(sqlInsert);
        //always close the writeable database
        db.close();
    }

    public Cursor getSelectAllContactsCursor(){
        //SELECT *  FROM  tableContacts
        String sqlSelect = "SELECT * FROM " + TABLE_CONTACTS;
        //get a reference to a database
        SQLiteDatabase db = getReadableDatabase();
        Log.d(TAG, "getSelectAllContactsCursor: " + sqlSelect);
        //use rawQuery() to execute the select query
        //queries return a Cursor reference
        //we will walk through the query result set using the Cursor
        Cursor cursor = db.rawQuery(sqlSelect, null);
        //dont close the database!!  the cursor needs it open
        return cursor;
    }

    //for debug purposes only!!
    //for PA7, you will use SimpleCursorAdapter
    //to wire a list view to a database
    public List<Contact> getSelectAllContactsList(){
        List<Contact> contactList = new ArrayList<>();

        //goal: walk through each record with a select all cursor
        //create a Contact for the record
        //add the Contact to the contactLast
        Cursor cursor = getSelectAllContactsCursor();
        //cursor starts "before" the record in case there is no first record
        while (cursor.moveToNext()) {    //return false when there are no more records
            //extract values
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phoneNumber = cursor.getString(2);
            int imageResource = cursor.getInt(3);
            Contact contact = new Contact(id, name, phoneNumber, imageResource);
            contactList.add(contact);
        }
        return contactList;
    }

    public void updateContactById(int id, Contact newContact){
        //UPDATE tablecontacts SET name='SPIKE', phonenumber='208' WHERE _id=1
        String sqlUpdate = "UPDATE " + TABLE_CONTACTS + " SET " + NAME + "='" +
                newContact.getName() + "', " + PHONE_NUMBER + "='" +
                newContact.getPhoneNumber() + "' WHERE " + ID + "=" + id;
        Log.d(TAG, "updateContactById " + sqlUpdate);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    public void deleteAllContacts(){
        //DELETE FROM tableContacts
        String sqlDelete = "DELETE FROM " + TABLE_CONTACTS;
        Log.d(TAG, "deleteAllContacts" + sqlDelete);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }
}
