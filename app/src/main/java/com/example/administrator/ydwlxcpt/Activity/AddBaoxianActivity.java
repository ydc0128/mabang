package com.example.administrator.ydwlxcpt.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.R;

import java.util.ArrayList;
import java.util.List;

public class AddBaoxianActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ImageView iv_back;
    private TextView tv_aiche_name;
    private RelativeLayout rl_aiche;
    private RelativeLayout rl_shangyexian;
    private RelativeLayout rl_jiaoqiangxian;
    private RelativeLayout rl_chechuanshui;

    private LinearLayout ll_shangyexian;
    private LinearLayout ll_jiaoqiangxian;
    private LinearLayout ll_chechuanshui;

    private boolean isShow_jiaoqiangxian = false;
    private boolean isShow_shangyexian = false;
    private boolean isShow_chechuanshui = false;

    private CheckBox cb_shangyexian;
    private CheckBox cb_jiaoqiangxian;
    private CheckBox cb_chechuanshui;
    private CheckBox cb_shangyexian_yincang;
    private TextView tv_hejijine;
    private Button btn_qutoubao;
    private TextView tv_shangyexian_zongjia;
    private TextView tv_jiaoqiangxian_zongjia;
    private TextView tv_chechuanshui_zongjia;
    private RelativeLayout rl_quanchedaoqiangxian;
    private RelativeLayout rl_bolidanduposuixian;
    private RelativeLayout rl_cheshenhuahensunshixian;
    private RelativeLayout rl_ziransunshixian;
    private RelativeLayout rl_cheliangsunshixian;
    private RelativeLayout rl_disanzhezerenxian;
    private RelativeLayout rl_sijizuoweizerenxian;
    private RelativeLayout rl_chengkezuoweizerenxian;
    private RelativeLayout rl_fadongjisheshuisunshixian;
    private RelativeLayout rl_jidongchesunshixian;
    private RelativeLayout rl_zhidingxiulichangxian;
    private TextView tv_shangyexian_baofei_cheliangsunshixian;
    private TextView tv_shangyexian_baofei_disanfangzerenxian;
    private TextView tv_shangyexian_baofei_quanchedaoqiangxian;
    private TextView tv_shangyexian_baofei_sijizuoweizerenxian;
    private TextView tv_shangyexian_baofei_chengkezuoweizerenxian;
    private TextView tv_shangyexian_baofei_bolidanduposuixian;
    private TextView tv_shangyexian_baofei_cheshenhuahensunshixian;
    private TextView tv_shangyexian_baofei_ziransunshixian;
    private TextView tv_shangyexian_baofei_fadongjisheshuisunshixian;
    private TextView tv_shangyexian_baofei_zhidingxiulichangxian;
    private TextView tv_shangyexian_baofei_jidongchesunshixian;

    private TextView tv_shangyexian_baoe_disanzhezerenxian;
    private TextView tv_shangyexian_baoe_sijizuoweizerenxian;
    private TextView tv_shangyexian_baoe_chengkezuoweizerenxian;
    private TextView tv_shangyexian_baoe_bolidanduposuixian;
    private TextView tv_shangyexian_baoe_cheshenhuahensunshixian;
    private TextView tv_shangyexian_baoe_zhidingxiulichangxian;
    private CheckBox cb_shangyexian_cheliangsunshixian;
    private CheckBox cb_shangyexian_disanfangzerenxian;
    private CheckBox cb_shangyexian_quanchedaoqiangxian;
    private CheckBox cb_shangyexian_sijizuoweizerenxian;
    private CheckBox cb_shangyexian_chengkezuoweizerenxian;
    private CheckBox cb_shangyexian_bolidanduposuixian;
    private CheckBox cb_shangyexian_cheshenhuahensunshixian;
    private CheckBox cb_shangyexian_ziransunshixian;
    private CheckBox cb_shangyexian_fadongjisheshuisunshixian;
    private CheckBox cb_shangyexian_zhidingxiulichangxian;
    private CheckBox cb_shangyexian_jidongchesunshixian;
    private ImageView iv_shangyexian_disanfangzerenxian_baoe;
    private ImageView iv_shangyexian_sijizuoweizerenxian_baoe;
    private ImageView iv_shangyexian_chengkezuoweizerenxian_baoe;
    private ImageView iv_shangyexian_bolidanduposuixian_baoe;
    private ImageView iv_shangyexian_cheshenhuahensunshixian_baoe;
    private ImageView iv_shangyexian_zhidingxiulichangxian_baoe;
    private String aiche;
    private List<String> aicheList;
    private List<String> disanzhezerenxianList;
    private List<String> sijizuoweizerenxianList;
    private List<String> chengkezuoweizerenxianList;
    private List<String> bolidanduposuizerenxianList;
    private List<String> cheshenhuahensunshixianList;
    private List<String> zhidingxiulichangxianList;
    private double shangyexian_zongjia = 6.00;
    private double jiaoqiangxian_zongjia = 665.00;
    private double chechuanshui_zongjia = 1800.00;
    private double baoxianzongjia = 2471.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baoxian);
        Intent intent = getIntent();
        aiche = intent.getStringExtra("aiche");
        initViews();
        tv_aiche_name.setText(aiche);
        tv_shangyexian_zongjia.setText("" + shangyexian_zongjia);
        tv_jiaoqiangxian_zongjia.setText("" + jiaoqiangxian_zongjia);
        tv_chechuanshui_zongjia.setText("" + chechuanshui_zongjia);
        tv_hejijine.setText("" + baoxianzongjia);
        rl_quanchedaoqiangxian.setVisibility(View.GONE);
        rl_bolidanduposuixian.setVisibility(View.GONE);
        rl_cheshenhuahensunshixian.setVisibility(View.GONE);
        rl_ziransunshixian.setVisibility(View.GONE);
        rl_zhidingxiulichangxian.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCheckBoxs();
        initData();
    }

    private void setCheckBoxs() {
        cb_shangyexian.setChecked(true);
        cb_jiaoqiangxian.setChecked(true);
        cb_chechuanshui.setChecked(true);

        cb_shangyexian_cheliangsunshixian.setChecked(true);
        cb_shangyexian_disanfangzerenxian.setChecked(true);
        cb_shangyexian_sijizuoweizerenxian.setChecked(true);
        cb_shangyexian_chengkezuoweizerenxian.setChecked(true);
        cb_shangyexian_fadongjisheshuisunshixian.setChecked(true);
        cb_shangyexian_jidongchesunshixian.setChecked(true);
        cb_shangyexian_quanchedaoqiangxian.setChecked(false);
        cb_shangyexian_bolidanduposuixian.setChecked(false);
        cb_shangyexian_cheshenhuahensunshixian.setChecked(false);
        cb_shangyexian_ziransunshixian.setChecked(false);
        cb_shangyexian_zhidingxiulichangxian.setChecked(false);
    }

    private void initData() {
        aicheList = new ArrayList<>();
        aicheList.add("陕A57V37");
        aicheList.add("陕A49V28");
        aicheList.add("陕A14V96");

        disanzhezerenxianList = new ArrayList<>();
        disanzhezerenxianList.add("50万");
        disanzhezerenxianList.add("40万");
        disanzhezerenxianList.add("30万");
        disanzhezerenxianList.add("20万");
        disanzhezerenxianList.add("10万");

        sijizuoweizerenxianList = new ArrayList<>();
        sijizuoweizerenxianList.add("1万");
        sijizuoweizerenxianList.add("2万");
        sijizuoweizerenxianList.add("3万");
        sijizuoweizerenxianList.add("4万");
        sijizuoweizerenxianList.add("5万");

        chengkezuoweizerenxianList = new ArrayList<>();
        chengkezuoweizerenxianList.add("1万");
        chengkezuoweizerenxianList.add("2万");
        chengkezuoweizerenxianList.add("3万");
        chengkezuoweizerenxianList.add("4万");
        chengkezuoweizerenxianList.add("5万");

        bolidanduposuizerenxianList = new ArrayList<>();
        bolidanduposuizerenxianList.add("不投保");
        bolidanduposuizerenxianList.add("进口玻璃");

        cheshenhuahensunshixianList = new ArrayList<>();
        cheshenhuahensunshixianList.add("不投保");
        cheshenhuahensunshixianList.add("2万");
        cheshenhuahensunshixianList.add("1万");
        cheshenhuahensunshixianList.add("5000");
        cheshenhuahensunshixianList.add("2000");

        zhidingxiulichangxianList = new ArrayList<>();
        zhidingxiulichangxianList.add("不投保");
        zhidingxiulichangxianList.add("进口");

    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_addbaoxian_back);
        tv_aiche_name = (TextView) findViewById(R.id.tv_addbaoxian_aiche_name);
        rl_aiche = (RelativeLayout) findViewById(R.id.rl_addbaoxian_aiche);
        rl_shangyexian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian);
        rl_jiaoqiangxian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_jiaoqiangxian);
        rl_chechuanshui = (RelativeLayout) findViewById(R.id.rl_addbaoxian_chechuanshui);

        ll_shangyexian = (LinearLayout) findViewById(R.id.ll_addbaoxian_shangyexian);
        ll_jiaoqiangxian = (LinearLayout) findViewById(R.id.ll_addbaoxian_jiaoqiangxian);
        ll_chechuanshui = (LinearLayout) findViewById(R.id.ll_addbaoxian_chechuanshui);

        rl_quanchedaoqiangxian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_quanchedaoqiangxian);
        rl_bolidanduposuixian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_bolidandubosuixian);
        rl_cheshenhuahensunshixian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_cheshenhuahensunshixian);
        rl_ziransunshixian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_ziransunshixian);
        rl_zhidingxiulichangxian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_zhidingxiulichangxian);
        rl_cheliangsunshixian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_cheliangsunshixian);
        rl_disanzhezerenxian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_disanzhezerenxian);
        rl_sijizuoweizerenxian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_sijizuoweizerenxian);
        rl_chengkezuoweizerenxian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_chengkezuoweizerenxian);
        rl_fadongjisheshuisunshixian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_fadongjisheshuisunshixian);
        rl_jidongchesunshixian = (RelativeLayout) findViewById(R.id.rl_addbaoxian_shangyexian_jidongchesunshixian);


        cb_shangyexian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian);
        cb_jiaoqiangxian = (CheckBox) findViewById(R.id.cb_addbaoxian_jiaoqiangxian);
        cb_chechuanshui = (CheckBox) findViewById(R.id.cb_addbaoxian_chechuanshui);
        cb_shangyexian_yincang = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_xianzhong_yincangweitoubaoxianbie);
        tv_shangyexian_zongjia = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_zongjia);
        tv_jiaoqiangxian_zongjia = (TextView) findViewById(R.id.tv_addbaoxian_jiaoqiangxian_zongjia);
        tv_chechuanshui_zongjia = (TextView) findViewById(R.id.tv_addbaoxian_chechuanshui_zongjia);

        cb_shangyexian_cheliangsunshixian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_cheliangsunshi);
        cb_shangyexian_disanfangzerenxian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_disanfangzerenxian);
        cb_shangyexian_quanchedaoqiangxian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_quanchedaoqiangxian);
        cb_shangyexian_sijizuoweizerenxian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_sijizuoweizerenxian);
        cb_shangyexian_chengkezuoweizerenxian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_chengkezuoweizerenxian);
        cb_shangyexian_bolidanduposuixian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_bolidanduposuixian);
        cb_shangyexian_cheshenhuahensunshixian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_cheshenhuahensunshixian);
        cb_shangyexian_ziransunshixian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_ziransunshixian);
        cb_shangyexian_fadongjisheshuisunshixian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_fadongjisheshuisunshixian);
        cb_shangyexian_zhidingxiulichangxian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_zhidingxiulichangxian);
        cb_shangyexian_jidongchesunshixian = (CheckBox) findViewById(R.id.cb_addbaoxian_shangyexian_jidongchesunshixian);

        tv_shangyexian_baofei_cheliangsunshixian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_cheliangsunshixian_baofei);
        tv_shangyexian_baofei_disanfangzerenxian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_disanfangzerenxian_baofei);
        tv_shangyexian_baofei_quanchedaoqiangxian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_quanchedaoqiangxian_baofei);
        tv_shangyexian_baofei_sijizuoweizerenxian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_sijizuoweizerenxian_baofei);
        tv_shangyexian_baofei_chengkezuoweizerenxian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_chengkezuoweizerenxian_baofei);
        tv_shangyexian_baofei_bolidanduposuixian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_bolidanduposuixian_baofei);
        tv_shangyexian_baofei_cheshenhuahensunshixian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_cheshenhuahensunshixian_baofei);
        tv_shangyexian_baofei_ziransunshixian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_ziransunshixian_baofei);
        tv_shangyexian_baofei_fadongjisheshuisunshixian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_fadongjisheshuisunshixian_baofei);
        tv_shangyexian_baofei_zhidingxiulichangxian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_zhidingxiulichangxian_baofei);
        tv_shangyexian_baofei_jidongchesunshixian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_jidongchesunshixian_baofei);

        tv_shangyexian_baoe_disanzhezerenxian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_disanfangzerenxian_baoe);
        tv_shangyexian_baoe_sijizuoweizerenxian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_sijizuoweizerenxian_baoe);
        tv_shangyexian_baoe_chengkezuoweizerenxian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_chengkezuoweizerenxian_baoe);
        tv_shangyexian_baoe_bolidanduposuixian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_bolidanduposuixian_baoe);
        tv_shangyexian_baoe_cheshenhuahensunshixian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_cheshenhuahensunshixian_baoe);
        tv_shangyexian_baoe_zhidingxiulichangxian = (TextView) findViewById(R.id.tv_addbaoxian_shangyexian_zhidingxiulichangxian_baoe);

        tv_hejijine = (TextView) findViewById(R.id.tv_addbaoxian_zongjia);
        btn_qutoubao = (Button) findViewById(R.id.btn_addbaoxian_qutoubao);

        iv_shangyexian_disanfangzerenxian_baoe = (ImageView) findViewById(R.id.iv_addbaoxian_shangyexian_disanfangzerenxian_baoe);
        iv_shangyexian_sijizuoweizerenxian_baoe = (ImageView) findViewById(R.id.iv_addbaoxian_shangyexian_sijizuoweizerenxian_baoe);
        iv_shangyexian_chengkezuoweizerenxian_baoe = (ImageView) findViewById(R.id.iv_addbaoxian_shangyexian_chengkezuoweizerenxian_baoe);
        iv_shangyexian_bolidanduposuixian_baoe = (ImageView) findViewById(R.id.iv_addbaoxian_shangyexian_bolidanduposuixian_baoe);
        iv_shangyexian_cheshenhuahensunshixian_baoe = (ImageView) findViewById(R.id.iv_addbaoxian_shangyexian_cheshenhuahensunshixian_baoe);
        iv_shangyexian_zhidingxiulichangxian_baoe = (ImageView) findViewById(R.id.iv_addbaoxian_shangyexian_zhidingxiulichangxian_baoe);


        iv_back.setOnClickListener(this);
        rl_aiche.setOnClickListener(this);
        rl_shangyexian.setOnClickListener(this);
        rl_jiaoqiangxian.setOnClickListener(this);
        rl_chechuanshui.setOnClickListener(this);
        iv_shangyexian_disanfangzerenxian_baoe.setOnClickListener(this);
        iv_shangyexian_sijizuoweizerenxian_baoe.setOnClickListener(this);
        iv_shangyexian_chengkezuoweizerenxian_baoe.setOnClickListener(this);
        iv_shangyexian_bolidanduposuixian_baoe.setOnClickListener(this);
        iv_shangyexian_cheshenhuahensunshixian_baoe.setOnClickListener(this);
        iv_shangyexian_zhidingxiulichangxian_baoe.setOnClickListener(this);

        cb_shangyexian.setOnCheckedChangeListener(this);
        cb_jiaoqiangxian.setOnCheckedChangeListener(this);
        cb_chechuanshui.setOnCheckedChangeListener(this);
        cb_shangyexian_yincang.setOnCheckedChangeListener(this);

        cb_shangyexian_cheliangsunshixian.setOnCheckedChangeListener(this);
        cb_shangyexian_disanfangzerenxian.setOnCheckedChangeListener(this);
        cb_shangyexian_quanchedaoqiangxian.setOnCheckedChangeListener(this);
        cb_shangyexian_sijizuoweizerenxian.setOnCheckedChangeListener(this);
        cb_shangyexian_chengkezuoweizerenxian.setOnCheckedChangeListener(this);
        cb_shangyexian_bolidanduposuixian.setOnCheckedChangeListener(this);
        cb_shangyexian_cheshenhuahensunshixian.setOnCheckedChangeListener(this);
        cb_shangyexian_ziransunshixian.setOnCheckedChangeListener(this);
        cb_shangyexian_fadongjisheshuisunshixian.setOnCheckedChangeListener(this);
        cb_shangyexian_zhidingxiulichangxian.setOnCheckedChangeListener(this);
        cb_shangyexian_jidongchesunshixian.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addbaoxian_back:
                finish();
                break;
            case R.id.rl_addbaoxian_aiche:
                View bottomView = View.inflate(AddBaoxianActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView = (ListView) bottomView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView.setAdapter(new DialogListViewAdapter(AddBaoxianActivity.this, aicheList));//ListView设置适配器

                final AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("选择爱车").setView(bottomView)//在这里把写好的这个listview的布局加载dialog中
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        tv_aiche_name.setText(aiche);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.rl_addbaoxian_shangyexian:
                if (!isShow_shangyexian) {
                    ll_shangyexian.setVisibility(View.VISIBLE);
                    isShow_shangyexian = !isShow_shangyexian;
                } else {
                    ll_shangyexian.setVisibility(View.GONE);
                    isShow_shangyexian = !isShow_shangyexian;
                }
                break;
            case R.id.rl_addbaoxian_jiaoqiangxian:
                if (!isShow_jiaoqiangxian) {
                    ll_jiaoqiangxian.setVisibility(View.VISIBLE);
                    isShow_jiaoqiangxian = !isShow_jiaoqiangxian;
                } else {
                    ll_jiaoqiangxian.setVisibility(View.GONE);
                    isShow_jiaoqiangxian = !isShow_jiaoqiangxian;
                }
                break;
            case R.id.rl_addbaoxian_chechuanshui:
                if (!isShow_chechuanshui) {
                    ll_chechuanshui.setVisibility(View.VISIBLE);
                    isShow_chechuanshui = !isShow_chechuanshui;
                } else {
                    ll_chechuanshui.setVisibility(View.GONE);
                    isShow_chechuanshui = !isShow_chechuanshui;
                }
                break;
            case R.id.iv_addbaoxian_shangyexian_disanfangzerenxian_baoe:
                View disanzhezerenxian = View.inflate(AddBaoxianActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView disanzhezerenxianListView = (ListView) disanzhezerenxian.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                disanzhezerenxianListView.setAdapter(new DialogListViewAdapter(AddBaoxianActivity.this, disanzhezerenxianList));//ListView设置适配器

                final AlertDialog disanzhezerenxianDialog = new AlertDialog.Builder(this)
                        .setView(disanzhezerenxian)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                disanzhezerenxianDialog.show();
                disanzhezerenxianListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        tv_shangyexian_baoe_disanzhezerenxian.setText(aiche);
                        disanzhezerenxianDialog.dismiss();
                    }
                });
                break;
            case R.id.iv_addbaoxian_shangyexian_sijizuoweizerenxian_baoe:
                View sijizuoweizerenxian = View.inflate(AddBaoxianActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView sijizuoweizerenxianListView = (ListView) sijizuoweizerenxian.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                sijizuoweizerenxianListView.setAdapter(new DialogListViewAdapter(AddBaoxianActivity.this, sijizuoweizerenxianList));//ListView设置适配器

                final AlertDialog sijizuoweizerenxianDialog = new AlertDialog.Builder(this)
                        .setView(sijizuoweizerenxian)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                sijizuoweizerenxianDialog.show();
                sijizuoweizerenxianListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        tv_shangyexian_baoe_sijizuoweizerenxian.setText(aiche);
                        sijizuoweizerenxianDialog.dismiss();
                    }
                });
                break;
            case R.id.iv_addbaoxian_shangyexian_chengkezuoweizerenxian_baoe:
                View chengkezuoweizerenxian = View.inflate(AddBaoxianActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView chengkezuoweizerenxianListView = (ListView) chengkezuoweizerenxian.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                chengkezuoweizerenxianListView.setAdapter(new DialogListViewAdapter(AddBaoxianActivity.this, chengkezuoweizerenxianList));//ListView设置适配器

                final AlertDialog chengkezuoweizerenxianDialog = new AlertDialog.Builder(this)
                        .setView(chengkezuoweizerenxian)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                chengkezuoweizerenxianDialog.show();
                chengkezuoweizerenxianListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        tv_shangyexian_baoe_chengkezuoweizerenxian.setText(aiche);
                        chengkezuoweizerenxianDialog.dismiss();
                    }
                });
                break;
            case R.id.iv_addbaoxian_shangyexian_bolidanduposuixian_baoe:
                View bolidanduposuixian = View.inflate(AddBaoxianActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView bolidanduposuixianListView = (ListView) bolidanduposuixian.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                bolidanduposuixianListView.setAdapter(new DialogListViewAdapter(AddBaoxianActivity.this, bolidanduposuizerenxianList));//ListView设置适配器

                final AlertDialog bolidanduposuixianDialog = new AlertDialog.Builder(this)
                        .setView(bolidanduposuixian)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                bolidanduposuixianDialog.show();
                bolidanduposuixianListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        tv_shangyexian_baoe_bolidanduposuixian.setText(aiche);
                        bolidanduposuixianDialog.dismiss();
                    }
                });
                break;
            case R.id.iv_addbaoxian_shangyexian_cheshenhuahensunshixian_baoe:
                View cheshenhuahenxian = View.inflate(AddBaoxianActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView cheshenhuahenxianListView = (ListView) cheshenhuahenxian.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                cheshenhuahenxianListView.setAdapter(new DialogListViewAdapter(AddBaoxianActivity.this, cheshenhuahensunshixianList));//ListView设置适配器

                final AlertDialog cheshenhuahenxianDialog = new AlertDialog.Builder(this)
                        .setView(cheshenhuahenxian)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                cheshenhuahenxianDialog.show();
                cheshenhuahenxianListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        tv_shangyexian_baoe_cheshenhuahensunshixian.setText(aiche);
                        cheshenhuahenxianDialog.dismiss();
                    }
                });
                break;
            case R.id.iv_addbaoxian_shangyexian_zhidingxiulichangxian_baoe:
                View zhidingxiulichangxian = View.inflate(AddBaoxianActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView zhidingxiulichangxianListView = (ListView) zhidingxiulichangxian.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                zhidingxiulichangxianListView.setAdapter(new DialogListViewAdapter(AddBaoxianActivity.this, zhidingxiulichangxianList));//ListView设置适配器

                final AlertDialog zhidingxiulichangxianDialog = new AlertDialog.Builder(this)
                        .setView(zhidingxiulichangxian)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                zhidingxiulichangxianDialog.show();
                zhidingxiulichangxianListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        tv_shangyexian_baoe_zhidingxiulichangxian.setText(aiche);
                        zhidingxiulichangxianDialog.dismiss();
                    }
                });
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_addbaoxian_shangyexian:
                shangyexian_zongjia = Double.parseDouble(tv_shangyexian_zongjia.getText().toString());
                if (isChecked) {
                    baoxianzongjia += shangyexian_zongjia;
                } else {
                    baoxianzongjia -= shangyexian_zongjia;
                }
                tv_hejijine.setText("" + baoxianzongjia);
                break;
            case R.id.cb_addbaoxian_jiaoqiangxian:
                jiaoqiangxian_zongjia = Double.parseDouble(tv_jiaoqiangxian_zongjia.getText().toString());
                if (isChecked) {
                    baoxianzongjia += jiaoqiangxian_zongjia;
                } else {
                    baoxianzongjia -= jiaoqiangxian_zongjia;
                }
                tv_hejijine.setText("" + baoxianzongjia);
                break;
            case R.id.cb_addbaoxian_chechuanshui:
                chechuanshui_zongjia = Double.parseDouble(tv_chechuanshui_zongjia.getText().toString());
                if (isChecked) {
                    baoxianzongjia += chechuanshui_zongjia;
                } else {
                    baoxianzongjia -= chechuanshui_zongjia;
                }
                tv_hejijine.setText("" + baoxianzongjia);
                break;
            case R.id.cb_addbaoxian_shangyexian_xianzhong_yincangweitoubaoxianbie:
                if (isChecked) {
                    if (cb_shangyexian_cheliangsunshixian.isChecked()) {
                        rl_cheliangsunshixian.setVisibility(View.VISIBLE);
                    } else {
                        rl_cheliangsunshixian.setVisibility(View.GONE);
                    }
                    if (cb_shangyexian_disanfangzerenxian.isChecked()) {
                        rl_disanzhezerenxian.setVisibility(View.VISIBLE);
                    } else {
                        rl_disanzhezerenxian.setVisibility(View.GONE);
                    }
                    if (cb_shangyexian_quanchedaoqiangxian.isChecked()) {
                        rl_quanchedaoqiangxian.setVisibility(View.VISIBLE);
                    } else {
                        rl_quanchedaoqiangxian.setVisibility(View.GONE);
                    }
                    if (cb_shangyexian_sijizuoweizerenxian.isChecked()) {
                        rl_sijizuoweizerenxian.setVisibility(View.VISIBLE);
                    } else {
                        rl_sijizuoweizerenxian.setVisibility(View.GONE);
                    }
                    if (cb_shangyexian_chengkezuoweizerenxian.isChecked()) {
                        rl_chengkezuoweizerenxian.setVisibility(View.VISIBLE);
                    } else {
                        rl_chengkezuoweizerenxian.setVisibility(View.GONE);
                    }
                    if (cb_shangyexian_bolidanduposuixian.isChecked()) {
                        rl_bolidanduposuixian.setVisibility(View.VISIBLE);
                    } else {
                        rl_bolidanduposuixian.setVisibility(View.GONE);
                    }
                    if (cb_shangyexian_cheshenhuahensunshixian.isChecked()) {
                        rl_cheshenhuahensunshixian.setVisibility(View.VISIBLE);
                    } else {
                        rl_cheshenhuahensunshixian.setVisibility(View.GONE);
                    }
                    if (cb_shangyexian_ziransunshixian.isChecked()) {
                        rl_ziransunshixian.setVisibility(View.VISIBLE);
                    } else {
                        rl_ziransunshixian.setVisibility(View.GONE);
                    }
                    if (cb_shangyexian_fadongjisheshuisunshixian.isChecked()) {
                        rl_fadongjisheshuisunshixian.setVisibility(View.VISIBLE);
                    } else {
                        rl_fadongjisheshuisunshixian.setVisibility(View.GONE);
                    }
                    if (cb_shangyexian_zhidingxiulichangxian.isChecked()) {
                        rl_zhidingxiulichangxian.setVisibility(View.VISIBLE);
                    } else {
                        rl_zhidingxiulichangxian.setVisibility(View.GONE);
                    }
                    if (cb_shangyexian_jidongchesunshixian.isChecked()) {
                        rl_jidongchesunshixian.setVisibility(View.VISIBLE);
                    } else {
                        rl_jidongchesunshixian.setVisibility(View.GONE);
                    }


                } else {
                    rl_quanchedaoqiangxian.setVisibility(View.VISIBLE);
                    rl_bolidanduposuixian.setVisibility(View.VISIBLE);
                    rl_cheshenhuahensunshixian.setVisibility(View.VISIBLE);
                    rl_ziransunshixian.setVisibility(View.VISIBLE);
                    rl_zhidingxiulichangxian.setVisibility(View.VISIBLE);
                    rl_cheliangsunshixian.setVisibility(View.VISIBLE);
                    rl_disanzhezerenxian.setVisibility(View.VISIBLE);
                    rl_sijizuoweizerenxian.setVisibility(View.VISIBLE);
                    rl_chengkezuoweizerenxian.setVisibility(View.VISIBLE);
                    rl_fadongjisheshuisunshixian.setVisibility(View.VISIBLE);
                    rl_jidongchesunshixian.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.cb_addbaoxian_shangyexian_cheliangsunshi:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_cheliangsunshixian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_cheliangsunshixian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_cheliangsunshixian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_cheliangsunshixian.getText().toString());
                }
                setJiage();
                break;
            case R.id.cb_addbaoxian_shangyexian_disanfangzerenxian:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_disanfangzerenxian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_disanfangzerenxian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_disanfangzerenxian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_disanfangzerenxian.getText().toString());
                }
                setJiage();
                break;
            case R.id.cb_addbaoxian_shangyexian_quanchedaoqiangxian:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_quanchedaoqiangxian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_quanchedaoqiangxian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_quanchedaoqiangxian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_quanchedaoqiangxian.getText().toString());
                }
                setJiage();
                break;
            case R.id.cb_addbaoxian_shangyexian_sijizuoweizerenxian:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_sijizuoweizerenxian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_sijizuoweizerenxian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_sijizuoweizerenxian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_sijizuoweizerenxian.getText().toString());
                }
                setJiage();
                break;
            case R.id.cb_addbaoxian_shangyexian_chengkezuoweizerenxian:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_chengkezuoweizerenxian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_chengkezuoweizerenxian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_chengkezuoweizerenxian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_chengkezuoweizerenxian.getText().toString());
                }
                setJiage();
                break;
            case R.id.cb_addbaoxian_shangyexian_bolidanduposuixian:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_bolidanduposuixian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_bolidanduposuixian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_bolidanduposuixian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_bolidanduposuixian.getText().toString());
                }
                setJiage();
                break;
            case R.id.cb_addbaoxian_shangyexian_cheshenhuahensunshixian:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_cheshenhuahensunshixian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_cheshenhuahensunshixian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_cheshenhuahensunshixian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_cheshenhuahensunshixian.getText().toString());
                }
                setJiage();
                break;
            case R.id.cb_addbaoxian_shangyexian_ziransunshixian:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_ziransunshixian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_ziransunshixian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_ziransunshixian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_ziransunshixian.getText().toString());
                }
                setJiage();
                break;
            case R.id.cb_addbaoxian_shangyexian_fadongjisheshuisunshixian:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_fadongjisheshuisunshixian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_fadongjisheshuisunshixian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_fadongjisheshuisunshixian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_fadongjisheshuisunshixian.getText().toString());
                }
                setJiage();
                break;
            case R.id.cb_addbaoxian_shangyexian_zhidingxiulichangxian:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_zhidingxiulichangxian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_zhidingxiulichangxian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_zhidingxiulichangxian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_zhidingxiulichangxian.getText().toString());
                }
                setJiage();
                break;
            case R.id.cb_addbaoxian_shangyexian_jidongchesunshixian:
                if (isChecked) {
                    shangyexian_zongjia += Double.parseDouble(tv_shangyexian_baofei_jidongchesunshixian.getText().toString());
                    baoxianzongjia += Double.parseDouble(tv_shangyexian_baofei_jidongchesunshixian.getText().toString());
                } else {
                    shangyexian_zongjia -= Double.parseDouble(tv_shangyexian_baofei_jidongchesunshixian.getText().toString());
                    baoxianzongjia -= Double.parseDouble(tv_shangyexian_baofei_jidongchesunshixian.getText().toString());
                }
                setJiage();
                break;
        }
    }

    private void setJiage() {
        tv_shangyexian_zongjia.setText("" + shangyexian_zongjia);
        tv_hejijine.setText("" + baoxianzongjia);
    }
}
