package com.FinalProject.Betelhem.EtNews.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.FinalProject.Betelhem.EtNews.common.NewsContract.*;

public class NewsDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "newslist.db";
    public static final int DATABASE_VARSION=1;

    public NewsDbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VARSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final  String SQL_CREATE_NEWS_TABEL = "CREATE TABLE IF NOT EXISTS " +
                NewsEntery.TABLE_NAME + " ( " +
                NewsEntery._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NewsEntery.COLUMN_TITlE  + " TEXT NOT NULL, " +
                NewsEntery.COLUMN_DATE  + " TEXT NOT NULL, " +
                NewsEntery.COLUMN_TAG  + " TEXT, " +
                NewsEntery.COLUMN_URL  + " TEXT, " +
                NewsEntery.COLUMN_CONTENT  + " TEXT, " +
                NewsEntery.COLUMN_ISREADEN  + " INTEGER" +
                ");";
        db.execSQL(SQL_CREATE_NEWS_TABEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //REMOVE THE TABLE IF IT EXIST AND RE-POPULATE IT WITH THE NEW DATA
            db.execSQL("DROP TABLE IF EXISTS "+ NewsEntery.TABLE_NAME);
            onCreate(db);
    }
    public void removeDb(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+ NewsEntery.TABLE_NAME);
        onCreate(db);
    }
}
