package com.example.android.vitbus1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHandler extends SQLiteOpenHelper {

    private static String key_id="reg_no";
    private static String key_name="name";
    private static String key_email="email";
    private static String key_password="password";
    private static String key_busno="bussno";

    private static String key_table="user_table";

    public MyDBHandler( Context context,String name,SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_user="CREATE TABLE "+key_table+"("+key_id+"INTEGER PRIMARY KEY,"+key_name+" TEXT ,"+key_email+" TEXT, "+key_password+" TEXT, "+key_busno+" TEXT "+" );";
        db.execSQL(create_user);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insertUser(User user)
    {
        int i=0;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(key_id,user.getRegNo());
        contentValues.put(key_name,user.getName1());
        contentValues.put(key_email,user.getEmail1());
        contentValues.put(key_password,user.getPassword());
        contentValues.put(key_busno,user.getBusNo());
        sqLiteDatabase.insert(key_table,null,contentValues);
        i=1;

        return i;
    }
}
