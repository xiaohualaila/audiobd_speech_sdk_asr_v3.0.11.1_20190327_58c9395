package com.aier.speech.recognizer.recog;


import com.aier.speech.recognizer.R;

/**
 * 展示语义功能
 * 本类可以忽略
 */

public class ActivityNlu extends ActivityAbstractRecog {


    public ActivityNlu() {
        super(R.raw.nlu_recog, true);
        // uiasr\src\main\res\raw\nlu_recog.txt 本Activity使用的说明文件
        // true 表示activity支持离线
    }


}
