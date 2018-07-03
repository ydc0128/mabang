package com.example.administrator.ydwlxcpt.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Bean.User;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.Fragment.JibenxinxiFragment;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.ImageUtils;
import com.example.administrator.ydwlxcpt.Utils.StringUtils;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//个人资料
public class GerenziliaoActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "GerenziliaoActivity";

    private String url = Contast.Domain + "api/UserInfo.ashx?";

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int NAME = 3;

    private ImageView iv_back;
    private CircleImageView cv_touxiang;
    private EditText tv_xingming;
    private TextView tv_lianxifangshi;
    private Button btn_bianji;
    private Button btn_baocun;
    private List<String> photoList;
    private RadioButton rb_nan;
    private RadioButton rb_nv;
    private String xingbie;
    private Uri imageUri;
    private File finalFile;
    String imageStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenziliao);
        initViews();
        setViews();
        initListView();
    }

    private void initListView() {
        photoList = new ArrayList<>();
        photoList.add("拍照");
        photoList.add("从相册中选择");
    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_gerenziliao_back);
        btn_bianji = (Button) findViewById(R.id.btn_gerenziliao_more);
        btn_baocun = (Button) findViewById(R.id.btn_gerenziliao_baocun);
        rb_nan =(RadioButton) findViewById(R.id.rb_myziliao_nan);
        rb_nv =  (RadioButton) findViewById(R.id.rb_myziliao_nv);
        cv_touxiang = (CircleImageView) findViewById(R.id.cv_gerenziliao_touxiang);
        tv_xingming = (EditText) findViewById(R.id.tv_gerenziliao_xingming);
        tv_lianxifangshi = (TextView) findViewById(R.id.tv_gerenziliao_lianxifangshi);

        iv_back.setOnClickListener(this);
        btn_bianji.setOnClickListener(this);
        btn_baocun.setOnClickListener(this);
        cv_touxiang.setOnClickListener(this);
        tv_xingming.setOnClickListener(this);
        rb_nan.setOnClickListener(this);
        rb_nv.setOnClickListener(this);
        rb_nan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb_nv.setChecked(false);
                }else{
                    rb_nv.setChecked(true);
                }
            }
        });
        rb_nv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb_nan.setChecked(false);
                }else{
                    rb_nan.setChecked(true);
                }
            }
        });

    }


    private void setViews() {
        if(TextUtils.isEmpty(Contast.user.getU_Image())){
            Picasso.with(GerenziliaoActivity.this).load(R.drawable.touxiang).into(cv_touxiang);
        }else {
            Uri image = Uri.parse(Contast.Domain + Contast.user.getU_Image());
            Picasso.with(GerenziliaoActivity.this).load(image)
                    .placeholder(R.drawable.touxiang)
                    .error(R.drawable.touxiang)
                    .into(cv_touxiang);
        }
        tv_lianxifangshi.setText(Contast.user.getU_Tel());
        if(Contast.user.getU_Sex().equals("男")){
            rb_nan.setChecked(true);
        }else if(Contast.user.getU_Sex().equals("女")){
            rb_nv.setChecked(true);
        }
        tv_xingming.setText(Contast.user.getU_Name());
        cv_touxiang.setClickable(false);
        tv_xingming.setClickable(false);
        rb_nan.setClickable(false);
        rb_nv.setClickable(false);
        tv_xingming.setCursorVisible(false);
        tv_xingming.setFocusable(false);
        tv_xingming.setFocusableInTouchMode(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_gerenziliao_back:
                finish();
                break;
            case R.id.btn_gerenziliao_more:
                cv_touxiang.setClickable(true);
                tv_xingming.setClickable(true);
                rb_nv.setClickable(true);
                rb_nan.setClickable(true);
                tv_xingming.setCursorVisible(true);
                tv_xingming.setFocusable(true);
                tv_xingming.setFocusableInTouchMode(true);
                btn_bianji.setVisibility(View.INVISIBLE);
                btn_baocun.setVisibility(View.VISIBLE);
                break;
            case R.id.cv_gerenziliao_touxiang:
                View photoView = View.inflate(GerenziliaoActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView = (ListView) photoView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView.setAdapter(new DialogListViewAdapter(GerenziliaoActivity.this, photoList));//ListView设置适配器

                final AlertDialog dialog = new AlertDialog.Builder(GerenziliaoActivity.this)
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
                            File outputImage = new File(getExternalCacheDir(),
                                    System.currentTimeMillis()+"output_image.jpg");
                            try {
                                if (outputImage.exists()) {
                                    outputImage.delete();
                                }
                                outputImage.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (Build.VERSION.SDK_INT >= 24) {
                                imageUri = FileProvider.getUriForFile(GerenziliaoActivity.this,
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
            case R.id.btn_gerenziliao_baocun:
                if(rb_nan.isChecked()){
                    xingbie = "男";
                }else if(rb_nv.isChecked()){
                    xingbie = "女";
                }
                final String xingming = tv_xingming.getText().toString().trim();
                    if (TextUtils.isEmpty(xingming)) {
                        Toast.makeText(GerenziliaoActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(xingbie)) {
                        Toast.makeText(GerenziliaoActivity.this, "性别不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                if(TextUtils.isEmpty(Contast.user.getU_Image())){
                    if (finalFile == null) {
                        Toast.makeText(GerenziliaoActivity.this, "请上传头像", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(!StringUtils.isChinese(xingming)){
                    Toast.makeText(GerenziliaoActivity.this, "姓名输入有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(finalFile==null){
                    imageStr = "";
                }else{
                    imageStr = ImageUtils.getBase64String(finalFile);
                }
                FormBody.Builder params = new FormBody.Builder();
                Log.i(TAG, "onClick: imageStr="+imageStr);
                params.add("data", imageStr);
                params.add("keys", Contast.KEYS);
                params.add("U_Tel", Contast.user.getU_Tel());
                params.add("U_IMEI", Contast.user.getU_IMEI());
                params.add("U_Name", xingming);
                params.add("U_Sex", xingbie);
                OkHttpClient client = new OkHttpClient();
                //构建请求
                final Request request = new Request.Builder()
                        .url(url)
                        .post(params.build())//添加请求体
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GerenziliaoActivity.this, "上传失败，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        if(response.code() != HttpURLConnection.HTTP_OK){
                            Toast.makeText(GerenziliaoActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                        }else {
                            Log.i(TAG, "onResponse: " + string);
                            if (!TextUtils.isEmpty(string)) {
                                if (string.contains("ErrorStr")) {
                                    final Error error = JSON.parseObject(string, Error.class);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GerenziliaoActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    });
                                } else {
                                    Contast.user = JSON.parseObject(string, User.class);
                                    Log.i(TAG, "onResponse: " + Contast.user.toString());
                                    //如果验证成功
                                    SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                                    SharedPreferences.Editor edit = sp.edit();
                                    edit.putBoolean("isLogin", true);
                                    edit.putString("U_Tel", Contast.user.getU_Tel());
                                    edit.putString("U_IMEI", Contast.user.getU_IMEI());
                                    edit.commit();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GerenziliaoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Toast.makeText(GerenziliaoActivity.this, "服务器繁忙，请稍后重试...", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });
                            }
                        }
                    }
                });
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    beginCrop(imageUri);
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKatKit(data);
                    } else {
                        handleImageBeforKatKit(data);
                    }
                }
                break;
            case NAME:
                if(resultCode == RESULT_OK){
                    String name = data.getStringExtra("name");
                    Log.i(TAG, "onActivityResult: name="+name);
                    tv_xingming.setText(name);
                }
                break;
            case Crop.REQUEST_PICK:
                if (resultCode == RESULT_OK) {
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
        if (resultCode == RESULT_OK) {
            cv_touxiang.setImageURI(null);
            Uri uri = Crop.getOutput(data);
            try {
                finalFile = new File(ImageUtils.saveBitmap(GerenziliaoActivity.this, uri.getPath()));
                Uri image = Uri.fromFile(finalFile);
                Picasso.with(GerenziliaoActivity.this).load(image).into(cv_touxiang);
                Log.i("image", "finalFile=" + finalFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(GerenziliaoActivity.this, Crop.getError(data).getMessage(),
                    Toast.LENGTH_SHORT).show();

        }
    }


    // 开始裁剪
    private void beginCrop(Uri uri) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped.jpg"));
        // start() 方法根据其的需求选择不同的重载方法
        Crop.of(uri, destination).asSquare().start(this);
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
        if (DocumentsContract.isDocumentUri(GerenziliaoActivity.this, uri)) {
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
            Toast.makeText(GerenziliaoActivity.this, "图片选取失败", Toast.LENGTH_SHORT).show();
        }
    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过uri和selection来获取图片的真是路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
