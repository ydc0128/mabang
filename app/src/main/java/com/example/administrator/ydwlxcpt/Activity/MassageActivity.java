package com.example.administrator.ydwlxcpt.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.MSGAdapter;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.Massage;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by Administrator on 2018/1/11.
 */
//消息
public class MassageActivity extends BaseActivity {
    private static final String TAG = "XIAOXI";
    @BindView(R.id.iv_msg_back)
    ImageView ivMsgBack;
    @BindView(R.id.tv_msg_title)
    TextView tvMsgTitle;
    @BindView(R.id.btn_msg_more)
    Button btnMsgMore;
    @BindView(R.id.ptrlv_masg_listview)
    PullToRefreshListView ptrlvMasgListview;
//    @BindView(R.id.riqi)
//    TextView riqi;
//    @BindView(R.id.xianhao)
//    TextView xianhao;
    @BindView(R.id.tv_xiaoxi)
    ListView tvXiaoxi;
    @BindView(R.id.rl_myxiaoxi_null)
    RelativeLayout rlMyxiaoxiNull;
    @BindView(R.id.ll_xianhao)
    LinearLayout llXianhao;
    private String url = Contast.Domain + "api/MessageList.ashx";
    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWay;
    private String shijian;
    private List<Massage> massageList;
    private MSGAdapter adapter;
    private List<Massage> jinxingzhongList;
    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        ButterKnife.bind(this);
        initListView();
//      xianxing();
    }
    private void initListView() {
        PullToRefreshListView listView = (PullToRefreshListView) findViewById(R.id.ptrlv_masg_listview);
        massageList = new ArrayList<>();
        adapter = new MSGAdapter(this, massageList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Massage massage = (Massage) adapter.getItem(i - 1);
                Intent intent = new Intent();
                switch (massage.getM_PartID()) {
                    case 1:
                        break;
                    case 2:
                        intent.setClass(getApplicationContext(), ChongZhiTongZhi.class);
                        intent.putExtra("chongzhi", massage);
                        MassageActivity.this.startActivity(intent);
                        flag++;
                        break;
                    case 3:
                        intent.setClass(getApplicationContext(), YouHuiJuanTongzhi.class);
                        intent.putExtra("yhj", massage);
                        MassageActivity.this.startActivity(intent);
                       flag++;
                }
            }
        });
    }
    public void refelash() {
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                pd.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MassageActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });
            }
            @Override
            public void onResponse(final Call call, Response response) throws IOException {
//                pd.dismiss();
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(MassageActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MassageActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;

                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    massageList = JSON.parseArray(string, Massage.class);
                                    jinxingzhongList = new ArrayList<>();
                                    for (Massage item : massageList) {
                                        int type = item.getM_PartID();
                                        if (item.getM_PartID() != 1)
                                            if (type != 1 && type == 2 || type == 3) {
                                                    jinxingzhongList.add(item);
                                                    Log.e("xiqaoxi", jinxingzhongList.toString());
                                                    checkList(jinxingzhongList);
                                                    adapter.setData(jinxingzhongList);
                                            }
                                        int weidu = jinxingzhongList.size() - flag;
                                        weidu--;
                                        SharedPreferences sh = getSharedPreferences("weidu", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sh.edit();
                                        editor.putString("wdsl", weidu + "");
                                        editor.commit();
                                    }
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MassageActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    }
                }
            }

        });
    }

//


    @Override
    protected void onResume() {
        super.onResume();
        refelash();
    }

    //    private void tixian() {
