package com.example.administrator.ydwlxcpt.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Activity.CehuaDiaLayout;
import com.example.administrator.ydwlxcpt.Activity.DialogActivity;
import com.example.administrator.ydwlxcpt.Activity.DingdanXiangqingActivity;
import com.example.administrator.ydwlxcpt.Activity.MainActivity;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.ActivityUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrator on 2018/2/28.
 */
public class CehuaAdapter extends MyBaseAdapter {
    CehuaDiaLayout cehuaDiaLayout;
    public List<String> quxiaoyuanyin;
    private int star;
    public CehuaAdapter(CehuaDiaLayout cehuaDiaLayout, List dataList) {
        super(cehuaDiaLayout, dataList);
        this.cehuaDiaLayout = cehuaDiaLayout;
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_dingdan_cehua;
    }

    @Override
    public Object getViewHolder(View rootView) {
        return new CehuaAdapter.ViewHolder(rootView);
    }

    @Override
    public void setItemData(final int position, Object dataItem, Object viewHolder) {
        final Dingdan dingdan = (Dingdan) dataItem;
        //将holder 转为自己holder
        final CehuaAdapter.ViewHolder myHolder = (CehuaAdapter.ViewHolder) viewHolder;
//        String str = dingdan.getO_Time();
//        String shijian = str.replace("T", " ");
        if (dingdan.getO_WashType()==1) {
            String shijian = dingdan.getO_WashTime();
            myHolder.tv_xiadanshijian.setText(shijian);

        }else {
            String xiadan=dingdan.getO_Time();
            String shijian = xiadan.replace("T", " ");
            myHolder.tv_xiadanshijian.setText(shijian);
        }
        if (dingdan.getO_TypeID()==1){
            myHolder.tv_name.setText("未知");
            myHolder.bt_quxiao.setVisibility(View.VISIBLE);
            myHolder.bt_dadianhua.setVisibility(View.GONE);
        }else {
            myHolder.tv_name.setText(dingdan.getO_WorkerName());
            myHolder.bt_dadianhua.setVisibility(View.VISIBLE);
            myHolder.bt_quxiao.setVisibility(View.GONE);
        }
        myHolder.tv_chepai.setText(dingdan.getO_PlateNumber());
        myHolder.tv_dingdanhao.setText(dingdan.getO_ID() + "");
        switch (dingdan.getO_TypeID()) {
            case 1:
                if (dingdan.getO_ISCancel() == 0) {
                    myHolder.tv_dingdanzhuangtai.setText("待接单");
                } else {
                    myHolder.tv_dingdanzhuangtai.setText("已取消");
                }
                break;
            case 2:
                myHolder.tv_dingdanzhuangtai.setText("已接单");
                break;
            case 3:
                myHolder.tv_dingdanzhuangtai.setText("洗车中");
                break;
            case 4:
                myHolder.tv_dingdanzhuangtai.setText("已完成");
                break;
            case 5:
                myHolder.tv_dingdanzhuangtai.setText("申请退款");
            case 6:
                myHolder.tv_dingdanzhuangtai.setText("已退款");
                break;
            case 7:
                myHolder.tv_dingdanzhuangtai.setText("检查车辆状况");
                break;
            case 8:
                myHolder.tv_dingdanzhuangtai.setText("已到达");
                break;
            case 9:
                myHolder.tv_dingdanzhuangtai.setText("赶往途中");
                break;
        }


        String fuwu = dingdan.getO_WPart();
        StringBuffer sb = new StringBuffer();
        if (fuwu.contains("|")) {
            String[] split = fuwu.split("\\|");
            for (int i = 0; i < split.length; i++) {
                String[] strings = split[i].split(",");
                int num = Integer.parseInt(strings[0]);
                switch (num) {
                    case 1:
                    case 7:
                        sb.append("车外清洗").append(",");
                        break;
                    case 2:
                    case 9:
                        sb.append("标准清洗").append(",");
                        break;
                    case 16:
                    case 17:
                        sb.append("轮毂除锈").append(",");
                        break;
                    case 4:
                    case 11:
                        sb.append("打蜡").append(",");
                        break;
                    case 5:
                    case 12:
                        sb.append("室内精洗").append(",");
                        break;
                    case 18:
                    case 19:
                        sb.append("引擎清洗").append(",");
                        break;
                }
            }
        } else {
            String[] split = fuwu.split(",");
            for (int i = 0; i < split.length; i++) {
            }
            int num = Integer.parseInt(split[0]);
            switch (num) {
                case 1:
                case 7:
                    sb.append("车外清洗").append(",");
                    break;
                case 2:
                case 9:
                    sb.append("标准清洗").append(",");
                    break;
                case 16:
                case 17:
                    sb.append("轮毂除锈").append(",");
                    break;
                case 4:
                case 11:
                    sb.append("打蜡").append(",");
                    break;
                case 5:
                case 12:
                    sb.append("室内精洗").append(",");
                    break;
                case 18:
                case 19:
                    sb.append("引擎清洗").append(",");
                    break;
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        myHolder.tv_fuxm.setText(sb.toString());
//        Toast.makeText(context,dingdan.getO_WPart(),Toast.LENGTH_SHORT).show();
        Picasso.with(context).load(Uri.parse(Contast.Domain+dingdan.getO_WorkerImage()))
                .placeholder(R.drawable.carmoren)
                .error(R.drawable.carmoren)
                .into(myHolder.yangongtouxiang);
        myHolder.bt_dadianhua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dingdan.getO_TypeID() != 1) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dingdan.getO_WorkerTel()));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "您的订单还没有接单，请稍后", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        if (Contast.dingdan.getO_TypeID()!=1){
//            myHolder.bt_quxiao.setVisibility(View.GONE);
//        }else {
//            myHolder.bt_quxiao.setVisibility(View.VISIBLE);
//        }
        myHolder.bt_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dingdan.getO_TypeID() == 1) {
                    cehuaDiaLayout.quxiao(position);
                    myHolder.bt_quxiao.setVisibility(View.VISIBLE);
                } else {
                    myHolder.bt_dadianhua.setVisibility(View.VISIBLE);
                    myHolder.bt_quxiao.setVisibility(View.VISIBLE);
                }
            }
        });
        if (dingdan.getO_TypeID()== 1) {
            myHolder.cb_1.setVisibility(View.GONE);
            myHolder.cb_2.setVisibility(View.GONE);
            myHolder.cb_3.setVisibility(View.GONE);
            myHolder.cb_4.setVisibility(View.GONE);
            myHolder.cb_5.setVisibility(View.GONE);
            myHolder.bt_dadianhua.setVisibility(View.GONE);
        } else {
            Log.e("dingd",dingdan.getO_EvaluateStar()+"");
            if (dingdan.getO_EvaluateStar() == 5) {
                myHolder.cb_1.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_2.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_3.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_4.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_5.setBackgroundResource(R.drawable.xing_sel);
            } else if (dingdan.getO_EvaluateStar() == 4) {
                myHolder.cb_1.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_2.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_3.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_4.setBackgroundResource(R.drawable.xing_sel);
            } else if (dingdan.getO_EvaluateStar() == 3) {
                myHolder.cb_1.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_2.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_3.setBackgroundResource(R.drawable.xing_sel);
            } else if (dingdan.getO_EvaluateStar() == 2) {
                myHolder.cb_1.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_2.setBackgroundResource(R.drawable.xing_sel);
//                myHolder.cb_3.setVisibility(View.GONE);
//                myHolder.cb_4.setVisibility(View.GONE);
//                myHolder.cb_5.setVisibility(View.GONE);
            } else if (dingdan.getO_EvaluateStar() == 1) {
                myHolder.cb_1.setBackgroundResource(R.drawable.xing_sel);
//                myHolder.cb_2.setBackgroundResource(R.drawable.xing_sel);
//                myHolder.cb_2.setVisibility(View.GONE);
//                myHolder.cb_3.setVisibility(View.GONE);
//                myHolder.cb_4.setVisibility(View.GONE);
//                myHolder.cb_5.setVisibility(View.GONE);
            } else if (dingdan.getO_EvaluateStar() == 0){
                /*
                 *  假数据
                 */
                myHolder.cb_1.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_2.setBackgroundResource(R.drawable.xing_sel);
                myHolder.cb_3.setBackgroundResource(R.drawable.xing_sel);
            }

        }
    }

    public static class ViewHolder {
        public final View root;
        private TextView tv_dingdanhao;
        private TextView tv_dingdanzhuangtai;
        private TextView tv_xiadanshijian;
        private CircleImageView yangongtouxiang;
        private TextView tv_name;
        private Button bt_dadianhua;
        private Button bt_quxiao;
        private ImageView cb_1, cb_2, cb_3, cb_4, cb_5;
        private TextView tv_fuxm;
        private TextView tv_chepai;


        public ViewHolder(View root) {
            this.root = root;
            this.tv_dingdanhao = (TextView) this.root.findViewById(R.id.tv_dingdanhao);
            this.tv_dingdanzhuangtai = (TextView) this.root.findViewById(R.id.tv_dingdan_zhuangtai);
            this.tv_xiadanshijian = (TextView) this.root.findViewById(R.id.tv_xiadanshijian);
            this.yangongtouxiang = (CircleImageView) this.root.findViewById(R.id.iv_yangong_touxiang);
            this.tv_name = (TextView) this.root.findViewById(R.id.tv_name);
            this.bt_dadianhua = (Button) this.root.findViewById(R.id.bt_dadianhua);
            this.bt_quxiao = (Button) this.root.findViewById(R.id.bt_quxiao);
            this.cb_1 = (ImageView) this.root.findViewById(R.id.cb_ygpingjia_yixing);
            this.cb_2 = (ImageView) this.root.findViewById(R.id.cb_ygpingjia_erxing);
            this.cb_3 = (ImageView) this.root.findViewById(R.id.cb_ygpingjia_sanxing);
            this.cb_4 = (ImageView) this.root.findViewById(R.id.cb_ygpingjia_sixing);
            this.cb_5 = (ImageView) this.root.findViewById(R.id.cb_ygpingjia_wuxing);
            this.tv_fuxm=(TextView) this.root.findViewById(R.id.tv_fwxm);
            this.tv_chepai=(TextView)this.root.findViewById(R.id.tv_dingdan_chepai);
        }
    }
}
