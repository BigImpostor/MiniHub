package com.example.minihub.sqlite;

public class ArticleSql {

    public static final String ARTICLE_TABLE = "ARTICLE_TABLE";
    public static final String _ID = "_id";
    public static final String CHAPTER_NAME = "CHAPTER_NAME";
    public static final String DESC = "DESCRIPTION";
    public static final String AUTHOR = "AUTHOR";
    public static final String DATE = "DATE";
    public static final String LINK = "LINK";

    private static final String TEXT_TYPE = " TEXT"; //前面要有个空格，在sql语句里隔开了列名和类型
    private static final String COMMA_SEP = ",";

    public static final String CREATE_TABLE = "CREATE TABLE " + ARTICLE_TABLE +
                                                    "(" + _ID  + " INTEGER PRIMARY KEY" + COMMA_SEP
                                                    + CHAPTER_NAME  + TEXT_TYPE + COMMA_SEP
                                                    + DESC + TEXT_TYPE + COMMA_SEP
                                                    + AUTHOR + TEXT_TYPE + COMMA_SEP
                                                    + DATE + TEXT_TYPE + COMMA_SEP
                                                    + LINK + TEXT_TYPE
                                                    +")";

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + ARTICLE_TABLE;


}
