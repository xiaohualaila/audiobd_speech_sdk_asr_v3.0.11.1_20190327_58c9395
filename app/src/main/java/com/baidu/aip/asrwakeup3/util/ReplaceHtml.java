package com.baidu.aip.asrwakeup3.util;

public class ReplaceHtml {
    //方法一
    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        // 去掉空格
        content = content.replaceAll(" ", "");

        return content;
    }

    //方法二
    public static String delHtmlTag(String str){
        String newstr = "";
        newstr = str.replaceAll("<[.[^>]]*>","");
        newstr = newstr.replaceAll(" ", "");
        return newstr;
    }
}
