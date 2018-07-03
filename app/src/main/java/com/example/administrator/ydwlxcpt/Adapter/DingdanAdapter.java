package com.example.administrator.ydwlxcpt.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Activity.AddPwdActivity;
import com.example.administrator.ydwlxcpt.Activity.DingdanXiangqingActivity;
import com.example.administrator.ydwlxcpt.Activity.MainActivity;
import com.example.administrator.ydwlxcpt.Activity.MyDingdanActivity;
import com.example.administrator.ydwlxcpt.Activity.PingjiaActivity;
import com.example.administrator.ydwlxcpt.Activity.XicheActivity;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.ActivityUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/8/15.
 */

public class DingdanAdapter extends MyBaseAdapter {

    private static final String TAG = "DingdanAdapter";
    MyDingdanActivity context;

//    private String url = Contast.Domain + "api/OrderDelete.ashx?";

    public DingdanAdapter(MyDingdanActivity context, List dataList) {
        super(context, dataList);
        this.context=context;
    }

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_dingdan_listview;
    }

    @Override
    public Object getViewHolder(View rootView) {
        return new ViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void setItemData(int position, Object dataItem, Object viewHolder) {
        final Dingdan dingdan = (Dingdan) dataItem;
        //将holder 转为自己holder
        ViewHolder myHolder = (ViewHolder) viewHolder;
        myHolder.heji.setText("¥" + dingdan.getO_Money() + "");
        myHolder.tv_daijinquan.setText("-" + "¥" + dingdan.getO_Ticket() + "");
        if (dingdan.getO_WashType() == 1) {
            String str = dingdan.getO_WashTime();
//        String shijian = str.replace("T", " ");
            myHolder.tv_shijian.setText(str);
        } else {
            String xiadan = dingdan.getO_Time();
            String shijian = xiadan.replace("T", " ");
            myHolder.tv_shijian.setText(shijian);
        }
        Picasso.with(context).load(Uri.parse(Contast.Domain + dingdan.getO_CarImage()))
                .placeholder(R.drawable.carmoren)
                .error(R.drawable.carmoren)
                .into(myHolder.lv_dingdan_tupian);
        myHolder.tv_chepaihao.setText(dingdan.getO_PlateNumber());
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
        myHolder.tv_xiangmu.setText(sb.toString());
        myHolder.tv_jiage.setText("¥" + dingdan.getO_Price());

        switch (dingdan.getO_TypeID()) {
            case 1:
                if (dingdan.getO_ISCancel() == 0) {
                    myHolder.tv_zhuangtai.setText("待接单");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                } else {
                    myHolder.tv_zhuangtai.setText("已取消");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                }
                break;
            case 2:
                if (dingdan.getO_ISCancel() == 0) {
                    myHolder.tv_zhuangtai.setText("已接单");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                } else {
                    myHolder.tv_zhuangtai.setText("已取消");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                }
                break;
            case 3:
                if (dingdan.getO_ISCancel() == 0) {
                    myHolder.tv_zhuangtai.setText("洗车中");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                } else {
                    myHolder.tv_zhuangtai.setText("已取消");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                }
                break;
            case 4:
                if (dingdan.getO_ISCancel() == 0) {
                    myHolder.tv_zhuangtai.setText("已完成");
                    myHolder.btn_qupingjia.setVisibility(View.VISIBLE);
                } else {
                    myHolder.tv_zhuangtai.setText("已取消");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                }
                break;
            case 5:
                myHolder.tv_zhuangtai.setText("申请退款");
                myHolder.btn_qupingjia.setVisibility(View.VISIBLE);
                break;
            case 6:
                myHolder.tv_zhuangtai.setText("已退款");
                myHolder.btn_qupingjia.setVisibility(View.VISIBLE);
                break;
            case 7:
                myHolder.tv_zhuangtai.setText("检查车辆状况");
                myHolder.btn_qupingjia.setVisibility(View.GONE);
                break;
            case 8:
                if (dingdan.getO_ISCancel() == 0) {
                    myHolder.tv_zhuangtai.setText("已到达");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                } else {
                    myHolder.tv_zhuangtai.setText("已取消");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                }
                break;
            case 9:
                if (dingdan.getO_ISCancel() == 0) {
                    myHolder.tv_zhuangtai.setText("赶往途中");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                } else {
                    myHolder.tv_zhuangtai.setText("已取消");
                    myHolder.btn_qupingjia.setVisibility(View.GONE);
                }
                break;
        }
        if (dingdan.getO_IsEvaluate() == 0) {
            myHolder.btn_qupingjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PingjiaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dingdan", dingdan);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        } else {
            myHolder.btn_qupingjia.setVisibility(View.GONE);
        }
        String yy = dingdan.getO_WashTime();
        long yytime = getDateFromStr(yy);
        String time = dingdan.getO_Time();
        String shijian1 = time.replace("T", " ");
        String shijian2=shijian1.substring(0,shijian1.length()-3);
        long zztime=getDateFromStr(shijian2);
        long minutes = yytime - zztime;
        long shijiancha1=minutes/1000/60;
        if (shijiancha1 > 30&&shijiancha1<60*6) {
            myHolder.tv_fuwufei.setText("(含服务费10元)");
        }else if  (shijiancha1>60*6) {
            myHolder.tv_fuwufei.setText("(含服务费20元)");
        }else if (shijiancha1<30){
            myHolder.tv_fuwufei.setVisibility(View.GONE);
        }
        myHolder.btn_zailaiyidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishAll();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("from", 1);
                context.startActivity(intent);
            }
        });
        context.dianji();
        if (context.IT!=1){
            myHolder.ll_zhuangtai.setVisibility(View.GONE);
        }else {
            myHolder.ll_zhuangtai.setVisibility(View.VISIBLE);
        }
    }

