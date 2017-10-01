package com.example.shijiawen.zifeiyu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Handler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.os.Handler;
import com.example.shijiawen.zifeiyu.bean.News;
import com.example.shijiawen.zifeiyu.Adapter.NewsAdapter;
import com.example.shijiawen.zifeiyu.R;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {



    private SwipeRefreshLayout swipeRefreshLayout;
    private List<News> newsList;
    private NewsAdapter adapter;
    private Handler handler;
    private RecyclerView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "暂未开发", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.xiala);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                refresh();
            }
        });


        getNews();
        newsList = new ArrayList<>();
        lv = (RecyclerView) findViewById(R.id.news_lv);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        lv.setLayoutManager(layoutManager);

        handler = new Handler(){

            @Override

            public void handleMessage(Message msg) {
                if (msg.what == 1){adapter = new NewsAdapter(newsList);
                    lv.setAdapter( adapter);





                   /* lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            News news = newsList.get(position);
                            Intent intent = new Intent(MainActivity.this,NewsDisplayActivity.class);
                            intent.putExtra("news_url",news.getNewsUrl());
                            startActivity(intent);
                        }
                    })*/;
                }

            }
        };


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.wangzhanrukou) {
 Intent jinruwangzhan = new Intent(MainActivity.this,webActivity.class);
           startActivity(jinruwangzhan);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent jinruShouCangJia = new Intent(MainActivity.this,ShouCangJiaActivity.class);
            startActivity(jinruShouCangJia);

        } else if (id == R.id.nav_slideshow) {
            Intent jinruBianqian = new Intent(MainActivity.this,BianqianActivity.class);
            startActivity(jinruBianqian);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void getNews(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://www.shijiawen4.top/index.php/wp-json/wp/v2/posts")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    jsontest(responseData);

                    /*用jsoup爬取的

                    org.jsoup.nodes.Document doc = Jsoup.connect("https://www.shijiawen4.top/index.php/博客/").get();

                    Elements titleLinks = doc.select("div.post-inner");
                 //   Elements descLinks = doc.select("div.list-content");
                    Elements timeLinks = doc.select("div.entry-content");

                    for (int j = 0;j < titleLinks.size();j++){
                        String title = titleLinks.get(j).select("header.entry-header").select("h2.entry-title").select("a").text();
                        String url = titleLinks.get(j).select("a").attr("href");
                      //  String desc = descLinks.get(j).select("a").text();
                        String time = timeLinks.get(j).select("p").text();

                        News news = new News(title, url, null, time);
                        newsList.add(news);

                    }*/

                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jsontest(String jsonData){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0 ;i <jsonArray.length();i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject title1 = jsonObject.getJSONObject("title");
                String title = title1.getString("rendered");
                String  newsTime = jsonObject.getString("date");
                newsTime = newsTime.replaceAll("T","   ");
                JSONObject content1 = jsonObject.getJSONObject("excerpt") ;
                String Desc = content1.getString("rendered");
                //过滤
                Desc = Desc.replaceAll("<p>","");
                Desc = Desc.replaceAll("</p>","");
                Desc = Desc.replaceAll("</b>","");
                Desc = Desc.replaceAll("[\\[][^\\[\\]]+[\\]]", "");

               JSONObject HREF =jsonObject.getJSONObject("_links");
                JSONArray Href = HREF.getJSONArray("self");
                JSONObject hhh = Href.getJSONObject(0);
                String newsUrl =hhh.getString("href");

                News news = new News(title,newsUrl,Desc ,  newsTime);
                newsList.add(news);
            }
        }
        catch  (Exception e){
            e.printStackTrace();
            Log.d("bbbbbbbb","bbbbbb");
        }
    }


    private void refresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                 Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsList.clear();
                        getNews();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });
            }
        }).start();

    }

}