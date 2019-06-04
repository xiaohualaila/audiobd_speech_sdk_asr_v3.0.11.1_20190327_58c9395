package com.baidu.aip.asrwakeup3;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;


public class MainActivity extends RobotSpeechActivity{
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this).load(R.drawable.xiaohui).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

    }

    public  void wakup(){
        startSpeech();
        Log.i("sss","唤醒");
    }
}
