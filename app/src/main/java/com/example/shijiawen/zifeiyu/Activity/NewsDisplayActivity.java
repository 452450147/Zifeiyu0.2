package com.example.shijiawen.zifeiyu.Activity;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.shijiawen.zifeiyu.DateBase.DatabaseHandler;
import com.example.shijiawen.zifeiyu.R;
import com.example.shijiawen.zifeiyu.bean.Shoucang;


/**
 * Created by shijiawen on 2017/9/20.
 */

public class NewsDisplayActivity extends AppCompatActivity {
    private String newsUrl;
    private String newstitle;
    private android.os.Handler handler2;
    String WENZHANG;
    int shoucangflag = 0;
    private DatabaseHandler handler;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

handler = new DatabaseHandler(this);
         final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.shoucang);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shoucangflag == 0) {
                    Snackbar.make(view, "收藏成功,再按一次取消收藏", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Shoucang ADDshoucang = new Shoucang(id,newstitle,newsUrl);
                   // Log.i("testqqq",ADDshoucang.getNewstitle());
                    handler.addShoucang(ADDshoucang);

                    fab.setImageResource(R.mipmap.ic_no_collection);
                    shoucangflag=1;
                }else {
                    Snackbar.make(view, "取消收藏", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Shoucang DELshoucang = new Shoucang(id,newstitle,newsUrl);
                    handler.deleteShoucang(DELshoucang);

                    fab.setImageResource(R.mipmap.ic_collection);

                    shoucangflag=0;

                }

            }
        });


        newsUrl = getIntent().getStringExtra("news_url");
        newstitle = getIntent().getStringExtra("news_title");
        final TextView textView =(TextView)findViewById(R.id.wenzhang);


        getwenzhang();
        handler2 = new android.os.Handler(){

            @Override

            public void handleMessage(Message msg) {
                if (msg.what == 1){
                    textView.setText(Html.fromHtml(WENZHANG));

                }

            }
        };




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







    private void getwenzhang(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(newsUrl)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    jsontest(responseData);



                    Message msg1 = new Message();
                    msg1.what = 1;
                    handler2.sendMessage(msg1);




                } catch (IOException e) {
                    e.printStackTrace();
                }
            }}).start();
    }


    private void jsontest(String jsonData) {
        try {
           // JSONArray jsonArray = new JSONArray(jsonData);
            //JSONObject jsonObject = jsonArray.getJSONObject();
            JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject content = jsonObject.getJSONObject("content");
        WENZHANG = content.getString("rendered");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


