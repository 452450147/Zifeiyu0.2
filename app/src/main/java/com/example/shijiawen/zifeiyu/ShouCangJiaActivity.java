package com.example.shijiawen.zifeiyu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by shijiawen on 2017/9/24.
 */

public class ShouCangJiaActivity extends AppCompatActivity {
    private ListView shoucangliebiao;
    private ShoucangAdapter adapter;
    private DatabaseHandler dbHandler;
    private List<Shoucang> shoucangList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoucangjia_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dbHandler = new DatabaseHandler(this);
        shoucangliebiao = (ListView)findViewById(R.id.shoucangjialiebiao);
        shoucangList=dbHandler.getALllShoucang();
        adapter=new ShoucangAdapter(this,shoucangList);
        shoucangliebiao.setAdapter(adapter);
        shoucangliebiao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String url=  shoucangList.get(i).getNewsurl();
                Intent intent = new Intent(ShouCangJiaActivity.this,NewsDisplayActivity.class);
                intent.putExtra("news_url",url);
                startActivity(intent);

            }
        });

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
}
