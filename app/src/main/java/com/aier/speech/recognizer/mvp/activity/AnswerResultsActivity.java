package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import butterknife.BindView;
import butterknife.OnClick;

public class AnswerResultsActivity extends BaseActivity {

    private int score = 0;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        score = getIntent().getIntExtra("score",0);
        if(score==100){
            tv_tip.setText("智慧之神我就是智慧与美貌的化身～");
        }else if(score>60){
            tv_tip.setText("聪明机智，速度过人");
        }else {
            tv_tip.setText("离胜利还差一丢丢~");
        }
        tv_score.setText(score+"");

    }




    @OnClick({R.id.take_photo, R.id.back_first,R.id.conti_,R.id.iv_left_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                finish();
                break;
            case R.id.back_first:
                finish();
                break;
            case R.id.conti_:
                startActiviys(AnswerQuestionActivity.class);
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
        return R.layout.activity_finish_answer;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
