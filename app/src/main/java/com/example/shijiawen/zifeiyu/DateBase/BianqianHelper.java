package com.example.shijiawen.zifeiyu.DateBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shijiawen on 2017/10/1.
 */

public class BianqianHelper extends SQLiteOpenHelper {

    public BianqianHelper(Context context) {
        super(context, "mydate", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mybook(" +
                "ids integer PRIMARY KEY autoincrement," +
                "title text," +
                "content text," +
                "times text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }
}
