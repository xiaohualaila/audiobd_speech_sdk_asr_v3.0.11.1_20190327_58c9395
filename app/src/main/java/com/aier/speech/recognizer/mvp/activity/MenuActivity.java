package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.aier.speech.recognizer.R;
import butterknife.OnClick;

public class MenuActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @OnClick({R.id.take_photo, R.id.iv_answer_question, R.id.iv_back, R.id.iv_back_,R.id.iv_left_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                finish();
                break;
            case R.id.iv_answer_question:
                startActiviys(AnswerQuestionActivity.class);
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_back_:
                finish();
                break;
            case R.id.iv_left_btn://初心地图
                startActiviys(MapActivity.class);
                finish();
                break;


        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_menu;
    }
}
