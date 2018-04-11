package com.erze.yqj.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */

public class Music implements Serializable {

    /**
     * code : 200
     * result : {"list":[{"mp3_name":"7JZ,满舒克,Xilly - DAY LOVE NIGHT.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7JZ%2C%E6%BB%A1%E8%88%92%E5%85%8B%2CXilly+-+DAY+LOVE+NIGHT.m4a"},{"mp3_name":"7妹 - 全民女神 (Remix).m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7%E5%A6%B9+-+%E5%85%A8%E6%B0%91%E5%A5%B3%E7%A5%9E+%28Remix%29.m4a"},{"mp3_name":"7妹 - 她说她没喝醉.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7%E5%A6%B9+-+%E5%A5%B9%E8%AF%B4%E5%A5%B9%E6%B2%A1%E5%96%9D%E9%86%89.m4a"},{"mp3_name":"7妹 - 我最摇摆 (Remix).m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7%E5%A6%B9+-+%E6%88%91%E6%9C%80%E6%91%87%E6%91%86+%28Remix%29.m4a"},{"mp3_name":"7妹 - 灵魂火花.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7%E5%A6%B9+-+%E7%81%B5%E9%AD%82%E7%81%AB%E8%8A%B1.m4a"},{"mp3_name":"7妹 - 生猛轰炸 (Remix).m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7%E5%A6%B9+-+%E7%94%9F%E7%8C%9B%E8%BD%B0%E7%82%B8+%28Remix%29.m4a"},{"mp3_name":"Alan Walker - Alone.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/Alan+Walker+-+Alone.m4a"},{"mp3_name":"Alan Walker,Alex Skrindo - Sky.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/Alan+Walker%2CAlex+Skrindo+-+Sky.m4a"},{"mp3_name":"Allexinno,Starchild - Joanna (Radio Edit).m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/Allexinno%2CStarchild+-+Joanna+%28Radio+Edit%29.m4a"},{"mp3_name":"Bridge - 老大.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/Bridge+-+%E8%80%81%E5%A4%A7.m4a"}],"count":"54"}
     * msg : 成功
     */

    private String code;
    private ResultBean result;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResultBean implements Serializable {
        /**
         * list : [{"mp3_name":"7JZ,满舒克,Xilly - DAY LOVE NIGHT.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7JZ%2C%E6%BB%A1%E8%88%92%E5%85%8B%2CXilly+-+DAY+LOVE+NIGHT.m4a"},{"mp3_name":"7妹 - 全民女神 (Remix).m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7%E5%A6%B9+-+%E5%85%A8%E6%B0%91%E5%A5%B3%E7%A5%9E+%28Remix%29.m4a"},{"mp3_name":"7妹 - 她说她没喝醉.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7%E5%A6%B9+-+%E5%A5%B9%E8%AF%B4%E5%A5%B9%E6%B2%A1%E5%96%9D%E9%86%89.m4a"},{"mp3_name":"7妹 - 我最摇摆 (Remix).m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7%E5%A6%B9+-+%E6%88%91%E6%9C%80%E6%91%87%E6%91%86+%28Remix%29.m4a"},{"mp3_name":"7妹 - 灵魂火花.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7%E5%A6%B9+-+%E7%81%B5%E9%AD%82%E7%81%AB%E8%8A%B1.m4a"},{"mp3_name":"7妹 - 生猛轰炸 (Remix).m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7%E5%A6%B9+-+%E7%94%9F%E7%8C%9B%E8%BD%B0%E7%82%B8+%28Remix%29.m4a"},{"mp3_name":"Alan Walker - Alone.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/Alan+Walker+-+Alone.m4a"},{"mp3_name":"Alan Walker,Alex Skrindo - Sky.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/Alan+Walker%2CAlex+Skrindo+-+Sky.m4a"},{"mp3_name":"Allexinno,Starchild - Joanna (Radio Edit).m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/Allexinno%2CStarchild+-+Joanna+%28Radio+Edit%29.m4a"},{"mp3_name":"Bridge - 老大.m4a","mp3_url":"https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/Bridge+-+%E8%80%81%E5%A4%A7.m4a"}]
         * count : 54
         */

        private String count;
        private List<ListBean> list;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * mp3_name : 7JZ,满舒克,Xilly - DAY LOVE NIGHT.m4a
             * mp3_url : https://ks3-cn-beijing.ksyun.com/mp3/erze.dj.com/7JZ%2C%E6%BB%A1%E8%88%92%E5%85%8B%2CXilly+-+DAY+LOVE+NIGHT.m4a
             */

            private String mp3_name;
            private String mp3_url;

            public String getMp3_name() {
                return mp3_name;
            }

            public void setMp3_name(String mp3_name) {
                this.mp3_name = mp3_name;
            }

            public String getMp3_url() {
                return mp3_url;
            }

            public void setMp3_url(String mp3_url) {
                this.mp3_url = mp3_url;
            }
        }
    }
}
