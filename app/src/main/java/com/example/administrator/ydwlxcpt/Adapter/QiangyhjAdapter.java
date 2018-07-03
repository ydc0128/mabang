package com.example.administrator.ydwlxcpt.Adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ydwlxcpt.Activity.YouHuiJuanTongzhi;
import com.example.administrator.ydwlxcpt.Bean.YHJMingxi;
import com.example.administrator.ydwlxcpt.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class QiangyhjAdapter extends MyBaseAdapter {
    YouHuiJuanTongzhi youHuiJuanTongzhi;

    public QiangyhjAdapter(YouHuiJuanTongzhi youHuiJuanTongzhi, List dataList) {
        super(youHuiJuanTongzhi, dataList);
        this.youHuiJuanTongzhi = youHuiJuanTongzhi;
    }

    public int getItemLayoutResId() {
        return R.layout.item_qiang_yhj_listview;
    }


    public Object getViewHolder(View rootView) {
        return new QiangyhjAdapter.ViewHolder(rootView);
    }

    @Override
    public void setItemData(int position, Object dataItem, Object viewHolder) {
        final YHJMingxi yhjMingxi = (YHJMingxi) dataItem;
        //将holder 转为自己holder
        QiangyhjAdapter.ViewHolder myHolder = (QiangyhjAdapter.ViewHolder) viewHolder;
        String time = yhjMingxi.getTM_Time();
        int index = time.indexOf("T");
        String substring = time.substring(0, index);
        myHolder.tv_youxiaoqi.setText("有效至"+substring);
        myHolder.tv_youxiaoqi.setText(substring);
        myHolder.tv_chongzhijine.setText("" + yhjMingxi.getTM_Ticket());
        myHolder.tv_guize.setText(yhjMingxi.getTM_Remark());
//        myHolder.tv_lijishuyong.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View view) {
//                   youHuiJuanTongzhi.qiang();
//
//               }
//           });
    }

    /**
     * ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     * Created by bailiangjin on 16/7/5.
     */
    public static class ViewHolder {
        public final View root;
        private TextView tv_youxiaoqi;
        private TextView tv_chongzhijine;
        private TextView tv_lijishuyong;
        private TextView tv_guize;


        public ViewHolder(View root) {
            this.root = root;
            this.tv_youxiaoqi = (TextView) this.root.findViewById(R.id.tv_qiang_huodongshijian_start);
            this.tv_chongzhijine = (TextView) this.root.findViewById(R.id.tv_yhj_jine);
            this.tv_lijishuyong = (TextView) this.root.findViewById(R.id.tv_qiang_yhj_lijishiyong);
            this.tv_guize = (TextView) this.root.findViewById(R.id.tv_guize);
        }
    }
}
