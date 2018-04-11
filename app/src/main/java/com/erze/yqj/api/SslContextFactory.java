package com.erze.yqj.api;

import android.util.Log;

import com.erze.yqj.MyApplication;
import com.erze.yqj.R;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by 1227228155@qq.com on 2017/6/23.
 */

public class SslContextFactory {
   // private static final String CLIENT_TRUST_PASSWORD = "changeit";//信任证书密码，该证书默认密码是changeit
    private static final String CLIENT_TRUST_PASSWORD = "654321";//信任证书密码，该证书默认密码是654321
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_TRUST_MANAGER = "X509";
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";
    SSLContext sslContext = null;
    public SSLContext getSslSocket() {
        try {
//取得SSL的SSLContext实例
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
//取得TrustManagerFactory的X509密钥管理器实例
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);
//取得BKS密库实例
            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            InputStream is = MyApplication.getInstance().getResources().openRawResource(R.raw.key);
            try {
                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
            } finally {
                is.close();
            }
//初始化密钥管理器
            trustManager.init(tks);
//初始化SSLContext
            sslContext.init(null, trustManager.getTrustManagers(), null);
        } catch (Exception e) {
            Log.e("SslContextFactory", e.getMessage());
        }
        return sslContext;    }

}
