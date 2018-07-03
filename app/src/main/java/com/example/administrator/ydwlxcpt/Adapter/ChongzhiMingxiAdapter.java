package com.example.administrator.ydwlxcpt.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.Bean.ChongzhiMingxi;
import com.example.administrator.ydwlxcpt.Bean.Youhuiquan;
import com.example.administrator.ydwlxcpt.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class ChongzhiMingxiAdapter extends MyBaseAdapter {

    public ChongzhiMingxiAdapter(Activity activity, List dataList) {
        super(activity, dataList);
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_chongzhimingxi_listview;
    }

    @Override
    public Object getViewHolder(View rootView) {
        return new ChongzhiMingxiAdapter.ViewHolder(rootView);
    }

    @Override
    public void setItemData(int position, Object dataItem, Object viewHolder) {
        final ChongzhiMingxi chongzhiMingxi = (ChongzhiMingxi) dataItem;
        //将holder 转为自己holder
        ChongzhiMingxiAdapter.ViewHolder myHolder = (ChongzhiMingxiAdapter.ViewHolder) viewHolder;
        myHolder.tv_zhanghao.setText(chongzhiMingxi.getP_TakeUTel());
        String shijian = chongzhiMingxi.getP_Time();
        String replace = shijian.replace("T", "  ");
        myHolder.tv_shijian.setText(replace);
        if(chongzhiMingxi.getP_Type()==1){
            myHolder.tv_chongzhifangshi.setText("支付宝付款");
        }else if(chongzhiMingxi.getP_Type()==2){
            myHolder.tv_chongzhifangshi.setText("微信付款");
        }else{
            myHolder.tv_chongzhifangshi.setText("其他方式付款");
        }
//        myHolder.tv_jieguo.setText(""+chongzhiMingxi.getP_);
        myHolder.tv_zhifujine.setText(""+chongzhiMingxi.getP_PayPrice());
        myHolder.tv_daozhangjine.setText(""+(chongzhiMingxi.getP_TakePrice()+chongzhiMingxi.getP_TakePriceAdd()));
    }

    /**
     * ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     * Created by bailiangjin on 16/7/5.
     */
    public static class ViewHolder {
        public final View root;
        private TextView tv_zhanghao;
        private TextView tv_shijian;
        private TextView tv_chongzhifangshi;
        private TextView tv_zhifujine;
        private TextView tv_daozhangjine;
        private TextView tv_jieguo;


        public ViewHolder(View root) {
            this.root = root;
            this.tv_zhanghao = (TextView) this.root.findViewById(R.id.tv_chongzhimingxi_listview_zhanghao);
            this.tv_shijian = (TextView) this.root.findViewById(R.id.tv_chongzhimingxi_listview_shijian);
            this.tv_chongzhifangshi = (TextView) this.root.findViewById(R.id.tv_chongzhimingxi_listview_chongzhifangshi);
            this.tv_zhifujine = (TextView) this.root.findViewById(R.id.tv_chongzhimingxi_listview_zhifujine);
            this.tv_daozhangjine = (TextView) this.root.findViewById(R.id.tv_chongzhimingxi_listview_daozhangjine);
//          this.tv_jieguo=(TextView)this.root.findViewById(R.id.tv_chongzhimingxi_shifouchenggong);
        }
    }
}
