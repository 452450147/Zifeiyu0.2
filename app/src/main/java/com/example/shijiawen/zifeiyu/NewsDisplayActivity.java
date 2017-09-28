package com.example.shijiawen.zifeiyu;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.handle;
import static android.R.attr.listViewStyle;
import com.example.shijiawen.zifeiyu.News;


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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.shoucang);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shoucangflag == 0) {
                    Snackbar.make(view, "收藏成功,再按一次取消收藏", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Shoucang ADDshoucang = new Shoucang(id,newstitle,newsUrl);
                   // Log.i("testqqq",ADDshoucang.getNewstitle());
                    handler.addShoucang(ADDshoucang);

                    shoucangflag=1;
                }else {
                    Snackbar.make(view, "取消收藏", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Shoucang DELshoucang = new Shoucang(id,newstitle,newsUrl);
                    handler.deleteShoucang(DELshoucang);

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


