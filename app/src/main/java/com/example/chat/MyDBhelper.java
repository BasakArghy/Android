package com.example.chat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_ID = 1;
    private static final  String TABLE_CONTACT ="contacts";
    private static final  String KEY_ID = "id";
    private static final  String KEY_NAME = "name";
    private static final  String KEY_PHONE_NO = "phone_no";


    public MyDBhelper(Context context,String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, DATABASE_ID);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_CONTACT +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_NAME + " TEXT,"+KEY_PHONE_NO+ " TEXT"+")");


//SQLiteDatabase database =this.getWritableDatabase();

//database.close();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACT);
        onCreate(db);

    }

    public void addContact(String name,String phone_no){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_PHONE_NO,phone_no);


        db.insert(TABLE_CONTACT,null,values);

    }


    public ArrayList<ContactModel> fetchContct() {
        SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_CONTACT,null);
      ArrayList<ContactModel> arrContact = new ArrayList<>();

        while (cursor.moveToNext()){
            ContactModel model = new ContactModel();
            model.id = cursor.getInt(0);
            model.name = cursor.getString(1);
            model.phone_no =cursor.getString(2);
            arrContact.add(model);
        }
        return  arrContact;
    }

    public  void updateContact(ContactModel contactModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_PHONE_NO,contactModel.phone_no);

        db.update(TABLE_CONTACT,cv,KEY_ID+" = "+contactModel.id,null);

    }
    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT,KEY_ID+" = ? ",new String[]{String.valueOf(id)});


    }
    public void deleteAllContct() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_CONTACT,null);

        SQLiteDatabase dbc = this.getWritableDatabase();

        while (cursor.moveToNext()){
            dbc.delete(TABLE_CONTACT,KEY_ID+" = ? ",new String[]{String.valueOf(cursor.getInt(0))});

        }

    }
    public void updateaddition(String name,String phone_no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_CONTACT,null);
     int flag =0,Rid;

        while (cursor.moveToNext()){
            if(phone_no.equals(cursor.getString(2))){
                flag=1;
                break;
            }
        }
        if(flag==0){
            addContact(name,phone_no);
        }



    }
}
