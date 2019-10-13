package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.model.MessageWrap;
import com.aier.speech.recognizer.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class MenuActivity extends BaseActivity {
    @BindView(R.id.tip)
    TextView tip;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().unregister(this);
        String msg = SharedPreferencesUtil.getString(this,"tips","");
        tip.setText(msg);
    }

    @OnClick({R.id.take_photo, R.id.iv_answer_question, R.id.iv_back, R.id.iv_back_,
            R.id.iv_left_btn, R.id.jj_icon})
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
            case R.id.jj_icon://简介
                startActiviys(IntroductionActivity.class);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        Log.i("zzz", " -->"+message.message );
        tip.setText(message.message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
