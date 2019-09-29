package com.aier.speech.recognizer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.aier.speech.recognizer.R;


public class DialogSubmit extends Dialog {
    ImageView zhifu_dialog;

    private DoClick doClick;

    public void setDoClick(DoClick doClick) {
        this.doClick = doClick;
    }

    public DialogSubmit(Context context) {
        super(context, R.style.dim_dialog);
        zhifu_dialog = findViewById(R.id.zhifu_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //为了锁定app界面的东西是来自哪个xml文件
        setContentView(R.layout.dialog_submit);


        //找到组件
        zhifu_dialog = findViewById(R.id.zhifu_dialog);
        //为两个按钮添加点击事件
        zhifu_dialog.setOnClickListener(v -> doClick.todo());
    }


    public interface DoClick {
        void todo();
    }
}
