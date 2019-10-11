package com.aier.speech.recognizer.bean;

public class UniqidResult {

    /**
     * error_code : 0
     * error_msg : 上传成功
     * data : {"url":"http://travel-red.oss-cn-hangzhou.aliyuncs.com/2334d40f8bc9984a8e80e2930863c449.png","uniqid":"07576752a1fffcf32a9f0be9e32c7bf7"}
     */

    private int error_code;
    private String error_msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * url : http://travel-red.oss-cn-hangzhou.aliyuncs.com/2334d40f8bc9984a8e80e2930863c449.png
         * uniqid : 07576752a1fffcf32a9f0be9e32c7bf7
         */

        private String url;
        private String uniqid;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUniqid() {
            return uniqid;
        }

        public void setUniqid(String uniqid) {
            this.uniqid = uniqid;
        }
    }
}
