package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import com.aier.speech.recognizer.mvp.presenter.StartPresenter;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class StartActivity extends BaseActivity implements StartContract.View, SurfaceHolder.Callback {
    private static String TAG = "StartActivity";
    @BindView(R.id.sf_video)
    SurfaceView surfaceView;
    @BindView(R.id.btn_fruit_1)
    TextView btn_fruit_1;
    @BindView(R.id.btn_fruit_2)
    TextView btn_fruit_2;
    @BindView(R.id.btn_fruit_3)
    TextView btn_fruit_3;
    @BindView(R.id.btn_fruit_4)
    TextView btn_fruit_4;
    private StartPresenter presenter;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder myholder;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StartPresenter(this);
        presenter.getTime();
        presenter.getWeather();
        // startDelay();
        myholder = surfaceView.getHolder();
        myholder.addCallback(this);
        mMediaPlayer = new MediaPlayer();
        AssetManager assets = getAssets();
        Typeface tf = Typeface.createFromAsset(assets, "fonts/MStiffHeiPRC.ttf");
        btn_fruit_1.setTypeface(tf);
        btn_fruit_2.setTypeface(tf);
        btn_fruit_3.setTypeface(tf);
        btn_fruit_4.setTypeface(tf);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }


    //第一步
    public void stepFirstBtn(View view) {
        startActivity(new Intent(this, ChooseActivity.class));
    }

    //第二步
    public void stepSecondBtn(View view) {

    }
//    public void returnRedTime(View view) {
//        if(openCVIsOk){
//            startActivity(new Intent(this, CameraActivity.class));
//        }else {
//            ToastyUtil.INSTANCE.showError("openCV初始化失败！，请重启软件。");
//        }
//
//    }

    @OnClick({R.id.btn_fruit_1, R.id.btn_fruit_2, R.id.btn_fruit_3, R.id.btn_fruit_4,
            R.id.iv_step_first,R.id.iv_step_second})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fruit_1:
               startActiviys(BuyFruitActivity.class,1);
                break;
            case R.id.btn_fruit_2:
                startActiviys(BuyFruitActivity.class,2);
                break;
            case R.id.btn_fruit_3:
                startActiviys(BuyFruitActivity.class,3);
                break;
            case R.id.btn_fruit_4:
                startActiviys(BuyFruitActivity.class,4);
                break;
            case R.id.iv_step_second:
                startActivity(new Intent(this, SecondStepActivity.class));
                break;
            case R.id.iv_step_first:
                startActivity(new Intent(this, ChooseActivity.class));
                break;
        }
    }


    @Override
    public void backTime(String time, String date) {
//        tv_time.setText(time);
//        tv_date.setText(date);
    }

    @Override
    public void getWeatherDataSuccess(String weather, String wendu) {
//        tv_weather.setText(weather);
//        tv_wendu.setText(wendu);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer.setDisplay(myholder);    //设置显示视频显示在SurfaceView上
        play(currentPosition);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            currentPosition = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.stop();
        }
    }


    private void play(int msec) {
        File file = new File(Environment.getExternalStorageDirectory()
                + "/Download/", "test.mp4");

        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(file.getPath());
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(mp -> {
                mMediaPlayer.start();
                mMediaPlayer.seekTo(msec);
            });
            mMediaPlayer.setOnCompletionListener(mp -> {
                mMediaPlayer.start();
                mp.setLooping(true);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
