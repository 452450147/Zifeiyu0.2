package com.example.shijiawen.shujukutest;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by shijiawen on 2017/9/24.
 */

public class ScheduleFragment extends Fragment { private static final String TAG = "ScheduleFragment";
    public final static int NAME = 0;
    private RecyclerView recyclerView;
    private List<Schedule> scheduleList;
    private ScheduleAdapter mAdapter;
    PopOptionUtil mPop;//用于长按时弹出删除提示框

    public ScheduleFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        scheduleList.clear(); //去掉之前的数据
        List<Schedule> newList = DataSupport.order("date desc").find(Schedule.class);
        scheduleList.addAll(newList);//注意要将数据复制过来，而不是直接使用，不然无法更新数据
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

//fab点击后跳转到添加便签的界面
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.schedule_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: intent");
                Intent intent = new Intent(getContext(), AddScheduleActivity.class);
                intent.putExtra("ActivityName", NAME);
                startActivity(intent);
            }
        });

        mPop = new PopOptionUtil(getContext());

        scheduleList = DataSupport.order("date desc").find(Schedule.class);
        mAdapter = new ScheduleAdapter(scheduleList);

        recyclerView = (RecyclerView) view.findViewById(R.id.schedule_recyclerview);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

//设置recyclerview的长按事件
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // ...
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                Log.d(TAG, "onItemLongClick: ");
                mPop.setOnPopClickEvent(new PopClickEvent() {

                    @Override
                    public void onNextClick() {
                        //删除item
                        Log.d(TAG, "onNextClick: delete");

                        int id = scheduleList.get(position).getId();
                        Log.d(TAG, "onNextClick: position:" + position + " id: " + id);
                        deleteData(id);

                        scheduleList.remove(position);

                        mAdapter.notifyItemRemoved(position);
                        mAdapter.notifyItemRangeChanged(0,scheduleList.size());//这个需要设置，因为删除后item的position会改变
                        mPop.dismiss();
                    }
                });
                mPop.show(view);
            }
        }));


        return view;
    }

    //litepal删除数据
    public void deleteData(int id){
        DataSupport.deleteAll(Schedule.class, "id = ?", String.valueOf(id));
        Log.d(TAG, "deleteData: " + id);
    }

}

}
