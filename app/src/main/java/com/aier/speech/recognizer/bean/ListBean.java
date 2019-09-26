package com.aier.speech.recognizer.bean;

import java.util.List;

public class ListBean {
        /**
         * id : 51
         * question : 贺龙同志入党处位于哪里？
         * type : 1
         * level : 2
         * topics : [{"id":202,"question_id":51,"answer":"信丰","is_answer":2},{"id":201,"question_id":51,"answer":"兴国","is_answer":2},{"id":203,"question_id":51,"answer":"石城","is_answer":2},{"id":200,"question_id":51,"answer":"瑞金","is_answer":1}]
         */

        private int id;
        private String question;
        private int type;
        private int level;
        private List<TopicsBean> topics;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public List<TopicsBean> getTopics() {
            return topics;
        }

        public void setTopics(List<TopicsBean> topics) {
            this.topics = topics;
        }


}
