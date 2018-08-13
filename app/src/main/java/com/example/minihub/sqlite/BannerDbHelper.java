package com.example.minihub.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.minihub.AppConfig;

public class BannerDbHelper extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "Banner";
    public static final String _ID = "_id";
    public static final String IMAGE_PATH = "image_path";
    public static final String URL = "url";
    public static final String TITLE = "title";

    private static final String TEXT_TYPE = " TEXT"; //前面要有个空格，在sql语句里隔开了列名和类型
    private static final String COMMA_SEP = ",";

    //意外发现android studio还会提示sql语句的错误。。。
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(" + _ID  + " INTEGER PRIMARY KEY,"
            + IMAGE_PATH + TEXT_TYPE + COMMA_SEP
            + TITLE + TEXT_TYPE + COMMA_SEP
            + URL + TEXT_TYPE
            + ")";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public BannerDbHelper(Context context){
        super(context, AppConfig.APP, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(ArticleSql.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE);
        sqLiteDatabase.execSQL(ArticleSql.DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }


}
