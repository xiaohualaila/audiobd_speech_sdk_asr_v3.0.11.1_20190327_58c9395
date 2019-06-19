package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.os.Handler;
import com.aier.speech.recognizer.R;

public class StartActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startDelay();
    }

    private void startDelay() {
        new Handler().postDelayed(() -> {
            startActiviys(MainActivity.class);
            finish();
        }, 5000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }
}
