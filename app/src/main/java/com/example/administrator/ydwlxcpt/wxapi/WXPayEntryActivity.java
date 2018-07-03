package com.example.administrator.ydwlxcpt.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Activity.BaseActivity;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
//    private String url_login = Contast.Domain + "api/UserLoginDefault.ashx?";

    @BindView(R.id.iv_tixianxiangqing_back)
    ImageView ivTixianxiangqingBack;
    //    @BindView(R.id.tv_jyxq_daozhangshijian)
//    TextView tvJyxqDaozhangshijian;
//    @BindView(R.id.tv_jyxq_yinhangka)
//    TextView tvJyxqYinhangka;
//    @BindView(R.id.tv_jyxq_tixianjine)
//    TextView tvJyxqTixianjine;
//    @BindView(R.id.tv_jyxq_chuangjianshijian)
//    TextView tvJyxqChuangjianshijian;
//    @BindView(R.id.tv_jyxq_dingdanhao)
//    TextView tvJyxqDingdanhao;
    @BindView(R.id.bt_jyxq_tixian)
    Button btJyxqTixian;
    @BindView(R.id.ll_chongzhi_chenggong)
    LinearLayout llChongzhiChenggong;
    @BindView(R.id.ll_chongzhi_shibai)
    LinearLayout llChongzhiShibai;
    private IWXAPI api;
    public static int chongzhiqian ;
    private String url_login = Contast.Domain + "api/UserLoginDefault.ashx?";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        ButterKnife.bind(this);
        chongzhiqian = getIntent().getIntExtra("U_Mone", Contast.user.getU_Money());
        api = WXAPIFactory.createWXAPI(this, Contast.APP_ID);
        api.handleIntent(getIntent(), this);
        shuaxin();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    //微信支付结果回调
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.e(TAG, "onPayFinish,errCode=" + resp.errCode);
            switch (resp.errCode) {
                //0  成功	展示成功页面
                //如果支付成功则去后台查询支付结果再展示用户实际支付结果
                case 0:
                    Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    break;
                //-1  错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                case -1:
                    Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                //-2  用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
                case -2:
                    Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    }


    @OnClick({R.id.iv_tixianxiangqing_back, R.id.bt_jyxq_tixian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_tixianxiangqing_back:
                finish();
                break;
            case R.id.bt_jyxq_tixian:
                finish();
                break;
        }
    }

    private void shuaxin() {
        FormBody.Builder params = new FormBody.Builder();
        params.add("U_Tel", Contast.user.getU_Tel());
        params.add("U_IMEI", Contast.user.getU_IMEI());
        params.add("keys", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_login)
                .post(params.build())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //响应失败
                Toast.makeText(WXPayEntryActivity.this, "网络连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                String string = response.body().string();
                Log.i(TAG, "onResponse: string=" + string);
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(WXPayEntryActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "onResponse: json=" + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WXPayEntryActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            User user = JSON.parseObject(string, User.class);
                            Contast.user = user;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int chongzhihou = Contast.user.getU_Money();
                                    if (chongzhihou == chongzhiqian) {
                                        llChongzhiChenggong.setVisibility(View.GONE);
                                        llChongzhiShibai.setVisibility(View.VISIBLE);
                                    }else {

                                        llChongzhiChenggong.setVisibility(View.VISIBLE);
                                        llChongzhiShibai.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WXPayEntryActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }


}
