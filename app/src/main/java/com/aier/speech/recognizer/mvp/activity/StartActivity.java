package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import com.aier.speech.recognizer.mvp.presenter.StartPresenter;
import java.io.File;
import java.io.IOException;
import butterknife.BindView;

public class StartActivity extends BaseActivity implements StartContract.View, SurfaceHolder.Callback {
    private static String TAG = "StartActivity";
    @BindView(R.id.sf_video)
    SurfaceView surfaceView;
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
    }



    private void startDelay() {
        new Handler().postDelayed(() -> {
            startActiviys(MainActivity.class);
            finish();
        }, 2000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }

    public void openIv1(View view) {
       // startActivity(new Intent(this, .class));
    }
    public void openIv2(View view) {
     //   startActivity(new Intent(this, .class));
    }
    public void openIv3(View view) {
      //  startActivity(new Intent(this, .class));
    }
    public void openIv4(View view) {
     //   startActivity(new Intent(this, .class));
    }
    public void openIv5(View view) {
      //  startActivity(new Intent(this, .class));
    }
    public void openIv6(View view) {
       // startActivity(new Intent(this, .class));
    }
//    public void returnRedTime(View view) {
//        if(openCVIsOk){
//            startActivity(new Intent(this, CameraActivity.class));
//        }else {
//            ToastyUtil.INSTANCE.showError("openCV初始化失败！，请重启软件。");
//        }
//
//    }


    @Override
    public void backTime(String time,String date) {
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
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            currentPosition = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.stop();
        }
    }



    private void play(int msec){
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
        if(mMediaPlayer != null){
            finish();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
