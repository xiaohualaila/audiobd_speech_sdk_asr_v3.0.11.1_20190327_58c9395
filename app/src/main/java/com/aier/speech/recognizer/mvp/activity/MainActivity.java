package com.aier.speech.recognizer.mvp.activity;

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

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.model.NetState;
import com.aier.speech.recognizer.mvp.contract.MainContract;
import com.aier.speech.recognizer.mvp.model.MainModel;
import com.aier.speech.recognizer.network.schedulers.SchedulerProvider;

import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.model.MessageWrap;
import com.aier.speech.recognizer.mvp.presenter.MainPresenter;
import com.aier.speech.recognizer.util.ImageUtils;
import com.aier.speech.recognizer.util.ReplaceHtml;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import java.io.IOException;
import butterknife.BindView;
import pl.droidsonroids.gif.GifImageView;


public class MainActivity extends RobotSpeechActivity implements MainContract.View {
    private static final String TAG = "MainActivity";
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.net_state)
    TextView net_state;
    @BindView(R.id.iv_expression)
            GifImageView iv_expression;
    @BindView(R.id.iv_expression2)
            GifImageView iv_expression2;
    @BindView(R.id.voice)
    GifImageView voice;
    @BindView(R.id.tv_marquee)
    TextView tv_marquee;
    private MainPresenter presenter;
    private MediaPlayer mediaPlayer;

    private AudioManager am;
    private boolean isCheckFace = false;
    private boolean isWebVisible = true;
    private boolean isShowImage = false;
    private Long updateTime;
    private boolean isNetConnection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        presenter = new MainPresenter(new MainModel(), this, SchedulerProvider.getInstance());

        iv_expression.setVisibility(View.VISIBLE);
        iv_expression2.setVisibility(View.GONE);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void wakup() {
        Log.i(TAG, "唤醒");
        stopTTS();
        stopYuBai();
        stopMediaPlay();
        speak("在");
        startSpeech();
    }

    protected void speechStart() {
        Log.i(TAG, "msg ---->   speechStart  ");
        voice.setVisibility(View.VISIBLE);
    }

    protected void speechBackMsg(String msg) {
        Log.i(TAG, "msg ---->     " + msg);
        if (msg.equals("增大音量") || msg.equals("增加声音") || msg.equals("声音变大") || msg.equals("增加音量") || msg.equals("调高音量") || msg.equals("提高音量")) {
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
        }
        toastLong(msg);
        if(isNetConnection){
            presenter.getYubaiData(msg);
        }else {
            toastLong("网络无法连接！");
        }

     //   Log.i(TAG, "msg ---->   speechBackMsg  ");
    }

    protected void speechTemporary(String msg){
        tip.setText(msg);
    }

    /**
     * 语音识别结束
     */
    protected void speechFinish() {
        voice.setVisibility(View.GONE);
        tip.setText("");
    //    Log.i(TAG, "msg ---->   speechFinish  ");
    }

    /**
     * 语音合成播放完成
     */
    protected void ttsFinish() {
        if (isWebVisible || isShowImage) {
            handler.postDelayed(() -> {
                if (System.currentTimeMillis() - updateTime > 10000) {
                    stopYuBai();
                }
            }, 10000);
        }
        tv_marquee.setVisibility(View.GONE);
        iv_expression.setVisibility(View.VISIBLE);
        iv_expression2.setVisibility(View.GONE);
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
      //  Log.i("xxxx", "label---->  " + label);
        String text;
        if (label.equals("新闻资讯")) {
            String url = bean.getUrl();
            if (!TextUtils.isEmpty(url)) {
                web_view.setVisibility(View.VISIBLE);
                isWebVisible = true;
                updateTime = System.currentTimeMillis();
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
                String resultNotHtml = ReplaceHtml.delHtmlTag(result);
                int indexLast = resultNotHtml.indexOf("你还可以问我");
                text = resultNotHtml.substring(0, indexLast);
            } catch (Exception e) {
                text = result;
            }
        } else {
            try {
                int index = result.indexOf("你还可以问我");
                text = result.substring(0, index);
            } catch (Exception e) {
                text = result;
            }
            tv_marquee.setText(text);
            tv_marquee.setSelected(true);
        }

        Log.i(TAG, "羽白结果----> text  " + text);
        String voice = bean.getVoice();
        if (TextUtils.isEmpty(voice)) {
            speak(text);
            iv_expression.setVisibility(View.GONE);
            iv_expression2.setVisibility(View.VISIBLE);
        } else {
            playVoice(voice);
        }
        String image_url = bean.getImagereply();
        if (!TextUtils.isEmpty(image_url)) {
            img.setVisibility(View.VISIBLE);
            ImageUtils.image(mContext, image_url, img);
            isShowImage = true;
            updateTime = System.currentTimeMillis();
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
            mediaPlayer.setOnCompletionListener(mp -> tv_marquee.setVisibility(View.GONE));
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
                    Log.i("rr", "OpenCV loaded successfully");
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetNetState(NetState state) {
        isNetConnection= state.isUse;
        if(!isNetConnection){
            net_state.setText(state.message);
        }else {
            net_state.setText("");
        }
        toastLong(state.message);
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
