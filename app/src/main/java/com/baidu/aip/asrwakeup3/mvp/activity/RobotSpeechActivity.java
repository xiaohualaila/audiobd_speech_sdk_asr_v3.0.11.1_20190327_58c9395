package com.baidu.aip.asrwakeup3.mvp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.IRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener;
import com.baidu.aip.asrwakeup3.uiasr.params.CommonRecogParams;
import com.baidu.aip.asrwakeup3.uiasr.params.OnlineRecogParams;
import java.util.Map;

import static com.baidu.aip.asrwakeup3.core.recog.IStatus.STATUS_FINISHED_RESULT;

/**
 * 语音识别
 */

public class RobotSpeechActivity extends RobotTTSActivity {
    protected MyRecognizer myRecognizer;
    private CommonRecogParams apiParams;
    protected Handler handlerSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handlerSpeech = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleSpeechMsg(msg);
            }

        };
        IRecogListener listener = new MessageStatusRecogListener(handlerSpeech);
        myRecognizer = new MyRecognizer(this, listener);
        apiParams = new OnlineRecogParams();

    }

    /**
     * 返回语音识别的结果
     * @param msg
     */
    protected void handleSpeechMsg(Message msg) {
        if (msg.obj != null) {
            if (msg.what == STATUS_FINISHED_RESULT) {
                String result = msg.obj.toString();
                result = result.substring(0,result.length()-1);
                backMsg(result);
            }
            Log.i("xxx", "语音识别---->" + msg.obj.toString()+"  what "+msg.what);
        }
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
