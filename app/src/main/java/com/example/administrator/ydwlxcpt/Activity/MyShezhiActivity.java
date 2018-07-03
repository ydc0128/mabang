package com.example.administrator.ydwlxcpt.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.ActivityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//设置
public class MyShezhiActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MyShezhiActivity";
    private String url = Contast.Domain+"api/UserGetIdentifyCodeUserLogin.ashx?";

    private ImageView iv_back;
    private LinearLayout ll_xiugaimima;
    private LinearLayout ll_changyongdizhi;
    private CheckBox cb_xiwanche;
    private CheckBox cb_yuyintishi;
    private LinearLayout ll_changjianwenti;
    private LinearLayout ll_shiyongxuzhi;
    private LinearLayout ll_fuwutiaokuan;
    private LinearLayout ll_guanyuwomen;
    private LinearLayout ll_gengxin;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shezhi);
        initViews();
    }

    //初始化
    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_myshezhi_back);
        ll_xiugaimima = (LinearLayout) findViewById(R.id.ll_myshezhi_xiugaimima);
        ll_changyongdizhi = (LinearLayout) findViewById(R.id.ll_myshezhi_changyongdizhi);
        ll_changjianwenti = (LinearLayout) findViewById(R.id.ll_myshezhi_changjianwenti);
        ll_shiyongxuzhi = (LinearLayout) findViewById(R.id.ll_myshezhi_shiyongxuzhi);
        ll_fuwutiaokuan = (LinearLayout) findViewById(R.id.ll_myshezhi_fuwutiaokuan);
        ll_guanyuwomen = (LinearLayout) findViewById(R.id.ll_myshezhi_guanyuwomen);

        cb_xiwanche = (CheckBox) findViewById(R.id.cb_myshezhi_xiwanche);
//      cb_yuyintishi = (CheckBox) findViewById(R.id.cb_myshezhi_yuyintishi);
        btn_logout = (Button) findViewById(R.id.btn_myshezhi_logout);
        iv_back.setOnClickListener(this);
        ll_xiugaimima.setOnClickListener(this);
        ll_changyongdizhi.setOnClickListener(this);
        ll_changjianwenti.setOnClickListener(this);
        ll_shiyongxuzhi.setOnClickListener(this);
        ll_fuwutiaokuan.setOnClickListener(this);
        ll_guanyuwomen.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("shezhi", MODE_PRIVATE);
        boolean iscall = sp.getBoolean("iscall", true);
        if(iscall){
            cb_xiwanche.setChecked(true);
        }else {
            cb_xiwanche.setChecked(false);
        }

        cb_xiwanche.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sp = getSharedPreferences("shezhi", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                if(cb_xiwanche.isChecked()){
                    edit.putBoolean("iscall",true);
                    edit.commit();
                }else{
                    edit.putBoolean("iscall",false);
                    edit.commit();
                }
            }
        });
//        cb_yuyintishi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_myshezhi_back:
                finish();
                break;
            case R.id.ll_myshezhi_xiugaimima:
//                View bottomView = View.inflate(MyShezhiActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
//                ListView listView = (ListView) bottomView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
//                List<String> mimas = new ArrayList<String>();
//                mimas.add("短信验证修改");
//                mimas.add("用原密码修改");
//                listView.setAdapter(new DialogListViewAdapter(MyShezhiActivity.this, mimas));//ListView设置适配器
//                final AlertDialog dialog = new AlertDialog.Builder(MyShezhiActivity.this)
//                        .setTitle("修改方式").setView(bottomView)//在这里把写好的这个listview的布局加载dialog中
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).create();
//                dialog.show();
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件
//
//                    @Override
//                    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//                                            long arg3) {
//                        // TODO Auto-generated method stub
//                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
//                        String str = tv.getText().toString();
//                        if(str.equals("短信验证修改")){
//                            Intent intent = new Intent(MyShezhiActivity.this,DuanxinxiugaiActivity.class);
//                            startActivity(intent);
//                        }else if(str.equals("用原密码修改")){
                            Intent intent = new Intent(MyShezhiActivity.this,YuanmimaxiugaiActivity.class);
                            startActivity(intent);
//                        }
//                        dialog.dismiss();
//                    }
//                });
                break;
            case R.id.ll_myshezhi_changyongdizhi:
                Intent intent1 = new Intent(MyShezhiActivity.this,ChangyongdizhiActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_myshezhi_changjianwenti:
                break;
            case R.id.ll_myshezhi_shiyongxuzhi:
                break;
            case R.id.ll_myshezhi_fuwutiaokuan:
                Intent intent3 = new Intent(MyShezhiActivity.this,ContentActivity.class);
                intent3.putExtra("from","fuwutiaokuan");
                startActivity(intent3);
                break;
            case R.id.ll_myshezhi_guanyuwomen:
                Intent intent2 = new Intent(MyShezhiActivity.this,GuanyuwomenActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_myshezhi_logout:
                //退出登录
                SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("isLogin", false);
                edit.putString("U_Tel","");
                edit.putString("U_IMEI","");
                Contast.user = null;
                ActivityUtils.finishAll();
                Intent intent4 = new Intent(MyShezhiActivity.this,MainActivity.class);
                startActivity(intent4);
                edit.commit();
                Toast.makeText(MyShezhiActivity.this, "已经退出登录", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
