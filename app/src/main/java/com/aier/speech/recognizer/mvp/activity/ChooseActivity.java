package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.weight.TipGrayView;
import com.aier.speech.recognizer.weight.TipRedView;
import com.aier.speech.recognizer.weight.TipView;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseActivity extends BaseActivity {

    @BindView(R.id.tip_1)
    TipGrayView tip_1;
    @BindView(R.id.tip_2)
    TipView tip_2;
    @BindView(R.id.tip_3)
    TipView tip_3;
    @BindView(R.id.tip_4)
    TipGrayView tip_4;
    @BindView(R.id.tip_5)
    TipRedView tip_5;
    @BindView(R.id.tip_6)
    TipRedView tip_6;
    @BindView(R.id.tip_7)
    TipRedView tip_7;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tip_1.setTipText("来点蜜桃桃园");
        tip_2.setTipText("银银蜜桃桃园");
        tip_3.setTipText("吴家采摘桃园");
        tip_4.setTipText("来点蜜桃桃园");
        tip_5.setTipText("张连会桃园");
        tip_6.setTipText("桃都会桃园");
        tip_7.setTipText("桃丰密桃园");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_choose;
    }


    //关闭界面
    public void back(View view) {
        finish();
    }


    @OnClick({R.id.tip_5,R.id.tip_6,R.id.tip_7})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tip_5:
                startActivity(new Intent(this, WebActivity.class));
                break;
            case R.id.tip_6:
                startActivity(new Intent(this, WebActivity.class));
                break;
            case R.id.tip_7:
                startActivity(new Intent(this, WebActivity.class));
                break;
        }
    }

}
