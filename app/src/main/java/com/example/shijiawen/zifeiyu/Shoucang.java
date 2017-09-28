package com.example.shijiawen.zifeiyu;

import java.io.Serializable;

/**
 * Created by shijiawen on 2017/9/24.
 */

public class Shoucang implements Serializable {
    private int id;
    private String newsurl;
    private String newstitle;

    public Shoucang(){}
    public Shoucang(int id,String newstitle,String newsurl){
        this.newstitle=newstitle;
        this.newsurl = newsurl;
        this.id = id;
    }

    public String getNewsurl(){
        return newsurl;
    }
    public String getNewstitle(){
        return newstitle;
    }
    public int getId(){
        return id;
    }
    public void setId(int i){this.id=id;}
    public void setNewsurl(String newsurl){this.newsurl=newsurl;}
    public void  setNewstitle(String newstitle){this.newstitle=newstitle;}
}
