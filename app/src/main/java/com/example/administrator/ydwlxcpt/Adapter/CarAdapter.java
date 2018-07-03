package com.example.administrator.ydwlxcpt.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.Bean.Car;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class CarAdapter extends MyBaseAdapter {
    public CarAdapter(Activity activity, List dataList) {
        super(activity, dataList);
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_mycar_listview;
    }

    @Override
    public Object getViewHolder(View rootView) {
        return new CarAdapter.ViewHolder(rootView);
    }

    @Override
    public void setItemData(int position, Object dataItem, Object viewHolder) {
        final Car car = (Car) dataItem;
        //将holder 转为自己holder
        CarAdapter.ViewHolder myHolder = (CarAdapter.ViewHolder) viewHolder;
        //从网络加载图片
        Picasso.with(context).load(Uri.parse(Contast.Domain+car.getC_Image()))
                .placeholder(R.drawable.carmoren)
                .error(R.drawable.carmoren)
                .into(myHolder.iv_photo);
        myHolder.tv_chepaihao.setText(car.getC_PlateNumber());
        if(car.getC_CTID()==3){
            myHolder.tv_jibie.setText("小轿车");
        }else if(car.getC_CTID()==4){
            myHolder.tv_jibie.setText("SUV/MPV");
        }

    }

    /**
     * ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     */
    public static class ViewHolder {
        public final View root;
        private ImageView iv_photo;
        private TextView tv_chepaihao;
        private TextView tv_jibie;


        public ViewHolder(View root) {
            this.root = root;
            this.iv_photo = (ImageView) this.root.findViewById(R.id.iv_mycar_listview_photo);
            this.tv_chepaihao = (TextView) this.root.findViewById(R.id.tv_mycar_listview_chepaihao);
            this.tv_jibie = (TextView) this.root.findViewById(R.id.tv_mycar_listview_cheliangjibie);
        }
    }
}
