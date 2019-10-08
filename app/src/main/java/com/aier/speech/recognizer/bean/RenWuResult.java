package com.aier.speech.recognizer.bean;

import java.util.List;

public class RenWuResult {

    /**
     * error_code : 0
     * error_msg : 获取成功
     * data : {"list":[{"title":"石城县革命烈士纪念馆","image":"https://cache.buwangchuxin.com/images/FejdxWZhY2.jpg","description":"为县重点文物保护单位。1956年兴建，占地面积2500平方米","content":"<\/p>","intro":"石城县革命烈士纪念馆","lat":"26.336803","lng":"116.34428"},{"title":"贺龙同志入党处","image":"https://cache.buwangchuxin.com/images/p8J6hhxAMm.jpg","description":"贺龙、郭沫若、彭泽民入党处旧址位于江西省瑞金市象湖镇八一路居委会原瑞金第一中学（现为瑞金四中）。系赖氏公祠，座北向南偏东南，始建于1897年，三厅两井，硬山顶，砖木结构，占地849平方米。 ","content":"<p><span style=\"font-family: 宋体, SimSun;\">南昌起义部队起义胜利后，南下广东，1927年8月26日进占瑞金，驻于绵江中学。贺龙、郭沫若、彭泽民在此宣誓，加入中国共产党。<\/span><\/p>","intro":"贺龙同志入党处","lat":"25.86812 ","lng":"116.03828"},{"title":"壬田战斗遗址","image":"https://cache.buwangchuxin.com/images/Fph22bmCAA.jpg","description":"壬田战斗遗址位于瑞金市壬田镇境内，地处下街、高轩两村之间。壬田战斗战场的范围，南北长约2公里，东西宽1公里。战场被端在高轩村下河子北后山，山高约30米左右长条形，占地5000平方米。 ","content":"<p> <span style=\"font-family: 宋体, SimSun;\">1927年8月3日，八一南昌起义部队在周恩来、贺龙、叶挺、朱德、刘伯承等率领下撤离南...<\/span><\/p><p><br/><\/p>","intro":"壬田战斗遗址","lat":"25.98358","lng":"116.11841"}]}
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
             * title : 石城县革命烈士纪念馆
             * image : https://cache.buwangchuxin.com/images/FejdxWZhY2.jpg
             * description : 为县重点文物保护单位。1956年兴建，占地面积2500平方米
             * content : </p>
             * intro : 石城县革命烈士纪念馆
             * lat : 26.336803
             * lng : 116.34428
             */

            private String title;
            private String image;
            private String description;
            private String content;
            private String intro;
            private String lat;
            private String lng;

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
