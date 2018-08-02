package com.example.minihub.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper{

    private static String DB_NAME = "User";


    private static String CREATE_TABLE = "create table" + DB_NAME + "("  ;

    public UserDBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
