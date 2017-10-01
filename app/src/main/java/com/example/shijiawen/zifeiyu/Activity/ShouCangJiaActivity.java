package com.example.shijiawen.zifeiyu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shijiawen.zifeiyu.Adapter.BianqianAdapter;
import com.example.shijiawen.zifeiyu.DateBase.DatabaseHandler;
import com.example.shijiawen.zifeiyu.R;
import com.example.shijiawen.zifeiyu.bean.Shoucang;
import com.example.shijiawen.zifeiyu.Adapter.ShoucangAdapter;

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


        init();
       /* dbHandler = new DatabaseHandler(this);
        shoucangliebiao = (ListView)findViewById(R.id.shoucangjialiebiao);
        shoucangList=dbHandler.getALllShoucang();
        adapter=new ShoucangAdapter(this,shoucangList);
        shoucangliebiao.setAdapter(adapter);*/
        shoucangliebiao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String url=  shoucangList.get(i).getNewsurl();
                Intent intent = new Intent(ShouCangJiaActivity.this,NewsDisplayActivity.class);
                intent.putExtra("news_url",url);
                startActivity(intent);

            }
        });

        shoucangliebiao.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                final String newstitle=  shoucangList.get(i).getNewstitle();

                new AlertDialog.Builder(ShouCangJiaActivity.this)
                        .setTitle("删除")
                        .setMessage("是否删除收藏")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //mdb.toDelete(array.get(position).getIds());
                               // array=mdb.getArray();

                                //注意，这里我偷懒用了按标题名删除，id设为0可能会有bug
                                Shoucang DELshoucang = new Shoucang(0,newstitle,null);
                                dbHandler.deleteShoucang(DELshoucang);

                                init();
                            }
                        })
                        .create().show();

                return true;
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

    private void init(){
        dbHandler = new DatabaseHandler(this);
        shoucangliebiao = (ListView)findViewById(R.id.shoucangjialiebiao);
        shoucangList=dbHandler.getALllShoucang();
        adapter=new ShoucangAdapter(this,shoucangList);
        shoucangliebiao.setAdapter(adapter);

    }
}
