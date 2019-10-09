package com.aier.speech.recognizer.bean;

import java.util.List;

public class GuanlianCitiaoResult {

    /**
     * error_code : 0
     * error_msg : 获取成功
     * data : {"relate":[{"to_label":"妻子","name":"罗一秀","image":"https://cache.buwangchuxin.com/罗一秀.jpg"},{"to_label":"妻子","name":"江青","image":"https://cache.buwangchuxin.com/江青.jpg"},{"to_label":"妻子","name":"杨开慧","image":"https://cache.buwangchuxin.com/杨开慧.jpg"},{"to_label":"妻子","name":"贺子珍","image":"https://cache.buwangchuxin.com/贺子珍.jpg"},{"to_label":"女儿","name":"李讷","image":"https://cache.buwangchuxin.com/images/6Q3tm4wN23.png"},{"to_label":"儿子","name":"毛岸青","image":"https://cache.buwangchuxin.com/毛岸青.jpeg"},{"to_label":"儿子","name":"毛岸英","image":"https://cache.buwangchuxin.com/毛岸英.png"},{"to_label":"儿子","name":"毛岸龙","image":""},{"to_label":"儿子","name":"毛岸红","image":""},{"to_label":"女儿","name":"李敏","image":"https://cache.buwangchuxin.com/images/TH4YZAAbYS.png"},{"to_label":"领导","name":"何克全","image":"http://www.hsxgw.gov.cn/n1338/n1356/n3351/c17641/pic_17641.jpg"},{"to_label":"领导","name":"吴亮平","image":"http://www.hsxgw.gov.cn/n1338/n1356/n3364/c17650/pic_17650.jpg"},{"to_label":"丈夫","name":"贺子珍","image":"https://cache.buwangchuxin.com/贺子珍.jpg"},{"to_label":"哥哥","name":"毛泽民","image":"https://cache.buwangchuxin.com/images/4wtt3EjNKK.jpg"},{"to_label":"堂哥","name":"毛泽建","image":"https://cache.buwangchuxin.com/images/TCtP3P6enN.jpg"},{"to_label":"学生","name":"徐特立","image":"http://www.hsxgw.gov.cn/n1338/n1356/n3352/c17639/pic_17639.jpg"},{"to_label":"同学","name":"罗学瓒","image":"https://cache.buwangchuxin.com/images/7pr4WpdWDJ.jpg"},{"to_label":"亲家","name":"孔从洲","image":"https://cache.buwangchuxin.com/images/Pw8ZM3Ptdp.jpg"},{"to_label":"岳父","name":"孔令华","image":"https://cache.buwangchuxin.com/images/NXZWaSJkia.jpg"},{"to_label":"外孙","name":"孔继宁","image":"https://cache.buwangchuxin.com/images/NPCYEHw4Fn.jpg"},{"to_label":"外公","name":"孔东梅","image":"https://cache.buwangchuxin.com/images/esnsWzWacR.jpg"}]}
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
        private List<RelateBean> relate;

        public List<RelateBean> getRelate() {
            return relate;
        }

        public void setRelate(List<RelateBean> relate) {
            this.relate = relate;
        }

        public static class RelateBean {
            /**
             * to_label : 妻子
             * name : 罗一秀
             * image : https://cache.buwangchuxin.com/罗一秀.jpg
             */

            private String to_label;
            private String name;
            private String image;

            public String getTo_label() {
                return to_label;
            }

            public void setTo_label(String to_label) {
                this.to_label = to_label;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
