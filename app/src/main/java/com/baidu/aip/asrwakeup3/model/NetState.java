package com.baidu.aip.asrwakeup3.model;

public class NetState {
    public boolean isUse;
    public  String message;

    public static NetState getInstance(boolean isUse,String message) {
        return new NetState(isUse,message);
    }

    private NetState(boolean isUse,String message) {
        this.isUse = isUse;
        this.message = message;
    }
}
