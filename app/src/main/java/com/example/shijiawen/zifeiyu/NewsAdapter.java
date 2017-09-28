package com.example.shijiawen.zifeiyu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by shijiawen on 2017/9/20.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>  {
    private List<News>mnewsList;
    private Context mContext;

    public NewsAdapter(List<News> newsList){
        mnewsList = newsList;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item,parent,false);
        // 创建并放回一个ViewHolder对象


        final ViewHolder holder = new ViewHolder(view);



        holder.newDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                News news = mnewsList.get(position);
              try{ Intent intent = new Intent();
               intent.setClass(v.getContext(), NewsDisplayActivity.class);
                //第一个参数就是获取当前的context。在activity类中可以用CurrentActivity.this代替
                //这里第二个参数是将跳到的activity
                  intent.putExtra("news_url",news.getNewsUrl());
                  intent.putExtra("news_title",news.getNewsTitle());
                v.getContext().startActivity(intent);
               // Intent intent = new Intent(mContext,NewsDisplayActivity.class);

                  }
                  catch ( Exception E){
                 E.printStackTrace();Log.i("cccc","cccccccc");
              }


                //mContext.startActivity(intent);

            }
        });





        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news =mnewsList.get(position);
        holder.newTitle.setText(news.getNewsTitle());
        holder.newDesc.setText(news.getDesc());
        holder.newsTime.setText(news.getNewsTime());

    }
    @Override
    public int getItemCount() {
        return mnewsList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
View newsview;
        TextView newTitle;
        TextView newDesc;
        TextView newsTime;

        public ViewHolder(View view) {
            super(view);
            newsview = view;
            newTitle = (TextView)view.findViewById(R.id.news_title);
            newDesc = (TextView)view.findViewById(R.id.news_desc);
            newsTime = (TextView)view.findViewById(R.id.news_time);

        }
    }




}/* @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }
*/
   /* @Override
    public void onBindViewHolder(NewsAdapter holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }
*/


   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.news_item, null);

            viewHolder = new ViewHolder();
            viewHolder.newTitle = (TextView) view
                    .findViewById(R.id.news_title);
            viewHolder.newDesc = (TextView)view.findViewById(R.id.news_desc);
            viewHolder.newsTime = (TextView)view.findViewById(R.id.news_time);
            view.setTag(viewHolder);

        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.newTitle.setText(newsList.get(position).getNewsTitle());
        viewHolder.newDesc.setText(newsList.get(position).getDesc());
        viewHolder.newsTime.setText("更改时间 : "+newsList.get(position).getNewsTime());
        return view;
    }*/

