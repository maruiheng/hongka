package com.cwtcn.kmlib.data;

/**
 * 周期实体
 *
 * @author Allen
 */
public class TrackerTimeData implements Comparable<TrackerTimeData> {
    /**
     * 编号
     */
    public int no = 0;
    /**
     * 可用状态
     */
    public boolean enabled = false;

    public String range = "00010600";
    /**
     * 重复星期标识 0000000
     */
    public String week = "1111111";

    public TrackerTimeData() {
        super();
    }

    public TrackerTimeData(int no, boolean enable, String range, String week) {
        super();
        this.no = no;
        this.enabled = enable;
        this.range = range;
        this.week = week;
    }


    @Override
    public int compareTo(TrackerTimeData another) {
        return another.no - this.no;
    }

    public String getMuteBg() {
        return range.substring(0, 4);
    }

    public String getMuteEd() {
        return range.substring(4, 8);
    }


    public boolean isEnabled() {
        return enabled;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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
}
