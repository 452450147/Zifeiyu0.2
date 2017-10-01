package com.example.shijiawen.zifeiyu.DateBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shijiawen.zifeiyu.bean.Bianqian;

import java.util.ArrayList;

/**
 * Created by shijiawen on 2017/10/1.
 */

public class BianqianData {
    Context context;
    BianqianHelper myHelper;
    SQLiteDatabase myDatabase;
    /*
     * 别的类实例化这个类的同时，创建数据库
     */
    public BianqianData(Context con){
        this.context=con;
        myHelper=new BianqianHelper(context);
    }
    /*
     * 得到ListView的数据，从数据库里查找后解析
     */
    public ArrayList<Bianqian> getArray(){
        ArrayList<Bianqian> array = new ArrayList<Bianqian>();
        ArrayList<Bianqian> array1 = new ArrayList<Bianqian>();
        myDatabase = myHelper.getWritableDatabase();
        Cursor cursor=myDatabase.rawQuery("select ids,title,times from mybook" , null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndex("ids"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String times = cursor.getString(cursor.getColumnIndex("times"));
            Bianqian bianqian = new Bianqian(id, title, times);
            array.add(bianqian);
            cursor.moveToNext();
        }
        myDatabase.close();
        for (int i = array.size(); i >0; i--) {
            array1.add(array.get(i-1));
        }
        return array1;
    }

    /*
     * 返回可能要修改的数据
     */
    public Bianqian getTiandCon(int id){
        myDatabase = myHelper.getWritableDatabase();
        Cursor cursor=myDatabase.rawQuery("select title,content from mybook where ids='"+id+"'" , null);
        cursor.moveToFirst();
        String title=cursor.getString(cursor.getColumnIndex("title"));
        String content=cursor.getString(cursor.getColumnIndex("content"));
        Bianqian bianqian=new Bianqian(title,content);
        myDatabase.close();
        return bianqian;
    }

    /*
     * 用来修改日记
     */
    public void toUpdate(Bianqian bianqian){
        myDatabase = myHelper.getWritableDatabase();
        myDatabase.execSQL(
                "update mybook set title='"+ bianqian.getTitle()+
                        "',times='"+bianqian.getTimes()+
                        "',content='"+bianqian.getContent() +
                        "' where ids='"+ bianqian.getIds()+"'");
        myDatabase.close();
    }

    /*
     * 用来增加新的便签
     */
    public void toInsert(Bianqian bianqian){
        myDatabase = myHelper.getWritableDatabase();
        myDatabase.execSQL("insert into mybook(title,content,times)values('"
                + bianqian.getTitle()+"','"
                +bianqian.getContent()+"','"
                +bianqian.getTimes()
                +"')");
        myDatabase.close();
    }

    /*
     * 长按点击后选择删除便签
     */
    public void toDelete(int ids){
        myDatabase  = myHelper.getWritableDatabase();
        myDatabase.execSQL("delete from mybook where ids="+ids+"");
        myDatabase.close();
    }
}

