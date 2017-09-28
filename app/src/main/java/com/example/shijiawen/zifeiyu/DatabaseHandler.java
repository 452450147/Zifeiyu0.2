package com.example.shijiawen.zifeiyu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiawen on 2017/9/24.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Test";
    private static final String TABLE_NAME="shou";
    private static final int VERSION=1;
    private static final String KEY_ID="id";
    private static final String KEY_TITLE="newstitle";
    private static final String KEY_URL="newsurl";

    //建表语句
    private static final String CREATE_TABLE="create table "+TABLE_NAME+"("+KEY_ID+
            " integer primary key autoincrement,"+KEY_TITLE+" text not null,"+
            KEY_URL+" text not null);";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addShoucang(Shoucang shoucang){
        SQLiteDatabase db=this.getWritableDatabase();

        //使用ContentValues添加数据
        ContentValues values=new ContentValues();
        values.put(KEY_TITLE,shoucang.getNewstitle());
        values.put(KEY_URL,shoucang.getNewsurl());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Shoucang getShoucang(String newstitle){
        SQLiteDatabase db=this.getWritableDatabase();

        //Cursor对象返回查询结果
        Cursor cursor=db.query(TABLE_NAME,new String[]{KEY_ID,KEY_TITLE,KEY_URL},
                KEY_TITLE+"=?",new String[]{newstitle},null,null,null,null);


        Shoucang shoucang = null;

        //注意返回结果有可能为空
        if(cursor.moveToFirst()){
            shoucang=new Shoucang(cursor.getInt(0),cursor.getString(1), cursor.getString(2));
        }
        return shoucang;
    }
    public int getCounts(){
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        cursor.close();

        return cursor.getCount();
    }

    //查找所有
    public List<Shoucang> getALllShoucang(){
        List<Shoucang> shoucangList= new ArrayList<>();

        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Shoucang shoucang = new Shoucang();
                shoucang.setId(Integer.parseInt(cursor.getString(0)));
                shoucang.setNewstitle(cursor.getString(1));
                shoucang.setNewsurl(cursor.getString(2));
                shoucangList.add(shoucang);

            }while(cursor.moveToNext());
        }
        return shoucangList;
    }

    //更新
    public int updateShoucang(Shoucang shoucang){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_TITLE,shoucang.getNewstitle());
        values.put(KEY_URL,shoucang.getNewsurl());

        return db.update(TABLE_NAME,values,KEY_ID+"=?",new String[]{String.valueOf(shoucang.getId())});
    }
    public void deleteShoucang(Shoucang shoucang){
        SQLiteDatabase db=this.getWritableDatabase();
        //db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(shoucang.getId())});
        db.delete(TABLE_NAME,KEY_TITLE+"=?",new String[]{String.valueOf(shoucang.getNewstitle())});
        db.close();
    }


}
