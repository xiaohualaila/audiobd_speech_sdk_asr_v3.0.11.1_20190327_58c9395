package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.view.View;

import com.aier.speech.recognizer.R;



public class SecondStepActivity extends BaseActivity {
    private static String TAG = "SecondStepActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_second_step;
    }


    public void back(View view) {
        finish();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
