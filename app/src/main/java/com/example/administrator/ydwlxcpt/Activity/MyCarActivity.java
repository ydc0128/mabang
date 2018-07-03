package com.example.administrator.ydwlxcpt.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.CarAdapter;
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


//我的爱车
public class MyCarActivity extends BaseActivity implements View.OnClickListener {

    private String url = Contast.Domain+"api/CarList.ashx?";
    private String url_delete = Contast.Domain + "api/CarDelete.ashx?";
    private ImageView iv_back;
    private ImageView btn_addCar;
    private ListView listView;
    private RelativeLayout rl_null;
    private CarAdapter adapter;
    private List<Car> carList;
    private Car car;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);
        initViews();
    }

    private void initListView() {
        carList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.lv_mycar_listview);
        adapter = new CarAdapter(MyCarActivity.this,carList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Car car = (Car) adapter.getItem(position);
                Intent intent = new Intent(MyCarActivity.this,AddCarActivity.class);
                intent.putExtra("from",0);
                intent.putExtra("car",car);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
//                AlertDialog.Builder builder=new AlertDialog.Builder(MyCarActivity.this);
//                builder.setMessage("确定删除?");
//                //添加AlertDialog.Builder对象的setPositiveButton()方法
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(carList.remove(position)!=null){
//                          deleteCar(1);
//                        }else {
//                            System.out.println("failed");
//                        }
//                        adapter.notifyDataSetChanged();
//                        Toast.makeText(getBaseContext(), "删除成功", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                //添加AlertDialog.Builder对象的setNegativeButton()方法
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//
//                builder.create().show();
                new NDialog(MyCarActivity.this)
                        .setMessageCenter(false)
                        .setMessage("是否删除车辆？")
                        .setMessageSize(16)
                        .setMessageColor(Color.parseColor("#a0000000"))
                        .setNegativeTextColor(Color.parseColor("#a0000000"))
                        .setPositiveTextColor(Color.parseColor("#a0000000"))
                        .setButtonCenter(false)
                        .setButtonSize(16)
                        .setCancleable(true)
                        .setOnConfirmListener(new NDialog.OnConfirmListener() {
                            @Override
                            public void onClick(int which) {
                                //which,0代表NegativeButton，1代表PositiveButton
                                switch (which) {
                                    case 0:
                                        finish();
                                        break;
                                    case 1:
                                        deleteCar(position);
                                        break;

                                }

                            }
                        }).create(NDialog.CONFIRM).show();
                return true;
            }
        });
}
    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_mycar_back);
        btn_addCar = (ImageView) findViewById(R.id.btn_mycar_more);
        rl_null = (RelativeLayout) findViewById(R.id.rl_mycar_null);
        iv_back.setOnClickListener(this);
        btn_addCar.setOnClickListener(this);
    }
    private void deleteCar(final int position) {
            Car car = (Car) adapter.getItem(position);
            FormBody.Builder params = new FormBody.Builder();
            params.add("keys", Contast.KEYS);
            params.add("C_UTel", car.getC_CarTel());
            params.add("C_PlateNumber", car.getC_PlateNumber());
            OkHttpClient client = new OkHttpClient();
            //构建请求
            final Request request = new Request.Builder()
                    .url(url_delete)
                    .post(params.build())//添加请求体
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyCarActivity.this, "上传失败，请稍后重试...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    if (response.code() != HttpURLConnection.HTTP_OK) {
                        Toast.makeText(MyCarActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    } else {
//                    Log.i(TAG, "onResponse: " + string);
                        if (!TextUtils.isEmpty(string)) {
                            if (string.contains("ErrorStr")) {
                                final Error error = JSON.parseObject(string, Error.class);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MyCarActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (string.contains("OkStr")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MyCarActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyCarActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });
    }
    @Override
    protected void onResume() {
        super.onResume();
        initListView();
        refelash();
        if(carList.size()!=0){
            rl_null.setVisibility(View.INVISIBLE);
        }else{
            rl_null.setVisibility(View.VISIBLE);
        }
    }

    private void refelash() {
        final ProgressDialog pd = new ProgressDialog(MyCarActivity.this);
        pd.setMessage("拼命加载中...");
        pd.show();
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("C_UTel", Contast.user.getU_Tel());
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
                        Toast.makeText(MyCarActivity.this, "网络连接异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });
            }
            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                pd.dismiss();
                final String string = response.body().string();
                if(response.code() != HttpURLConnection.HTTP_OK){
                    Toast.makeText(MyCarActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;

                }else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyCarActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;

                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    carList = JSON.parseArray(string, Car.class);
                                    if (carList.size() > 0) {
                                        rl_null.setVisibility(View.GONE);
                                    } else {
                                        rl_null.setVisibility(View.VISIBLE);
                                    }
                                    checkList(carList);
                                    adapter.setData(carList);
                                    Log.e("che",carList.toString());
                                }
                            });

                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MyCarActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_mycar_back:
                finish();
                break;
            case R.id.btn_mycar_more:
//                if (carList.size()>=3) {
//                    Toast.makeText(MyCarActivity.this, "您已经添加3辆车", Toast.LENGTH_SHORT).show();
//                }else {
                    Intent intent = new Intent(MyCarActivity.this, AddCarActivity.class);
                    intent.putExtra("from", 1);
                    startActivity(intent);
//                }
                 break;
        }
    }
    private void checkList(List<Car> list) {
        if (list.size() >= 3) {
            btn_addCar.setVisibility(View.INVISIBLE);
        } else {
            btn_addCar.setVisibility(View.VISIBLE);
        }
    }
}
