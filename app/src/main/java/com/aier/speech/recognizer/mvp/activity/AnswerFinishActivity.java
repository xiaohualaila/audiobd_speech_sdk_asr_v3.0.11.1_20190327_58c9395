package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.mvp.contract.AnswerFinishContract;
import com.aier.speech.recognizer.mvp.presenter.AnswerFinishPresenter;
import com.aier.speech.recognizer.util.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerFinishActivity extends BaseActivity implements AnswerFinishContract.View{

    private int score = 0;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.tip)
    TextView tip;
    private AnswerFinishPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new AnswerFinishPresenter(this);
        score = getIntent().getIntExtra("score",0);

        if(score==100){
            tv_tip.setText("智慧之神我就是智慧与美貌的化身～");
        }else if(score>60){
            tv_tip.setText("聪明机智，速度过人");
        }else {
            tv_tip.setText("离胜利还差一丢丢~");
        }
        tv_score.setText(score+"");
       String uniqid = SharedPreferencesUtil.getString(this,"uniqid","");
       if(!TextUtils.isEmpty(uniqid)){
           presenter.upLoadScore(uniqid,score+"");
       }
    }

    @OnClick({R.id.take_photo, R.id.back_first,R.id.conti_,R.id.iv_left_btn,R.id.tips_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.conti_:
                startActiviys(AnswerQuestionActivity.class);
                finish();
                break;
            case R.id.iv_left_btn://初心地图
                startActiviys(MapActivity.class);
                finish();
                break;
            case R.id.iv_right_btn://菜单
                startActiviys(MenuActivity.class);
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
        return R.layout.activity_finish_answer;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void getDataFail() {
        SharedPreferencesUtil.putString(this,"uniqid","");
    }

    @Override
    public void getDataSuccess() {
        SharedPreferencesUtil.putString(this,"uniqid","");
    }
}
