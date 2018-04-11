package com.erze.yqj.moudle.user;

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
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.erze.yqj.R;
import com.erze.yqj.moudle.voice.ui.LoadingView;
import com.erze.yqj.utils.AutoUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateUserDataActivity extends AppCompatActivity {
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.update_avatar)
    CircleImageView updateAvatar;
    @BindView(R.id.update_birthday)
    TextView updateBirthday;
    @BindView(R.id.update_sex)
    TextView updateSex;
    @BindView(R.id.update_school)
    TextView updateSchool;
    @BindView(R.id.update_xl)
    TextView updateXl;
    @BindView(R.id.update_name)
    TextView updateName;
    @BindView(R.id.sex)
    ImageView sex;
    @BindView(R.id.flowlayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.qm)
    TextView qm;
    private Uri tempUri;
    @BindView(R.id.common_title)
    TextView commonTitle;
    private PopupWindow popupWindow;
    private View popuwindowView;
    private Button pp_btn1, pp_btn2, pp_btn3;
    private Window window;
    private String AVATAR_PATH = "/sdcard/yqj/";
    private LoadingView loading;//加载View
    private Bitmap photo;//用户选择的头像
    private SPUtils spUtils = SPUtils.getInstance("user");
    private List<String> labelList = new ArrayList<>();
    private List<String> labelList1 = new ArrayList<>();
    Calendar startDate;
    Calendar endDate;
    int yourChoice;
    TagFlowLayout mFlowLayout1;
     LinearLayout loginDialog;
    TextView btn_sure ;
    AlertDialog dialog;
    ArrayList list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_data);
        AutoUtils.auto(this);
        ButterKnife.bind(this);
        commonTitle.setText("个人资料");
        //初始化PoPupWindow
        initPopupWindow();
        //初始化标签
        initLabelView();
        //初始化所有的标签，让用户选择
        initLabelView1();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initAvatarView();
    }

    private void initLabelView() {
        String userlabel = spUtils.getString("userlabel");
        String[] split = userlabel.split(",");
        labelList = com.erze.yqj.utils.ConvertUtils.array2List(split);
        mFlowLayout.setAdapter(new TagAdapter<String>(labelList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(UpdateUserDataActivity.this).inflate(R.layout.item_label_no_selector, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        mFlowLayout.setSelected(false);

    }

    private void initAvatarView() {
        updateName.setText(spUtils.getString("username"));
        String sex1 = spUtils.getString("usersex");
        updateSex.setText(sex1);
        if (updateSex.getText().equals("男")) {
            sex.setImageResource(R.drawable.sex_nan);
        } else {
            sex.setImageResource(R.drawable.sex_nv);
        }
        updateBirthday.setText(spUtils.getString("userbirth"));
        if (spUtils.getString("useravatar") != null) {
            Glide.with(this).load(spUtils.getString("useravatar")).into(updateAvatar);
        } else {
            updateAvatar.setImageResource(R.drawable.null_avatar);
        }
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

    @OnClick({R.id.rl1, R.id.rl2, R.id.common_back, R.id.rl_birthday, R.id.rl_sex, R.id.rl_school, R.id.rl_xl, R.id.qm_rl,R.id.rl_label})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.rl1:
                showCustomizeDialog2();//修改昵称
                break;
            case R.id.rl2://修改头像
                showPopupWindow();
                break;
            case R.id.rl_birthday://修改生日
                initDate();
                break;
            case R.id.rl_sex:
                showSingleChoiceDialog();//修改性别
                break;
            case R.id.rl_xl:
                showSingleChoiceDialog2();//修改学历
                break;
            case R.id.rl_school://添加学校名称
                showCustomizeDialog();
                break;
            case R.id.qm_rl://修改签名
                showCustomizeDialog3();
                break;
            case R.id.rl_label://修改标签
                showCustomViewDialog();
                break;

        }
    }
    private void initLabelView1() {
        loginDialog= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_view,null);
        btn_sure = (TextView) loginDialog.findViewById(R.id.sure);
        mFlowLayout1 = (TagFlowLayout) loginDialog.findViewById(R.id.dilog_flowlayout);
        labelList1.add("文艺");
        labelList1.add("活跃");
        labelList1.add("逗比");
        labelList1.add("完美主义");
        labelList1.add("二次元");
        labelList1.add("自信");
        labelList1.add("萌");
        mFlowLayout1.setAdapter(new TagAdapter<String>(labelList1) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(UpdateUserDataActivity.this).inflate(R.layout.item_label_tv, mFlowLayout1, false);
                tv.setText(s);
                return tv;
            }
        });
        mFlowLayout1.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (mFlowLayout1.getSelectedList().size()==4){
                    Toast.makeText(UpdateUserDataActivity.this, "最多选择三个标签", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showCustomViewDialog(){
        if (dialog==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            /**
             * 设置内容区域为自定义View
             */
            builder.setView(loginDialog);
            builder.setCancelable(true);
            dialog=builder.create();

            btn_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFlowLayout1.getSelectedList().size()<4){
                        dialog.dismiss();
                        if (list==null){
                            list = new ArrayList();
                        }
                        list.clear();
                        Set<Integer> selectedList = mFlowLayout1.getSelectedList();
                        for (int a : selectedList) {
                            list.add(labelList1.get(a));
                        }
                        mFlowLayout.setAdapter(new TagAdapter<String>(list) {
                            @Override
                            public View getView(FlowLayout parent, int position, String s) {
                                TextView tv = (TextView) LayoutInflater.from(UpdateUserDataActivity.this).inflate(R.layout.item_label_no_selector, mFlowLayout1, false);
                                tv.setText(s);
                                return tv;
                            }
                        });
                    }else {
                        Toast.makeText(UpdateUserDataActivity.this, "标签选择数量过多，请重新选择", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            dialog.show();
        }else {
            dialog.show();
        }

    }
    private void initDate() {
        startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);
        //正确设置方式 原因：注意事项有说明
        startDate.set(1980, 0, 1);
        endDate.set(2017, 11, 31);
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                updateBirthday.setText(getTime(date));
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
    }

    //转换时间格式
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void showPopupWindow() {

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
            updateAvatar.setImageBitmap(photo);
            String path = AVATAR_PATH + System.currentTimeMillis() + "avatar.png";
            FileIOUtils.writeFileFromBytesByStream(path, ConvertUtils.bitmap2Bytes(photo, Bitmap.CompressFormat.PNG));
            spUtils.put("useravatar", path);
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
                        updateSex.setText(items[yourChoice]);
                        if (updateSex.getText().equals("男")) {
                            sex.setImageResource(R.drawable.sex_nan);
                        } else {
                            sex.setImageResource(R.drawable.sex_nv);
                        }
                    }
                });
        AlertDialog alertDialog = singleChoiceDialog.create();
        alertDialog.show();
        updateDialog(alertDialog);
    }

    private void showSingleChoiceDialog2() {
        final String[] items = {"博士", "硕士", "本科", "专科", "其他"};
        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(this);
        singleChoiceDialog.setTitle("选择学历");
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
                        updateXl.setText(items[yourChoice]);
                    }
                });
        AlertDialog alertDialog = singleChoiceDialog.create();
        alertDialog.show();
        updateDialog(alertDialog);
    }

    private void showCustomizeDialog2() {
    /* @setView 装入自定义View ==> R.layout.dialog_customize
     * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
     * dialog_customize.xml可自定义更复杂的View
     */
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_view, null);
        customizeDialog.setTitle("输入昵称");
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        EditText edit_text =
                                (EditText) dialogView.findViewById(R.id.edit_text);
                        edit_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                        String trim = edit_text.getText().toString().trim();
                        if (EmptyUtils.isNotEmpty(trim)) {
                            updateName.setText(trim);
                        }
                    }
                });
        AlertDialog alertDialog = customizeDialog.create();
        alertDialog.show();
        updateDialog(alertDialog);
    }

    private void showCustomizeDialog3() {
    /* @setView 装入自定义View ==> R.layout.dialog_customize
     * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
     * dialog_customize.xml可自定义更复杂的View
     */
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_view, null);
        customizeDialog.setTitle("输入签名");
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        EditText edit_text =
                                (EditText) dialogView.findViewById(R.id.edit_text);
                        edit_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
                        String trim = edit_text.getText().toString().trim();
                        if (EmptyUtils.isNotEmpty(trim)) {
                            qm.setText(trim);
                        }
                    }
                });
        AlertDialog alertDialog = customizeDialog.create();
        alertDialog.show();
        updateDialog(alertDialog);
    }

    private void showCustomizeDialog() {
    /* @setView 装入自定义View ==> R.layout.dialog_customize
     * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
     * dialog_customize.xml可自定义更复杂的View
     */
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_view, null);
        customizeDialog.setTitle("输入学校名称");
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        EditText edit_text =
                                (EditText) dialogView.findViewById(R.id.edit_text);
                        edit_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                        String trim = edit_text.getText().toString().trim();
                        if (EmptyUtils.isNotEmpty(trim)) {
                            updateSchool.setText(trim);
                        }
                    }
                });
        AlertDialog alertDialog = customizeDialog.create();
        alertDialog.show();
        updateDialog(alertDialog);

    }

    /**
     * 修改系统Dialog字体颜色
     * @param alertDialog
     */
    private void updateDialog(AlertDialog alertDialog){
        alertDialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }
}
