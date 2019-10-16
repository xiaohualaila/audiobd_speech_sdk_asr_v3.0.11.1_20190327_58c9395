package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.aier.speech.recognizer.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MenuActivity extends BaseActivity {
    @BindView(R.id.tip)
    TextView tip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tip.setText("热烈祝贺2019年赣州经开区“传承红色基因·牢记初心使命”赣南苏区红色故事演讲大赛圆满成功!");
        tip.setSelected(true);
    }

    @OnClick({R.id.take_photo, R.id.iv_answer_question, R.id.iv_back, R.id.iv_back_,
            R.id.iv_left_btn, R.id.tips_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_answer_question:
                startActiviys(AnswerQuestionActivity.class);
                finish();
                break;
            case R.id.iv_left_btn://初心地图
                startActiviys(MapActivity.class);
                finish();
                break;
            case R.id.tips_view://点击顶部消息
                startActiviys(NewsActivity.class);
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_menu;
    }
}
