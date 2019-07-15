package com.cwtcn.kmlib.data;

import java.util.List;

public class MessagePushData {
    //{"imei":"860860000032597","resource":[{"n":"星星火炬广播","u":"https:\/\/cdn.hongkazhijia.com\/5a\/5a7cb8e56b1f86da741c46852591d4139e377f3c1c2c4286758b40b002da3ebc.mp3","s":"0","zshk":{"albumId":"181221013174721948475","albumName":"星星火炬广播","imei":"860860000032597","userId":"abardeen-18624267423","programList":[{"programName":"师大附小呈贡校区——新闻播报2019-0","programId":"190411013157676662494"}]}}]}
    /**
     * imei : 860860000032597
     * resource : [{"n":"星星火炬广播","u":"https://cdn.hongkazhijia.com/5a/5a7cb8e56b1f86da741c46852591d4139e377f3c1c2c4286758b40b002da3ebc.mp3","s":"0","zshk":{"albumId":"181221013174721948475","albumName":"星星火炬广播","imei":"860860000032597","userId":"abardeen-18624267423","programList":[{"programName":"师大附小呈贡校区\u2014\u2014新闻播报2019-0","programId":"190411013157676662494"}]}}]
     */

    private String imei;
    private List<ResourceBean> resource;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public List<ResourceBean> getResource() {
        return resource;
    }

    public void setResource(List<ResourceBean> resource) {
        this.resource = resource;
    }

    public static class ResourceBean {
        /**
         * n : 星星火炬广播
         * u : https://cdn.hongkazhijia.com/5a/5a7cb8e56b1f86da741c46852591d4139e377f3c1c2c4286758b40b002da3ebc.mp3
         * s : 0
         * zshk : {"albumId":"181221013174721948475","albumName":"星星火炬广播","imei":"860860000032597","userId":"abardeen-18624267423","programList":[{"programName":"师大附小呈贡校区\u2014\u2014新闻播报2019-0","programId":"190411013157676662494"}]}
         */

        private String n;
        private String u;
        private String s;
        private ZshkBean zshk;

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public ZshkBean getZshk() {
            return zshk;
        }

        public void setZshk(ZshkBean zshk) {
            this.zshk = zshk;
        }

        public static class ZshkBean {
            /**
             * albumId : 181221013174721948475
             * albumName : 星星火炬广播
             * imei : 860860000032597
             * userId : abardeen-18624267423
             * programList : [{"programName":"师大附小呈贡校区\u2014\u2014新闻播报2019-0","programId":"190411013157676662494"}]
             */

            private String albumId;
            private String albumName;
            private String imei;
            private String userId;
            private List<ProgramListBean> programList;

            public String getAlbumId() {
                return albumId;
            }

            public void setAlbumId(String albumId) {
                this.albumId = albumId;
            }

            public String getAlbumName() {
                return albumName;
            }

            public void setAlbumName(String albumName) {
                this.albumName = albumName;
            }

            public String getImei() {
                return imei;
            }

            public void setImei(String imei) {
                this.imei = imei;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public List<ProgramListBean> getProgramList() {
                return programList;
            }

            public void setProgramList(List<ProgramListBean> programList) {
                this.programList = programList;
            }

            public static class ProgramListBean {
                /**
                 * programName : 师大附小呈贡校区——新闻播报2019-0
                 * programId : 190411013157676662494
                 */

                private String programName;
                private String programId;

                public String getProgramName() {
                    return programName;
                }

                public void setProgramName(String programName) {
                    this.programName = programName;
                }

                public String getProgramId() {
                    return programId;
                }

                public void setProgramId(String programId) {
                    this.programId = programId;
                }
            }
        }
    }




}
