package com.erze.yqj.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 1227228155@qq.com on 2017/6/30.
 */

public class KSYSDKBean {

    /**
     * Data : {"Authorization":"AWS4-HMAC-SHA256 Credential=YOURAK/20170414/cn-beijing-6/ksvs/aws4_request, SignedHeaders=host;x-amz-date, Signature=742b927f176d43de5aada2f344d15cecfccacd73284151d6d1d5cfdaf9ac583f","RetCode":0,"RetMsg":"success","x-amz-date":"20170414T091747Z"}
     */

    private DataBean Data;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * Authorization : AWS4-HMAC-SHA256 Credential=YOURAK/20170414/cn-beijing-6/ksvs/aws4_request, SignedHeaders=host;x-amz-date, Signature=742b927f176d43de5aada2f344d15cecfccacd73284151d6d1d5cfdaf9ac583f
         * RetCode : 0
         * RetMsg : success
         * x-amz-date : 20170414T091747Z
         */

        private String Authorization;
        private int RetCode;
        private String RetMsg;
        @SerializedName("x-amz-date")
        private String xamzdate;

        public String getAuthorization() {
            return Authorization;
        }

        public void setAuthorization(String Authorization) {
            this.Authorization = Authorization;
        }

        public int getRetCode() {
            return RetCode;
        }

        public void setRetCode(int RetCode) {
            this.RetCode = RetCode;
        }

        public String getRetMsg() {
            return RetMsg;
        }

        public void setRetMsg(String RetMsg) {
            this.RetMsg = RetMsg;
        }

        public String getXamzdate() {
            return xamzdate;
        }

        public void setXamzdate(String xamzdate) {
            this.xamzdate = xamzdate;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "Authorization='" + Authorization + '\'' +
                    ", RetCode=" + RetCode +
                    ", RetMsg='" + RetMsg + '\'' +
                    ", xamzdate='" + xamzdate + '\'' +
                    '}';
        }
    }
}
