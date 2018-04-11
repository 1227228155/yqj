package com.erze.yqj.moudle.release;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.erze.yqj.R;
import com.erze.yqj.api.NetDao;
import com.erze.yqj.entity.KS3Bean;
import com.erze.yqj.entity.UpdateBean;
import com.erze.yqj.moudle.main.MainActivity;
import com.erze.yqj.moudle.release.adapter.ReleaseAdapter;
import com.erze.yqj.moudle.shortvideo.KS3TokenTask;
import com.erze.yqj.moudle.voice.ui.LoadingView;
import com.erze.yqj.rxframe.rx.Rxschedulers;
import com.ksyun.ks3.exception.Ks3Error;
import com.ksyun.ks3.model.acl.CannedAccessControlList;
import com.ksyun.ks3.services.AuthListener;
import com.ksyun.ks3.services.Ks3Client;
import com.ksyun.ks3.services.Ks3ClientConfiguration;
import com.ksyun.ks3.services.handler.PutObjectACLResponseHandler;
import com.ksyun.ks3.services.handler.PutObjectResponseHandler;
import com.ksyun.ks3.services.request.PutObjectACLRequest;
import com.ksyun.ks3.services.request.PutObjectRequest;
import com.ksyun.media.player.misc.KSYProbeMediaInfo;

import org.apache.http.Header;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

