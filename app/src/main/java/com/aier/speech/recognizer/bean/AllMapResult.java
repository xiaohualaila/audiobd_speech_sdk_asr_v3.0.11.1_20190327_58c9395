package com.aier.speech.recognizer.bean;

import java.util.List;

public class AllMapResult {

    /**
     * error_code : 0
     * error_msg : 获取成功
     * data : {"list":[{"name":"毛泽东","title":"泸定桥","lat":29.23804809100362,"lng":102.91727682821848},{"name":"周恩来","title":"泸定桥","lat":29.23804809100362,"lng":102.91727682821848}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * name : 毛泽东
             * title : 泸定桥
             * lat : 29.23804809100362
             * lng : 102.91727682821848
             */

            private String name;
            private String title;
            private double lat;
            private double lng;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }
    }
}
