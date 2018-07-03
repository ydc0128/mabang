package com.example.administrator.ydwlxcpt.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.Bean.Car;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Youhuiquan;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2017/8/29.
 */

public class YuemingxiAdapter extends MyBaseAdapter {

    public YuemingxiAdapter(Activity activity, List dataList) {
        super(activity, dataList);
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_yuemingxi_listview;
    }

    @Override
    public Object getViewHolder(View rootView) {
        return new YuemingxiAdapter.ViewHolder(rootView);
    }

    @Override
    public void setItemData(int position, Object dataItem, Object viewHolder) {
        final Dingdan dingdan = (Dingdan) dataItem;
        //将holder 转为自己holder
        YuemingxiAdapter.ViewHolder myHolder = (YuemingxiAdapter.ViewHolder) viewHolder;
        myHolder.tv_chepaihao.setText(dingdan.getO_PlateNumber());
        if (dingdan.getO_WriteAdress()==null||dingdan.getO_WriteAdress().equals("")) {
            myHolder.tv_dizhi.setText(dingdan.getO_Adress());
        }else {
            myHolder.tv_dizhi.setText(dingdan.getO_Adress() + "," + dingdan.getO_WriteAdress());
        }
        String shijian = dingdan.getO_Time();
        String replace = shijian.replace("T", "  ");
        myHolder.tv_shijian.setText(replace);
        myHolder.tv_xiaofeijine.setText(""+dingdan.getO_Money()+".00");
        Picasso.with(context).load(Uri.parse(Contast.Domain+dingdan.getO_CarImage()))
                .placeholder(R.drawable.carmoren)
                .error(R.drawable.carmoren)
                .into(myHolder.iv_cheliang_zhaopian);
    }

    /**
     * ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     * Created by bailiangjin on 16/7/5.
     */
    public static class ViewHolder {
        public final View root;
        private TextView tv_chepaihao;
        private TextView tv_dizhi;
        private TextView tv_shijian;
        private TextView tv_xiaofeijine;
        private ImageView iv_cheliang_zhaopian;


        public ViewHolder(View root) {
            this.root = root;
            this.tv_chepaihao = (TextView) this.root.findViewById(R.id.tv_yuemingxi_listview_chepaihao);
            this.tv_dizhi = (TextView) this.root.findViewById(R.id.tv_yuemingxi_listview_dizhi);
            this.tv_shijian = (TextView) this.root.findViewById(R.id.tv_yuemingxi_listview_shijian);
            this.tv_xiaofeijine = (TextView) this.root.findViewById(R.id.tv_yuemingxi_listview_xiaofeijine);
            this.iv_cheliang_zhaopian=(ImageView)this.root.findViewById(R.id.iv_cheliang_zhanpian);
        }
    }

}
