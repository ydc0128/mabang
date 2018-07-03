package com.example.administrator.ydwlxcpt.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Activity.AddCarActivity;
import com.example.administrator.ydwlxcpt.Activity.RegisterActivity;
import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Bean.Car;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaoxianxinxiFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "BaoxianxinxiFragment";
    private String url = Contast.Domain + "api/CarMoreUpdate.ashx?";
    private EditText et_dengjiriqi;
    private EditText et_chejiahao;
    private EditText et_fadongjihao;
    private EditText et_zhengjianhaoma;
    private LinearLayout ll_zhengjianleixing;
    private TextView tv_zhengjianleixing;
    private List<String> zhengjianleixingList;
    private Button btn_baocun;
    private Car car;
    private int from;
    public BaoxianxinxiFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baoxianxinxi, container, false);
        initViews(view);
        setViews();
        return view;
    }

    private void setViews() {
        from = AddCarActivity.from;
        car = AddCarActivity.car;
        if (from == 0) {
            String str = car.getC_CarTime();
            String shijian = str.replace("T","  ");
            et_dengjiriqi.setText(shijian);
            et_chejiahao.setText(car.getC_VIN());
            et_fadongjihao.setText(car.getC_MoterNumber());
            et_zhengjianhaoma.setText(car.getC_IdentityCard());
            if(car.getC_IdentityType()==1){
                tv_zhengjianleixing.setText("居民身份证");
            }
            if(car.getC_IdentityType()==2){
                tv_zhengjianleixing.setText("护照");
            }
            if(car.getC_IdentityType()==3){
                tv_zhengjianleixing.setText("军官证");
            }
            if(car.getC_IdentityType()==4){
                tv_zhengjianleixing.setText("驾驶证");
            }
            if(car.getC_IdentityType()==5){
                tv_zhengjianleixing.setText("港澳回乡证或台胞证");
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    private void initList() {
        zhengjianleixingList = new ArrayList<>();
        zhengjianleixingList.add("居民身份证");
        zhengjianleixingList.add("护照");
        zhengjianleixingList.add("军官证");
        zhengjianleixingList.add("驾驶证");
        zhengjianleixingList.add("港澳回乡证或台胞证");
    }


    private void initViews(View view) {
        et_dengjiriqi = (EditText) view.findViewById(R.id.et_fragment_baoxianxinxi_dengjiriqi);
        et_chejiahao = (EditText) view.findViewById(R.id.et_fragment_baoxianxinxi_chejiahao);
        et_fadongjihao = (EditText) view.findViewById(R.id.et_fragment_baoxianxinxi_fadongjihao);
        et_zhengjianhaoma = (EditText) view.findViewById(R.id.et_fragment_baoxianxinxi_zhengjianhaoma);
        tv_zhengjianleixing = (TextView) view.findViewById(R.id.tv_fragment_baoxianxinxi_zhengjianleixing);
        ll_zhengjianleixing = (LinearLayout) view.findViewById(R.id.ll_fragment_baoxianxinxi_zhengjianleixing);
        btn_baocun = (Button) view.findViewById(R.id.btn_fragment_baoxianxinxi_baocun);

        ll_zhengjianleixing.setOnClickListener(this);
        btn_baocun.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_fragment_baoxianxinxi_zhengjianleixing:
                View bottomView = View.inflate(getActivity(), R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView = (ListView) bottomView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView.setAdapter(new DialogListViewAdapter(getActivity(), zhengjianleixingList));//ListView设置适配器
                final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setView(bottomView)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        tv_zhengjianleixing.setText(aiche);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_fragment_baoxianxinxi_baocun:
                if (from == 0) {
                    String time = et_dengjiriqi.getText().toString();
                    String chejiahao = et_chejiahao.getText().toString();
                    String fadongjihao = et_fadongjihao.getText().toString();
                    String leixing = tv_zhengjianleixing.getText().toString();
                    String zhengjianhao = et_zhengjianhaoma.getText().toString();
                    if (TextUtils.isEmpty(time)) {
                        Toast.makeText(getActivity(), "登记日期不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(chejiahao)) {
                        Toast.makeText(getActivity(), "车架号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(fadongjihao)) {
                        Toast.makeText(getActivity(), "发动机号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (leixing.equals("请选择")) {
                        Toast.makeText(getActivity(), "请选择证件类型", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(zhengjianhao)) {
                        Toast.makeText(getActivity(), "证件号码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final ProgressDialog pd = new ProgressDialog(getActivity());
                    pd.setMessage("拼命加载中...");
                    pd.show();
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("C_UTel", Contast.user.getU_Tel());
                    params.add("keys", Contast.KEYS);
                    params.add("C_PlateNumber", car.getC_PlateNumber());
                    params.add("C_CarTypeID", "5");
                    params.add("C_CarTime", time);
                    params.add("C_VIN", chejiahao);
                    params.add("C_MoterNumber", fadongjihao);
                    if (leixing.equals("居民身份证")) {
                        params.add("C_IdentityType", "1");
                    }
                    if (leixing.equals("护照")) {
                        params.add("C_IdentityType", "2");
                    }
                    if (leixing.equals("军官证")) {
                        params.add("C_IdentityType", "3");
                    }
                    if (leixing.equals("驾驶证")) {
                        params.add("C_IdentityType", "4");
                    }
                    if (leixing.equals("港澳回乡证台胞证")) {
                        params.add("C_IdentityType", "5");
                    }
                    params.add("C_IdentityCard", zhengjianhao);
                    OkHttpClient client = new OkHttpClient();
                    Request request=new Request.Builder()
                            .url(url)
                            .post(params.build())
                            .build();
                    okhttp3.Call call=client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            pd.dismiss();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "上传失败，请稍后重试...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            pd.dismiss();
                            String string = response.body().string();
                            if (response.code() != HttpURLConnection.HTTP_OK) {
                                Toast.makeText(getActivity(), "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.i(TAG, "onResponse: " + string);
                                if (!TextUtils.isEmpty(string)) {
                                    if (string.contains("ErrorStr")) {
                                        final Error error = JSON.parseObject(string, Error.class);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        car = JSON.parseObject(string, Car.class);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), "详细信息添加成功", Toast.LENGTH_SHORT).show();
                                                getActivity().finish();
                                            }
                                        });
                                    }
                                } else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "请先保存车辆基本信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
