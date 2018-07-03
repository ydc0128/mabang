package com.example.administrator.ydwlxcpt.Activity;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.ydwlxcpt.Adapter.DialogListViewAdapter;
import com.example.administrator.ydwlxcpt.Bean.Dingdan;
import com.example.administrator.ydwlxcpt.Bean.Error;
import com.example.administrator.ydwlxcpt.Contast.Contast;
import com.example.administrator.ydwlxcpt.Fragment.JibenxinxiFragment;
import com.example.administrator.ydwlxcpt.R;
import com.example.administrator.ydwlxcpt.Utils.ImageUtils;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//评价
public class PingjiaActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = Contast.Domain + "api/Evaluate.ashx?";
    private static final String TAG = "PingjiaActivity";

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;


    private ImageView iv_back;
    private ImageView iv_touxiang;
    private TextView tv_name;
    private TextView tv_gonghao;
    private TextView tv_dingdanhao;
    private TextView tv_lianxidianhua;
    private ImageView iv_lianxita;
    private CheckBox cb_yixing;
    private CheckBox cb_erxing;
    private CheckBox cb_sanxing;
    private CheckBox cb_sixing;
    private CheckBox cb_wuxing;

    private CheckBox cb_fuwuzhoudao;
    private CheckBox cb_xichezhuanye;
    private CheckBox cb_zhunshidaoda;
    private CheckBox cb_fengyuwuzu;
    private CheckBox cb_chuanzhuozhengjie;
    private CheckBox cb_niming;
    private EditText et_pingjia;

    private ImageView iv_photo1;

