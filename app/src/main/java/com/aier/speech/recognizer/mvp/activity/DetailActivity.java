package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.util.ImageUtils;
import com.aier.speech.recognizer.util.StatusBarUtil;
import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity {
    @BindView(R.id.iv_photo)
    ImageView iv_photo;

    @BindView(R.id.name)
    TextView tv_name;
    @BindView(R.id.name_1)
    TextView name_1;
    @BindView(R.id.tv_work)
    TextView tv_work;
    @BindView(R.id.tv_history)
    TextView tv_history;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @Override
    protected void beforeInit() {
        super.beforeInit();
        StatusBarUtil.INSTANCE.setTranslucent(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String my_name =  bundle.getString("my_name");
        String name =  bundle.getString("name");
        String duty =  bundle.getString("duty");
        String description =  bundle.getString("description");
        String  score =  bundle.getString("score");
        String img =  bundle.getString("img");

        ImageUtils.image(this,img,iv_photo);
        tv_name.setText(my_name);
        name_1.setText(name);
        tv_work.setText(duty);
        tv_history.setText(description);
        tv_score.setText(score+"%");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }


    @OnClick({R.id.tv_play_again})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_play_again:
                finish();
                break;
        }

    }
}
