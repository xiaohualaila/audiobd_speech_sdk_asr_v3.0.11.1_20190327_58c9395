package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.model.NetState;
import com.aier.speech.recognizer.mvp.contract.MainContract;
import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.model.MessageWrap;
import com.aier.speech.recognizer.mvp.presenter.MainPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends RobotSpeechActivity implements MainContract.View {
    private static final String TAG = "MainActivity";
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.ed_name)
    EditText ed_name;
    private MainPresenter presenter;
    private AudioManager am;
    private boolean isCheckFace = false;
    private Long updateTime;
    private boolean isNetConnection = false;
    private boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        presenter = new MainPresenter(this);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        //  speak("欢迎您重回红军时代！");
    }

    @OnClick({R.id.start_speak})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_speak:
                //    startSpeech();
                String my_name = ed_name.getText().toString();
                if (TextUtils.isEmpty(my_name)) {
                    toastLong("姓名不能为空！");
                    return;
                }

                Intent intent = new Intent(this, CameraActivity.class);
                intent.putExtra("my_name", my_name);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void wakup() {
        Log.i(TAG, "唤醒");
        stopTTS();
        //   speak("在");
        startSpeech();
    }

    protected void speechStart() {
        Log.i(TAG, "msg ---->   speechStart  ");
        tip.setText("正在识别");
    }

    protected void speechBackMsg(String msg) {
        //  Log.i(TAG, "msg ---->     " + msg);
        if (msg.contains("增大音量") || msg.contains("增加声音") || msg.contains("声音变大") || msg.contains("增加音量") || msg.contains("调高音量") || msg.equals("提高音量")) {
            am.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);//增大
            return;
        } else if (msg.contains("减小音量") || msg.contains("减小声音") || msg.contains("声音变小") || msg.contains("调低音量")) {
            am.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);//增小
            return;
        } else if (msg.contains("打开相机") || msg.contains("人脸识别") || msg.contains("打开摄像头") || msg.contains("相机")) {
            if (isCheckFace) {
                startActiviys(CameraActivity.class);
            } else {
                speak("无法进入拍照界面");
            }
            return;
        }
        ed_name.setText(msg);
        if (isNetConnection) {
            presenter.loadData(msg);
        } else {
            toastLong("网络无法连接！");
        }

        Log.i(TAG, "msg ---->   speechBackMsg  " + msg);
    }

    protected void speechTemporary(String msg) {
        Log.i(TAG, "msg ---->   识别结果  " + msg);
    }

    /**
     * 语音识别结束
     */
    protected void speechFinish() {
        //    Log.i(TAG, "msg ---->   speechFinish  ");
        tip.setText("");
    }

    /**
     * 语音合成播放完成
     */
    protected void ttsFinish() {
        if (isFirst) {
            startWakeUp();
            isFirst = false;
            return;
        }
    }


    @Override
    public void getDataSuccess(YUBAIBean bean) {

    }


    @Override
    public void getDataFail() {
        toastShort("请检查网络！");
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
        //    Log.i("zzz", " -->"+message.message );
        speak(message.message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetNetState(NetState state) {
        isNetConnection = state.isUse;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presenter.dispose();
    }


}
