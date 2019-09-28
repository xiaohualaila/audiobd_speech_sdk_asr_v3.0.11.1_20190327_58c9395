package com.aier.speech.recognizer.mvp.activity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.aier.speech.recognizer.R;

import butterknife.BindView;

public class AddrActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AssetManager assets = getAssets();
        Typeface tf = Typeface.createFromAsset(assets, "fonts/MStiffHeiPRC.ttf");
//        tv_total.setTypeface(tf);
//        tv_num.setTypeface(tf);
//        tv_tiandu.setTypeface(tf);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_addr;
    }
}
