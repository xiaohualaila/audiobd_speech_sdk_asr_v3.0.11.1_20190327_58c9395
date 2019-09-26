package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aier.speech.recognizer.R;

import butterknife.OnClick;

public class MapActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_map;
    }

    @OnClick({R.id.take_photo, R.id.iv_back,R.id.right_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                startActiviys(Camera2Activity.class);
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.right_btn://菜单
                startActiviys(MapActivity.class);
                finish();
                break;
        }
    }
}
