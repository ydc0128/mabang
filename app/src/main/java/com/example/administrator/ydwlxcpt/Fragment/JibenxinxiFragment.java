package com.example.administrator.ydwlxcpt.Fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Activity.AddCarActivity;
import com.example.administrator.ydwlxcpt.Activity.FlowLayoutActivity;
import com.example.administrator.ydwlxcpt.Activity.PingpaiActivity;
import com.example.administrator.ydwlxcpt.Activity.YanSeActivity;
import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Bean.Car;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.ImageUtils;
import com.example.administrator.ydwlxcpt.Utils.StringUtils;
import com.example.administrator.ydwlxcpt.View.FirstEvent;
import com.example.administrator.ydwlxcpt.View.TwoEvent;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class JibenxinxiFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "JibenxinxiFragment";

    private String url = Contast.Domain + "api/CarAdd.ashx?";
    private String url_update = Contast.Domain + "api/CarUpdate.ashx?";
    private String url_delete = Contast.Domain + "api/CarDelete.ashx?";

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private ImageView iv_photo;
    private Button btn_photo;
    private TextView et_chepaihao;
    private TextView tv_chexing;
    private TextView et_yanse;
    private EditText et_xingming;
    private EditText et_shoujihao;
    private LinearLayout ll_jibie;
    private LinearLayout ll_chexing;
    private LinearLayout ll_chepai;
    private LinearLayout ll_yanse;
    private Button btn_baocun;
    //    private Button btn_shanchu;
    private List<String> jibieList;
    private TextView tv_jibie;
    private Uri imageUri;
    private File finalFile;
    private PopupWindow popupWindow;
    private Car car;
    private int from;
    private AddCarActivity mActivity;


    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


    private List<String> photoList;


    public JibenxinxiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jibenxinxi, container, false);
        initViews(view);
        initList();
        setViews();
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        if (!TextUtils.isEmpty(Contast.Pingpai) && !TextUtils.isEmpty(Contast.Xinghao)) {
            tv_chexing.setText(Contast.Pingpai + "-" + Contast.Xinghao);
        }
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    iv_photo.setClickable(true);
//                    et_chepaihao.setCursorVisible(true);
//                    et_chepaihao.setFocusable(true);
//                    et_chepaihao.setFocusableInTouchMode(true);
                    et_shoujihao.setCursorVisible(true);
                    et_shoujihao.setFocusable(true);
                    et_shoujihao.setFocusableInTouchMode(true);
                    et_xingming.setCursorVisible(true);
                    et_xingming.setFocusable(true);
                    et_xingming.setFocusableInTouchMode(true);
//                    et_yanse.setCursorVisible(true);
//                    et_yanse.setFocusable(true);
//                    et_yanse.setFocusableInTouchMode(true);
                    ll_chexing.setClickable(true);
                    ll_jibie.setClickable(true);
                    ll_chepai.setClickable(true);
                    btn_baocun.setVisibility(View.VISIBLE);
                    ll_yanse.setClickable(true);