//        final ProgressDialog pd = new ProgressDialog(MassageActivity.this);
//        pd.setMessage("拼命加载中...");
//        pd.show();
//        FormBody.Builder params = new FormBody.Builder();
//        params.add("keys", Contast.KEYS);
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .post(params.build())
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                pd.dismiss();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MassageActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                pd.dismiss();
//                final String string = response.body().string();
//                if (response.code() != HttpURLConnection.HTTP_OK) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(MassageActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
//                            Log.i("TAG", string);
//                        }
//                    });
//                } else {
//                    if (!TextUtils.isEmpty(string)) {
//                        if (string.contains("ErrorStr")) {
//                            final Error error = JSON.parseObject(string, Error.class);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(MassageActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else {
//                            msglist = JSON.parseArray(string, Massage.class);
//
//                            Log.i("aaaaaaaaaaaaaa", msglist.toString() + msglist.size());
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (xxlx.equals("2")) {
//                                        chongzhiNeirong.setText(neirong);
//                                        chongzhiRiqi.setText(biaoti);
//                                        llChongzhi.setVisibility(View.VISIBLE);
//                                        llChongzhi.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View view) {
//                                                Intent intent = new Intent(MassageActivity.this, ChongZhiTongZhi.class);
//                                                startActivity(intent);
//                                            }
//                                        });
//
//                                    } else if (xxlx.equals("3")) ;
//                                    youhuijuanNeirong.setText(neirong);
//                                    youhuijuanRiqi.setText(biaoti);
//                                    llYouhuijuan.setVisibility(View.VISIBLE);
//                                    llYouhuijuan.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            Intent intent1 = new Intent(MassageActivity.this, YouHuiJuanTongzhi.class);
//                                            startActivity(intent1);
//                                        }
//                                    });
//
//                                }
//                            });
//                        }
//                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MassageActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//            }
//        });
    private void checkList(List<Massage> list) {
        if (list.size() > 0) {
            rlMyxiaoxiNull.setVisibility(View.INVISIBLE);
        } else {
            rlMyxiaoxiNull.setVisibility(View.VISIBLE);
        }
    }

    private void xianxing() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        shijian = mYear + "-" + mMonth + "-" + mDay;
        Calendar start = Calendar.getInstance();
        start.set(2017, 11, 20);
        Calendar end = Calendar.getInstance();
        end.set(2018, 3, 15);
        Calendar now = Calendar.getInstance();
        Calendar start1 = Calendar.getInstance();
        start1.set(2018, 4, 16);
        Calendar end1= Calendar.getInstance();
        end1.set(2018, 9, 30);
        Calendar now1 = Calendar.getInstance();
        if (now.before(start) && now.after(end)||now1.before(start) && now.after(end)) {
            llXianhao.setVisibility(View.GONE);
        } else {
            if ("1".equals(mWay)) {
                mWay = "天";
                Toast.makeText(MassageActivity.this, "今天是周末，不限行", Toast.LENGTH_SHORT).show();
//                xianhao.setText("今天是周末，不限行");
//                riqi.setText(shijian + "  " + "周" + mWay);
            } else if ("2".equals(mWay)) {
                mWay = "一";
                Toast.makeText(MassageActivity.this, "今天限行尾号：1和6", Toast.LENGTH_SHORT).show();
//                xianhao.setText("今天限行尾号：1和6");
//                riqi.setText(shijian + "  " + "周" + mWay);
            } else if ("3".equals(mWay)) {
                mWay = "二";
                Toast.makeText(MassageActivity.this, "今天限行尾号：2和7", Toast.LENGTH_SHORT).show();
//                xianhao.setText("今天限行尾号：2和7");
//                riqi.setText(shijian + "  " + "周" + mWay);
            } else if ("4".equals(mWay)) {
                mWay = "三";
                Toast.makeText(MassageActivity.this, "今天限行尾号：3和8", Toast.LENGTH_SHORT).show();
//                riqi.setText(shijian + "  " + "周" + mWay);
//                xianhao.setText("今天限行尾号：3和8");
            } else if ("5".equals(mWay)) {
                mWay = "四";
                Toast.makeText(MassageActivity.this, "今天限行尾号：4和9", Toast.LENGTH_SHORT).show();
//                xianhao.setText("今天限行尾号：4和9");
            } else if ("6".equals(mWay)) {
                mWay = "五";
                Toast.makeText(MassageActivity.this, "今天限行尾号：5和0", Toast.LENGTH_SHORT).show();
//                riqi.setText(shijian + "  " + "周" + mWay);
//                xianhao.setText("今天限行尾号：5和0");
            } else if ("7".equals(mWay)) {
                mWay = "六";
                Toast.makeText(MassageActivity.this, "今天是周末，不限行", Toast.LENGTH_SHORT).show();
//                riqi.setText(shijian + "  " + "周" + mWay);
//                xianhao.setText("今天是周末，不限行");
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @OnClick({R.id.iv_msg_back, R.id.btn_msg_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_msg_back:
                finish();
                break;
            case R.id.btn_msg_more:
                break;
        }
    }
}