public class ReleaseVideoActivity extends AppCompatActivity {
    @BindView(R.id.release_et)
    EditText releaseEt;
    private String path = "http://appresource.ks3-cn-beijing.ksyun.com/";
    private String play_path;
    private Map<String, String> map = new ArrayMap<>();
    private SPUtils spUtils = SPUtils.getInstance("user");
    //Get ks3 Token
    private static final String TYPE = "contType";
    private static final String MD5 = "contMd5";
    private static final String VERB = "httpVerb";
    private static final String RES = "res";
    private static final String HEADERS = "headers";
    private static final String DATE = "date";
    String url;
    Bitmap bitmap8, bitmap6, bitmap7, bitmap5, bitmap4, bitmap3, bitmap2, bitmap1;
    ArrayList<Bitmap> bitmaps = new ArrayList<>();
    float time;
    @BindView(R.id.gridView)
    GridView hlistview;
    @BindView(R.id.release_fm)
    ImageView releaseFm;
    Ks3Client client;
    Ks3ClientConfiguration configuration;
    KS3TokenTask tokenTask;
    Bitmap bitmap;//用户选择的封面图
    final String IMAGE_PATH = "/sdcard/yqj" + "/fm.jpg";
    String objKey;
    String str;
    LoadingView loading;//加载View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        ButterKnife.bind(this);
        tokenTask = new KS3TokenTask(this);
        objKey = getIntent().getStringExtra("key");
        play_path = path + objKey;
        url = getIntent().getAction();
        time = Float.parseFloat(getIntent().getPackage());
        LogUtils.e("url", url);
        LogUtils.e("time", time);
        //获得合成完成后视频的缩略图
        Random random = new Random();
        KSYProbeMediaInfo var5 = new KSYProbeMediaInfo();
        getBitmap(random, var5);
        setOnListener();
      //  initKs3();
    }

    private void setOnListener() {
        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseFm.setImageBitmap(bitmaps.get(position));
                bitmap = bitmaps.get(position);
            }
        });
    }

    private void getBitmap(Random random, KSYProbeMediaInfo var5) {
        loading = new LoadingView(this, R.style.CustomDialog);
        bitmap1 = var5.getVideoThumbnailAtTime(url, random.nextInt((int) time) * 1000, 0, 0);
        bitmap2 = var5.getVideoThumbnailAtTime(url, random.nextInt((int) time) * 1000, 0, 0);
        bitmap3 = var5.getVideoThumbnailAtTime(url, random.nextInt((int) time) * 1000, 0, 0);
        bitmap4 = var5.getVideoThumbnailAtTime(url, random.nextInt((int) time) * 1000, 0, 0);
        bitmap5 = var5.getVideoThumbnailAtTime(url, random.nextInt((int) time) * 1000, 0, 0);
        bitmap6 = var5.getVideoThumbnailAtTime(url, random.nextInt((int) time) * 1000, 0, 0);
        bitmap7 = var5.getVideoThumbnailAtTime(url, random.nextInt((int) time) * 1000, 0, 0);
        bitmap8 = var5.getVideoThumbnailAtTime(url, random.nextInt((int) time) * 1000, 0, 0);
        bitmaps.add(bitmap8);
        bitmaps.add(bitmap7);
        bitmaps.add(bitmap6);
        bitmaps.add(bitmap5);
        bitmaps.add(bitmap4);
        bitmaps.add(bitmap1);
        bitmaps.add(bitmap3);
        bitmaps.add(bitmap2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("listsize", bitmaps.size());
        releaseFm.setImageBitmap(bitmap1);
        ReleaseAdapter adapter = new ReleaseAdapter(this, bitmaps);
        hlistview.setAdapter(adapter);
        int itemWidth = (int) (70 * getApplicationContext().getResources().getDisplayMetrics().density);
        int itemSize = bitmaps.size();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemSize * itemWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        hlistview.setLayoutParams(params);
        hlistview.setNumColumns(itemSize);
    }

    public void releaseVideo() {
        loading.show();
        client = new Ks3Client("AKLTreSNKentSamx5EWX_KdTXA","OADZU1xh7V2qS9h3DFcpjDXZRIFpaiJt35+vXltXGV6bg5Mwa2Ct9o88RogVK2Rg5g==", this);
        configuration = Ks3ClientConfiguration.getDefaultConfiguration();
        client.setConfiguration(configuration);
        client.setEndpoint("ks3-cn-beijing.ksyun.com");
        boolean b = FileIOUtils.writeFileFromBytesByStream(IMAGE_PATH, ConvertUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.JPEG));
        uploadKs3();
    }

    private void initACL() {
        CannedAccessControlList list = CannedAccessControlList.PublicRead;
        client.putObjectACL(new PutObjectACLRequest("appimg", objKey, list), new PutObjectACLResponseHandler() {
                    @Override
                    public void onFailure(int statesCode, Ks3Error error, Header[] responceHeaders, String response, Throwable paramThrowable) {
                        LogUtils.e("acl","设置权限失败");
                    }

                    @Override
                    public void onSuccess(int statesCode, Header[] responceHeaders) {
                        loading.dismiss();
                        Toast.makeText(ReleaseVideoActivity.this, "上传作品成功", Toast.LENGTH_SHORT).show();
                        LogUtils.e("acl","设置权限成功");
                    }
                }
        );
    }

    private void uploadKs3() {
        client.putObject(new PutObjectRequest("appimg", objKey,  new File(IMAGE_PATH)), new PutObjectResponseHandler() {
                    @Override
                    public void onTaskFailure(int statesCode, Ks3Error error, Header[] responceHeaders, String response, Throwable paramThrowable) {
                    }

                    @Override
                    public void onTaskSuccess(int statesCode, Header[] responceHeaders) {

                    }

                    @Override
                    public void onTaskStart() {
                        LogUtils.e("release","开始上传");
                    }

                    @Override
                    public void onTaskFinish() {
                        updateNet();
                        initACL();
                    }

                    @Override
                    public void onTaskCancel() {

                    }

                    @Override
                    public void onTaskProgress(double progress) {

                    }
                }
        );
    }

    private void initKs3() {
        client = new Ks3Client(new AuthListener() {
            @Override
            public String onCalculateAuth(String httpMethod,
                                          String ContentType, String Date, String ContentMD5,
                                          String Resource, String Headers) {
                // 此处应由APP端向业务服务器发送post请求返回Token。
                // 需要注意该回调方法运行在非主线程
                //
                String token = requsetTokenToAppServer("PUT", "image/jpeg", " Fri, 04 Aug 2017 00:53:20 GMT", "", "appimg" + objKey
                        , "");
                return token;
            }
        }, this);
        configuration = Ks3ClientConfiguration.getDefaultConfiguration();
        client.setConfiguration(configuration);

    }

    private String requsetTokenToAppServer(String httpMethod, String contentType, String
            date, String contentMD5, String resource, String headers) {
        Map<String, String> param = new HashMap<>();
        MyAsyncTask asyncTask = new MyAsyncTask();
        param.put(VERB, httpMethod);
        param.put(MD5, contentMD5);
        param.put(TYPE, contentType);
        param.put(HEADERS, headers);
        param.put(DATE, date);
        param.put(RES, resource);
        Response<KS3Bean> ks3 = asyncTask.doInBackground(param);
        LogUtils.e("a", httpMethod);
        LogUtils.e("a1", contentType);
        LogUtils.e("a2", date);
        LogUtils.e("a3", contentMD5);
        LogUtils.e("a4", resource);
        LogUtils.e("a5", headers);
        LogUtils.e("ks3", ks3.body().toString());
        return ks3.body().getAuthorization();
    }

    @OnClick(R.id.release)
    public void onViewClicked() {
        releaseVideo();
    }

    private void updateNet() {
        str = releaseEt.getText().toString().trim();
        map.clear();
        map.put("id", spUtils.getString("userid"));
        map.put("type", "v");
        if (EmptyUtils.isNotEmpty(str)){
            map.put("des", str);
        }else {
            map.put("des", "暂无标题");
        }
        map.put("objKey", objKey);
        map.put("img", objKey);
        NetDao.getInstance().getCommonApi().update(map).compose(Rxschedulers.<UpdateBean>io_main()).subscribe(new Consumer<UpdateBean>() {
            @Override
            public void accept(@NonNull UpdateBean updateBean) throws Exception {
                if (updateBean.getCode().equals("200")) {
                    LogUtils.e("release","上传成功");
                } else {
                    Toast.makeText(ReleaseVideoActivity.this, updateBean.getCode(), Toast.LENGTH_SHORT).show();
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
             ActivityUtils.startActivity(ReleaseVideoActivity.this, MainActivity.class);
                finish();
            }
        });
    }


    public class MyAsyncTask extends AsyncTask<Object, Object, Response<KS3Bean>> {

        @Override
        protected Response<KS3Bean> doInBackground(Object... params) {
            try {
                return NetDao.getInstance().getKSVSApi().getKS3((Map<String, String>) params[0]).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}