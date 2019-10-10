package com.aier.speech.recognizer.bean;

import java.util.List;

public class AddQuestionResult {

    /**
     * error_code : 0
     * error_msg : 上报成功!
     * data : []
     */

    private int error_code;
    private String error_msg;
    private List<?> data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
