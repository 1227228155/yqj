package com.erze.yqj.entity;

/**
 * Created by 1227228155@qq.com on 2017/7/4.
 */

public class KS3Bean {


    /**
     * Date : Tue, 04 Jul 2017 05:18:52 GMT
     * Authorization : KSS AKLTreSNKentSamx5EWX_KdTXA:pO7f1VOddW9j7JD9Pl7uKgf+oiI=
     */

    private String Date;
    private String Authorization;

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String Authorization) {
        this.Authorization = Authorization;
    }

    @Override
    public String toString() {
        return "KS3Bean{" +
                "Date='" + Date + '\'' +
                ", Authorization='" + Authorization + '\'' +
                '}';
    }
}
