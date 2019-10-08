package com.aier.speech.recognizer.bean;

import java.util.List;

public class JingdianResult {

    /**
     * error_code : 0
     * error_msg : 获取成功
     * data : {"news_info":{"id":264,"title":"红井","intro":"红井","image":"https://cache.buwangchuxin.com/images/TmcP4E4EX2.jpg","vr_image":"","vr_hot_image":"","vr_path_path":"","voice_path":"","desc_voice_path":null,"description":"红井这口井之所以称为红井，是苏区时期，毛泽东亲自带领干部群众一起开挖的，它是当时党和苏维埃政府密切联系群众、解决群众生活困难的历史见证。","content":"<p><span style=\"font-family: 宋体, SimSun; font-size: 16px;\">  一九三一年，瑞金成立了中华苏维埃中央政府。政府起初设在叶坪，后来因为白匪军狂轰滥炸，为了安全防空，从叶坪迁到沙洲坝。毛泽东主席便住在沙洲坝的村子里。<\/span><\/p><p><span style=\"font-family: 宋体, SimSun; font-size: 16px;\">  有一天傍晚，毛主席办完公事从外面回来，一下马，看见乡亲们在塘里挑水，便问：\u201c这水挑去做什么用？\u201d乡亲们回答说：\u201c吃呀！\u201d毛主席说：\u201c水这么脏，能吃吗？\u201d乡亲们苦笑着说：\u201c有什么办法，再脏也得吃啊！\u201d毛主席说：\u201c不会打井么？\u201d乡亲们摇摇头说：\u201c沙洲坝人喝不得井水，这是天命！\u201d毛主席哈哈大笑说：\u201c不要信天命，要信革命！还是打口井吧！\u201d说罢，牵马进村去了。大家也就散了，谁也没有把这话记在心里。仍然每天到塘里挑水吃，毛主席也每天早出晚归忙他的公事。<\/span><\/p><p><span style=\"font-family: 宋体, SimSun; font-size: 16px;\">  有一天，天刚朦朦亮，起来挑水的人，看见有两个人影在村头走来走去。一个拿着锄头，一个拿着锹，这里锄锄，那里铲铲。谁这么早起来干什么呢？走前一看，原来是毛主席和他的警卫员。挑水的乡亲问：\u201c毛主席，你这是干什么？\u201d毛主席说：\u201c找水源、挖井。\u201d说完，便和警卫员在一块长满油草地地方铲开地皮挖了起来。挖到约有两三尺深，毛主席抓起一把泥土在手里捏了捏，对警卫员说：\u201c行，井位就定在这里，你去叫人来挖。\u201d于是毛主席叫挖井的事，立即传遍了全村，众人都自动的找着锄头铁锹，齐集到村头和毛主席一道挖井。毛主席一边和大家挖井，一边对乡亲们说：\u201c这几天忙，没工夫找大家商量打井的事，今天有半天空，我先替大家找个有水源的地方，定个位，破个土。我知道，你们信风水，怕得罪旱龙王，我不怕，如果旱龙王怪罪下来，让它来找我算账好了！\u201d逗得大家都笑了。大家说说笑笑，不到一天工夫，一口井便挖成了。从此，沙洲坝的乡亲喝上了井水，结束了祖祖辈辈挑塘水喝的历史。因为井是红军来了以后毛主席亲手挖的，所以乡亲们给这井起了个名字叫\u201c红井\u201d。<\/span><\/p><p><\/p><p><br/><\/p>","lat":"25.8797400000","lng":"116.0075400000"},"about_news":[{"title":"茅店镇樟树坝革命烈士纪念塔","image":"https://cache.buwangchuxin.com/images/7EFcJDP5yW.jpg"}]}
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
        /**
         * news_info : {"id":264,"title":"红井","intro":"红井","image":"https://cache.buwangchuxin.com/images/TmcP4E4EX2.jpg","vr_image":"","vr_hot_image":"","vr_path_path":"","voice_path":"","desc_voice_path":null,"description":"红井这口井之所以称为红井，是苏区时期，毛泽东亲自带领干部群众一起开挖的，它是当时党和苏维埃政府密切联系群众、解决群众生活困难的历史见证。","content":"<p><span style=\"font-family: 宋体, SimSun; font-size: 16px;\">  一九三一年，瑞金成立了中华苏维埃中央政府。政府起初设在叶坪，后来因为白匪军狂轰滥炸，为了安全防空，从叶坪迁到沙洲坝。毛泽东主席便住在沙洲坝的村子里。<\/span><\/p><p><span style=\"font-family: 宋体, SimSun; font-size: 16px;\">  有一天傍晚，毛主席办完公事从外面回来，一下马，看见乡亲们在塘里挑水，便问：\u201c这水挑去做什么用？\u201d乡亲们回答说：\u201c吃呀！\u201d毛主席说：\u201c水这么脏，能吃吗？\u201d乡亲们苦笑着说：\u201c有什么办法，再脏也得吃啊！\u201d毛主席说：\u201c不会打井么？\u201d乡亲们摇摇头说：\u201c沙洲坝人喝不得井水，这是天命！\u201d毛主席哈哈大笑说：\u201c不要信天命，要信革命！还是打口井吧！\u201d说罢，牵马进村去了。大家也就散了，谁也没有把这话记在心里。仍然每天到塘里挑水吃，毛主席也每天早出晚归忙他的公事。<\/span><\/p><p><span style=\"font-family: 宋体, SimSun; font-size: 16px;\">  有一天，天刚朦朦亮，起来挑水的人，看见有两个人影在村头走来走去。一个拿着锄头，一个拿着锹，这里锄锄，那里铲铲。谁这么早起来干什么呢？走前一看，原来是毛主席和他的警卫员。挑水的乡亲问：\u201c毛主席，你这是干什么？\u201d毛主席说：\u201c找水源、挖井。\u201d说完，便和警卫员在一块长满油草地地方铲开地皮挖了起来。挖到约有两三尺深，毛主席抓起一把泥土在手里捏了捏，对警卫员说：\u201c行，井位就定在这里，你去叫人来挖。\u201d于是毛主席叫挖井的事，立即传遍了全村，众人都自动的找着锄头铁锹，齐集到村头和毛主席一道挖井。毛主席一边和大家挖井，一边对乡亲们说：\u201c这几天忙，没工夫找大家商量打井的事，今天有半天空，我先替大家找个有水源的地方，定个位，破个土。我知道，你们信风水，怕得罪旱龙王，我不怕，如果旱龙王怪罪下来，让它来找我算账好了！\u201d逗得大家都笑了。大家说说笑笑，不到一天工夫，一口井便挖成了。从此，沙洲坝的乡亲喝上了井水，结束了祖祖辈辈挑塘水喝的历史。因为井是红军来了以后毛主席亲手挖的，所以乡亲们给这井起了个名字叫\u201c红井\u201d。<\/span><\/p><p><\/p><p><br/><\/p>","lat":"25.8797400000","lng":"116.0075400000"}
         * about_news : [{"title":"茅店镇樟树坝革命烈士纪念塔","image":"https://cache.buwangchuxin.com/images/7EFcJDP5yW.jpg"}]
         */

