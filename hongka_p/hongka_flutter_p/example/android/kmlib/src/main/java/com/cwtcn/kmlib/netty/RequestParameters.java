
package com.cwtcn.kmlib.netty;

/**
 * 构造请求参数
 */
public class RequestParameters {

    /** 参数构建队列buffer */
    private StringBuffer par;
    /** 是否首次添加参数 */
    private boolean isFirst;

    /**
     * 完成构建前的初始化
     */
    public RequestParameters() {
        isFirst = true;
        par = new StringBuffer();
    }

    /**
     * 添加string类型参数
     *
     * @param value
     */
    public void put(String value) {
        if ((value == null)) {
            return;
        }
        if (isFirst) {
            isFirst = false;
            par.append(value);
        } else {
            par.append("&" + value);
        }
    }

    /**
     * 添加int类型参数
     * @param value
     */
    public void put(int value) {
        if (isFirst) {
            isFirst = false;
            par.append( value);
        } else {
        	if(value == -99) {
        		par.append("&");
        	}else {
        		par.append("&" + value);
        	}
        }
    }

    /**
     * 添加double类型参数
     * @param value
     */
    public void put(double value) {
        if (isFirst) {
            isFirst = false;
            par.append(value);
        } else {
        	if(value == -99) {
        		par.append("&");
        	}else {
        		par.append("&" + value);
        	}
        }
    }

    /**
     * 返回构建完毕的参数字符串
     */
    public String toString() {
        return par.toString();
    }
}
