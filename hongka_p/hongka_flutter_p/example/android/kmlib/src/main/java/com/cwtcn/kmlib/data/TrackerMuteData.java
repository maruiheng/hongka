package com.cwtcn.kmlib.data;

/**
 * 静音实体
 *
 * @author Allen
 */
public class TrackerMuteData implements Comparable<TrackerMuteData> {
    /**
     * 编号
     */
    private int no = 0;
    /**
     * 可用状态
     */
    private boolean enabled = false;

    private String range = "00010600";
    /**
     * 0101010表示周二、周四、周六
     */
    public String week = "1111111";

    public TrackerMuteData(int no, boolean enable, String range) {
        this.no = no;
        this.enabled = enable;
        this.range = range;
    }

    public TrackerMuteData(int no, boolean enable, String range, String week) {
        this.no = no;
        this.enabled = enable;
        this.range = range;
        this.week = week;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Override
    public int compareTo(TrackerMuteData another) {
        return another.no - this.no;
    }

}
