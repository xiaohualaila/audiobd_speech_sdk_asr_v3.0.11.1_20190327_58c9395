package com.aier.speech.recognizer.bean;

import java.util.List;

public class YUBAIBean {

    /**
     * city : []
     * errmsg :
     * imagereply : https://www.izhenqi.cn/shuiyin/img/city/杭州.jpg?t=2019060310
     * ip : 218.75.124.2
     * ipcity : 杭州
     * keyword : []
     * label : 今日天气
     * number : []
     * query : 今天天气怎么样
     * result : 杭州今日天气: 多云，21~31℃，无持续风向，风力小于3级，当前温度为27℃，空气质量等级为良, 可以正常进行室外活动。

     你还可以问我：
     --------------
     1: 廊坊人均GDP
     2: 沈阳GDP
     3: 北京历史名人
     * succeed : true
     * time : 2019-06-03 10:43:13
     * timeused : 21
     * type : text
     * usertype : YUBAI
     */

    private String errmsg;
    private String imagereply;
    private String ip;
    private String ipcity;
    private String label;
    private String query;
    private String result;
    private boolean succeed;
    private String time;
    private int timeused;
    private String type;
    private String usertype;
    private List<?> city;
    private List<?> keyword;
    private List<?> number;
    private String voice;
    private String url;
    private String weburl;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getImagereply() {
        return imagereply;
    }

    public void setImagereply(String imagereply) {
        this.imagereply = imagereply;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpcity() {
        return ipcity;
    }

    public void setIpcity(String ipcity) {
        this.ipcity = ipcity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTimeused() {
        return timeused;
    }

    public void setTimeused(int timeused) {
        this.timeused = timeused;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public List<?> getCity() {
        return city;
    }

    public void setCity(List<?> city) {
        this.city = city;
    }

    public List<?> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<?> keyword) {
        this.keyword = keyword;
    }

    public List<?> getNumber() {
        return number;
    }

    public void setNumber(List<?> number) {
        this.number = number;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    @Override
    public String toString() {
        return "YUBAIBean{" +
                "errmsg='" + errmsg + '\'' +
                ", imagereply='" + imagereply + '\'' +
                ", ip='" + ip + '\'' +
                ", ipcity='" + ipcity + '\'' +
                ", label='" + label + '\'' +
                ", query='" + query + '\'' +
                ", result='" + result + '\'' +
                ", succeed=" + succeed +
                ", time='" + time + '\'' +
                ", timeused=" + timeused +
                ", type='" + type + '\'' +
                ", usertype='" + usertype + '\'' +
                ", city=" + city +
                ", keyword=" + keyword +
                ", number=" + number +
                ", voice='" + voice + '\'' +
                ", url='" + url + '\'' +
                ", weburl='" + weburl + '\'' +
                '}';
    }
}
