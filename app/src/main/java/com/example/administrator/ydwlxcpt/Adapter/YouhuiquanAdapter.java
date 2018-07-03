package com.example.administrator.ydwlxcpt.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ydwlxcpt.Activity.MyYouhuiquanActivity;
import com.example.administrator.ydwlxcpt.Bean.Chongyongdizhi;
import com.example.administrator.ydwlxcpt.Bean.Youhuiquan;
import com.example.administrator.ydwlxcpt.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public class YouhuiquanAdapter extends MyBaseAdapter {
    MyYouhuiquanActivity myYouhuiquanActivity;
    public YouhuiquanAdapter(MyYouhuiquanActivity activity, List dataList) {
        super(activity, dataList);
        this.myYouhuiquanActivity=activity;
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_youhuiquan_listview;
    }

    @Override
    public Object getViewHolder(View rootView) {
        return new YouhuiquanAdapter.ViewHolder(rootView);
    }

    @Override
    public void setItemData(final int position, Object dataItem, Object viewHolder) {
        final Youhuiquan youhuiquan = (Youhuiquan) dataItem;
        //将holder 转为自己holder
        YouhuiquanAdapter.ViewHolder myHolder = (YouhuiquanAdapter.ViewHolder) viewHolder;
        myHolder.tv_jine.setText(""+youhuiquan.getT_Ticket());
        myHolder.tv_man.setText("满"+youhuiquan.getT_Money()+"元可用");
        String time = youhuiquan.getT_Time();
        int index = time.indexOf("T");
        String substring = time.substring(0, index);
        myHolder.tv_youxiaoqi.setText(substring);
//        myHolder.tv_lijishuyong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myYouhuiquanActivity.dianji(position-1);
//            }
//        });
    }

    /**
     * ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     * Created by bailiangjin on 16/7/5.
     */
    public static class ViewHolder {
        public final View root;
        private TextView tv_jine;
        private TextView tv_man;
        private TextView tv_youxiaoqi;
        private TextView tv_lijishuyong;

        public ViewHolder(View root) {
            this.root = root;
            this.tv_jine = (TextView) this.root.findViewById(R.id.tv_my_yhj_jine);
            this.tv_man = (TextView) this.root.findViewById(R.id.tv_my_guize);
            this.tv_youxiaoqi = (TextView) this.root.findViewById(R.id.tv_my_qiang_huodongshijian_start);
            this.tv_lijishuyong = (TextView) this.root.findViewById(R.id.iv_my_yhj_5);
        }
    }

}
