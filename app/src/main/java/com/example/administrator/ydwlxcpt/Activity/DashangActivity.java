package com.example.administrator.ydwlxcpt.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class DashangActivity extends AppCompatActivity implements View.OnClickListener{
//打赏
    private ImageView iv_back;
    private ImageView iv_touxiang;
    private ImageView iv_lianxita;

    private TextView tv_name;
    private TextView tv_gonghao;
    private TextView tv_dingdanhao;
    private TextView tv_lianxifangshi;

    private CheckBox cb_5;
    private CheckBox cb_10;
    private CheckBox cb_20;

    private Button btn_dashang;

    private Dingdan dingdan;
    private List<String> lianxidianhua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashang);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        dingdan = (Dingdan) extras.getSerializable("dingdan");
        initViews();
        setViews();

    }

    private void setViews() {
        tv_name.setText(dingdan.getO_WorkerName());
        tv_gonghao.setText(""+dingdan.getO_WorkerID());
        tv_lianxifangshi.setText(dingdan.getO_WorkerTel());
        tv_dingdanhao.setText(dingdan.getO_ID());
    }

    private void initViews() {
        lianxidianhua = new ArrayList<>();
        lianxidianhua.add(dingdan.getO_WorkerTel());
        iv_back = (ImageView) findViewById(R.id.iv_dashang_back);
        iv_touxiang = (ImageView) findViewById(R.id.iv_dashang_xichegong_touxiang);
        iv_lianxita = (ImageView) findViewById(R.id.iv_dashang_xichegong_lianxita);

        tv_name = (TextView) findViewById(R.id.tv_dashang_xichegong_name);
        tv_gonghao = (TextView) findViewById(R.id.tv_dashang_xichegong_gonghao);
        tv_dingdanhao = (TextView) findViewById(R.id.tv_dashang_dingdanhao);
        tv_lianxifangshi = (TextView) findViewById(R.id.tv_dashang_xichegong_lianxifangshi);

        cb_5 = (CheckBox) findViewById(R.id.cb_dashang_5);
        cb_10 = (CheckBox) findViewById(R.id.cb_dashang_10);
        cb_20 = (CheckBox) findViewById(R.id.cb_dashang_20);

        btn_dashang = (Button) findViewById(R.id.btn_dashang_submit);

        btn_dashang.setOnClickListener(this);
        iv_lianxita.setOnClickListener(this);
        iv_back.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_dashang_back:
                finish();
                break;
            case R.id.iv_dashang_xichegong_lianxita:
                View photoView = View.inflate(DashangActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView = (ListView) photoView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView.setAdapter(new DialogListViewAdapter(DashangActivity.this, lianxidianhua));//ListView设置适配器

                final AlertDialog dialog = new AlertDialog.Builder(DashangActivity.this)
                        .setView(photoView)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView tv = (TextView) view.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String dianhua = tv.getText().toString();
                        // 拨号：激活系统的拨号组件
                        Intent intent = new Intent(); // 意图对象：动作 + 数据
                        intent.setAction(Intent.ACTION_DIAL); // 设置动作
                        Uri data = Uri.parse("tel:" + dianhua); // 设置数据
                        intent.setData(data);
                        startActivity(intent); // 激活Activity组件
                    }
                });
                break;
            case R.id.btn_dashang_submit:
                Toast.makeText(DashangActivity.this,"打赏TA",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
