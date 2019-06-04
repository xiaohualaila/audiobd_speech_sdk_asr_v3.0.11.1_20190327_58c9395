package com.baidu.aip.asrwakeup3;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import com.baidu.aip.asrwakeup3.control.InitConfig;
import com.baidu.aip.asrwakeup3.listener.UiMessageListener;
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

/**
 * 语音合成
 */

public class RobotTTSActivity extends RobotWakeUpActivity {
    protected Handler handler;
    protected String appId = "11005757";
    protected String appKey = "Ovcz19MGzIKoDDb3IsFFncG1";
    protected String secretKey = "e72ebb6d43387fc7f85205ca7e6706e2";

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    private TtsMode ttsMode = TtsMode.ONLINE;
    private static final String TEMP_DIR = "/sdcard/baiduTTS"; // 重要！请手动将assets目录下的3个dat 文件复制到该目录
    private static final String TEXT_FILENAME = TEMP_DIR + "/" + "bd_etts_text.dat";
    private static final String MODEL_FILENAME = TEMP_DIR + "/" + "bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat";

    protected SpeechSynthesizer mSpeechSynthesizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }

        };
        initTTs();
    }

    protected void handleMsg(Message msg) {
        if (text != null && msg.obj != null) {
            if (msg.what == STATUS_FINISHED_RESULT) {
                String result = msg.obj.toString();
                text.setText(result);
            }
            Log.i("sss", "---->" + msg.obj.toString());
        }
    }

    private void initTTs() {
        LoggerProxy.printable(true); // 日志打印在logcat中
        boolean isMix = ttsMode.equals(TtsMode.MIX);
        boolean isSuccess;
        if (isMix) {
            // 检查2个离线资源是否可读
            isSuccess = checkOfflineResources();
            if (!isSuccess) {
                return;
            } else {
                print("离线资源存在并且可读, 目录：" + TEMP_DIR);
            }
        }
        // 日志更新在UI中，可以换成MessageListener，在logcat中查看日志
        SpeechSynthesizerListener listener = new UiMessageListener(handler);
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(this);
        mSpeechSynthesizer.setSpeechSynthesizerListener(listener);
        mSpeechSynthesizer.setAppId(appId);
        mSpeechSynthesizer.setApiKey(appKey, secretKey);
        if (isMix) {
            isSuccess = checkAuth();
            if (!isSuccess) {
                return;
            }
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME);
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME);
        }
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        mSpeechSynthesizer.setAudioStreamType(AudioManager.MODE_IN_CALL);

        // x. 额外 ： 自动so文件是否复制正确及上面设置的参数
        Map<String, String> params = new HashMap<>();
        // 复制下上面的 mSpeechSynthesizer.setParam参数
        // 上线时请删除AutoCheck的调用
        if (isMix) {
            params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME);
            params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME);
        }
        InitConfig initConfig =  new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);
        AutoCheck2.getInstance(getApplicationContext()).check(initConfig, new Handler() {
            @Override
            /**
             * 开新线程检查，成功后回调
             */
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck2 autoCheck = (AutoCheck2) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainDebugMessage();
                        print(message); // 可以用下面一行替代，在logcat中查看代码
                        // Log.w("AutoCheckMessage", message);
                    }
                }
            }

        });
        // 6. 初始化
        mSpeechSynthesizer.initTts(ttsMode);

    }
    /**
     * 检查 TEXT_FILENAME, MODEL_FILENAME 这2个文件是否存在，不存在请自行从assets目录里手动复制
     *
     * @return
     */
    private boolean checkOfflineResources() {
        String[] filenames = {TEXT_FILENAME, MODEL_FILENAME};
        for (String path : filenames) {
            File f = new File(path);
            if (!f.canRead()) {
                print("[ERROR] 文件不存在或者不可读取，请从assets目录复制同名文件到：" + path);
                print("[ERROR] 初始化失败！！！");
                return false;
            }
        }
        return true;
    }


    private void print(String message) {
        Log.i("sss", message);
    }


    private boolean checkAuth() {
        AuthInfo authInfo = mSpeechSynthesizer.auth(ttsMode);
        if (!authInfo.isSuccess()) {
            // 离线授权需要网站上的应用填写包名。本demo的包名是com.baidu.tts.sample，定义在build.gradle中
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            print("【error】鉴权失败 errorMsg=" + errorMsg);
            return false;
        } else {
            print("验证通过，离线正式授权文件存在。");
            return true;
        }
    }

    private void speak(String text) {
        if (mSpeechSynthesizer == null) {
            return;
        }
        mSpeechSynthesizer.speak(text);
    }

    private void stop() {
        mSpeechSynthesizer.stop();
    }

    public void speek(View view) {
        speak("小明今天上班了吗");

    }
    /**
     * 销毁时需要释放识别资源。
     */
    @Override
    protected void onDestroy() {
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.stop();
            mSpeechSynthesizer.release();
            mSpeechSynthesizer = null;
        }
        super.onDestroy();
    }

}
