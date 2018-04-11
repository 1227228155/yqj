package com.erze.yqj.moudle.report;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.CodeBean;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.erze.yqj.utils.AutoUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReportActivity extends AppCompatActivity {
    protected static final int CHOOSE_PICTURE = 0;
    private static final int CROP_SMALL_PICTURE = 2;
    @BindView(R.id.report_add_pic)
    ImageView reportAddPic;
    @BindView(R.id.report_edit)
    EditText reportEdit;
    private SPUtils spUtils = SPUtils.getInstance("user");
    private String reportString;
    private Map<String, String> map = new ArrayMap<>();
    private String AVATAR_PATH = "/sdcard/yqj"+"/"+ System.currentTimeMillis()+".png";//举报图片存放的地址
    private  Bitmap photo;//举报的图片
    private String video_url,pic_url,title,video_id,username,userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        video_url = getIntent().getStringExtra("video_url");
        pic_url = getIntent().getStringExtra("pic_url");
        title = getIntent().getStringExtra("title");
        video_id = getIntent().getStringExtra("video_id");
        username = getIntent().getStringExtra("username");
        userid = getIntent().getStringExtra("userid");
    }

    @OnClick({R.id.submit, R.id.report_add_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                sunmit();
                break;
            case R.id.report_add_pic:
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, CHOOSE_PICTURE);
                break;
        }
    }

    private void sunmit() {
        reportString = reportEdit.getText().toString().trim();
        if (EmptyUtils.isNotEmpty(reportString)){
            File file = new File(AVATAR_PATH);//filePath 图片地址
            sendReport(spUtils.getString("userid"),userid,video_id,reportString,file);
        }else {
            Toast.makeText(this, "举报内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendReport(String a1,String a2,String a3,String a4,File a5) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("user_id", a1);//ParamKey.TOKEN 自定义参数key常量类，即参数名
        builder.addFormDataPart("report_user_id", a2);
        builder.addFormDataPart("video_id", a3);
        builder.addFormDataPart("report_content", a4);
        if (photo!=null){
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), a5);
            builder.addFormDataPart("img", a5.getName(), imageBody);//imgfile 后台接收图片流的参数名
        }else {
            builder.addFormDataPart("img", "");
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        NetDao.getInstance().getCommonApi().report(parts).compose(Rxschedulers.<CodeBean>io_main()).subscribe(new Consumer<CodeBean>() {
            @Override
            public void accept(@NonNull CodeBean bean) throws Exception {
                        if (bean.getCode().equals("200")){
                            Toast.makeText(ReportActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                            reportEdit.setText("");
                            reportAddPic.setImageResource(R.mipmap.add_pic);
                        }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
             photo = extras.getParcelable("data");
            reportAddPic.setImageBitmap(photo);
            FileIOUtils.writeFileFromBytesByStream(AVATAR_PATH, ConvertUtils.bitmap2Bytes(photo, Bitmap.CompressFormat.PNG));
        }
    }


    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
}
