package com.aier.speech.recognizer.bean;

public class YUBAIBean2 {


    /**
     * result : {"title":"毛泽东的历史功绩","answer":"毛泽东的历史功绩为一、毛泽东引导中国走上社会主义发展道路，确立了社会主义基本制度。二、毛泽东领导中国人民开辟了社会主义现代化建设道路，开始了沿着社会主义道路实现中华民族伟大复兴的新纪元。三、开创了人民当家作主的新时代，开始了实现社会主义民主的艰辛而曲折的探索。四、奠定了中国共产党的执政地位，对保持马克思主义政党的先进性和执政地位作了不懈的探索。五、奠定了新中国在国际上的大国地位，为开创独立自主的和平外交作了不懈的努力。","score":1}
     * timeused : 8
     */

    private ResultBean result;
    private int timeused;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getTimeused() {
        return timeused;
    }

    public void setTimeused(int timeused) {
        this.timeused = timeused;
    }

    public static class ResultBean {
        /**
         * title : 毛泽东的历史功绩
         * answer : 毛泽东的历史功绩为一、毛泽东引导中国走上社会主义发展道路，确立了社会主义基本制度。二、毛泽东领导中国人民开辟了社会主义现代化建设道路，开始了沿着社会主义道路实现中华民族伟大复兴的新纪元。三、开创了人民当家作主的新时代，开始了实现社会主义民主的艰辛而曲折的探索。四、奠定了中国共产党的执政地位，对保持马克思主义政党的先进性和执政地位作了不懈的探索。五、奠定了新中国在国际上的大国地位，为开创独立自主的和平外交作了不懈的努力。
         * score : 1.0
         */

        private String title;
        private String answer;
        private double score;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
