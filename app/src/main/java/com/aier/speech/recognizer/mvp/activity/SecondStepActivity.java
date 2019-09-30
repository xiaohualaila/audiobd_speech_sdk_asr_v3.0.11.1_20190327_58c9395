package com.aier.speech.recognizer.mvp.activity;

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
import com.aier.speech.recognizer.weight.ChooseView;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;


public class SecondStepActivity extends BaseActivity implements SurfaceHolder.Callback, ChooseView.ChooseCallBack {
    private static String TAG = "SecondStepActivity";
    @BindView(R.id.sf_video)
    SurfaceView surfaceView;
    @BindView(R.id.choose_view)
    ChooseView choose_view;
    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.text)
    TextView text;

    private MediaPlayer mMediaPlayer;
    private SurfaceHolder myholder;
    private int currentPosition = 0;

    private int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myholder = surfaceView.getHolder();
        myholder.addCallback(this);
        mMediaPlayer = new MediaPlayer();
        choose_view.setChooseCallBack(this);
        AssetManager assets = getAssets();
        Typeface tf = Typeface.createFromAsset(assets, "fonts/MStiffHeiPRC.ttf");
        text_title.setTypeface(tf);
        text.setTypeface(tf);
        setTextContent();
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
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            currentPosition = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.stop();
        }
    }

    private void play(int msec) {
        try {
            File file;
            if (index == 1) {
                choose_view.setContine(index);
                file = new File(Environment.getExternalStorageDirectory() + "/Download/", "产线1.mp4");
            }else if(index == 2){
                choose_view.setContine(index);
                file = new File(Environment.getExternalStorageDirectory() + "/Download/", "产线2.mp4");
            }else {
                choose_view.setContine(index);
                file = new File(Environment.getExternalStorageDirectory() + "/Download/", "产线3.mp4");
            }


            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(file.getPath());
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(mp -> {
                mMediaPlayer.start();
                mMediaPlayer.seekTo(msec);
            });
            mMediaPlayer.setOnCompletionListener(mp -> {
//                mMediaPlayer.start();
//                mp.setLooping(true);
                index++;
                if(index>3){
                    index=1;
                }
                play(0);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void setCallBack(int num) {
        if(index!=num){
            index = num;
        }else{
            return;
        }
        index = num;
        play(0);
//        Log.i("sss", "+++++++++num " + num);
        if (num == 1) {
            setTextContent();
        } else if (num == 2) {
            text_title.setText("视觉分拣");
            text.setText("通过AI深度的视觉监测，对桃子的着色度，大小，破损情况，虫蛀情况及含糖量等进行检测。与下单系统联动，分选至不同对的下果平台和包装路线。");
        } else if (num == 3) {
            text_title.setText("智能码垛说明");
            text.setText("自动封箱机封箱完成后，码垛机器人统一码放，由AGV叉车转运至水果排放区 ，智联下单系统和物流系统，运送至顾客之手。");
        }
    }

    public void setTextContent() {
        text_title.setText("无人搬运");
        text.setText("AVG小车将成像的桃子从储桃区运送到物料传送带上，再由机器自动翻箱倾倒在自动上果机上，自动上果机将桃子输送到等级分选机中，调整桃子姿态，到达检测区");
    }
}
