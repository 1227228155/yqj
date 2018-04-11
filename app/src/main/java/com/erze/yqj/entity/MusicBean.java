package com.erze.yqj.entity;

import java.util.List;

/**
 * Created by 1227228155@qq.com on 2017/7/29.
 */

public class MusicBean {

    /**
     * code : 0
     * data : {"keyword":"","priority":0,"qc":[],"semantic":{"curnum":0,"curpage":1,"list":[],"totalnum":0},"song":{"curnum":3,"curpage":1,"list":[{"albumName_hilight":"你是我的骄傲演唱会","chinesesinger":0,"docid":"1686449820018762247","f":"7060362|练习|163|刘德华|643907|你是我的骄傲演唱会|2570188|298|7|1|0|11926677|4770798|320000|0|30986310|32443483|6472225|7220205|0|002Dujsj0nteKX|003aQYLo2x8izP|0034HL4j4YEPGg|0|8006","fiurl":"","fnote":1006,"fsinger":"刘德华","fsinger2":"","fsong":"练习 (Live)","grp":[{"albumName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>中国巡回演唱会·上海","chinesesinger":0,"docid":"7124875280666137494","f":"444527|练习|163|刘德华|36187|刘德华中国巡回演唱会·上海|2762647|340|7|1|0|0|5451067|0|0|0|0|0|0|0|0012JcDu1aq6j1|003aQYLo2x8izP|001jDc1M0WAthx|0|4013","fiurl":"","fnote":2013,"fsinger":"刘德华","fsinger2":"","fsong":"练习 (Live)","isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"d0012z3t8bv","nt":10000,"only":0,"pubTime":1222272000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习 (Live)","t":0,"tag":0,"ver":0},{"albumName_hilight":"我们的<span class=\"c_tx_highlight\">刘德华<\/span> (国语版)","chinesesinger":0,"docid":"8859806344503066586","f":"1811482|练习|163|刘德华|142221|我们的刘德华 (国语版)|2104771|259|1|1|0|10416797|4154829|320000|0|29423778|29583988|6284993|6283146|0|0026hVVe1wwQRY|003aQYLo2x8izP|000c84SZ2tBpDz|0|4013","fiurl":"","fnote":2013,"fsinger":"刘德华","fsinger2":"","fsong":"练习","isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"d0011dvx6mp","nt":10000,"only":0,"pubTime":1344528000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习","t":0,"tag":0,"ver":0}],"isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"i0022763h8t","nt":10000,"only":0,"pubTime":1038672000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习 (Live)","t":0,"tag":10,"ver":0}],"totalnum":395},"totaltime":8.7E-5,"zhida":{"chinesesinger":0,"type":0}}
     * message :
     * notice :
     * subcode : 0
     * time : 1501295327
     * tips :
     */

