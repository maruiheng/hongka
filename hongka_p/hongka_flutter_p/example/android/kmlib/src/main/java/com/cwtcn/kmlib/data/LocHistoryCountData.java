package com.cwtcn.kmlib.data;

import java.util.List;

/**
 * Created by xhl on 2017/4/20.
 */

public class LocHistoryCountData {
    /**
     * count : 1
     * list : [{"lat":38.861377,"locateType":2,"lon":121.522921,"mapType":"4","power":1,"time":"170107112859"}]
     */

    private int count;
    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * lat : 38.861377
         * locateType : 2
         * lon : 121.522921
         * mapType : 4
         * power : 1
         * time : 170107112859
         */

        private double lat;
        private int locateType;
        private double lon;
        private String mapType;
        private int power;
        private String time;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public int getLocateType() {
            return locateType;
        }

        public void setLocateType(int locateType) {
            this.locateType = locateType;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getMapType() {
            return mapType;
        }

        public void setMapType(String mapType) {
            this.mapType = mapType;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
