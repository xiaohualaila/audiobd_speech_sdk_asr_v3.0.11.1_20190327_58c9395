package com.baidu.aip.asrwakeup3;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

import com.baidu.aip.asrwakeup3.control.InitConfig;
import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.IRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener;
import com.baidu.aip.asrwakeup3.listener.UiMessageListener;
import com.baidu.aip.asrwakeup3.uiasr.params.CommonRecogParams;
import com.baidu.aip.asrwakeup3.uiasr.params.OnlineRecogParams;
import com.baidu.aip.asrwakeup3.util.AutoCheck2;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.baidu.aip.asrwakeup3.core.recog.IStatus.STATUS_FINISHED_RESULT;


public class RobotMainActivity extends RobotTTSActivity {
    protected MyRecognizer myRecognizer;
    private CommonRecogParams apiParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IRecogListener listener = new MessageStatusRecogListener(handler);
        myRecognizer = new MyRecognizer(this, listener);
        apiParams = new OnlineRecogParams();
    }

    public  void wakup(){
        startSpeech();
        Log.i("sss","唤醒");
    }

    /**
     * 开始录音，点击“开始”按钮后调用。
     * 基于DEMO集成2.1, 2.2 设置识别参数并发送开始事件
     */
    protected void startSpeech() {
        final Map<String, Object> params = fetchParams();
        (new AutoCheck(getApplicationContext(), new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
                        // txtLog.append(message + "\n");
                        // 可以用下面一行替代，在logcat中查看代码
                        Log.w("AutoCheckMessage", message);
                    }
                }
            }
        }, false)).checkAsr(params);
        myRecognizer.start(params);
    }
    protected Map<String, Object> fetchParams() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, Object> params = apiParams.fetch(sp);
        return params;
    }
    //语音识别 stop
    protected void stopSpeech() {
        myRecognizer.stop();
    }

    //语音识别 cancel
    protected void cancelSpeech() {
        myRecognizer.cancel();
    }//语音识别


    /**
     * 销毁时需要释放识别资源。
     */
    @Override
    protected void onDestroy() {
        myRecognizer.release();
        super.onDestroy();
    }

}
