package com.erze.yqj.moudle.Login.personal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.entity.RegisterPersonalDataBean;
import com.erze.yqj.moudle.main.MainActivity;
import com.erze.yqj.moudle.voice.ui.LoadingView;
import com.erze.yqj.rxframe.base.BaseFrameActivity;
import com.erze.yqj.utils.AutoUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterPersonalDataActivity extends BaseFrameActivity<RegisterPersonalDataPresenter, RegisterPersonalDataModel> implements RegisterPersonalDataContract.View {
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    @BindView(R.id.register_data_sex)
    TextView registerDataSex;
    @BindView(R.id.sex_iv)
    ImageView sexIv;
    private Uri tempUri;
    @BindView(R.id.register_data_avatar)
    CircleImageView registerDataAvatar;
    List<String> labelList = new ArrayList<>();
    Set<Integer> selectedList;
    StringBuilder labelStrings = new StringBuilder();
    StringBuilder birthdayStrings = new StringBuilder();
    Map<String, String> map = new HashMap<>();
    TagFlowLayout mFlowLayout;
    String mNickName, mBirthday;
    String usernamePhone;
    String code;
    String sexString;
    @BindView(R.id.register_data_nickname)
    EditText registerDataNickname;
    @BindView(R.id.register_data_birthday)
    TextView registerDataBirthday;
    @BindView(R.id.rl)
    RelativeLayout rl;
    private SPUtils spUtils = SPUtils.getInstance("user");
    Calendar startDate;
    Calendar endDate;
    private PopupWindow popupWindow;
    private View popuwindowView;
    private Button pp_btn1, pp_btn2, pp_btn3;
    private Window window;
    private Bitmap photo;//用户选择的头像
    private String base64Encode2String;//Base64编码之后的头像
    private String AVATAR_PATH = "/sdcard/yqj" + "/avatar.png";
    private LoadingView loading;//加载View
    private int yourChoice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_personal_data);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        //初始化标签选项
        initLabelView();
        //初始化日期选择器的内容，起始终止年月日设定
        initDate();
        //初始化PoPupWindow
        initPopupWindow();
    }

    private void initPopupWindow() {
        window = getWindow();
        loading = new LoadingView(this, R.style.CustomDialog);
        popuwindowView = LayoutInflater.from(this).inflate(R.layout.popuwindow, null);
        pp_btn1 = (Button) popuwindowView.findViewById(R.id.pp_btn1);
        pp_btn2 = (Button) popuwindowView.findViewById(R.id.pp_btn2);
        pp_btn3 = (Button) popuwindowView.findViewById(R.id.pp_btn3);
        pp_btn1.setText("拍照");
        pp_btn2.setText("从相册获取");
    }

    private void initDate() {
        startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);
        //正确设置方式 原因：注意事项有说明
        startDate.set(1980, 0, 1);
        endDate.set(2017, 11, 31);
    }


    private void initLabelView() {
        labelList.add("文艺");
        labelList.add("活跃");
        labelList.add("逗比");
        labelList.add("完美主义");
        labelList.add("二次元");
        labelList.add("自信");
        labelList.add("萌");
        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
        mFlowLayout.setAdapter(new TagAdapter<String>(labelList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(RegisterPersonalDataActivity.this).inflate(R.layout.item_label_tv, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (mFlowLayout.getSelectedList().size()==4){
                    Toast.makeText(RegisterPersonalDataActivity.this, "最多选择三个标签", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @OnClick({R.id.register_data_go, R.id.register_data_birthday, R.id.register_data_avatar,R.id.register_data_sex_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_data_go:
                birthdayStrings.delete(0, birthdayStrings.length());
                labelStrings.delete(0, labelStrings.length());
                mNickName = registerDataNickname.getText().toString().trim();
                mBirthday = registerDataBirthday.getText().toString().trim();
                //取得用户所选的标签集合position，转换拼接为字符串
                selectedList = mFlowLayout.getSelectedList();
                //---------------------------------------------
                if (EmptyUtils.isEmpty(mNickName)) {
                    Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                } else if (EmptyUtils.isEmpty(mBirthday)) {
                    Toast.makeText(this, "生日不能为空", Toast.LENGTH_SHORT).show();
                } else if (EmptyUtils.isEmpty(selectedList)) {
                    Toast.makeText(this, "请选择标签", Toast.LENGTH_SHORT).show();
                }else if (mFlowLayout.getSelectedList().size()>3) {
                    Toast.makeText(this, "标签不能超多三个，请检查标签数量", Toast.LENGTH_SHORT).show();
                } else {
                    for (int a : selectedList) {
                        labelStrings.append(labelList.get(a)).append(",");
                    }
                    LogUtils.e("birthdayStrings", birthdayStrings.toString());
                    LogUtils.e("labelStrings", labelStrings.toString());
                    usernamePhone = getIntent().getAction();
                    upLoad();
                }
                break;
            case R.id.register_data_birthday:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        registerDataBirthday.setText(getTime(date));
                    }
                }).setRangDate(startDate, endDate)//起始终止年月日设定
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                        .setCancelColor(Color.BLACK)//取消按钮文字颜色
                        .setTitleColor(Color.WHITE)
                        .build();
                pvTime.show();
                break;
            case R.id.register_data_avatar:
                showPopupWindow();
                break;
            case R.id.register_data_sex_rl:
                showSingleChoiceDialog();
                break;
        }

    }
    private void showSingleChoiceDialog() {
        final String[] items = {"男", "女"};
        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(this);
        singleChoiceDialog.setTitle("选择性别");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        registerDataSex.setText(items[yourChoice]);
                        if (yourChoice==0){
                            sexIv.setImageResource(R.drawable.sex_nan);
                        }else {
                            sexIv.setImageResource(R.drawable.sex_nv);
                        }
                    }
                });
        singleChoiceDialog.show();
    }
    private void showPopupWindow() {

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
        popupWindow = new PopupWindow(popuwindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(rl, Gravity.BOTTOM, 0, 0);
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

    //转换时间格式
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void getPersonalData(RegisterPersonalDataBean bean) {
        code = bean.getCode();
        LogUtils.e("getPersonalData", code);
        spUtils.put("isLogin", "登录状态");
        spUtils.put("userid", bean.getResult().getUser_id());
        spUtils.put("userhxid", bean.getResult().getUser_id_number());
        spUtils.put("username", bean.getResult().getUser_nickname());
        spUtils.put("usersex", bean.getResult().getUser_sex());
        spUtils.put("useravatar", bean.getResult().getUser_pic());
        spUtils.put("uservip", bean.getResult().getUser_vip_status());
        spUtils.put("userphone", bean.getResult().getUser_mobile());
        spUtils.put("userbirth", bean.getResult().getUser_birth());
        spUtils.put("userlabel", bean.getResult().getUser_label());
    }

    @Override
    public void gotoMain() {
        LogUtils.e("gotoMain", "gotoMain");
        if (code.equals("200")) {
            loading.dismiss();
            ActivityUtils.startActivity(RegisterPersonalDataActivity.this, MainActivity.class);
            finish();
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
            registerDataAvatar.setImageBitmap(photo);
            FileIOUtils.writeFileFromBytesByStream(AVATAR_PATH, ConvertUtils.bitmap2Bytes(photo, Bitmap.CompressFormat.PNG));
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

    private void upLoad() {
        loading.show();
        File file = new File(AVATAR_PATH);//filePath 图片地址
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("mobile", usernamePhone);//ParamKey.TOKEN 自定义参数key常量类，即参数名
        builder.addFormDataPart("nickname", mNickName);
        builder.addFormDataPart("user_sex", registerDataSex.getText().toString());
        builder.addFormDataPart("birthday", mBirthday);
        builder.addFormDataPart("nickname", mNickName);
        builder.addFormDataPart("label", labelStrings.toString());
        if (photo != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("pic", file.getName(), imageBody);//imgfile 后台接收图片流的参数名
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        mPresenter.getRegisterPersonalData(parts);
    }

}
