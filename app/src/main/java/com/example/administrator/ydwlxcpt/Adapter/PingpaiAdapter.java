package com.example.administrator.ydwlxcpt.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.Bean.Pingpai;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/20.
 */

public class PingpaiAdapter extends MyBaseAdapter {
    private Map<String, Integer> marks = new HashMap<>();
    public PingpaiAdapter(Activity activity, List dataList) {
        super(activity, dataList);
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_pingpai_listview;
    }

    @Override
    public Object getViewHolder(View rootView) {
        return new ViewHolder(rootView);
    }

    @Override
    public void setItemData(int position, Object dataItem, Object viewHolder) {
        Pingpai pingpai = (Pingpai) dataItem;
        PingpaiAdapter.ViewHolder myHolder = (PingpaiAdapter.ViewHolder) viewHolder;
        myHolder.tv_name.setText(pingpai.getCB_BrandName());
    }

    /**
     * ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     * Created by bailiangjin on 16/7/5.
     */
    public static class ViewHolder {
        public final View root;
        private TextView tv_name;


        public ViewHolder(View root) {
            this.root = root;
            this.tv_name = (TextView) this.root.findViewById(R.id.tv_pingpai_listview_name);
        }
    }
//    public Integer getPosition(String s) {
//        Integer result = marks.get(s);
//        if (result != null) {
//            result = result - 1;
//        }
//        return result;
//    }
}
