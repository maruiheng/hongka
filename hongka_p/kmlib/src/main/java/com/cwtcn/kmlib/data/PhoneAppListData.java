package com.cwtcn.kmlib.data;

import java.util.List;

/**
 * Created by xhl on 2017/4/25.
 */

public class PhoneAppListData {
    public Setting setting;

    public List<PhoneAppData> tpaList;

    public List<XDD> xddList;

    public static class Setting {

        public int enabledDownApp = 0;

        public int enabledDurationApp = 0;
    }

    public static class XDD {

        public String appName;

        public String packageName;

        public int duration;
    }
}
