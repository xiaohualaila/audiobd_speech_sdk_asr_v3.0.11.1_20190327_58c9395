package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.util.ImageUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends RobotSpeechActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_photo)
    ImageView iv_photo;
    @BindView(R.id.name)
    TextView tv_name;
    @BindView(R.id.tv_work)
    TextView tv_work;
    @BindView(R.id.tv_history)
    TextView tv_history;
    @BindView(R.id.tv_score)
    TextView tv_score;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title.setText("重回红军时代");
        tv_title.setTextColor(getResources().getColor(R.color.black));
        Bundle bundle = getIntent().getExtras();
        String name =  bundle.getString("name");
        String duty =  bundle.getString("duty");
        String description =  bundle.getString("description");
        String  score =  bundle.getString("score");
        String img =  bundle.getString("img");
        speak("您回到红军时代是" + name + "相似度" + score + "%" + duty);
        ImageUtils.image(this,img,iv_photo);
        tv_name.setText(name);
        tv_work.setText(duty);
        tv_history.setText("   "+description);
        tv_score.setText(score);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }


    @OnClick({R.id.iv_back,R.id.iv_back_})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_back_:
                finish();
                break;
        }
    }
}
