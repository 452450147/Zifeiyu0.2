package com.example.shijiawen.zifeiyu.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.shijiawen.zifeiyu.Adapter.BianqianAdapter;
import com.example.shijiawen.zifeiyu.DateBase.BianqianData;
import com.example.shijiawen.zifeiyu.R;
import com.example.shijiawen.zifeiyu.bean.Bianqian;

import java.util.ArrayList;

/**
 * Created by shijiawen on 2017/10/1.
 */

public class BianqianActivity extends AppCompatActivity {
    ImageButton imageButton;
    ListView lv;
    LayoutInflater inflater;
    ArrayList<Bianqian> array;
    BianqianData mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bianqian_display);
        lv=(ListView) findViewById(R.id.lv_bwlList);
        imageButton=(ImageButton) findViewById(R.id.btnAdd);
        inflater=getLayoutInflater();

        mdb=new BianqianData(this);
        array=mdb.getArray();
        BianqianAdapter adapter=new BianqianAdapter(inflater,array);
        lv.setAdapter(adapter);
        /*
         * 点击listView里面的item,用来修改日记
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(getApplicationContext(),Bianqian_noteActivity.class);
                intent.putExtra("ids",array.get(position).getIds() );
                startActivity(intent);
                BianqianActivity.this.finish();
            }
        });

        /*
         * 长点后来判断是否删除数据
         */
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                //AlertDialog,来判断是否删除日记。
                new AlertDialog.Builder(BianqianActivity.this)
                        .setTitle("删除")
                        .setMessage("是否删除笔记")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mdb.toDelete(array.get(position).getIds());
                                array=mdb.getArray();
                                BianqianAdapter adapter=new BianqianAdapter(inflater,array);
                                lv.setAdapter(adapter);
                            }
                        })
                        .create().show();
                return true;
            }
        });
        /*
         * 按钮点击事件，用来新建日记
         */
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Bianqian_noteActivity.class);
                startActivity(intent);
                BianqianActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent=new Intent(getApplicationContext(),Bianqian_noteActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.item2:
                this.finish();
                break;
            default:
                break;
        }
        return true;
    }
}
