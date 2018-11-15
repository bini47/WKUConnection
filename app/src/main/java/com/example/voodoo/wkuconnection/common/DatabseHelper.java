/*
package com.example.voodoo.wkuconnection.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

*/
/*
 * Created by voodoo on 1/4/2018.

*//*


public class DatabseHelper extends SQLiteOpenHelper {


    public static final String DB_NAME="articledb";
    public static final String TABLE_NAME="articles";
    public static final String ARTICLE_ID="ARTICLE_ID";
    public static final String TITLE="title";
    public static final String PUB_DATE="pubdate";
    public static final String URL="url";
    public static final String CONTENT="content";

    public DatabseHelper(Context context) {
        super(context, DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table  IF NOT EXISTS articles  (ARTICLE_ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,pubdate VARCHAR(50), " +
                "content TEXT, url TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        onCreate(db);
    }

    public boolean insertData(String title,String pubdate, String content,String url){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TITLE,title);
        values.put(PUB_DATE,pubdate);
        values.put(CONTENT, content);
        values.put(URL,url);

        long res= db.insert("articles",null,values);

        if (res==-1){
            return  false;

        }
        else
            return true;


    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from articles",null);
        return res;


    }
}
*/
