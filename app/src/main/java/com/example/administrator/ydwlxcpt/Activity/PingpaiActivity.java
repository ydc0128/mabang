package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.PingpaiAdapter;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.Pingpai;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.View.CommUtil;
import com.example.administrator.ydwlxcpt.View.SideBar;

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
//品牌
public class  PingpaiActivity extends BaseActivity {

    private static final String TAG = "PingpaiActivity";
    private String url = Contast.Domain + "api/CarPinPai.ashx?";
    private SideBar sb_sidebar;
    private ImageView iv_back;
    private List<Pingpai> pingpaiList;
    private ListView listView;
    private PingpaiAdapter adapter;
    private EditText et_search;
    private ImageView iv_delete;

//    private SortListView sortListView;

    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String content = et_search.getText().toString().trim();
                List<Pingpai> baohan = new ArrayList<>();
                for (Pingpai item : pingpaiList) {
                    String type = item.getCB_BrandName();
                    String pinyin = item.getCB_FirstLetter();
                    if (type.contains(content) || pinyin.contains(content)) {
                        baohan.add(item);
                    }
                        adapter.setData(baohan);

                    }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingpai);
        initViews();
        initListView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refelash();
    }

    private void refelash() {
        final ProgressDialog pd = new ProgressDialog(PingpaiActivity.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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
                        pd.dismiss();
                        Toast.makeText(PingpaiActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                pd.dismiss();
                final String string = response.body().string();
                if(response.code() != HttpURLConnection.HTTP_OK){
                    Toast.makeText(PingpaiActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;

                }else {
                    Log.i(TAG, "onResponse: " + string);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PingpaiActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;

                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pingpaiList = JSON.parseArray(string, Pingpai.class);
                                    adapter.setData(pingpaiList);
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PingpaiActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    }
                }
            }
        });
    }

    private void initListView() {
//        sortListView = (SortListView) findViewById(R.id.mSortList);
        listView = (ListView) findViewById(R.id.lv_pingpai_listview);
        pingpaiList = new ArrayList<>();
        adapter = new PingpaiAdapter(this, pingpaiList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pingpai pingpai = (Pingpai) adapter.getItem(position);
                Intent intent = new Intent(PingpaiActivity.this, XinghaoActivity.class);
                intent.putExtra("pingpai", pingpai);
                startActivity(intent);
                finish();

            }
        });
//        sortListView.setmOnTouchListener(new SortListView.OnTouchListener() {
//            @Override
//            public void onTouch(String s) {
//                Integer index = adapter.getPosition(s);
//                if (index != null) {
//                    listView.setSelection(index);
//                }
//            }
//        });
//        listView.setAdapter(adapter);
    }
    private void getmDataSub(List<Pingpai> list, String data) {
        int length = pingpaiList.size();
        for (int i = 0; i < length; ++i) {
            if (pingpaiList.get(i).getCB_BrandName().contains(data)) {
                List<Pingpai> item = new ArrayList<>();
                item.add(pingpaiList.get(i));
                adapter.setData(item);
            }
        }
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_pingpai_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_search = (EditText) findViewById(R.id.et_pingpai_search);
        sb_sidebar=(SideBar)findViewById(R.id.sb_sidebar);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                if (s.length() == 0) {
                    iv_delete.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
                    adapter.setData(pingpaiList);
                } else {
                    iv_delete.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
                    Message msg = Message.obtain();
                    msg.what =1;
                    myhandler.sendMessage(msg);
                }
            }
        });

        iv_delete = (ImageView) findViewById(R.id.iv_pingpai_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         et_search.setText("");
            }
        });
        sb_sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = CommUtil.getLetterPosition(pingpaiList, s);
                if (position != -1) {
                    listView.setSelection(position);
                }
            }
        });
    }
}
