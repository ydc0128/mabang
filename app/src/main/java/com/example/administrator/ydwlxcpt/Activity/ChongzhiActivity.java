package com.example.administrator.ydwlxcpt.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.baidu.platform.comjni.util.AppMD5;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.PayResult;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Bean.WeinXinResult;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.MD5Util;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChongzhiActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private PopupWindow popupWindow;
    private View contentView;

    private String url = Contast.Domain + "GetResponse.aspx?";
    private static String PartnerId = "1491829132";//微信支付商户私钥
    private String url_weixin = Contast.Domain + "AppServer.aspx?";//http://apiceshi.mabangxiche.com/AppServer.aspx";
    private String url_login = Contast.Domain + "api/UserLoginDefault.ashx?";
    private static final String TAG = "ChongzhiActivity";

    private static final int SDK_PAY_FLAG = 1;

    private ImageView iv_back;
    private Button btn_chongzhijilu;
    private Button btn_lijichongzhi;
    private RadioButton rb_dangqianzhanghu;
    private RadioButton rb_tarenchongzhi;
    private View line_dangqianzhanghu;
    private View line_tarenchongzhi;
    private EditText et_phone;
    private RadioButton rb_100;
    private RadioButton rb_300;
    private RadioButton rb_500;
    private RadioButton rb_1000;
    private RadioButton rb_2000;
    private RadioButton rb_qita;
    private TextView tv_yue;
    private TextView tv_yue_jine;
    private TextView tv_xieyi;
    private EditText et_jine;
    private ImageView iv_pop_back;


    private CheckBox cb_weixin;
    private CheckBox cb_zhifubao;
    private TextView tv_pop_yue;
    private Button querenfukuan;
    private IWXAPI api;
    private String from;
    private Double chajia;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ChongzhiActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        initUser();
                        popupWindow.dismiss();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ChongzhiActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private void initUser() {

        FormBody.Builder params = new FormBody.Builder();
        params.add("U_Tel", Contast.user.getU_Tel());
        params.add("U_IMEI", Contast.user.getU_IMEI());
        params.add("keys", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_login)
                .post(params.build())
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //响应失败
                Toast.makeText(ChongzhiActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                //响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                String string = response.body().string();
                Log.i(TAG, "onResponse: string=" + string);
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(ChongzhiActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "onResponse: json=" + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChongzhiActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            User user = JSON.parseObject(string, User.class);
                            Contast.user = user;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_yue_jine.setText("" + Contast.user.getU_Money());
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChongzhiActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chongzhi);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
//        if("chenggong".equals(from)){
//            initUser();
//            Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
//        }else if("shibai".equals(from)){
//            Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
//        }else if("quxiao".equals(from)){
//            Toast.makeText(this,"支付取消",Toast.LENGTH_SHORT).show();
//        }
        initViews();
         chajia=intent.getDoubleExtra("chajia",0);
        if (chajia==0){

        }else {
            et_jine.setText(chajia.toString());
        }
        //商户APP工程中引入微信JAR包，调用API前，需要先向微信注册您的APPID
        api = WXAPIFactory.createWXAPI(ChongzhiActivity.this, null);
        // 将该app注册到微信
        api.registerApp(Contast.APP_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        et_phone.setText(Contast.user.getU_Tel());
        et_phone.setFocusable(false);
        initUser();
    }
    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_chongzhi_back);
        btn_chongzhijilu = (Button) findViewById(R.id.btn_chongzhi_more);
        btn_lijichongzhi = (Button) findViewById(R.id.btn_chongzhi_submit);
        rb_dangqianzhanghu = (RadioButton) findViewById(R.id.rb_chongzhi_dangqianzhanghu);
        rb_tarenchongzhi = (RadioButton) findViewById(R.id.rb_chongzhi_tarenchongzhi);
        line_dangqianzhanghu = findViewById(R.id.line_chongzhi_dangqianzhanghu);
        line_tarenchongzhi = findViewById(R.id.line_chongzhi_tarenchongzhi);
        tv_yue = (TextView) findViewById(R.id.tv_chongzhi_yue);
        tv_xieyi = (TextView) findViewById(R.id.tv_chongzhi_xieyi);
        tv_yue_jine = (TextView) findViewById(R.id.tv_chongzhi_yue_jine);
        et_jine = (EditText) findViewById(R.id.et_chongzhi_jine);
        et_phone = (EditText) findViewById(R.id.et_chongzhi_phone);
        rb_100 = (RadioButton) findViewById(R.id.rb_chongzhi_100);
        rb_300 = (RadioButton) findViewById(R.id.rb_chongzhi_300);
        rb_500 = (RadioButton) findViewById(R.id.rb_chongzhi_500);
        rb_1000 = (RadioButton) findViewById(R.id.rb_chongzhi_1000);
        rb_2000 = (RadioButton) findViewById(R.id.rb_chongzhi_2000);
        rb_qita = (RadioButton) findViewById(R.id.rb_chongzhi_qita);
        iv_back.setOnClickListener(this);
        tv_xieyi.setOnClickListener(this);
        btn_chongzhijilu.setOnClickListener(this);
        btn_lijichongzhi.setOnClickListener(this);
        rb_dangqianzhanghu.setOnCheckedChangeListener(this);
        rb_tarenchongzhi.setOnCheckedChangeListener(this);
        rb_100.setOnCheckedChangeListener(this);
        rb_300.setOnCheckedChangeListener(this);
        rb_500.setOnCheckedChangeListener(this);
        rb_1000.setOnCheckedChangeListener(this);
        rb_2000.setOnCheckedChangeListener(this);
        rb_qita.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_chongzhi_back:
                finish();
                break;
            case R.id.tv_chongzhi_xieyi:
                Intent intent2 = new Intent(ChongzhiActivity.this, ContentActivity.class);
                intent2.putExtra("from", "chongzhixieyi");
                startActivity(intent2);
                break;
            case R.id.btn_chongzhi_more:
                Intent intent = new Intent(ChongzhiActivity.this, ChongzhimingxiActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_chongzhi_submit:
                String  jine = et_jine.getText().toString();
                String phone = et_phone.getText().toString();
                if (TextUtils.isEmpty(jine)) {
                    Toast.makeText(ChongzhiActivity.this, "请先选择您要充值的金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                String first = jine.substring(0, 1);
                if ("0".equals(first)) {
                    Toast.makeText(ChongzhiActivity.this, "充值金额首位不能为0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(jine)) {
                    Toast.makeText(ChongzhiActivity.this, "请先选择您要充值的金额", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(ChongzhiActivity.this, "请选择您要充值的账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                int chongzhi= Integer.parseInt(et_jine.getText().toString());
                if (chongzhi<10||chongzhi>1000){
                    Toast.makeText(ChongzhiActivity.this, "充值金额应大于10元", Toast.LENGTH_SHORT).show();
                    return;
                }
                showPopwindow();
                popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.4f);
                break;
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */

    public void backgroundAlpha(float bgAlpha)

    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();

        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);

    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        //加载弹出框的布局
        contentView = LayoutInflater.from(ChongzhiActivity.this).inflate(
                R.layout.item_pop_down, null);

        cb_weixin = (CheckBox) contentView.findViewById(R.id.cb_item_pop_weixin);
        cb_zhifubao = (CheckBox) contentView.findViewById(R.id.cb_item_pop_zhifubao);
        tv_pop_yue = (TextView) contentView.findViewById(R.id.tv_item_pop_jine);
        querenfukuan = (Button) contentView.findViewById(R.id.btn_item_pop_querenfukuan);
        iv_pop_back = (ImageView) contentView.findViewById(R.id.iv_item_pop_back);

        iv_pop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        cb_weixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_zhifubao.setChecked(false);
                }
            }
        });

        cb_zhifubao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_weixin.setChecked(false);
                }
            }
        });

        tv_pop_yue.setText(et_jine.getText().toString().trim());

        querenfukuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ChongzhiActivity.this,"暂时不支持微信支付",Toast.LENGTH_SHORT).show();
                //TODO
                //根据选择支付方式的不同跳转不同平台进行支付
                if (cb_weixin.isChecked()) {
//                    Toast.makeText(ChongzhiActivity.this,"暂时不支持微信支付",Toast.LENGTH_SHORT).show();
                    if (isWeixinAvilible(ChongzhiActivity.this)) {
                        weixin();
                    } else {
                        Toast.makeText(ChongzhiActivity.this, "请先安装微信再进行支付", Toast.LENGTH_SHORT).show();
                    }
                } else if (cb_zhifubao.isChecked()) {
                    zhifubao();
                }
            }
        });

        //设置弹出框的宽度和高度
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);

        // 按下android回退物理键 PopipWindow消失解决
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        return true;
                    }
                }
                return false;
            }
        });

        backgroundAlpha(1f);
        //添加pop窗口关闭事件
        popupWindow.setOnDismissListener(new poponDismissListener());
    }

    //微信调起支付
    private void weixin() {
        //TODO
        //发送网络请求，对验证码进行确认
        final String jine = tv_pop_yue.getText().toString();
        Log.i(TAG, "weixin: jine=" + jine);
        int chongzhi = Integer.parseInt(jine) * 100;//正式版数据
//        int chongzhi = Integer.parseInt(jine) ;//测试版数据
        Log.i(TAG, "weixin: chongzhi=" + chongzhi);
        FormBody.Builder params = new FormBody.Builder();
        params.add("UTel", Contast.user.getU_Tel());
        params.add("WTel", et_phone.getText().toString().trim());
        params.add("fei", "" + chongzhi);
        params.add("keycode", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_weixin)
                .post(params.build())
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChongzhiActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.i(TAG, "onResponse: string=" + string);
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(ChongzhiActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChongzhiActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Log.i(TAG, "onResponse: string=" + string);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    WeinXinResult weinXinResult = JSON.parseObject(string, WeinXinResult.class);
                                    Log.i(TAG, "run: weinXinResult=" + weinXinResult.toString());
                                    PayReq request = new PayReq();
                                    request.appId = weinXinResult.getAppid();
                                    request.partnerId = weinXinResult.getPartnerid();
                                    request.prepayId = weinXinResult.getPrepayid();
                                    request.packageValue = "Sign=WXPay";
                                    request.nonceStr = weinXinResult.getNoncestr();
                                    request.timeStamp = weinXinResult.getTimestamp();
//                                    request.sign = weinXinResult.getSign();

                                    //二次签名
                                    // 把参数的值传进去SortedMap集合里面
                                    SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
                                    parameters.put("appid", request.appId);
                                    parameters.put("noncestr", request.nonceStr);
                                    parameters.put("package", request.packageValue);
                                    parameters.put("partnerid", request.partnerId);
                                    parameters.put("prepayid", request.prepayId);
                                    parameters.put("timestamp", request.timeStamp);

                                    String characterEncoding = "UTF-8";
                                    String mySign = createSign(characterEncoding, parameters);
                                    Log.i(TAG, "run: mySign =" + mySign);
                                    request.sign = mySign;

                                    boolean b = api.sendReq(request);
                                    Log.i(TAG, "run: b=" + b);
//                                    if (b) {
//                                        popupWindow.dismiss();
//                                        SharedPreferences sp = getSharedPreferences("Money",MODE_PRIVATE);
//                                        SharedPreferences.Editor edit = sp.edit();
//                                        int money = Integer.parseInt(jine) ;
//                                        edit.putInt("money",money);
//                                        edit.commit();
//                                    }
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChongzhiActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 微信支付签名算法sign
     *
     * @param characterEncoding
     * @param parameters
     * @return
     */
    public static String createSign(String characterEncoding,
                                    SortedMap<Object, Object> parameters) {

        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + Contast.APP_KEY); //KEY是商户秘钥
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding)
                .toUpperCase();
        return sign; // D3A5D13E7838E1D453F4F2EA526C4766
        // D3A5D13E7838E1D453F4F2EA526C4766
    }

    //判断用户是否安装微信
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    //支付宝调起支付
    private void zhifubao() {
        //TODO
        //发送网络请求，对验证码进行确认
        FormBody.Builder params = new FormBody.Builder();
        params.add("Body", et_phone.getText().toString().trim());//到账方
        params.add("Subject", Contast.user.getU_Tel());//付款方
        params.add("TotalAmount", tv_pop_yue.getText().toString());//充值金额
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChongzhiActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i(TAG, "onResponse: string=" + string);
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(ChongzhiActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChongzhiActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        } else {
                            Log.i(TAG, "onResponse: string=" + string);
                            final String orderInfo = string;
                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(ChongzhiActivity.this);
                                    Map<String, String> result = alipay.payV2(orderInfo, true);

                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();

                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChongzhiActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb_chongzhi_dangqianzhanghu:
                if (isChecked) {
                    line_dangqianzhanghu.setVisibility(View.VISIBLE);
                    line_tarenchongzhi.setVisibility(View.INVISIBLE);
                    et_phone.setText(Contast.user.getU_Tel());
                    et_phone.setFocusableInTouchMode(false);
                    et_phone.clearFocus();
                    tv_yue.setVisibility(View.VISIBLE);
                    tv_yue_jine.setVisibility(View.VISIBLE);
                    tv_yue_jine.setText("" + Contast.user.getU_Money());
                    rb_tarenchongzhi.setChecked(false);
                }
                break;
            case R.id.rb_chongzhi_tarenchongzhi:
                if (isChecked) {
                    line_tarenchongzhi.setVisibility(View.VISIBLE);
                    line_dangqianzhanghu.setVisibility(View.INVISIBLE);
                    et_phone.setFocusableInTouchMode(true);
                    et_phone.requestFocus();
                    et_phone.setEnabled(true);
                    et_phone.setText(null);
                    et_phone.setHint("请输入您要充值的账号");
                    tv_yue.setVisibility(View.INVISIBLE);
                    tv_yue_jine.setVisibility(View.INVISIBLE);
                    rb_dangqianzhanghu.setChecked(false);
                } else {
                    line_dangqianzhanghu.setVisibility(View.VISIBLE);
                    line_tarenchongzhi.setVisibility(View.INVISIBLE);
                    et_phone.setText(Contast.user.getU_Tel());
                    et_phone.setFocusable(false);
                    et_phone.setEnabled(false);
                    tv_yue.setVisibility(View.VISIBLE);
                    tv_yue_jine.setVisibility(View.VISIBLE);
                    tv_yue_jine.setText("" + Contast.user.getU_Money());
                    rb_dangqianzhanghu.setChecked(true);
                }
                break;

            case R.id.rb_chongzhi_100:
                if (isChecked) {
                    et_jine.setText("100");
                    et_jine.setFocusableInTouchMode(false);
                    et_jine.clearFocus();
                    rb_300.setChecked(false);
                    rb_500.setChecked(false);
                    rb_1000.setChecked(false);
                    rb_2000.setChecked(false);
                    rb_qita.setChecked(false);
                }
                break;
            case R.id.rb_chongzhi_300:
                if (isChecked) {
                    et_jine.setText("300");
                    et_jine.setFocusableInTouchMode(false);
                    et_jine.clearFocus();
                    rb_100.setChecked(false);
                    rb_500.setChecked(false);
                    rb_1000.setChecked(false);
                    rb_2000.setChecked(false);
                    rb_qita.setChecked(false);
                }
                break;
            case R.id.rb_chongzhi_500:
                if (isChecked) {
                    et_jine.setText("500");
                    et_jine.setFocusableInTouchMode(false);
                    et_jine.clearFocus();
                    rb_300.setChecked(false);
                    rb_100.setChecked(false);
                    rb_1000.setChecked(false);
                    rb_2000.setChecked(false);
                    rb_qita.setChecked(false);
                }
                break;
            case R.id.rb_chongzhi_1000:
                if (isChecked) {
                    et_jine.setText("1000");
                    et_jine.setFocusableInTouchMode(false);
                    et_jine.clearFocus();
                    rb_300.setChecked(false);
                    rb_500.setChecked(false);
                    rb_100.setChecked(false);
                    rb_2000.setChecked(false);
                    rb_qita.setChecked(false);
                }
                break;
            case R.id.rb_chongzhi_2000:
                if (isChecked) {
                    et_jine.setText("1500");
                    et_jine.setFocusableInTouchMode(false);
                    et_jine.clearFocus();
                    rb_300.setChecked(false);
                    rb_500.setChecked(false);
                    rb_1000.setChecked(false);
                    rb_100.setChecked(false);
                    rb_qita.setChecked(false);
                }
                break;
            case R.id.rb_chongzhi_qita:
                if (isChecked) {
                    et_jine.setFocusableInTouchMode(true);
                    et_jine.requestFocus();
                    et_jine.setText(null);
                    et_jine.setHint("请输入金额");
                    rb_300.setChecked(false);
                    rb_500.setChecked(false);
                    rb_1000.setChecked(false);
                    rb_2000.setChecked(false);
                    rb_100.setChecked(false);
                }
                break;

        }
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }
}


