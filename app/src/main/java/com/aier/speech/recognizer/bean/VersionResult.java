package com.aier.speech.recognizer.bean;

public class VersionResult {


    /**
     * success : true
     * data : {"gid":64,"name":"南康家居防伪","version":"0.1.1","type":"android","desp":"1. 版本更新测试","url":"http://cloud.zq12369.com/app/deviceinfo/history/0.1.2/H52240716.wgt","isAPK":0,"update_time":"2019-05-13 15:02:32"}
     */

    private boolean success;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * gid : 64
         * name : 南康家居防伪
         * version : 0.1.1
         * type : android
         * desp : 1. 版本更新测试
         * url : http://cloud.zq12369.com/app/deviceinfo/history/0.1.2/H52240716.wgt
         * isAPK : 0
         * update_time : 2019-05-13 15:02:32
         */

        private int gid;
        private String name;
        private String version;
        private String type;
        private String desp;
        private String url;
        private int isAPK;
        private String update_time;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesp() {
            return desp;
        }

        public void setDesp(String desp) {
            this.desp = desp;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getIsAPK() {
            return isAPK;
        }

        public void setIsAPK(int isAPK) {
            this.isAPK = isAPK;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
