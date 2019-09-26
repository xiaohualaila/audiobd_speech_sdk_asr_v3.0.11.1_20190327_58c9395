package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.aier.speech.recognizer.R;

public class ChooseActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_choose;
    }


    public void openVR(View view) {
           startActivity(new Intent(this, WebActivity.class));
    }

    //关闭界面
    public void back(View view) {
        finish();
    }
}