    private int code;
    private DataBean data;
    private String message;
    private String notice;
    private int subcode;
    private int time;
    private String tips;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getSubcode() {
        return subcode;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public static class DataBean {
        /**
         * keyword :
         * priority : 0
         * qc : []
         * semantic : {"curnum":0,"curpage":1,"list":[],"totalnum":0}
         * song : {"curnum":3,"curpage":1,"list":[{"albumName_hilight":"你是我的骄傲演唱会","chinesesinger":0,"docid":"1686449820018762247","f":"7060362|练习|163|刘德华|643907|你是我的骄傲演唱会|2570188|298|7|1|0|11926677|4770798|320000|0|30986310|32443483|6472225|7220205|0|002Dujsj0nteKX|003aQYLo2x8izP|0034HL4j4YEPGg|0|8006","fiurl":"","fnote":1006,"fsinger":"刘德华","fsinger2":"","fsong":"练习 (Live)","grp":[{"albumName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>中国巡回演唱会·上海","chinesesinger":0,"docid":"7124875280666137494","f":"444527|练习|163|刘德华|36187|刘德华中国巡回演唱会·上海|2762647|340|7|1|0|0|5451067|0|0|0|0|0|0|0|0012JcDu1aq6j1|003aQYLo2x8izP|001jDc1M0WAthx|0|4013","fiurl":"","fnote":2013,"fsinger":"刘德华","fsinger2":"","fsong":"练习 (Live)","isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"d0012z3t8bv","nt":10000,"only":0,"pubTime":1222272000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习 (Live)","t":0,"tag":0,"ver":0},{"albumName_hilight":"我们的<span class=\"c_tx_highlight\">刘德华<\/span> (国语版)","chinesesinger":0,"docid":"8859806344503066586","f":"1811482|练习|163|刘德华|142221|我们的刘德华 (国语版)|2104771|259|1|1|0|10416797|4154829|320000|0|29423778|29583988|6284993|6283146|0|0026hVVe1wwQRY|003aQYLo2x8izP|000c84SZ2tBpDz|0|4013","fiurl":"","fnote":2013,"fsinger":"刘德华","fsinger2":"","fsong":"练习","isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"d0011dvx6mp","nt":10000,"only":0,"pubTime":1344528000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习","t":0,"tag":0,"ver":0}],"isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"i0022763h8t","nt":10000,"only":0,"pubTime":1038672000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习 (Live)","t":0,"tag":10,"ver":0}],"totalnum":395}
         * totaltime : 8.7E-5
         * zhida : {"chinesesinger":0,"type":0}
         */

        private String keyword;
        private int priority;
        private SemanticBean semantic;
        private SongBean song;
        private double totaltime;
        private ZhidaBean zhida;
        private List<?> qc;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public SemanticBean getSemantic() {
            return semantic;
        }

        public void setSemantic(SemanticBean semantic) {
            this.semantic = semantic;
        }

        public SongBean getSong() {
            return song;
        }

        public void setSong(SongBean song) {
            this.song = song;
        }

        public double getTotaltime() {
            return totaltime;
        }

        public void setTotaltime(double totaltime) {
            this.totaltime = totaltime;
        }

        public ZhidaBean getZhida() {
            return zhida;
        }

        public void setZhida(ZhidaBean zhida) {
            this.zhida = zhida;
        }

        public List<?> getQc() {
            return qc;
        }

        public void setQc(List<?> qc) {
            this.qc = qc;
        }

        public static class SemanticBean {
            /**
             * curnum : 0
             * curpage : 1
             * list : []
             * totalnum : 0
             */

            private int curnum;
            private int curpage;
            private int totalnum;
            private List<?> list;

            public int getCurnum() {
                return curnum;
            }

            public void setCurnum(int curnum) {
                this.curnum = curnum;
            }

            public int getCurpage() {
                return curpage;
            }

            public void setCurpage(int curpage) {
                this.curpage = curpage;
            }

            public int getTotalnum() {
                return totalnum;
            }

            public void setTotalnum(int totalnum) {
                this.totalnum = totalnum;
            }

            public List<?> getList() {
                return list;
            }

            public void setList(List<?> list) {
                this.list = list;
            }
        }

        public static class SongBean {
            /**
             * curnum : 3
             * curpage : 1
             * list : [{"albumName_hilight":"你是我的骄傲演唱会","chinesesinger":0,"docid":"1686449820018762247","f":"7060362|练习|163|刘德华|643907|你是我的骄傲演唱会|2570188|298|7|1|0|11926677|4770798|320000|0|30986310|32443483|6472225|7220205|0|002Dujsj0nteKX|003aQYLo2x8izP|0034HL4j4YEPGg|0|8006","fiurl":"","fnote":1006,"fsinger":"刘德华","fsinger2":"","fsong":"练习 (Live)","grp":[{"albumName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>中国巡回演唱会·上海","chinesesinger":0,"docid":"7124875280666137494","f":"444527|练习|163|刘德华|36187|刘德华中国巡回演唱会·上海|2762647|340|7|1|0|0|5451067|0|0|0|0|0|0|0|0012JcDu1aq6j1|003aQYLo2x8izP|001jDc1M0WAthx|0|4013","fiurl":"","fnote":2013,"fsinger":"刘德华","fsinger2":"","fsong":"练习 (Live)","isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"d0012z3t8bv","nt":10000,"only":0,"pubTime":1222272000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习 (Live)","t":0,"tag":0,"ver":0},{"albumName_hilight":"我们的<span class=\"c_tx_highlight\">刘德华<\/span> (国语版)","chinesesinger":0,"docid":"8859806344503066586","f":"1811482|练习|163|刘德华|142221|我们的刘德华 (国语版)|2104771|259|1|1|0|10416797|4154829|320000|0|29423778|29583988|6284993|6283146|0|0026hVVe1wwQRY|003aQYLo2x8izP|000c84SZ2tBpDz|0|4013","fiurl":"","fnote":2013,"fsinger":"刘德华","fsinger2":"","fsong":"练习","isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"d0011dvx6mp","nt":10000,"only":0,"pubTime":1344528000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习","t":0,"tag":0,"ver":0}],"isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"i0022763h8t","nt":10000,"only":0,"pubTime":1038672000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习 (Live)","t":0,"tag":10,"ver":0}]
             * totalnum : 395
             */

            private int curnum;
            private int curpage;
            private int totalnum;
            private List<ListBean> list;

            public int getCurnum() {
                return curnum;
            }

            public void setCurnum(int curnum) {
                this.curnum = curnum;
            }

            public int getCurpage() {
                return curpage;
            }

            public void setCurpage(int curpage) {
                this.curpage = curpage;
            }

            public int getTotalnum() {
                return totalnum;
            }

            public void setTotalnum(int totalnum) {
                this.totalnum = totalnum;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * albumName_hilight : 你是我的骄傲演唱会
                 * chinesesinger : 0
                 * docid : 1686449820018762247
                 * f : 7060362|练习|163|刘德华|643907|你是我的骄傲演唱会|2570188|298|7|1|0|11926677|4770798|320000|0|30986310|32443483|6472225|7220205|0|002Dujsj0nteKX|003aQYLo2x8izP|0034HL4j4YEPGg|0|8006
                 * fiurl :
                 * fnote : 1006
                 * fsinger : 刘德华
                 * fsinger2 :
                 * fsong : 练习 (Live)
                 * grp : [{"albumName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>中国巡回演唱会·上海","chinesesinger":0,"docid":"7124875280666137494","f":"444527|练习|163|刘德华|36187|刘德华中国巡回演唱会·上海|2762647|340|7|1|0|0|5451067|0|0|0|0|0|0|0|0012JcDu1aq6j1|003aQYLo2x8izP|001jDc1M0WAthx|0|4013","fiurl":"","fnote":2013,"fsinger":"刘德华","fsinger2":"","fsong":"练习 (Live)","isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"d0012z3t8bv","nt":10000,"only":0,"pubTime":1222272000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习 (Live)","t":0,"tag":0,"ver":0},{"albumName_hilight":"我们的<span class=\"c_tx_highlight\">刘德华<\/span> (国语版)","chinesesinger":0,"docid":"8859806344503066586","f":"1811482|练习|163|刘德华|142221|我们的刘德华 (国语版)|2104771|259|1|1|0|10416797|4154829|320000|0|29423778|29583988|6284993|6283146|0|0026hVVe1wwQRY|003aQYLo2x8izP|000c84SZ2tBpDz|0|4013","fiurl":"","fnote":2013,"fsinger":"刘德华","fsinger2":"","fsong":"练习","isupload":0,"isweiyun":0,"lyric":"","lyric_hilight":"","mv":"d0011dvx6mp","nt":10000,"only":0,"pubTime":1344528000,"pure":0,"singerMID":"003aQYLo2x8izP","singerMID2":"","singerName2_hilight":"","singerName_hilight":"<span class=\"c_tx_highlight\">刘德华<\/span>","singerid":163,"singerid2":0,"songName_hilight":"练习","t":0,"tag":0,"ver":0}]
                 * isupload : 0
                 * isweiyun : 0
                 * lyric :
                 * lyric_hilight :
                 * mv : i0022763h8t
                 * nt : 10000
                 * only : 0
                 * pubTime : 1038672000
                 * pure : 0
                 * singerMID : 003aQYLo2x8izP
                 * singerMID2 :
                 * singerName2_hilight :
                 * singerName_hilight : <span class="c_tx_highlight">刘德华</span>
                 * singerid : 163
                 * singerid2 : 0
                 * songName_hilight : 练习 (Live)
                 * t : 0
                 * tag : 10
                 * ver : 0
                 */

                private String albumName_hilight;
                private int chinesesinger;
                private String docid;
                private String f;
                private String fiurl;
                private int fnote;
                private String fsinger;
                private String fsinger2;
                private String fsong;
                private int isupload;
                private int isweiyun;
                private String lyric;
                private String lyric_hilight;
                private String mv;
                private int nt;
                private int only;
                private int pubTime;
                private int pure;
                private String singerMID;
                private String singerMID2;
                private String singerName2_hilight;
                private String singerName_hilight;
                private int singerid;
                private int singerid2;
                private String songName_hilight;
                private int t;
                private int tag;
                private int ver;
                private List<GrpBean> grp;

                public String getAlbumName_hilight() {
                    return albumName_hilight;
                }

                public void setAlbumName_hilight(String albumName_hilight) {
                    this.albumName_hilight = albumName_hilight;
                }

                public int getChinesesinger() {
                    return chinesesinger;
                }

                public void setChinesesinger(int chinesesinger) {
                    this.chinesesinger = chinesesinger;
                }

                public String getDocid() {
                    return docid;
                }

                public void setDocid(String docid) {
                    this.docid = docid;
                }

                public String getF() {
                    return f;
                }

                public void setF(String f) {
                    this.f = f;
                }

                public String getFiurl() {
                    return fiurl;
                }

                public void setFiurl(String fiurl) {
                    this.fiurl = fiurl;
                }

                public int getFnote() {
                    return fnote;
                }

                public void setFnote(int fnote) {
                    this.fnote = fnote;
                }

                public String getFsinger() {
                    return fsinger;
                }

                public void setFsinger(String fsinger) {
                    this.fsinger = fsinger;
                }

                public String getFsinger2() {
                    return fsinger2;
                }

                public void setFsinger2(String fsinger2) {
                    this.fsinger2 = fsinger2;
                }

                public String getFsong() {
                    return fsong;
                }

                public void setFsong(String fsong) {
                    this.fsong = fsong;
                }

                public int getIsupload() {
                    return isupload;
                }

                public void setIsupload(int isupload) {
                    this.isupload = isupload;
                }

                public int getIsweiyun() {
                    return isweiyun;
                }

                public void setIsweiyun(int isweiyun) {
                    this.isweiyun = isweiyun;
                }

                public String getLyric() {
                    return lyric;
                }

                public void setLyric(String lyric) {
                    this.lyric = lyric;
                }

                public String getLyric_hilight() {
                    return lyric_hilight;
                }

                public void setLyric_hilight(String lyric_hilight) {
                    this.lyric_hilight = lyric_hilight;
                }

                public String getMv() {
                    return mv;
                }

                public void setMv(String mv) {
                    this.mv = mv;
                }

                public int getNt() {
                    return nt;
                }

                public void setNt(int nt) {
                    this.nt = nt;
                }

                public int getOnly() {
                    return only;
                }

                public void setOnly(int only) {
                    this.only = only;
                }

                public int getPubTime() {
                    return pubTime;
                }

                public void setPubTime(int pubTime) {
                    this.pubTime = pubTime;
                }

                public int getPure() {
                    return pure;
                }

                public void setPure(int pure) {
                    this.pure = pure;
                }

                public String getSingerMID() {
                    return singerMID;
                }

                public void setSingerMID(String singerMID) {
                    this.singerMID = singerMID;
                }

                public String getSingerMID2() {
                    return singerMID2;
                }

                public void setSingerMID2(String singerMID2) {
                    this.singerMID2 = singerMID2;
                }

                public String getSingerName2_hilight() {
                    return singerName2_hilight;
                }

                public void setSingerName2_hilight(String singerName2_hilight) {
                    this.singerName2_hilight = singerName2_hilight;
                }

                public String getSingerName_hilight() {
                    return singerName_hilight;
                }

                public void setSingerName_hilight(String singerName_hilight) {
                    this.singerName_hilight = singerName_hilight;
                }

                public int getSingerid() {
                    return singerid;
                }

                public void setSingerid(int singerid) {
                    this.singerid = singerid;
                }

                public int getSingerid2() {
                    return singerid2;
                }

                public void setSingerid2(int singerid2) {
                    this.singerid2 = singerid2;
                }

                public String getSongName_hilight() {
                    return songName_hilight;
                }

                public void setSongName_hilight(String songName_hilight) {
                    this.songName_hilight = songName_hilight;
                }

                public int getT() {
                    return t;
                }

                public void setT(int t) {
                    this.t = t;
                }

                public int getTag() {
                    return tag;
                }

                public void setTag(int tag) {
                    this.tag = tag;
                }

                public int getVer() {
                    return ver;
                }

                public void setVer(int ver) {
                    this.ver = ver;
                }

                public List<GrpBean> getGrp() {
                    return grp;
                }

                public void setGrp(List<GrpBean> grp) {
                    this.grp = grp;
                }

                public static class GrpBean {
                    /**
                     * albumName_hilight : <span class="c_tx_highlight">刘德华</span>中国巡回演唱会·上海
                     * chinesesinger : 0
                     * docid : 7124875280666137494
                     * f : 444527|练习|163|刘德华|36187|刘德华中国巡回演唱会·上海|2762647|340|7|1|0|0|5451067|0|0|0|0|0|0|0|0012JcDu1aq6j1|003aQYLo2x8izP|001jDc1M0WAthx|0|4013
                     * fiurl :
                     * fnote : 2013
                     * fsinger : 刘德华
                     * fsinger2 :
                     * fsong : 练习 (Live)
                     * isupload : 0
                     * isweiyun : 0
                     * lyric :
                     * lyric_hilight :
                     * mv : d0012z3t8bv
                     * nt : 10000
                     * only : 0
                     * pubTime : 1222272000
                     * pure : 0
                     * singerMID : 003aQYLo2x8izP
                     * singerMID2 :
                     * singerName2_hilight :
                     * singerName_hilight : <span class="c_tx_highlight">刘德华</span>
                     * singerid : 163
                     * singerid2 : 0
                     * songName_hilight : 练习 (Live)
                     * t : 0
                     * tag : 0
                     * ver : 0
                     */

                    private String albumName_hilight;
                    private int chinesesinger;
                    private String docid;
                    private String f;
                    private String fiurl;
                    private int fnote;
                    private String fsinger;
                    private String fsinger2;
                    private String fsong;
                    private int isupload;
                    private int isweiyun;
                    private String lyric;
                    private String lyric_hilight;
                    private String mv;
                    private int nt;
                    private int only;
                    private int pubTime;
                    private int pure;
                    private String singerMID;
                    private String singerMID2;
                    private String singerName2_hilight;
                    private String singerName_hilight;
                    private int singerid;
                    private int singerid2;
                    private String songName_hilight;
                    private int t;
                    private int tag;
                    private int ver;

                    public String getAlbumName_hilight() {
                        return albumName_hilight;
                    }

                    public void setAlbumName_hilight(String albumName_hilight) {
                        this.albumName_hilight = albumName_hilight;
                    }

                    public int getChinesesinger() {
                        return chinesesinger;
                    }

                    public void setChinesesinger(int chinesesinger) {
                        this.chinesesinger = chinesesinger;
                    }

                    public String getDocid() {
                        return docid;
                    }

                    public void setDocid(String docid) {
                        this.docid = docid;
                    }

                    public String getF() {
                        return f;
                    }

                    public void setF(String f) {
                        this.f = f;
                    }

                    public String getFiurl() {
                        return fiurl;
                    }

                    public void setFiurl(String fiurl) {
                        this.fiurl = fiurl;
                    }

                    public int getFnote() {
                        return fnote;
                    }

                    public void setFnote(int fnote) {
                        this.fnote = fnote;
                    }

                    public String getFsinger() {
                        return fsinger;
                    }

                    public void setFsinger(String fsinger) {
                        this.fsinger = fsinger;
                    }

                    public String getFsinger2() {
                        return fsinger2;
                    }

                    public void setFsinger2(String fsinger2) {
                        this.fsinger2 = fsinger2;
                    }

                    public String getFsong() {
                        return fsong;
                    }

                    public void setFsong(String fsong) {
                        this.fsong = fsong;
                    }

                    public int getIsupload() {
                        return isupload;
                    }

                    public void setIsupload(int isupload) {
                        this.isupload = isupload;
                    }

                    public int getIsweiyun() {
                        return isweiyun;
                    }

                    public void setIsweiyun(int isweiyun) {
                        this.isweiyun = isweiyun;
                    }

                    public String getLyric() {
                        return lyric;
                    }

                    public void setLyric(String lyric) {
                        this.lyric = lyric;
                    }

                    public String getLyric_hilight() {
                        return lyric_hilight;
                    }

                    public void setLyric_hilight(String lyric_hilight) {
                        this.lyric_hilight = lyric_hilight;
                    }

                    public String getMv() {
                        return mv;
                    }

                    public void setMv(String mv) {
                        this.mv = mv;
                    }

                    public int getNt() {
                        return nt;
                    }

                    public void setNt(int nt) {
                        this.nt = nt;
                    }

                    public int getOnly() {
                        return only;
                    }

                    public void setOnly(int only) {
                        this.only = only;
                    }

                    public int getPubTime() {
                        return pubTime;
                    }

                    public void setPubTime(int pubTime) {
                        this.pubTime = pubTime;
                    }

                    public int getPure() {
                        return pure;
                    }

                    public void setPure(int pure) {
                        this.pure = pure;
                    }

                    public String getSingerMID() {
                        return singerMID;
                    }

                    public void setSingerMID(String singerMID) {
                        this.singerMID = singerMID;
                    }

                    public String getSingerMID2() {
                        return singerMID2;
                    }

                    public void setSingerMID2(String singerMID2) {
                        this.singerMID2 = singerMID2;
                    }

                    public String getSingerName2_hilight() {
                        return singerName2_hilight;
                    }

                    public void setSingerName2_hilight(String singerName2_hilight) {
                        this.singerName2_hilight = singerName2_hilight;
                    }

                    public String getSingerName_hilight() {
                        return singerName_hilight;
                    }

                    public void setSingerName_hilight(String singerName_hilight) {
                        this.singerName_hilight = singerName_hilight;
                    }

                    public int getSingerid() {
                        return singerid;
                    }

                    public void setSingerid(int singerid) {
                        this.singerid = singerid;
                    }

                    public int getSingerid2() {
                        return singerid2;
                    }

                    public void setSingerid2(int singerid2) {
                        this.singerid2 = singerid2;
                    }

                    public String getSongName_hilight() {
                        return songName_hilight;
                    }

                    public void setSongName_hilight(String songName_hilight) {
                        this.songName_hilight = songName_hilight;
                    }

                    public int getT() {
                        return t;
                    }

                    public void setT(int t) {
                        this.t = t;
                    }

                    public int getTag() {
                        return tag;
                    }

                    public void setTag(int tag) {
                        this.tag = tag;
                    }

                    public int getVer() {
                        return ver;
                    }

                    public void setVer(int ver) {
                        this.ver = ver;
                    }
                }
            }
        }

        public static class ZhidaBean {
            /**
             * chinesesinger : 0
             * type : 0
             */

            private int chinesesinger;
            private int type;

            public int getChinesesinger() {
                return chinesesinger;
            }

            public void setChinesesinger(int chinesesinger) {
                this.chinesesinger = chinesesinger;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
