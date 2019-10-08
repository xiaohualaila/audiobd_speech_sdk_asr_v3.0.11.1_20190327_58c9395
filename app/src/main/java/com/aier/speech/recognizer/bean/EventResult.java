package com.aier.speech.recognizer.bean;

import java.util.List;

public class EventResult {

    /**
     * error_code : 0
     * error_msg : 获取成功
     * data : {"list":[{"event_name":"红井","event_content":"","title":"红井","image":"https://cache.buwangchuxin.com/images/TmcP4E4EX2.jpg","description":"","content":"","intro":"红井","lat":"25.8797400000","lng":"116.0075400000"},{"event_name":"红井的故事","event_content":"     沙洲坝是一个极其缺水的地方","title":"红井","image":"https://cache.buwangchuxin.com/images/TmcP4E4EX2.jpg","description":"红井这口井之所以称为红井，是苏区时期，毛泽东亲自带领干部群众一起开挖的，它是当时党和苏维埃政府密切联系群众、解决群众生活困难的历史见证。","content":"<p>","intro":"红井","lat":"25.8797400000","lng":"116.0075400000"},{"event_name":"保卫红井","event_content":"     国民党反动派和土豪劣伸为了消除","title":"红井","image":"https://cache.buwangchuxin.com/images/TmcP4E4EX2.jpg","description":"红井这口井之所以称为红井，是苏区时期，毛泽东亲自带领干部群众一起开挖的，它是当时党和苏维埃政府密切联系群众、解决群众生活困难的历史见证。","content":"<p>","intro":"红井","lat":"25.8797400000","lng":"116.0075400000"}]}
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
             * event_name : 红井
             * event_content :
             * title : 红井
             * image : https://cache.buwangchuxin.com/images/TmcP4E4EX2.jpg
             * description :
             * content :
             * intro : 红井
             * lat : 25.8797400000
             * lng : 116.0075400000
             */

            private String event_name;
            private String event_content;
            private String title;
            private String image;
            private String description;
            private String content;
            private String intro;
            private String lat;
            private String lng;

            public String getEvent_name() {
                return event_name;
            }

            public void setEvent_name(String event_name) {
                this.event_name = event_name;
            }

            public String getEvent_content() {
                return event_content;
            }

            public void setEvent_content(String event_content) {
                this.event_content = event_content;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }
        }
    }
}
