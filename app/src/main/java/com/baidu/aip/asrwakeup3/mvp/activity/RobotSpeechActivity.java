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

import java.util.HashMap;
import java.util.Map;

import static com.baidu.aip.asrwakeup3.core.recog.IStatus.STATUS_FINISHED;
import static com.baidu.aip.asrwakeup3.core.recog.IStatus.STATUS_FINISHED_RESULT;
import static com.baidu.aip.asrwakeup3.core.recog.IStatus.STATUS_LONG_SPEECH_FINISHED;
import static com.baidu.aip.asrwakeup3.core.recog.IStatus.STATUS_READY;
import static com.baidu.aip.asrwakeup3.core.recog.IStatus.STATUS_SPEAKING;
import static com.baidu.aip.asrwakeup3.core.recog.IStatus.WHAT_MESSAGE_STATUS;

/**
 * 语音识别
 */

public class RobotSpeechActivity extends RobotTTSActivity {
    private static final String TAG = "RobotSpeechActivity";
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
            String result = msg.obj.toString();
            if (msg.what == STATUS_READY) {
                speechStart();
            } else if (msg.what == STATUS_FINISHED_RESULT) {
                result = result.substring(0,result.length()-1);
                speechBackMsg(result);
            }else if(msg.what == STATUS_FINISHED){
                speechFinish();
            }
            Log.i("xxx", "语音识别---->" + msg.obj.toString()+"  what "+msg.what);
        }
    }
    protected void speechStart( ){

    }

    protected void speechFinish(){

    }

    protected void speechBackMsg(String msg){

    }




    /**
     * 开始录音，点击“开始”按钮后调用。
     * 基于DEMO集成2.1, 2.2 设置识别参数并发送开始事件
     */
    protected void startSpeech() {
      //  final Map<String, Object> params = fetchParams();
        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("vad.endpoint-timeout",0);
        params.put("accept-audio-volume",false);
        Log.i(TAG, "设置的start输入参数：" + params);
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
