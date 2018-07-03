package com.example.administrator.ydwlxcpt.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.CehuaAdapter;
import com.example.administrator.ydwlxcpt.Bean.Car;
import com.example.administrator.ydwlxcpt.Bean.Chongyongdizhi;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.necer.ndialog.NDialog;


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
 * Created by Administrator on 2018/2/28.
 */

public class CehuaDiaLayout extends Activity {
    private List<Dingdan> dingdanList;
    private CehuaAdapter adapter;
    private String url = Contast.Domain + "api/OrderList.ashx?";
    //  private List<String> quxiaoyuanyin;
    private String url_quxiao = Contast.Domain + "api/OrderDelete.ashx?";
    private List<Dingdan> jinxingzhongList;
    private RelativeLayout rl_meiyoushuju;
    private ImageView iv_tuichu;
    private String shuliang;
    public static CehuaDiaLayout instance = null;
    private String tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_cehua_you);
        initListView();
        instance = this;
        refelash();
//        setFinishOnTouchOutside(true);
//        WindowManager m = getWindowManager();
//        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
//        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
//        p.height = (int) (d.getHeight() * 1.0); // 高度设置为屏幕的0.3
//        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
//        getWindow().setAttributes(p);
//        p.gravity = Gravity.RIGHT;//设置对话框置顶显示
    }

    private void initListView() {
        iv_tuichu = (ImageView) findViewById(R.id.iv_cehua_tuichu);
        ListView listView = (ListView) findViewById(R.id.list_mian_dingdan);
        rl_meiyoushuju = (RelativeLayout) findViewById(R.id.meiyoushuju);
        dingdanList = new ArrayList<>();
        adapter = new CehuaAdapter(this, dingdanList);
        listView.setAdapter(adapter);
        listView.setAdapter(adapter);
        iv_tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void refelash() {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        String tel = sp.getString("U_Tel", Contast.user.getU_Tel());
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("O_UTel", tel);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                pd.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CehuaDiaLayout.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
//                pd.dismiss();
                final String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(CehuaDiaLayout.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CehuaDiaLayout.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dingdanList = JSON.parseArray(string, Dingdan.class);
                                    jinxingzhongList = new ArrayList<>();
                                    for (Dingdan item : dingdanList) {
                                        int type = item.getO_TypeID();
                                        if (item.getO_ISCancel() == 0)
                                            if (type == 1 || type == 2 || type == 3 || type == 8 || type == 7 || type == 9) {
                                                jinxingzhongList.add(item);
//                                                SharedPreferences sp1 = getSharedPreferences("dingdan_xiangqing", MODE_PRIVATE);
//                                                SharedPreferences.Editor edit1 = sp1.edit();
//                                                edit1.putString("O_PlateNumber", item.getO_PlateNumber() + "");
//                                                edit1.putString("O_ID", item.getO_IDS() + "");
//                                                edit1.commit();
//                                                Log.e("xingji", jinxingzhongList.toString());

                                            }
                                    }
                                    if (jinxingzhongList.size() > 0) {
                                        rl_meiyoushuju.setVisibility(View.GONE);
                                    } else {
                                        rl_meiyoushuju.setVisibility(View.VISIBLE);
                                    }
                                    adapter.setData(jinxingzhongList);
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CehuaDiaLayout.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }

        });
    }

    public void quxiao(final int position) {
        final String quxiaoyuanyin[] = {"行程改变", "我想重新下单", "车辆信息不符", "洗车工要求取消订单", "其他原因"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        TextView title = new TextView(this);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setText("取消原因");
        title.setTextSize(22);//字体大小
        title.setPadding(0, 30, 0, 30);//位置
        title.setTextColor(this.getResources().getColor(R.color.black));
        builder.setCustomTitle(title);
        // builder.setMessage("是否确认退出?"); //设置内容
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (quxiaoyuanyin != null) {
//                    String yuanyin = quxiaoyuanyin.toString();
                    quxiaodingdan(tt, position);
                    dialog.dismiss();
                } else {
                    Toast.makeText(CehuaDiaLayout.this, "请选择取消原因", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setSingleChoiceItems(quxiaoyuanyin, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tt = quxiaoyuanyin[which];
            }
        });
        builder.create().show();
    }

    private void quxiaodingdan(final String yuanyin, final int position) {
        final ProgressDialog pd = new ProgressDialog(CehuaDiaLayout.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        Dingdan dingdan = (Dingdan) adapter.getItem(position);
        FormBody.Builder params = new FormBody.Builder();
        params.add("O_UTel", Contast.user.getU_Tel());
        params.add("O_PlateNumber", dingdan.getO_PlateNumber());
        params.add("O_ID", "" + dingdan.getO_IDS());
        params.add("keys", Contast.KEYS);
        if (!TextUtils.isEmpty(yuanyin)) {
            params.add("O_ISCancelValue", yuanyin);
        } else {
            params.add("O_ISCancelValue", "");
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_quxiao)
                .post(params.build())
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pd.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CehuaDiaLayout.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pd.dismiss();
                String string = response.body().string();
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CehuaDiaLayout.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CehuaDiaLayout.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Contast.user = JSON.parseObject(string, User.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CehuaDiaLayout.this, "订单已取消", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(DingdanXiangqingActivity.this,MyDingdanActivity.class);
//                                startActivity(intent);
                                    Log.w("tttt", yuanyin);
                                    finish();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CehuaDiaLayout.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }
        });
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
////        Intent intent = new Intent(CehuaDiaLayout.this, MainActivity.class);
////                intent.putExtra("shuliang", jinxingzhongList.size()+"");
////                Log.e("111111111",jinxingzhongList.size()+"");
////                startActivityForResult(intent,1);
//}
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.translate_dialog_in, R.anim.translate_dialog_out);
//    }
}
