package com.aier.speech.recognizer.bean;

import java.util.List;

public class QuestionRankResult {


    /**
     * error_code : 0
     * error_msg : 获取成功!
     * data : {"left":[{"uniqid":"9dd9ca8de8dbe7bb3a3cb88599ee1796","score":60,"image":"http://travel-red.oss-cn-hangzhou.aliyuncs.com/54c76c0e7ebf548ee5f8cf1018e75feb.jpeg"},{"uniqid":"f61609f94979e1d0c6d1feae76e62f6e","score":60,"image":"http://travel-red.oss-cn-hangzhou.aliyuncs.com/d2c295c1d75a77b094ffba4fdb211442.jpeg"},{"uniqid":"3c98d71ef0898d7a18809a739f1bb01f","score":60,"image":"http://travel-red.oss-cn-hangzhou.aliyuncs.com/5f291b15ff47c3a7f7ce21efd9e06ddd.jpeg"},{"uniqid":"084b137b7a7c7386dc95fe09676db9ac","score":20,"image":"http://travel-red.oss-cn-hangzhou.aliyuncs.com/2588504ecf0883330887b20215a4f35a.jpeg"}],"right":[{"uniqid":"3c98d71ef0898d7a18809a739f1bb01f","score":60,"image":"http://travel-red.oss-cn-hangzhou.aliyuncs.com/5f291b15ff47c3a7f7ce21efd9e06ddd.jpeg"},{"uniqid":"f61609f94979e1d0c6d1feae76e62f6e","score":60,"image":"http://travel-red.oss-cn-hangzhou.aliyuncs.com/d2c295c1d75a77b094ffba4fdb211442.jpeg"},{"uniqid":"9dd9ca8de8dbe7bb3a3cb88599ee1796","score":60,"image":"http://travel-red.oss-cn-hangzhou.aliyuncs.com/54c76c0e7ebf548ee5f8cf1018e75feb.jpeg"},{"uniqid":"fe020c305a54ec582b916fbbb0ca5ef3","score":20,"image":"http://travel-red.oss-cn-hangzhou.aliyuncs.com/8acabf3a343f0972208e31a11d870b22.jpeg"}]}
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
        private List<LeftBean> left;
        private List<RightBean> right;

        public List<LeftBean> getLeft() {
            return left;
        }

        public void setLeft(List<LeftBean> left) {
            this.left = left;
        }

        public List<RightBean> getRight() {
            return right;
        }

        public void setRight(List<RightBean> right) {
            this.right = right;
        }

        public static class LeftBean {
            /**
             * uniqid : 9dd9ca8de8dbe7bb3a3cb88599ee1796
             * score : 60
             * image : http://travel-red.oss-cn-hangzhou.aliyuncs.com/54c76c0e7ebf548ee5f8cf1018e75feb.jpeg
             */

            private String uniqid;
            private int score;
            private String image;

            public String getUniqid() {
                return uniqid;
            }

            public void setUniqid(String uniqid) {
                this.uniqid = uniqid;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

        public static class RightBean {
            /**
             * uniqid : 3c98d71ef0898d7a18809a739f1bb01f
             * score : 60
             * image : http://travel-red.oss-cn-hangzhou.aliyuncs.com/5f291b15ff47c3a7f7ce21efd9e06ddd.jpeg
             */

            private String uniqid;
            private int score;
            private String image;

            public String getUniqid() {
                return uniqid;
            }

            public void setUniqid(String uniqid) {
                this.uniqid = uniqid;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