//    private Button btn_dashang;
    private Button btn_tijiao;

    private Dingdan dingdan;
    private List<String> xichegongdianhua;
    private List<String> photoList;
    private Uri imageUri1;
    private File finalFile;
    private int star;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjia);
        Intent intent = getIntent();
        dingdan = (Dingdan) intent.getSerializableExtra("dingdan");
        initViews();

        setViews();
    }

    private void setViews() {
        if(TextUtils.isEmpty(dingdan.getO_WorkerImage())){
            Picasso.with(this).load(R.drawable.morentouxiang).into(iv_touxiang);
        }else{
            Uri image = Uri.parse(Contast.Domain + dingdan.getO_WorkerImage());
            Picasso.with(this).load(image)
                    .placeholder(R.drawable.morentouxiang)
                    .error(R.drawable.morentouxiang)
                    .into(iv_touxiang);
        }
        tv_name.setText(dingdan.getO_WorkerName());
        tv_gonghao.setText("" + dingdan.getO_WorkerID());
        tv_dingdanhao.setText(dingdan.getO_ID());
        tv_lianxidianhua.setText(dingdan.getO_WorkerTel());
    }

    private void initViews() {
        xichegongdianhua = new ArrayList<>();
        xichegongdianhua.add(dingdan.getO_WorkerTel());
        photoList = new ArrayList<>();
        photoList.add("拍照");
        photoList.add("从相册中选择");
        iv_back = (ImageView) findViewById(R.id.iv_pingjia_back);
        et_pingjia = (EditText) findViewById(R.id.et_pingjia_jianyi);
        iv_touxiang = (ImageView) findViewById(R.id.iv_pingjia_xichegong_touxiang);
        cb_yixing = (CheckBox) findViewById(R.id.cb_pingjia_yixing);
        cb_erxing = (CheckBox) findViewById(R.id.cb_pingjia_erxing);
        cb_sanxing = (CheckBox) findViewById(R.id.cb_pingjia_sanxing);
        cb_sixing = (CheckBox) findViewById(R.id.cb_pingjia_sixing);
        cb_wuxing = (CheckBox) findViewById(R.id.cb_pingjia_wuxing);
        iv_lianxita = (ImageView) findViewById(R.id.iv_pingjia_xichegong_lianxita);
        iv_photo1 = (ImageView) findViewById(R.id.iv_pingjia_photo1);

        cb_fuwuzhoudao = (CheckBox) findViewById(R.id.cb_pingjia_fuwuzhoudao);
        cb_fengyuwuzu = (CheckBox) findViewById(R.id.cb_pingjia_fengyuwuzu);
        cb_xichezhuanye = (CheckBox) findViewById(R.id.cb_pingjia_xichezhuanye);
        cb_zhunshidaoda = (CheckBox) findViewById(R.id.cb_pingjia_zhunshidaoda);
        cb_fengyuwuzu = (CheckBox) findViewById(R.id.cb_pingjia_fengyuwuzu);
        cb_chuanzhuozhengjie = (CheckBox) findViewById(R.id.cb_pingjia_chuanzhuozhengjie);
        cb_niming = (CheckBox) findViewById(R.id.cb_pingjia_niming);

//        btn_dashang = (Button) findViewById(R.id.btn_pingjia_dashang);
        btn_tijiao = (Button) findViewById(R.id.btn_pingjia_tijiao);
        tv_name = (TextView) findViewById(R.id.tv_pingjia_xichegong_name);
        tv_dingdanhao = (TextView) findViewById(R.id.tv_pingjia_dingdanhao);
        tv_gonghao = (TextView) findViewById(R.id.tv_pingjia_title_xichegong_gonghao);
        tv_lianxidianhua = (TextView) findViewById(R.id.tv_pingjia_xichegong_lianxifangshi);

        iv_back.setOnClickListener(this);
        iv_touxiang.setOnClickListener(this);
        iv_lianxita.setOnClickListener(this);
        iv_photo1.setOnClickListener(this);

//        btn_dashang.setOnClickListener(this);
        btn_tijiao.setOnClickListener(this);

        cb_yixing.setOnClickListener(this);
        cb_erxing.setOnClickListener(this);
        cb_sanxing.setOnClickListener(this);
        cb_sixing.setOnClickListener(this);
        cb_wuxing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_pingjia_back:
                finish();
                break;
            case R.id.iv_pingjia_xichegong_touxiang:
                break;
            case R.id.cb_pingjia_yixing:
                cb_yixing.setChecked(true);
                cb_erxing.setChecked(false);
                cb_sanxing.setChecked(false);
                cb_sixing.setChecked(false);
                cb_wuxing.setChecked(false);
                break;
            case R.id.cb_pingjia_erxing:
                cb_yixing.setChecked(true);
                cb_erxing.setChecked(true);
                cb_sanxing.setChecked(false);
                cb_sixing.setChecked(false);
                cb_wuxing.setChecked(false);
                break;
            case R.id.cb_pingjia_sanxing:
                cb_yixing.setChecked(true);
                cb_erxing.setChecked(true);
                cb_sanxing.setChecked(true);
                cb_sixing.setChecked(false);
                cb_wuxing.setChecked(false);
                break;
            case R.id.cb_pingjia_sixing:
                cb_yixing.setChecked(true);
                cb_erxing.setChecked(true);
                cb_sanxing.setChecked(true);
                cb_sixing.setChecked(true);
                cb_wuxing.setChecked(false);
                break;
            case R.id.cb_pingjia_wuxing:
                cb_yixing.setChecked(true);
                cb_erxing.setChecked(true);
                cb_sanxing.setChecked(true);
                cb_sixing.setChecked(true);
                cb_wuxing.setChecked(true);
                break;
            case R.id.iv_pingjia_xichegong_lianxita:
                View photoView = View.inflate(PingjiaActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView = (ListView) photoView.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView.setAdapter(new DialogListViewAdapter(PingjiaActivity.this, xichegongdianhua));//ListView设置适配器

                final AlertDialog dialog = new AlertDialog.Builder(PingjiaActivity.this)
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
            case R.id.iv_pingjia_photo1:
                View photoView1 = View.inflate(PingjiaActivity.this, R.layout.item_listview_dialog, null);//填充ListView布局
                ListView listView1 = (ListView) photoView1.findViewById(R.id.lv_item_listview_dialog);//初始化ListView控件
                listView1.setAdapter(new DialogListViewAdapter(PingjiaActivity.this, photoList));//ListView设置适配器

                final AlertDialog dialog1 = new AlertDialog.Builder(PingjiaActivity.this)
                        .setView(photoView1)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                dialog1.show();
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        TextView tv = (TextView) arg1.findViewById(R.id.tv_item_listview_dialog_text);//取得每条item中的textview控件
                        String aiche = tv.getText().toString();
                        if ("拍照".equals(aiche)) {
                            File outputImage = new File(PingjiaActivity.this.getExternalCacheDir(),
                                    "output_image_1.jpg");
                            try {
                                if (outputImage.exists()) {
                                    outputImage.delete();
                                }
                                outputImage.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (Build.VERSION.SDK_INT >= 24) {
                                imageUri1 = FileProvider.getUriForFile(PingjiaActivity.this,
                                        "com.example.administrator.ydwlxcpt.fileprovider", outputImage);
                            } else {
                                imageUri1 = Uri.fromFile(outputImage);
                            }
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri1);
                            startActivityForResult(intent, TAKE_PHOTO);
                        } else if ("从相册中选择".equals(aiche)) {
                            Intent intent = new Intent("android.intent.action.GET_CONTENT");
                            intent.setType("image/*");
                            startActivityForResult(intent, CHOOSE_PHOTO);
                        }
                        dialog1.dismiss();
                    }
                });
                break;


