package com.example.shijiawen.shujukutest;

import android.provider.ContactsContract;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by shijiawen on 2017/9/24.
 */

public class Schedule extends DataSupport implements Serializable {
private int id;
    private ContactsContract.Data date;
    private String content;
    private Boolean finish;

    public int getId() {
        return id;
    }

    public ContactsContract.Data getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(ContactsContract.Data date) {
        this.date = date;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public void setId(int id) {
        this.id = id;
    }
}
