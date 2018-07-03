package com.example.administrator.ydwlxcpt.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.Bean.Weizhang;
import com.example.administrator.ydwlxcpt.R;

import java.util.List;
/**
 * Created by Administrator on 2017/11/11.
 */
public class WeizhangAdapter extends MyBaseAdapter<Weizhang> {

    public WeizhangAdapter(Activity activity, List<Weizhang> dataList) {
        super(activity, dataList);
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_weizhang_list;
    }

    @Override
    public Object getViewHolder(View rootView) {
         return new WeizhangAdapter.ViewHolder(rootView);
    }

    @Override
    public void setItemData(int position, Weizhang dataItem, Object viewHolder) {
        final Weizhang weizhang = (Weizhang) dataItem;
        //将holder 转为自己holder
        WeizhangAdapter.ViewHolder myHolder = (WeizhangAdapter.ViewHolder) viewHolder;
        myHolder.tv_time.setText(weizhang.getTime());
        myHolder.tv_fen.setText("-"+weizhang.getScore());
        myHolder.tv_didian.setText(weizhang.getAddress());
        myHolder.tv_qian.setText("-"+weizhang.getPrice());
        myHolder.tv_content.setText(weizhang.getContent());
    }

    /**
     * ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     */
    public static class ViewHolder {
        public final View root;
        private TextView tv_time;
        private TextView tv_fen;
        private TextView tv_didian;
        private TextView tv_qian;
        private TextView tv_content;


        public ViewHolder(View root) {
            this.root = root;
            this.tv_time = (TextView) this.root.findViewById(R.id.tv_weizhang_listview_time);
            this.tv_fen = (TextView) this.root.findViewById(R.id.tv_weizhang_listview_fen);
            this.tv_didian = (TextView) this.root.findViewById(R.id.tv_weizhang_listview_didian);
            this.tv_qian = (TextView) this.root.findViewById(R.id.tv_weizhang_listview_qian);
            this.tv_content = (TextView) this.root.findViewById(R.id.tv_weizhang_listview_content);
        }
    }
}
