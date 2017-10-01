package com.example.shijiawen.zifeiyu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.shijiawen.zifeiyu.DateBase.BianqianData;
import com.example.shijiawen.zifeiyu.R;
import com.example.shijiawen.zifeiyu.bean.Bianqian;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by shijiawen on 2017/10/1.
 */

public class Bianqian_noteActivity extends AppCompatActivity {
    EditText ed1,ed2;
    ImageButton imageButton;
    BianqianData myDatabase;
    Bianqian bianqian;
    int ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bianqian_note);
        ed1=(EditText) findViewById(R.id.editText1);
        ed2=(EditText) findViewById(R.id.editText2);
        imageButton=(ImageButton) findViewById(R.id.saveButton);
        myDatabase=new BianqianData(this);

        Intent intent=this.getIntent();
        ids=intent.getIntExtra("ids", 0);
        //默认为0，不为0,则为修改数据时跳转过来的
        if(ids!=0){
            bianqian=myDatabase.getTiandCon(ids);
            ed1.setText(bianqian.getTitle());
            ed2.setText(bianqian.getContent());
        }
        //保存按钮的点击事件，他和返回按钮是一样的功能，所以都调用isSave()方法；
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isSave();
            }
        });
    }

    /*
     * 返回按钮调用的方法。
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String times = formatter.format(curDate);
        String title=ed1.getText().toString();
        String content=ed2.getText().toString();
        //是要修改数据
        if(ids!=0){
            bianqian=new Bianqian(title,ids, content, times);
            myDatabase.toUpdate(bianqian);
            Intent intent=new Intent(Bianqian_noteActivity.this,MainActivity.class);
            startActivity(intent);
            Bianqian_noteActivity.this.finish();
        }
        //新建日记
        else{
            if(title.equals("")&&content.equals("")){
                Intent intent=new Intent(Bianqian_noteActivity.this,MainActivity.class);
                startActivity(intent);
                Bianqian_noteActivity.this.finish();
            }
            else{
                bianqian=new Bianqian(title,content,times);
                myDatabase.toInsert(bianqian);
                Intent intent=new Intent(Bianqian_noteActivity.this,MainActivity.class);
                startActivity(intent);
               Bianqian_noteActivity.this.finish();
            }

        }
    }
    private void isSave(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String times = formatter.format(curDate);
        String title=ed1.getText().toString();
        String content=ed2.getText().toString();
        //是要修改数据
        if(ids!=0){
            bianqian=new Bianqian(title,ids, content, times);
            myDatabase.toUpdate(bianqian);
            Intent intent=new Intent(Bianqian_noteActivity.this,MainActivity.class);
            startActivity(intent);
            Bianqian_noteActivity.this.finish();
        }
        //新建日记
        else{
            bianqian=new Bianqian(title,content,times);
            myDatabase.toInsert(bianqian);
            Intent intent=new Intent(Bianqian_noteActivity.this,MainActivity.class);
            startActivity(intent);
            Bianqian_noteActivity.this.finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.bianqian_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        "标题："+ed1.getText().toString()+"    " +
                                "内容："+ed2.getText().toString());
                startActivity(intent);
                break;

            default:
                break;
        }
        return false;
    }
}
