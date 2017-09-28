package com.example.shijiawen.zifeiyu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shijiawen on 2017/9/24.
 */

public class ShoucangAdapter extends BaseAdapter {
    private List<Shoucang> shoucangList;
    private Context context;
    public ShoucangAdapter(Context context, List<Shoucang> shoucangList) {
        super();
        this.shoucangList=shoucangList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return shoucangList.size();
    }

    @Override
    public Object getItem(int i) {
        return shoucangList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
        }

        TextView wenzhangming= (TextView) view.findViewById(R.id.wenzhangming);



        wenzhangming.setText("文章名 ：  "+shoucangList.get(i).getNewstitle());

        return view;
    }

}