//                    btn_shanchu.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (AddCarActivity) activity;
        mActivity.setHandler(mHandler);
    }


    private void initViews(View view) {
        iv_photo = (ImageView) view.findViewById(R.id.iv_fragment_jibenxinxi_photo);
        btn_photo = (Button) view.findViewById(R.id.btn_fragment_jibenxinxi_photo);
        et_chepaihao = (TextView) view.findViewById(R.id.et_fragment_jibenxinxi_chepaihaoma);
        tv_chexing = (TextView) view.findViewById(R.id.tv_fragment_jibenxinxi_chexing);
        et_yanse = (TextView) view.findViewById(R.id.et_fragment_jibenxinxi_yanse);
        et_xingming = (EditText) view.findViewById(R.id.et_fragment_jibenxinxi_xingming);
        et_shoujihao = (EditText) view.findViewById(R.id.et_fragment_jibenxinxi_shoujihao);
        ll_jibie = (LinearLayout) view.findViewById(R.id.ll_fragment_jibenxinxi_jibie);
        ll_chexing = (LinearLayout) view.findViewById(R.id.ll_fragment_jibenxinxi_chexing);
        btn_baocun = (Button) view.findViewById(R.id.btn_fragment_jibenxinxi_baocun);
//        btn_shanchu = (Button) view.findViewById(R.id.btn_fragment_jibenxinxi_shanchu);
        tv_jibie = (TextView) view.findViewById(R.id.tv_fragment_jibenxinxi_jibie);
        ll_chepai = (LinearLayout) view.findViewById(R.id.ll_fragment_jibenxinxi_chepaihaoma);
        ll_yanse = (LinearLayout) view.findViewById(R.id.ll_fragment_jibenxinxi_yanse);
        btn_photo.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        ll_jibie.setOnClickListener(this);
        ll_chexing.setOnClickListener(this);
        btn_baocun.setOnClickListener(this);
        et_yanse.setOnClickListener(this);
        ll_chepai.setOnClickListener(this);
        ll_yanse.setOnClickListener(this);


//        btn_shanchu.setOnClickListener(this);
    }

    private void setViews() {
        from = AddCarActivity.from;
        car = AddCarActivity.car;
        if (from == 0) {
            et_chepaihao.setText(car.getC_PlateNumber());
            Contast.Pingpai = car.getC_BrandName();
            Contast.Xinghao = car.getC_BrandTypeName();
            tv_chexing.setText(car.getC_BrandName() + "-" + car.getC_BrandTypeName());
            if (car.getC_CTID() == 3) {
                tv_jibie.setText("小轿车");
            } else if (car.getC_CTID() == 4) {
                tv_jibie.setText("SUV/MPV");
            }
            et_yanse.setText(car.getC_Color());
            et_xingming.setText(car.getC_CarName());
            et_shoujihao.setText(car.getC_UTel());
            //从网络加载图片
            Uri image = Uri.parse(Contast.Domain + car.getC_Image());
            Picasso.with(getActivity()).load(image)
                    .placeholder(R.drawable.carmoren)
                    .error(R.drawable.carmoren)
                    .into(iv_photo);
            btn_photo.setVisibility(View.INVISIBLE);
            iv_photo.setClickable(false);
//            et_chepaihao.setCursorVisible(false);
//            et_chepaihao.setFocusable(false);
//            et_chepaihao.setFocusableInTouchMode(false);
            et_shoujihao.setCursorVisible(false);
            et_shoujihao.setFocusable(false);
            et_shoujihao.setFocusableInTouchMode(false);
            et_xingming.setCursorVisible(false);
            et_xingming.setFocusable(false);
            et_xingming.setFocusableInTouchMode(false);
//            et_yanse.setCursorVisible(false);
//            et_yanse.setFocusable(false);
//            et_yanse.setFocusableInTouchMode(false);
            ll_chexing.setClickable(false);
            ll_jibie.setClickable(false);
            ll_chepai.setClickable(false);
            ll_yanse.setClickable(false);
//            btn_baocun.setVisibility(View.GONE);
//            btn_shanchu.setVisibility(View.GONE);
        } else {
            iv_photo.setClickable(true);
//            et_chepaihao.setCursorVisible(true);
//            et_chepaihao.setFocusable(true);
//            et_chepaihao.setFocusableInTouchMode(true);
            et_shoujihao.setCursorVisible(true);
            et_shoujihao.setFocusable(true);
            et_shoujihao.setFocusableInTouchMode(true);
            et_xingming.setCursorVisible(true);
            et_xingming.setFocusable(true);
            et_xingming.setFocusableInTouchMode(true);
//            et_yanse.setCursorVisible(true);
//            et_yanse.setFocusable(true);
//            et_yanse.setFocusableInTouchMode(true);
            ll_chexing.setClickable(true);
            ll_jibie.setClickable(true);
            ll_chepai.setClickable(true);
            btn_baocun.setVisibility(View.VISIBLE);
            ll_yanse.setClickable(true);
//            if (chepaihao.toString().substring(3).equals("D")||chepaihao.toString().substring(3).equals("F")){
////
//                }
//            btn_shanchu.setVisibility(View.GONE);
        }
    }

    private void initList() {
        jibieList = new ArrayList<>();
        jibieList.add("小轿车");
        jibieList.add("SUV/MPV");
        photoList = new ArrayList<>();
        photoList.add("拍照");
        photoList.add("从相册中选择");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment_jibenxinxi_photo:
            case R.id.iv_fragment_jibenxinxi_photo:
                View photoView = View.inflate(getActivity(), R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView = (ListView) photoView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView.setAdapter(new DialogListViewAdapter(getActivity(), photoList));//ListView设置适配器

                final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setView(photoView)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        if ("拍照".equals(aiche)) {
                            File outputImage = new File(getActivity().getExternalCacheDir(),
                                    System.currentTimeMillis() + "output_image.jpg");
                            try {
                                if (outputImage.exists()) {
                                    outputImage.delete();
                                }
                                outputImage.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (Build.VERSION.SDK_INT >= 24) {
                                imageUri = FileProvider.getUriForFile(getActivity(),
                                        "com.example.administrator.ydwlxcpt.fileprovider", outputImage);
                            } else {
                                imageUri = Uri.fromFile(outputImage);
                            }
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent, TAKE_PHOTO);
                        } else if ("从相册中选择".equals(aiche)) {
                            Intent intent = new Intent("android.intent.action.GET_CONTENT");
                            intent.setType("image/*");
                            startActivityForResult(intent, CHOOSE_PHOTO);
                        }
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.ll_fragment_jibenxinxi_jibie:
                View bottomView = View.inflate(getActivity(), R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView2 = (ListView) bottomView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView2.setAdapter(new DialogListViewAdapter(getActivity(), jibieList));//ListView设置适配器

                final AlertDialog dialog2 = new AlertDialog.Builder(getActivity())
                        .setView(bottomView)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                dialog2.show();
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        tv_jibie.setText(aiche);
                        dialog2.dismiss();
                    }
                });
                break;
            case R.id.ll_fragment_jibenxinxi_chepaihaoma:
                Intent intent1 = new Intent(getActivity(), FlowLayoutActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_fragment_jibenxinxi_chexing:
                Intent intent = new Intent(getActivity(), PingpaiActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_fragment_jibenxinxi_yanse:
//                final OptionPicker picker = new OptionPicker(getActivity(), new String[]{
//                        "白", "灰", "红", "蓝", "黑", "银", "棕","绿","黄"
//                });
//                picker.setCycleDisable(true);//不禁用循环
//                picker.setLineVisible(false);//可见行
//                picker.setTextSize(16);
//                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
//                    @Override
//                    public void onOptionPicked(int index, String item) {
//                        String s = picker.getSelectedItem().toString();
//                        et_yanse.setText(s);
//                    }
//                });
//                picker.show();
                Intent intent2 = new Intent(getActivity(), YanSeActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_fragment_jibenxinxi_baocun:
                final String chepaihao = et_chepaihao.getText().toString().trim();
                String jibie = tv_jibie.getText().toString().trim();
                String chexing = tv_chexing.getText().toString().trim();
                String yanse = et_yanse.getText().toString().trim();
                String xingming = et_xingming.getText().toString().trim();
                String shoujihao = et_shoujihao.getText().toString().trim();
                if (TextUtils.isEmpty(chepaihao)) {
                    Toast.makeText(getActivity(), "车牌号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!StringUtils.isChinese(xingming)) {
                    Toast.makeText(getActivity(), "姓名输入有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (chepaihao.toString().substring(2,3).equals("D")||chepaihao.toString().substring(2,3).equals("F")){

                } else if (!StringUtils.isChepaihao(chepaihao)) {
                    Toast.makeText(getActivity(), "车牌号输入有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (chexing.equals("请选择")) {
                    Toast.makeText(getActivity(), "车型不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(jibie)) {
                    Toast.makeText(getActivity(), "级别不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(yanse)) {
                    Toast.makeText(getActivity(), "颜色不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(xingming)) {
                    Toast.makeText(getActivity(), "联系人不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(shoujihao)) {
                    Toast.makeText(getActivity(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!StringUtils.isMobileNO(shoujihao)) {
                    Toast.makeText(getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (from == 1) {
                    if (finalFile == null) {
                        Toast.makeText(getActivity(), "请上传车辆照片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final ProgressDialog pd = new ProgressDialog(getActivity());
                    pd.setMessage("拼命加载中...");
                    pd.show();
                    FormBody.Builder params = new FormBody.Builder();
                    String imageStr = ImageUtils.getBase64String(finalFile);
                    params.add("C_PlateNumber", chepaihao);
                    params.add("C_BrandName", Contast.Pingpai);
                    params.add("C_BrandTypeName", Contast.Pingpai);
                    params.add("C_Color", yanse);
                    if (jibie.equals("小轿车")) {
                        params.add("C_CTID", "3");
                    } else if (jibie.equals("SUV/MPV")) {
                        params.add("C_CTID", "4");
                    }
                    params.add("C_CarTel", shoujihao);
                    params.add("C_CarName", xingming);
                    params.add("C_UTel", Contast.user.getU_Tel());
                    params.add("data", imageStr);
                    params.add("keys", Contast.KEYS);
                    Log.i(TAG, "onClick: " + imageStr);
                    OkHttpClient client = new OkHttpClient();
                    //构建请求
                    final Request request = new Request.Builder()
                            .url(url)
                            .post(params.build())//添加请求体
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            pd.dismiss();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "上传失败，请稍后重试...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            pd.dismiss();
                            String string = response.body().string();
                            if (response.code() != HttpURLConnection.HTTP_OK) {
                                Toast.makeText(getActivity(), "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.i(TAG, "onResponse: " + string);
                                if (!TextUtils.isEmpty(string)) {
                                    if (string.contains("ErrorStr")) {
                                        final Error error = JSON.parseObject(string, Error.class);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else if (string.contains("OkStr")) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), "车辆添加成功", Toast.LENGTH_SHORT).show();
                                                getActivity().finish();
                                            }
                                        });
                                    }
                                } else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else if (from == 0) {
                    final ProgressDialog pd = new ProgressDialog(getActivity());
                    pd.setMessage("拼命加载中...");
                    pd.show();
                    FormBody.Builder params = new FormBody.Builder();
                    iv_photo.setDrawingCacheEnabled(true);
                    Bitmap bitmap = iv_photo.getDrawingCache();
                    String imageStr = ImageUtils.getBase64String(ImageUtils.saveBitmapFile(bitmap, getActivity().getCacheDir()));
                    iv_photo.setDrawingCacheEnabled(false);
                    params.add("C_PlateNumber", car.getC_PlateNumber());
                    params.add("C_PlateNumberNew", chepaihao);
                    params.add("C_BrandName", Contast.Pingpai);
                    params.add("C_BrandTypeName", Contast.Xinghao);
                    params.add("C_Color", yanse);
                    if (jibie.equals("小轿车")) {
                        params.add("C_CTID", "3");
                    } else if (jibie.equals("SUV/MPV")) {
                        params.add("C_CTID", "4");
                    }
                    params.add("C_CarTel", shoujihao);
                    params.add("C_CarName", xingming);
                    params.add("C_UTel", Contast.user.getU_Tel());
                    params.add("data", imageStr);
                    params.add("keys", Contast.KEYS);
                    Log.i(TAG, "onClick: " + imageStr);
                    OkHttpClient client = new OkHttpClient();
                    //构建请求
                    final Request request = new Request.Builder()
                            .url(url_update)
                            .post(params.build())//添加请求体
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            pd.dismiss();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "上传失败，请稍后重试...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            pd.dismiss();
                            String string = response.body().string();
                            if (response.code() != HttpURLConnection.HTTP_OK) {
                                Toast.makeText(getActivity(), "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.i(TAG, "onResponse: " + string);
                                if (!TextUtils.isEmpty(string)) {
                                    if (string.contains("ErrorStr")) {
                                        final Error error = JSON.parseObject(string, Error.class);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else if (string.contains("OkStr")) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), "修改完成", Toast.LENGTH_SHORT).show();
                                                getActivity().finish();
                                            }
                                        });
                                    }
                                } else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
                break;
//            case R.id.btn_fragment_jibenxinxi_shanchu:
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("提示");
//                builder.setMessage("您确定要删除此车辆吗？");
//                builder.setNegativeButton("取消", null);
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        deleteCar();
//                    }
//                });
//                builder.create().show();
//                break;
        }
    }
//
//    private void deleteCar() {
//        FormBody.Builder params = new FormBody.Builder();
//        params.add("keys", Contast.KEYS);
//        params.add("C_UTel", car.getC_UTel());
//        params.add("C_PlateNumber", car.getC_PlateNumber());
//        OkHttpClient client = new OkHttpClient();
//        //构建请求
//        final Request request = new Request.Builder()
//                .url(url_delete)
//                .post(params.build())//添加请求体
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getActivity(), "上传失败，请稍后重试...", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String string = response.body().string();
//                if(response.code() != HttpURLConnection.HTTP_OK){
//                    Toast.makeText(getActivity(), "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
//                }else {
//                    Log.i(TAG, "onResponse: " + string);
//                    if (!TextUtils.isEmpty(string)) {
//                        if (string.contains("ErrorStr")) {
//                            final Error error = JSON.parseObject(string, Error.class);
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(getActivity(), error.getErrorStr(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else if (string.contains("OkStr")) {
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
//                                    getActivity().finish();
//                                }
//                            });
//                        }
//                    } else {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getActivity(), "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//            }
//        });
//    }

    /*
  * 判断sdcard是否被挂载
  */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    beginCrop(imageUri);
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKatKit(data);
                    } else {
                        handleImageBeforKatKit(data);
                    }
                }
                break;
            case Crop.REQUEST_PICK:
                if (resultCode == getActivity().RESULT_OK) {
                    beginCrop(data.getData());
                }
                break;
            case Crop.REQUEST_CROP:
                handleCrop(resultCode, data);
                break;
        }
    }

    // 将裁剪回来的数据进行处理
    private void handleCrop(int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            iv_photo.setImageURI(null);
            Uri uri = Crop.getOutput(data);
            try {
                finalFile = new File(ImageUtils.saveBitmap(getActivity(), uri.getPath()));
                Uri image = Uri.fromFile(finalFile);
                iv_photo.setImageURI(image);
                btn_photo.setVisibility(View.INVISIBLE);
                Log.i("image", "finalFile=" + finalFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(data).getMessage(),
                    Toast.LENGTH_SHORT).show();

        }
    }


    // 开始裁剪
    private void beginCrop(Uri uri) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped.jpg"));
        // start() 方法根据其的需求选择不同的重载方法
        Crop.of(uri, destination).withMaxSize(500, 500).start(getActivity(), JibenxinxiFragment.this);
    }


    private void handleImageBeforKatKit(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        disPlayImage(imagePath);
    }

    @TargetApi(19)
    private void handleImageOnKatKit(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            //  如果是Document类型的uri，则通过Document  Id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse(
                        "content://downloads/public/_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，则直接获取图片路径即可
            imagePath = uri.getPath();
        }
        disPlayImage(imagePath);
    }

    private void disPlayImage(String imagePath) {
        if (imagePath != null) {
            Uri uri = Uri.fromFile(new File(imagePath));
            beginCrop(uri);
        } else {
            Toast.makeText(getActivity(), "图片选取失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FirstEvent event) {
        String msg = event.getMsg();
        Log.d("harvic", msg);
        et_chepaihao.setText(msg);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent2(TwoEvent event) {
        String msg = event.getMsg();
        Log.d("harvic", msg);
        et_yanse.setText(msg);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过uri和selection来获取图片的真是路径
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


}
