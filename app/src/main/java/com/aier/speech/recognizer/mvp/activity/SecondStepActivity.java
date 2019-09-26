package com.aier.speech.recognizer.mvp.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.weight.ChooseView;

import java.io.File;
import java.io.IOException;
import butterknife.BindView;


public class SecondStepActivity extends BaseActivity implements SurfaceHolder.Callback,ChooseView.ChooseCallBack {
    private static String TAG = "SecondStepActivity";
    @BindView(R.id.sf_video)
    SurfaceView surfaceView;
    @BindView(R.id.choose_view)
    ChooseView choose_view;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder myholder;
    private int currentPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myholder = surfaceView.getHolder();
        myholder.addCallback(this);
        mMediaPlayer = new MediaPlayer();
        choose_view.setChooseCallBack(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_second_step;
    }


    public void back(View view) {
        finish();
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
        if(mMediaPlayer != null){
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void setCallBack(int num) {
        Log.i("sss","+++++++++num " +num);
    }
}
