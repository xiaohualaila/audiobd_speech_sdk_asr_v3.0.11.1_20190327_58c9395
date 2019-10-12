package com.aier.speech.recognizer.mvp.activity;

import android.view.View;

import com.aier.speech.recognizer.R;

import butterknife.OnClick;

public class IntroductionActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.activity_introduction;
    }


    @OnClick({R.id.iv_back, R.id.iv_back_, R.id.take_photo, R.id.iv_left_btn, R.id.iv_right_btn, R.id.iv_answer_question})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left_btn://初心地图
                startActiviys(MapActivity.class);
                finish();
                break;
            case R.id.iv_right_btn://菜单
                startActiviys(MenuActivity.class);
                finish();
                break;
            case R.id.iv_answer_question:
                startActiviys(AnswerQuestionActivity.class);
                finish();
                break;
            default:
                finish();
                break;
        }
    }
}
