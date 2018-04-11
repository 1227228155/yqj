package com.erze.yqj.moudle.release;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.UpdateBean;
import com.erze.yqj.moudle.main.MainActivity;
import com.erze.yqj.moudle.shortvideo.KS3TokenTask;
import com.erze.yqj.moudle.voice.ui.LoadingView;
import com.erze.yqj.moudle.voice.utils.Variable;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.ksyun.ks3.exception.Ks3Error;
import com.ksyun.ks3.model.acl.CannedAccessControlList;
import com.ksyun.ks3.services.Ks3Client;
import com.ksyun.ks3.services.Ks3ClientConfiguration;
import com.ksyun.ks3.services.handler.PutObjectACLResponseHandler;
import com.ksyun.ks3.services.handler.PutObjectResponseHandler;
import com.ksyun.ks3.services.request.PutObjectACLRequest;
import com.ksyun.ks3.services.request.PutObjectRequest;

import org.apache.http.Header;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ReleaseVoiceActivity extends AppCompatActivity {
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.pic_iv)
    ImageView picIv;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.ll)
    LinearLayout ll;
    private SPUtils spUtils = SPUtils.getInstance("user");
    private String path;
    private Map<String, String> map = new ArrayMap<>();
    LoadingView loading;//加载View
    Ks3Client client;
    Ks3Client client_img;
    Ks3ClientConfiguration configuration,configuration_img;
    KS3TokenTask tokenTask;
    String objKey,img_objkey;
    String str;
    private PopupWindow popupWindow;
    private View popuwindowView;
    private Button pp_btn1, pp_btn2, pp_btn3;
    private Window window;
    private Uri tempUri;
    private Bitmap photo;
   private String img_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_voice);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        path = getIntent().getAction();
        loading = new LoadingView(this, R.style.CustomDialog);
        tokenTask = new KS3TokenTask(this);
        objKey = getPackageName() + "/" + System.currentTimeMillis();
        popuwindowView = LayoutInflater.from(this).inflate(R.layout.popuwindow, null);
        pp_btn1 = (Button) popuwindowView.findViewById(R.id.pp_btn1);
        pp_btn2 = (Button) popuwindowView.findViewById(R.id.pp_btn2);
        pp_btn3 = (Button) popuwindowView.findViewById(R.id.pp_btn3);
        pp_btn1.setText("拍照");
        pp_btn2.setText("从相册获取");
    }


    @OnClick({R.id.voice_back_iv, R.id.pic_btn, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.voice_back_iv:
                finish();
                break;
            case R.id.pic_btn:
                showPopupWindow();
                break;
            case R.id.send:
                loading.show();
                if (EmptyUtils.isNotEmpty(img_path)){
                    releaseImg();
                    LogUtils.e("开始上传图片"," releaseImg();");
                }else {
                    releaseVoice();
                }
                break;
        }
    }


    private void updateVoice() {
        str = et.getText().toString().trim();
        map.clear();
        map.put("id", spUtils.getString("userid"));
        map.put("type", "a");
        if (EmptyUtils.isNotEmpty(str)) {
            map.put("des",str);
        } else {
            map.put("des", "暂无标题");
        }
        map.put("objKey",objKey);
        map.put("img",img_objkey);
        NetDao.getInstance().getCommonApi().update(map).compose(Rxschedulers.<UpdateBean>io_main()).subscribe(new Consumer<UpdateBean>() {
            @Override
            public void accept(@NonNull UpdateBean updateBean) throws Exception {
                if (updateBean.getCode().equals("200")) {
                    LogUtils.e("release", "上传成功");
                } else {
                    Toast.makeText(ReleaseVoiceActivity.this, updateBean.getCode(), Toast.LENGTH_SHORT).show();
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
                    ActivityUtils.startActivity(ReleaseVoiceActivity.this, MainActivity.class);
                   Toast.makeText(ReleaseVoiceActivity.this, "上传作品成功", Toast.LENGTH_SHORT).show();
                    finish();
            }
        });
    }


    public void releaseVoice() {
        client = new Ks3Client("AKLTreSNKentSamx5EWX_KdTXA", "OADZU1xh7V2qS9h3DFcpjDXZRIFpaiJt35+vXltXGV6bg5Mwa2Ct9o88RogVK2Rg5g==", this);
        configuration = Ks3ClientConfiguration.getDefaultConfiguration();
        client.setConfiguration(configuration);
        client.setEndpoint("ks3-cn-beijing.ksyun.com");
        uploadKs3("appaudio",objKey,path);
    }
    public void releaseImg() {
        client_img = new Ks3Client("AKLTreSNKentSamx5EWX_KdTXA", "OADZU1xh7V2qS9h3DFcpjDXZRIFpaiJt35+vXltXGV6bg5Mwa2Ct9o88RogVK2Rg5g==", this);
        configuration_img = Ks3ClientConfiguration.getDefaultConfiguration();
        client_img.setConfiguration(configuration_img);
        client_img.setEndpoint("ks3-cn-beijing.ksyun.com");
        img_objkey =getPackageName() + "/" + System.currentTimeMillis();
        uploadKs32("appimg",img_objkey,img_path);
    }

    private void uploadKs32(final String appimg, final String img_objkey, String img_path) {
        LogUtils.e("appimg",appimg);
        LogUtils.e("img_objkey",img_objkey);
        LogUtils.e("img_path",img_path);
        client_img.putObject(new PutObjectRequest(appimg, img_objkey, new File(img_path)), new PutObjectResponseHandler() {
                    @Override
                    public void onTaskFailure(int statesCode, Ks3Error error, Header[] responceHeaders, String response, Throwable paramThrowable) {
                        LogUtils.e("上传图片失败"," onTaskFailure();");
                    }

                    @Override
                    public void onTaskSuccess(int statesCode, Header[] responceHeaders) {
                        LogUtils.e("上传图片成功"," onTaskSuccess();");
                        initACL(appimg,img_objkey);
                    }

                    @Override
                    public void onTaskStart() {
                        LogUtils.e("release", "开始上传");
                    }

                    @Override
                    public void onTaskFinish() {
                        LogUtils.e("onTaskFinish",appimg+"上传完毕");
                        releaseVoice();
                    }

                    @Override
                    public void onTaskCancel() {
                        LogUtils.e("onTaskCancel",appimg+"上传失败");
                    }

                    @Override
                    public void onTaskProgress(double progress) {

                    }
                }
        );
    }

    private void initACL(String hous2e, final String objK2ey1) {
        CannedAccessControlList list = CannedAccessControlList.PublicRead;
        if (hous2e.equals("appaudio")){
            client.putObjectACL(new PutObjectACLRequest(hous2e, objK2ey1, list), new PutObjectACLResponseHandler() {
                        @Override
                        public void onFailure(int statesCode, Ks3Error error, Header[] responceHeaders, String response, Throwable paramThrowable) {
                            LogUtils.e("acl", "设置权限失败");
                        }

                        @Override
                        public void onSuccess(int statesCode, Header[] responceHeaders) {
                            LogUtils.e("acl", "设置权限成功");
                        }
                    }
            );
        }else {
            client_img.putObjectACL(new PutObjectACLRequest(hous2e, objK2ey1, list), new PutObjectACLResponseHandler() {
                        @Override
                        public void onFailure(int statesCode, Ks3Error error, Header[] responceHeaders, String response, Throwable paramThrowable) {
                            LogUtils.e("ac2", "设置权限失败");
                        }

                        @Override
                        public void onSuccess(int statesCode, Header[] responceHeaders) {
                            LogUtils.e("ac2", "设置权限成功");
                        }
                    }
            );
        }

    }

    private void uploadKs3(final String hous1e1, final String objKe1y1, String p1ath1) {
            client.putObject(new PutObjectRequest(hous1e1, objKe1y1, new File(p1ath1)), new PutObjectResponseHandler() {
                        @Override
                        public void onTaskFailure(int statesCode, Ks3Error error, Header[] responceHeaders, String response, Throwable paramThrowable) {
                            LogUtils.e("音频上传失败", "onTaskFailure");
                        }

                        @Override
                        public void onTaskSuccess(int statesCode, Header[] responceHeaders) {
                            LogUtils.e("音频上传成功", "onTaskSuccess");
                            initACL(hous1e1,objKe1y1);
                        }

                        @Override
                        public void onTaskStart() {
                            LogUtils.e("release", "开始上传");
                        }

                        @Override
                        public void onTaskFinish() {
                            updateVoice();
                        }

                        @Override
                        public void onTaskCancel() {
                            LogUtils.e("音频上传失败1", "onTaskSuccess");
                        }

                        @Override
                        public void onTaskProgress(double progress) {

                        }
                    }
            );

    }

    private void showPopupWindow() {
        window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
        popupWindow = new PopupWindow(popuwindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(ll, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失的时候恢复成原来的透明度
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
        pp_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                //popupwindow消失的时候恢复成原来的透明度
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
        pp_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                goCamera();//跳转到拍照
            }
        });
        pp_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, CHOOSE_PICTURE);
            }
        });
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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
            picIv.setVisibility(View.GONE);
            pic.setVisibility(View.VISIBLE);
            pic.setImageBitmap(photo);
            img_path = Variable.StorageImagePath+ System.currentTimeMillis() + "fm.png";
            FileIOUtils.writeFileFromBytesByStream(img_path, ConvertUtils.bitmap2Bytes(photo, Bitmap.CompressFormat.PNG));
        }
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
