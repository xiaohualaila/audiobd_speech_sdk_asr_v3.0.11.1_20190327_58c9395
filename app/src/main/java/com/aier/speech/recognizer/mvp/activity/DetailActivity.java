package com.aier.speech.recognizer.mvp.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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

    private static final Handler handler = new Handler();

    @Override
    protected void beforeInit() {
        super.beforeInit();
        StatusBarUtil.INSTANCE.setTranslucent(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String duty = bundle.getString("duty");
        String description = bundle.getString("description");
        String score = bundle.getString("score");
        String img = bundle.getString("img");

        ImageUtils.image(this, img, iv_photo);
        Typeface textFont = Typeface.createFromAsset(getAssets(), "fonts/RuiZiZhenYanTiMianFeiShangYong-2.ttf");
        tv_name.setTypeface(textFont);
        tv_score.setTypeface(textFont);

        tv_name.setText(name);
        name_1.setText(name);
        tv_work.setText(duty);
        tv_history.setText(description);

        tv_score.setText(score + "%");

        handler.postDelayed(runnable, 8000);
    }

    private Runnable runnable = () -> finish();

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