//    private void quxiaodingdan(final Dingdan dingdan, final ViewHolder myHolder) {
//        Log.i(TAG, "quxiaodingdan: " + "服务时间超时");
//        FormBody.Builder params = new FormBody.Builder();
//        params.add("O_UTel", Contast.user.getU_Tel());
//        params.add("O_PlateNumber", dingdan.getO_PlateNumber());
//        params.add("O_ID", "" + dingdan.getO_IDS());
//        params.add("keys", Contast.KEYS);
//        params.add("O_ISCancelValue", "服务时间超时");
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(params.build())
//                .build();
//        okhttp3.Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Looper.prepare();
//                Toast.makeText(context, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String string = response.body().string();
//                if (response.code() != HttpURLConnection.HTTP_OK) {
//                    Toast.makeText(context, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.i(TAG, "onResponse: " + string);
//                    if (!TextUtils.isEmpty(string)) {
//                        if (string.contains("ErrorStr")) {
//                            final Error error = JSON.parseObject(string, Error.class);
//                            Toast.makeText(context, error.getErrorStr(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Contast.user = JSON.parseObject(string, User.class);
//                            Activity activity = (Activity) context;
//                            activity.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    dataList.remove(dingdan);
//                                    notifyDataSetChanged();
//                                    Toast.makeText(context, "订单已取消", Toast.LENGTH_SHORT).show();
//                                }
//                            });
////                            dataList.remove(dingdan);
////                            notifyDataSetChanged();
////                            Toast.makeText(context, "订单已取消", Toast.LENGTH_SHORT).show();
//                        }
//                    }else {
//                        Toast.makeText(context, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//    }

    /**
     * ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     * Created by bailiangjin on 16/7/5.
     */
    public static class ViewHolder {
        public final View root;
        private TextView tv_shijian;
        private TextView tv_chepaihao;
        private TextView tv_xiangmu;
        private TextView tv_jiage;
        private TextView tv_zhuangtai;
        private Button btn_zailaiyidan;
        private Button btn_qupingjia;
        private TextView heji;
        private TextView tv_daijinquan;
        private ImageView lv_dingdan_tupian;
        private TextView tv_fuwufei;
        private LinearLayout ll_zhuangtai;

        public ViewHolder(View root) {
            this.root = root;
            this.tv_shijian = (TextView) this.root.findViewById(R.id.tv_dingdan_listview_shijian);
            this.tv_chepaihao = (TextView) this.root.findViewById(R.id.tv_dingdan_listview_fuwucheliang);
            this.tv_xiangmu = (TextView) this.root.findViewById(R.id.tv_dingdan_listview_fuwuxiangmu);
            this.tv_jiage = (TextView) this.root.findViewById(R.id.tv_dingdan_listview_dingdanjiage);
            this.tv_zhuangtai = (TextView) this.root.findViewById(R.id.tv_dingdan_zhuangtai);
            this.btn_zailaiyidan = (Button) this.root.findViewById(R.id.btn_dingdan_listview_zailaiyidan);
            this.btn_qupingjia = (Button) this.root.findViewById(R.id.btn_dingdan_listview_qupingjia);
            this.heji = (TextView) this.root.findViewById(R.id.tv_dangqiandingdan_listview_heji);
            this.tv_daijinquan = (TextView) this.root.findViewById(R.id.tv_dingdan_daijinquan);
            this.lv_dingdan_tupian = (ImageView) this.root.findViewById(R.id.lv_dingdan_tupian);
            this.tv_fuwufei = (TextView) this.root.findViewById(R.id.tv_fuwufei);
            this.ll_zhuangtai=(LinearLayout)this.root.findViewById(R.id.ll_zhuangtai);
        }
    }
    public long getDateFromStr(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Long temp = 0L;
        try {
            Date date = (Date) df.parse(dateStr);
            temp = date.getTime();
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
            return temp;
        }
    }


}
