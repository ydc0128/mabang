package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.ChongyongdizhiAdapter;
import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Bean.Chongyongdizhi;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.OkString;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.v7.appcompat.R.id.info;

public class ChangyongdizhiActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ChangyongdizhiActivity";
    private String url = Contast.Domain + "api/AdressOftenList.ashx?";
    private String url_delete = Contast.Domain + "api/AdressOftenDelete.ashx?";
    private ImageView iv_back;
    private LinearLayout ll_jia;
    private LinearLayout ll_gongsi;
    private TextView tv_jia;
    private TextView tv_jia_buchong;
    private TextView tv_gongsi;
    private TextView tv_gongsi_buchong;
    private ListView listView;
    private List<Chongyongdizhi> chongyongdizhiList;
    private ChongyongdizhiAdapter adapter;
    private List<String> deleteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changyongdizhi);
        initViews();
        initListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        setViews();
        refelash();
    }

    private void initListView() {
        Log.i(TAG, "initListView: ");
        deleteList = new ArrayList<>();
        deleteList.add("删除");
        listView = (ListView) findViewById(R.id.lv_changyongdizhi_listview);
        chongyongdizhiList = new ArrayList<>();
        adapter = new ChongyongdizhiAdapter(this, chongyongdizhiList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chongyongdizhi dizhi = (Chongyongdizhi) adapter.getItem(position);
                Intent intent = new Intent(ChangyongdizhiActivity.this,DituActivity.class);
                intent.putExtra("from",3);
                intent.putExtra("dizhi",dizhi);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i(TAG, "onItemLongClick: ");
                View bottomView = View.inflate(ChangyongdizhiActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView2 = (ListView) bottomView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView2.setAdapter(new DialogListViewAdapter(ChangyongdizhiActivity.this, deleteList));//ListView设置适配器

                final AlertDialog dialog2 = new AlertDialog.Builder(ChangyongdizhiActivity.this)
                        .setView(bottomView)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                dialog2.show();
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        deleteDizhi(position);
                        dialog2.dismiss();
                    }
                });
                return true;
            }
        });
    }


    private void deleteDizhi(final int position) {
        final ProgressDialog pd = new ProgressDialog(ChangyongdizhiActivity.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        Chongyongdizhi dizhi = (Chongyongdizhi) adapter.getItem(position);
        final FormBody.Builder params = new FormBody.Builder();
        params.add("A_UTel", Contast.user.getU_Tel());
        params.add("A_ID", "" + dizhi.getAW_ID());
        params.add("keys", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_delete)
                .post(params.build())
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                pd.dismiss();
                //响应失败
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangyongdizhiActivity.this, "网络连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                pd.dismiss();
                //响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                final String string = response.body().string();
                if(response.code() != HttpURLConnection.HTTP_OK){
                    Toast.makeText(ChangyongdizhiActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                }else {
                    Log.i(TAG, "onResponse: json=" + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangyongdizhiActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (string.contains("OkStr")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    chongyongdizhiList.remove(position);
                                    adapter.setData(chongyongdizhiList);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangyongdizhiActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangyongdizhiActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private void refelash() {
        Log.i(TAG, "refelash: ");
        FormBody.Builder params = new FormBody.Builder();
        params.add("A_UTel", Contast.user.getU_Tel());
        params.add("keys", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //响应失败
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangyongdizhiActivity.this, "网络连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                //响应成功  response.body().string() 获取字符串数据，当然还可以获取其他
                final String string = response.body().string();
                Log.i(TAG, "onResponse: json=" + string);
                if(response.code() != HttpURLConnection.HTTP_OK){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangyongdizhiActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                }else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangyongdizhiActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    chongyongdizhiList = JSON.parseArray(string, Chongyongdizhi.class);
                                    adapter.setData(chongyongdizhiList);
                                    Log.w("diozhi",chongyongdizhiList.toString());
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangyongdizhiActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                }
            }
        });
    }

    private void setViews() {
        Log.i(TAG, "setViews: ");
        if (!TextUtils.isEmpty(Contast.user.getU_Home())) {
            tv_jia.setText(Contast.user.getU_Home());
        }
        if (!TextUtils.isEmpty(Contast.user.getU_HomeWrite())) {
            tv_jia_buchong.setText(Contast.user.getU_HomeWrite());
        }
        if (!TextUtils.isEmpty(Contast.user.getU_Office())) {
            tv_gongsi.setText(Contast.user.getU_Office());
        }
        if (!TextUtils.isEmpty(Contast.user.getU_OfficeWrite())) {
            tv_gongsi_buchong.setText(Contast.user.getU_OfficeWrite());
        }
    }

    private void initViews() {
        Log.i(TAG, "initViews: ");
        deleteList = new ArrayList<>();
        deleteList.add("删除");
        iv_back = (ImageView) findViewById(R.id.iv_changyongdizhi_back);
        ll_jia = (LinearLayout) findViewById(R.id.ll_changyongdizhi_jia);
        ll_gongsi = (LinearLayout) findViewById(R.id.ll_changyongdizhi_gongsi);
        tv_jia = (TextView) findViewById(R.id.tv_changyongdizhi_jia);
        tv_jia_buchong = (TextView) findViewById(R.id.tv_changyongdizhi_jia_buchong);
        tv_gongsi = (TextView) findViewById(R.id.tv_changyongdizhi_gongsi);
        tv_gongsi_buchong = (TextView) findViewById(R.id.tv_changyongdizhi_gongsi_buchong);


        iv_back.setOnClickListener(this);
        ll_jia.setOnClickListener(this);
        ll_gongsi.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_changyongdizhi_back:
                finish();
                break;
            case R.id.ll_changyongdizhi_jia:
                Intent intent = new Intent(ChangyongdizhiActivity.this, DituActivity.class);
                intent.putExtra("from", 1);
                startActivity(intent);
                break;
            case R.id.ll_changyongdizhi_gongsi:
                Intent intent1 = new Intent(ChangyongdizhiActivity.this, DituActivity.class);
                intent1.putExtra("from", 2);
                startActivity(intent1);
                break;

        }
    }
}
