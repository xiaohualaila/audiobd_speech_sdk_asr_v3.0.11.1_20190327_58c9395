package com.baidu.aip.asrwakeup3.mvp.activity;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.baidu.aip.asrwakeup3.MyApplication;
import com.baidu.aip.asrwakeup3.R;
import com.baidu.aip.asrwakeup3.bean.YUBAIBean;
import com.baidu.aip.asrwakeup3.mvp.contract.MainContract;
import com.baidu.aip.asrwakeup3.mvp.presenter.MainPresenter;
import com.baidu.aip.asrwakeup3.network.schedulers.SchedulerProvider;
import com.baidu.aip.asrwakeup3.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.io.IOException;
import butterknife.BindView;


public class MainActivity extends RobotSpeechActivity implements  MainContract.View  {
    private static final String TAG ="MainActivity" ;
    @BindView(R.id.eye)
    ImageView eye;
    @BindView(R.id.mouth)
    ImageView mouth;
    @BindView(R.id.img)
    ImageView img;
    private MainPresenter presenter;
    private MediaPlayer mediaPlayer;
    private boolean isShowImage = false;
    private AudioManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this, SchedulerProvider.getInstance());
        Glide.with(this).load(R.drawable.eye).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(eye);
        Glide.with(this).load(R.drawable.mouth).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mouth);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
    }

    public void wakup(){
        Log.i(TAG,"唤醒");
        wakeUpAndUnlock();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        stopSpeech();//停止语音识别
        stop();//停止语音合成说话
        speak("在");
//        cancelSpeech();//取消语音识别
        startSpeech();
    }

    protected void backMsg(String msg){
        Log.i(TAG,"msg ---->     "+msg);
        if(msg.equals("增大音量")||msg.equals("增加声音")||msg.equals("声音变大")||msg.equals("增加音量")){
            am.adjustStreamVolume (AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);//增大
            return;
        } else if(msg.equals("减小音量")||msg.equals("减小声音")||msg.equals("声音变小")){
            am.adjustStreamVolume (AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);//增大
            return;
        }

        toastLong(msg);
        presenter.getYubaiData(msg);
    }

    @Override
    public void getDataSuccess(YUBAIBean bean) {
        Log.i(TAG,"羽白结果---->  "+bean.toString());
        String result = bean.getResult();
        String text;
        try {
            int index =result.indexOf("你还可以问我");
            text = result.substring(0,index);
            Log.i(TAG,"羽白结果----> text  "+text);
        }catch (Exception e){
            text = result;
        }

        Log.i(TAG,"羽白结果----> text  "+text);
        String voice = bean.getVoice();
        if (TextUtils.isEmpty(voice)) {
            speak(text);
            Glide.with(this).load(R.drawable.eye_speak).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(eye);
            Glide.with(this).load(R.drawable.mouth_speak).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mouth);
        } else {
            playVoice(voice);
        }
        String image_url = bean.getImagereply();
        if (!TextUtils.isEmpty(image_url)) {
            img.setVisibility(View.VISIBLE);
            ImageUtils.image(mContext,image_url,img);
            isShowImage = true;
        }
    }

    //语音合成播放完成
    protected void ttsFinish(){
        if(isShowImage){
            img.setVisibility(View.GONE);
            isShowImage =false;
        }
        Glide.with(this).load(R.drawable.eye).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(eye);
        Glide.with(this).load(R.drawable.mouth).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mouth);
    }

    @Override
    public void getDataFail() {
        toastShort("请检查网络！");
    }

    /**
     * 唤醒手机屏幕并解锁
     */
    public static void wakeUpAndUnlock() {
         // 获取电源管理器对象
        PowerManager pm = (PowerManager) MyApplication.getAppContext() .getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
           @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock( PowerManager.ACQUIRE_CAUSES_WAKEUP |
           PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
           wl.acquire(10000);
           // 点亮屏幕
             wl.release(); // 释放
            }
        // 屏幕解锁
         KeyguardManager keyguardManager = (KeyguardManager) MyApplication.getAppContext()
                 .getSystemService(KEYGUARD_SERVICE);
         KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
         // 屏幕锁定
         keyguardLock.reenableKeyguard();
         keyguardLock.disableKeyguard(); // 解锁
    }

    //播放语音
    private void playVoice(String voice) {
        try {
            mediaPlayer.setDataSource(voice);
            // 通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                // 装载完毕 开始播放流媒体
                mediaPlayer.start();
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        presenter.despose();
    }
}