//            case R.id.btn_pingjia_dashang:
//                Intent intent = new Intent(PingjiaActivity.this, DashangActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("dingdan", dingdan);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
            case R.id.btn_pingjia_tijiao:
                tijiao();
                break;

        }
    }

    private void tijiao() {
        if (cb_wuxing.isChecked()) {
            star = 5;
        } else if (cb_sixing.isChecked()) {
            star = 4;
        } else if (cb_sanxing.isChecked()) {
            star = 3;
        } else if (cb_erxing.isChecked()) {
            star = 2;
        } else if (cb_yixing.isChecked()) {
            star = 1;
        } else {
            star = 5;
        }

        StringBuffer sb = new StringBuffer();
        if (cb_fuwuzhoudao.isChecked()) {
            sb.append("服务周到,");
        } else if (cb_xichezhuanye.isChecked()) {
            sb.append("洗车专业,");
        } else if (cb_zhunshidaoda.isChecked()) {
            sb.append("准时到达,");
        } else if (cb_fengyuwuzu.isChecked()) {
            sb.append("风雨无阻,");
        } else if (cb_chuanzhuozhengjie.isChecked()) {
            sb.append("穿着整洁,");
        }
        if (!TextUtils.isEmpty(sb.toString())) {
            sb.substring(0, sb.length() - 1);
        }
        String type = sb.toString();
        String value = et_pingjia.getText().toString();
        FormBody.Builder params = new FormBody.Builder();
        params.add("keys", Contast.KEYS);
        params.add("O_UTel", dingdan.getO_UTel());
        params.add("O_PlateNumber", dingdan.getO_PlateNumber());
        params.add("O_ID", dingdan.getO_ID());
        if (cb_niming.isChecked()) {
            params.add("O_EvaluateAnonymous", "0");
        } else {
            params.add("O_EvaluateAnonymous", "1");
        }
        params.add("O_EvaluateStar", "" + star);
        params.add("O_EvaluateType", type);
        if (TextUtils.isEmpty(value)) {
            params.add("O_Evaluate", "");
        } else {
            params.add("O_Evaluate", value);
        }
        if (finalFile == null) {
            params.add("data", "");
        } else {
            String imageStr = ImageUtils.getBase64String(finalFile);
            params.add("data", imageStr);
        }
        for (int i = 0;i<params.build().size();i++){
            Log.i(TAG, "tijiao: "+params.build().value(i));
        }
        OkHttpClient client = new OkHttpClient();
//        构建请求
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
                        Toast.makeText(PingjiaActivity.this, "评价失败，请稍后重试...", Toast.LENGTH_SHORT).show();
                        return;

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i(TAG, "onResponse: " + string);
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(PingjiaActivity.this, "服务器连接异常，请稍后重试...", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    if (!TextUtils.isEmpty(string)) {
                        if (string.contains("ErrorStr")) {
                            final Error error = JSON.parseObject(string, Error.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PingjiaActivity.this, error.getErrorStr(), Toast.LENGTH_SHORT).show();
                                    return;

                                }
                            });
                        } else if (string.contains("OkStr")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PingjiaActivity.this, "评价成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PingjiaActivity.this, "评价失败，请稍后重试...", Toast.LENGTH_SHORT).show();
                                return;

                            }
                        });
                    }
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    beginCrop(imageUri1);
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
            case Crop.REQUEST_PICK:
                if (resultCode == RESULT_OK) {
                    beginCrop(data.getData());
                }
                break;
            case Crop.REQUEST_CROP:
                handleCrop(resultCode, data);
        }
    }


    // 将裁剪回来的数据进行处理
    private void handleCrop(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            iv_photo1.setImageURI(null);
            Uri uri = Crop.getOutput(data);
            try {
                finalFile = new File(ImageUtils.saveBitmap(PingjiaActivity.this, uri.getPath()));
                Uri image = Uri.fromFile(finalFile);
                iv_photo1.setImageURI(image);
                Log.i("image", "finalFile=" + finalFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(PingjiaActivity.this, Crop.getError(data).getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // 开始裁剪
    private void beginCrop(Uri uri) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped_pingjia.jpg"));
        // start() 方法根据其的需求选择不同的重载方法
        Crop.of(uri, destination).asSquare().start(PingjiaActivity.this);
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
        if (DocumentsContract.isDocumentUri(PingjiaActivity.this, uri)) {
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
            Toast.makeText(PingjiaActivity.this, "图片选取失败", Toast.LENGTH_SHORT).show();
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
