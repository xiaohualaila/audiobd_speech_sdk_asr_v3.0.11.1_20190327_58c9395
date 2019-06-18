package com.aier.speech.recognizer.bean;

import java.util.List;

public class FaceCheckBean {

    /**
     * result : [{"label":"陌生人","score":0.5396463871002197,"box":[420,705,720,1026]}]
     * timeused : 696
     */

    private int timeused;
    private List<ResultBean> result;

    public int getTimeused() {
        return timeused;
    }

    public void setTimeused(int timeused) {
        this.timeused = timeused;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * label : 陌生人
         * score : 0.5396463871002197
         * box : [420,705,720,1026]
         */

        private String label;
        private double score;
        private List<Integer> box;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public List<Integer> getBox() {
            return box;
        }

        public void setBox(List<Integer> box) {
            this.box = box;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "label='" + label + '\'' +
                    ", score=" + score +
                    ", box=" + box +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FaceCheckBean{" +
                "timeused=" + timeused +
                ", result=" + result +
                '}';
    }
}
