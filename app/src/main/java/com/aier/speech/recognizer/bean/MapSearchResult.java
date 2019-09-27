package com.aier.speech.recognizer.bean;

import java.util.List;

public class MapSearchResult {

    /**
     * error_code : 0
     * error_msg : 获取成功
     * data : {"list":[{"keyword":"毛泽覃","type":1,"sort":0},{"keyword":"刘伯坚","type":1,"sort":0},{"keyword":"服务为民 尽责履职 \u2014\u2014赣州水务集团有限责任公司党委书记、董事长李聪","type":2,"sort":0},{"keyword":"红六师石城阻击战指挥部旧址","type":3,"sort":0},{"keyword":"大柏地战斗旧址","type":3,"sort":0}]}
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
             * keyword : 毛泽覃
             * type : 1
             * sort : 0
             */

            private String keyword;
            private int type;
            private int sort;

            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }
    }
}
