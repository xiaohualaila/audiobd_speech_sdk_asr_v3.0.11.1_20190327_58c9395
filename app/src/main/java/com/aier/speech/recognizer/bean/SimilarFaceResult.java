package com.aier.speech.recognizer.bean;

import java.util.List;

public class SimilarFaceResult {

    /**
     * result : [{"name":"贺子珍","grade":"红色英雄","sex":"女","birthtime":"1909年9月28日","deathtime":"1984年4月19日","birthplace":"江西永新","nation":"汉族","partytime":"1926年","image":"https://cache.buwangchuxin.com/贺子珍.jpg","draw_image":"https://cache.buwangchuxin.com/images/kWeNFmH8Wn.png","content":"贺子珍（1909年9月28日-1984年4月19日），原名桂圆，又名自珍，江西永新乡绅贺焕文长女，毛泽东第二任妻子，贺子珍与毛泽东生三子三女，李敏是贺子珍和毛泽东惟一在世的孩子。大革命前后入党并投身游击战争，是井冈山第一位女党员。1925年加入中国共产主义青年团，1926年毕业于永新女子学校，同年加入中国共产党。曾任共青团永新县委书记、中共吉安县委妇女运动委员会书记。建国后，任杭州市妇联主任，是第五届全国政协委员，中国共产党优秀党员，杰出的妇女先驱。1984年4月19日17时17分，贺子珍同志逝世，葬于八宝山革命公墓。","description":"（1909-1984），红军时期机要科科长。江西永新人，大革命前后投身游击战争，她有男儿一样钢铁般的意志，大无畏的牺牲精神，是井冈山第一位女党员。毛泽东带兵到井冈山，\u201c湖南佬\u201d和\u201c江西妹\u201d成就了一段充满传奇色彩的\u201c井冈之恋\u201d，是毛泽东的第三任妻子。","duty":"机要科科长","score":0.38954490423202515,"box":[159,70,958,869]}]
     * timeused : 1366
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
         * name : 贺子珍
         * grade : 红色英雄
         * sex : 女
         * birthtime : 1909年9月28日
         * deathtime : 1984年4月19日
         * birthplace : 江西永新
         * nation : 汉族
         * partytime : 1926年
         * image : https://cache.buwangchuxin.com/贺子珍.jpg
         * draw_image : https://cache.buwangchuxin.com/images/kWeNFmH8Wn.png
         * content : 贺子珍（1909年9月28日-1984年4月19日），原名桂圆，又名自珍，江西永新乡绅贺焕文长女，毛泽东第二任妻子，贺子珍与毛泽东生三子三女，李敏是贺子珍和毛泽东惟一在世的孩子。大革命前后入党并投身游击战争，是井冈山第一位女党员。1925年加入中国共产主义青年团，1926年毕业于永新女子学校，同年加入中国共产党。曾任共青团永新县委书记、中共吉安县委妇女运动委员会书记。建国后，任杭州市妇联主任，是第五届全国政协委员，中国共产党优秀党员，杰出的妇女先驱。1984年4月19日17时17分，贺子珍同志逝世，葬于八宝山革命公墓。
         * description : （1909-1984），红军时期机要科科长。江西永新人，大革命前后投身游击战争，她有男儿一样钢铁般的意志，大无畏的牺牲精神，是井冈山第一位女党员。毛泽东带兵到井冈山，“湖南佬”和“江西妹”成就了一段充满传奇色彩的“井冈之恋”，是毛泽东的第三任妻子。
         * duty : 机要科科长
         * score : 0.38954490423202515
         * box : [159,70,958,869]
         */

        private String name;
        private String grade;
        private String sex;
        private String birthtime;
        private String deathtime;
        private String birthplace;
        private String nation;
        private String partytime;
        private String image;
        private String draw_image;
        private String content;
        private String description;
        private String duty;
        private double score;
        private List<Integer> box;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthtime() {
            return birthtime;
        }

        public void setBirthtime(String birthtime) {
            this.birthtime = birthtime;
        }

        public String getDeathtime() {
            return deathtime;
        }

        public void setDeathtime(String deathtime) {
            this.deathtime = deathtime;
        }

        public String getBirthplace() {
            return birthplace;
        }

        public void setBirthplace(String birthplace) {
            this.birthplace = birthplace;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getPartytime() {
            return partytime;
        }

        public void setPartytime(String partytime) {
            this.partytime = partytime;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDraw_image() {
            return draw_image;
        }

        public void setDraw_image(String draw_image) {
            this.draw_image = draw_image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
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
    }
}