        private NewsInfoBean news_info;
        private List<AboutNewsBean> about_news;

        public NewsInfoBean getNews_info() {
            return news_info;
        }

        public void setNews_info(NewsInfoBean news_info) {
            this.news_info = news_info;
        }

        public List<AboutNewsBean> getAbout_news() {
            return about_news;
        }

        public void setAbout_news(List<AboutNewsBean> about_news) {
            this.about_news = about_news;
        }

        public static class NewsInfoBean {
            /**
             * id : 264
             * title : 红井
             * intro : 红井
             * image : https://cache.buwangchuxin.com/images/TmcP4E4EX2.jpg
             * vr_image :
             * vr_hot_image :
             * vr_path_path :
             * voice_path :
             * desc_voice_path : null
             * description : 红井这口井之所以称为红井，是苏区时期，毛泽东亲自带领干部群众一起开挖的，它是当时党和苏维埃政府密切联系群众、解决群众生活困难的历史见证。
             * content : <p><span style="font-family: 宋体, SimSun; font-size: 16px;">  一九三一年，瑞金成立了中华苏维埃中央政府。政府起初设在叶坪，后来因为白匪军狂轰滥炸，为了安全防空，从叶坪迁到沙洲坝。毛泽东主席便住在沙洲坝的村子里。</span></p><p><span style="font-family: 宋体, SimSun; font-size: 16px;">  有一天傍晚，毛主席办完公事从外面回来，一下马，看见乡亲们在塘里挑水，便问：“这水挑去做什么用？”乡亲们回答说：“吃呀！”毛主席说：“水这么脏，能吃吗？”乡亲们苦笑着说：“有什么办法，再脏也得吃啊！”毛主席说：“不会打井么？”乡亲们摇摇头说：“沙洲坝人喝不得井水，这是天命！”毛主席哈哈大笑说：“不要信天命，要信革命！还是打口井吧！”说罢，牵马进村去了。大家也就散了，谁也没有把这话记在心里。仍然每天到塘里挑水吃，毛主席也每天早出晚归忙他的公事。</span></p><p><span style="font-family: 宋体, SimSun; font-size: 16px;">  有一天，天刚朦朦亮，起来挑水的人，看见有两个人影在村头走来走去。一个拿着锄头，一个拿着锹，这里锄锄，那里铲铲。谁这么早起来干什么呢？走前一看，原来是毛主席和他的警卫员。挑水的乡亲问：“毛主席，你这是干什么？”毛主席说：“找水源、挖井。”说完，便和警卫员在一块长满油草地地方铲开地皮挖了起来。挖到约有两三尺深，毛主席抓起一把泥土在手里捏了捏，对警卫员说：“行，井位就定在这里，你去叫人来挖。”于是毛主席叫挖井的事，立即传遍了全村，众人都自动的找着锄头铁锹，齐集到村头和毛主席一道挖井。毛主席一边和大家挖井，一边对乡亲们说：“这几天忙，没工夫找大家商量打井的事，今天有半天空，我先替大家找个有水源的地方，定个位，破个土。我知道，你们信风水，怕得罪旱龙王，我不怕，如果旱龙王怪罪下来，让它来找我算账好了！”逗得大家都笑了。大家说说笑笑，不到一天工夫，一口井便挖成了。从此，沙洲坝的乡亲喝上了井水，结束了祖祖辈辈挑塘水喝的历史。因为井是红军来了以后毛主席亲手挖的，所以乡亲们给这井起了个名字叫“红井”。</span></p><p></p><p><br/></p>
             * lat : 25.8797400000
             * lng : 116.0075400000
             */

