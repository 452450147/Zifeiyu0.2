package com.example.shijiawen.zifeiyu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shijiawen.zifeiyu.R;
import com.example.shijiawen.zifeiyu.bean.Bianqian;

import java.util.ArrayList;

/**
 * Created by shijiawen on 2017/10/1.
 */

public class BianqianAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<Bianqian> array;

    public BianqianAdapter(LayoutInflater inf, ArrayList<Bianqian> arry){
        this.inflater=inf;
        this.array=arry;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
           convertView=inflater.inflate(R.layout.list_item_bianqian, null);//注意导包，别导系统包
            vh.tv1=(TextView) convertView.findViewById(R.id.textView1);
            vh.tv2=(TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(vh);
        }
        vh=(ViewHolder) convertView.getTag();
        vh.tv1.setText(array.get(position).getTitle());
        vh.tv2.setText(array.get(position).getTimes());
        return convertView;
    }
    class ViewHolder{
        TextView tv1,tv2;
    }

}
