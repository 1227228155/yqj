package com.erze.yqj.moudle.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.BgBean;
import com.erze.yqj.moudle.shortvideo.KS3TokenTask;
import com.erze.yqj.moudle.voice.ui.LoadingView;
import com.erze.yqj.moudle.voice.utils.Variable;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.ksyun.ks3.services.Ks3Client;
import com.ksyun.ks3.services.Ks3ClientConfiguration;

import java.io.File;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SelectBgPhotoActivity extends AppCompatActivity {
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    private String BG_PATH ;
    private Uri tempUri;
    private Bitmap photo;
    private SPUtils spUtils;
    @BindView(R.id.common_title)
    TextView commonTitle;
    Ks3Client client_img;
    Ks3ClientConfiguration configuration_img;
    KS3TokenTask tokenTask;
    private String img_objkey;
    private LoadingView loading;//加载View
    private Map<String, String> map = new ArrayMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bg_photo);
        ButterKnife.bind(this);
        commonTitle.setText("更换封面");
        spUtils = SPUtils.getInstance("user");
        initKs3();
        BG_PATH = Variable.StorageImagePath+ System.currentTimeMillis() + "bg.png";
        loading = new LoadingView(this, R.style.CustomDialog);
    }

    private void initKs3() {
            client_img = new Ks3Client("AKLTreSNKentSamx5EWX_KdTXA", "OADZU1xh7V2qS9h3DFcpjDXZRIFpaiJt35+vXltXGV6bg5Mwa2Ct9o88RogVK2Rg5g==", this);
            configuration_img = Ks3ClientConfiguration.getDefaultConfiguration();
            client_img.setConfiguration(configuration_img);
            client_img.setEndpoint("ks3-cn-beijing.ksyun.com");
            img_objkey =getPackageName() + "/" + System.currentTimeMillis();
    }

    @OnClick({R.id.common_back, R.id.rl1, R.id.rl2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.rl1:
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, CHOOSE_PICTURE);
                break;
            case R.id.rl2:
                goCamera();
                break;
        }
    }

    private void goCamera() {
        if (Build.VERSION.SDK_INT >= 24) {
            File file24 = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file24.getParentFile().exists()) file24.getParentFile().mkdirs();
            tempUri = FileProvider.getUriForFile(this, "com.erze.yqj.fileprovider", file24);//通过FileProvider创建一个content类型的Uri
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);//将拍取的照片保存到指定URI
            startActivityForResult(intent, TAKE_PICTURE);
        } else {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
            // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片保存在本地
                    }
                    break;
            }
        }
    }

    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");
            FileIOUtils.writeFileFromBytesByStream(BG_PATH, ConvertUtils.bitmap2Bytes(photo, Bitmap.CompressFormat.PNG));
            spUtils.put("userbg", BG_PATH);
        }
        loading.show();
        uploadBgToServer();
    }



    private void uploadBgToServer() {
        LogUtils.e("file",BG_PATH);
        LogUtils.e("user_id",spUtils.getString("userid"));
        File file = new File(BG_PATH);
        LogUtils.e("filename",file.getName());
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("user_id", spUtils.getString("userid"));//ParamKey.TOKEN 自定义参数key常量类，即参数名
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
            builder.addFormDataPart("pic",file.getName(),imageBody);//imgfile 后台接收图片流的参数名
        List<MultipartBody.Part> parts = builder.build().parts();
        NetDao.getInstance().getCommonApi().uploadBg(parts).compose(Rxschedulers.<BgBean>io_main())
                .subscribe(new Consumer<BgBean>() {
                    @Override
                    public void accept(@NonNull BgBean bgBean) throws Exception {
                        Toast.makeText(SelectBgPhotoActivity.this,bgBean.getCode(), Toast.LENGTH_SHORT).show();
                        LogUtils.e("bean",bgBean.getResult().toString());
                            if (bgBean.getCode().equals("200")){

                            }else {
                                Toast.makeText(SelectBgPhotoActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                            }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        loading.dismiss();
                        finish();
                    }
                });
    }

    public void startPhotoZoom(Uri uri) {
        if (Build.VERSION.SDK_INT >= 24) {
            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri outputUri = FileProvider.getUriForFile(this, "com.erze.yqj.fileprovider", file);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true); // no face detection
            startActivityForResult(intent, CROP_SMALL_PICTURE);
        } else {
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
}
