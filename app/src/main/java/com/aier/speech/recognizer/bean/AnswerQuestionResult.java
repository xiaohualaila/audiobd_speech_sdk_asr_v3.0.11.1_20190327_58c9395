package com.aier.speech.recognizer.bean;

import java.util.List;

public class AnswerQuestionResult {

    /**
     * error_code : 0
     * error_msg : 获取成功!
     * data : {"list":[{"id":51,"question":"贺龙同志入党处位于哪里？","type":1,"level":2,"topics":[{"id":202,"question_id":51,"answer":"信丰","is_answer":2},{"id":201,"question_id":51,"answer":"兴国","is_answer":2},{"id":203,"question_id":51,"answer":"石城","is_answer":2},{"id":200,"question_id":51,"answer":"瑞金","is_answer":1}]},{"id":73,"question":"井冈山会师发生的时间？","type":1,"level":2,"topics":[{"id":289,"question_id":73,"answer":"1927年9月","is_answer":2},{"id":288,"question_id":73,"answer":"1928年4月","is_answer":1},{"id":290,"question_id":73,"answer":"1928年10月","is_answer":2},{"id":291,"question_id":73,"answer":"1929年5月","is_answer":2}]},{"id":58,"question":"苏维埃的\u201c大管家\u201d指的是哪一旧址？","type":1,"level":2,"topics":[{"id":230,"question_id":58,"answer":"中央粮食人民委员部旧址","is_answer":2},{"id":231,"question_id":58,"answer":"中央劳动人民委员部旧址","is_answer":2},{"id":228,"question_id":58,"answer":"中央内务人民委员部旧址","is_answer":1},{"id":229,"question_id":58,"answer":"中华苏维埃共和国最高法院旧址","is_answer":2}]},{"id":68,"question":"兴国\u201c四星望月\u201d是谁命名的？","type":1,"level":2,"topics":[{"id":269,"question_id":68,"answer":"周恩来","is_answer":2},{"id":270,"question_id":68,"answer":"彭德怀","is_answer":2},{"id":271,"question_id":68,"answer":"陈毅","is_answer":2},{"id":268,"question_id":68,"answer":"毛泽东","is_answer":1}]},{"id":87,"question":"朱德委员长一辈子偏爱兰花，是出于对谁的思念？","type":1,"level":1,"topics":[{"id":346,"question_id":87,"answer":"肖菊芳","is_answer":2},{"id":345,"question_id":87,"answer":"康克清","is_answer":2},{"id":344,"question_id":87,"answer":"伍若兰","is_answer":1},{"id":347,"question_id":87,"answer":"刘从珍","is_answer":2}]}]}
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

    @Override
    public String toString() {
        return "AnswerQuestionResult{" +
                "error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                ", data=" + data +
                '}';
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



        @Override
        public String toString() {
            return "DataBean{" +
                    "list=" + list +
                    '}';
        }
    }
}
