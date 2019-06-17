package com.baidu.aip.asrwakeup3.mvp.activity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.aip.asrwakeup3.R;
import com.baidu.aip.asrwakeup3.bean.YUBAIBean;
import com.baidu.aip.asrwakeup3.model.MessageWrap;
import com.baidu.aip.asrwakeup3.mvp.contract.MainContract;
import com.baidu.aip.asrwakeup3.mvp.model.MainModel;
import com.baidu.aip.asrwakeup3.mvp.presenter.MainPresenter;
import com.baidu.aip.asrwakeup3.network.schedulers.SchedulerProvider;
import com.baidu.aip.asrwakeup3.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainActivity extends RobotSpeechActivity implements MainContract.View {
    private static final String TAG = "MainActivity";
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.iv_expression)
    ImageView iv_expression;
    @BindView(R.id.tip)
    TextView tip;
    private MainPresenter presenter;
    private MediaPlayer mediaPlayer;
    private boolean isShowImage = false;
    private AudioManager am;
    private boolean isCheckFace = false;
    private Long updateTime;
    private boolean isWakeUp = false;
    private boolean isWebVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        presenter = new MainPresenter(new MainModel(), this, SchedulerProvider.getInstance());
        Glide.with(mContext).load(R.drawable.wait).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_expression);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        heartinterval();

    }

    public void wakup() {
        Log.i(TAG, "唤醒");
        speak("在");
        isWakeUp = true;
        startSpeech();
        Glide.with(mContext).load(R.drawable.listen2).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_expression);//语音识别表情
    }

    /**
     * 发送心跳数据
     */
    private void heartinterval() {
        updateTime = System.currentTimeMillis();
        Observable.interval(20, 20, TimeUnit.SECONDS)//20秒
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (isWakeUp) {
                        if (System.currentTimeMillis()-updateTime > 20000) {
                            cancelSpeech();//取消语音识别
                            isWakeUp = false;
                            Log.i(TAG, "diff ---->   取消语音识别  ");
                            stopYuBai();
                            Glide.with(mContext).load(R.drawable.wait).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_expression);
                        }
                    }
                });
    }

    protected void speechStart() {
        updateTime = System.currentTimeMillis();
        Log.i(TAG, "msg ---->   speechStart  ");
    }

    protected void speechBackMsg(String msg) {
        updateTime = System.currentTimeMillis();
        stopTTS();
        stopYuBai();
        stopMediaPlay();
        Log.i(TAG, "msg ---->     " + msg);
        if (msg.equals("增大音量") || msg.equals("增加声音") || msg.equals("声音变大") || msg.equals("增加音量") || msg.equals("调高音量")|| msg.equals("提高音量")) {
            am.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);//增大
            return;
        } else if (msg.equals("减小音量") || msg.equals("减小声音") || msg.equals("声音变小") || msg.equals("调低音量")) {
            am.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);//增小
            return;
        } else if (msg.equals("打开相机") || msg.equals("人脸识别") || msg.equals("打开摄像头")) {
            if (isCheckFace) {
                startActiviys(CameraActivity.class);
            } else {
                speak("无法进入拍照界面");
            }
            return;
        } else if (msg.equals("停止") || msg.equals("羽白停止")|| msg.equals("暂停")) {
            cancelSpeech();
            isWakeUp = false;
            Log.i(TAG, "diff ---->   取消语音识别  ");
            Glide.with(mContext).load(R.drawable.wait).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_expression);
            return;
        }
        presenter.getYubaiData(msg);
        Log.i(TAG, "msg ---->   speechBackMsg  ");
    }

    /**
     * 临时识别结果
     */
    protected void speechTemporary(String msg){
        tip.setText(msg);
    }


    /**
     * 语音识别结束
     */
    protected void speechFinish() {
        updateTime = System.currentTimeMillis();
        tip.setText("");
        Log.i(TAG, "msg ---->   speechFinish  ");
    }

    /**
     * 语音合成播放完成
     */
    protected void ttsFinish() {
        updateTime = System.currentTimeMillis();
        if(isWakeUp){
            Glide.with(mContext).load(R.drawable.listen2).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_expression);
        }else {
            Glide.with(mContext).load(R.drawable.wait).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_expression);
        }
    }

    private void stopYuBai() {
        if (isWebVisible) {
            web_view.setVisibility(View.GONE);
            isWebVisible = false;
        }
        if (isShowImage) {
            img.setVisibility(View.GONE);
            isShowImage = false;
        }
    }

    //停止播放音乐
    private void stopMediaPlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    @Override
    public void getDataSuccess(YUBAIBean bean) {
        Log.i(TAG, "羽白结果---->  " + bean.toString());
        String result = bean.getResult();
        String label = bean.getLabel();
        String text;
        if (label.equals("新闻资讯")) {
            String url = bean.getUrl();
            if (!TextUtils.isEmpty(url)) {
                web_view.setVisibility(View.VISIBLE);
                isWebVisible = true;
                web_view.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                });
                web_view.loadUrl(url);
            }

            try {
                int index = result.indexOf("-----------");
                text = result.substring(0, index);
                Log.i(TAG, "羽白结果----> text  " + text);
            } catch (Exception e) {
                text = result;
            }

        } else {
            try {
                int index = result.indexOf("你还可以问我");
                text = result.substring(0, index);
                Log.i(TAG, "羽白结果----> text  " + text);
            } catch (Exception e) {
                text = result;
            }
        }

        Log.i(TAG, "羽白结果----> text  " + text);
        String voice = bean.getVoice();
        if (TextUtils.isEmpty(voice)) {
            speak(text);
             Glide.with(mContext).load(R.drawable.speak).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_expression);//说话
        } else {
            playVoice(voice);
        }
        String image_url = bean.getImagereply();
        if (!TextUtils.isEmpty(image_url)) {
            img.setVisibility(View.VISIBLE);
            ImageUtils.image(mContext, image_url, img);
            isShowImage = true;
        }
    }

    @Override
    public void getDataFail() {
        toastShort("请检查网络！");
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

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(TAG, "OpenCV loaded successfully");
                    isCheckFace = true;
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        speak(message.message);
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
        EventBus.getDefault().unregister(this);
    }
}