            private int id;
            private String title;
            private String intro;
            private String image;
            private String vr_image;
            private String vr_hot_image;
            private String vr_path_path;
            private String voice_path;
            private Object desc_voice_path;
            private String description;
            private String content;
            private String lat;
            private String lng;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getVr_image() {
                return vr_image;
            }

            public void setVr_image(String vr_image) {
                this.vr_image = vr_image;
            }

            public String getVr_hot_image() {
                return vr_hot_image;
            }

            public void setVr_hot_image(String vr_hot_image) {
                this.vr_hot_image = vr_hot_image;
            }

            public String getVr_path_path() {
                return vr_path_path;
            }

            public void setVr_path_path(String vr_path_path) {
                this.vr_path_path = vr_path_path;
            }

            public String getVoice_path() {
                return voice_path;
            }

            public void setVoice_path(String voice_path) {
                this.voice_path = voice_path;
            }

            public Object getDesc_voice_path() {
                return desc_voice_path;
            }

            public void setDesc_voice_path(Object desc_voice_path) {
                this.desc_voice_path = desc_voice_path;
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

        public static class AboutNewsBean {
            /**
             * title : 茅店镇樟树坝革命烈士纪念塔
             * image : https://cache.buwangchuxin.com/images/7EFcJDP5yW.jpg
             */

            private String title;
            private String image;

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
        }
    }
}
