package com.example.administrator.ydwlxcpt.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.ydwlxcpt.Bean.Massage;
import com.example.administrator.ydwlxcpt.Bean.YHJMingxi;
import com.example.administrator.ydwlxcpt.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class MSGAdapter extends MyBaseAdapter {
    private static final String TAG = "MSGAdapter";
    private Activity activity;

    public MSGAdapter(Activity activity, List dataList) {
        super(activity, dataList);
        this.activity = activity;
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_massage;
    }

    @Override
    public Object getViewHolder(View rootView) {
        return new MSGAdapter.ViewHolder(rootView);
    }

    @Override
    public void setItemData(int position, Object dataItem, Object viewHolder) {
        //将holder 转为自己holder
        final Massage massage = (Massage) dataItem;
        MSGAdapter.ViewHolder myHolder = (MSGAdapter.ViewHolder) viewHolder;
        myHolder.msg_neirong.setText(massage.getM_Info() );
        myHolder.tv_msg_title.setText(massage.getM_Title());
        if (massage.getM_PartID()==2){
            myHolder.iv_msg_tupian.setImageResource(R.drawable.chongzhixianxi);
        }else if (massage.getM_PartID()==3){
            myHolder.iv_msg_tupian.setImageResource(R.drawable.daijinjuan);
        }
    }

    /**
     * ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     * Created by bailiangjin on 16/7/5.
     */
    public static class ViewHolder {
        public final View root;
        private TextView msg_neirong;
//        private TextView tv_msg_time;
        private TextView tv_msg_title;
        private ImageView iv_msg_tupian;
        public ViewHolder(View root) {
            this.root = root;
//            this.tv_msg_time=(TextView)this.root.findViewById(R.id.tv_msg_time);
            this.tv_msg_title=(TextView)this.root.findViewById(R.id.tv_msg_title);
            this.iv_msg_tupian=(ImageView)this.root.findViewById(R.id.iv_msg_tupian);
            this.msg_neirong = (TextView) this.root.findViewById(R.id.msg_neirong);

        }
    }
}
