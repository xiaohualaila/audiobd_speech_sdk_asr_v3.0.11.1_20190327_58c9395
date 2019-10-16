package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.util.Log;
import com.aier.speech.recognizer.R;
import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;
import java.util.TreeMap;

/**
 * 百度唤醒
 */
public class RobotWakeUpActivity extends BaseActivity implements EventListener {
    private EventManager wakeup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wakeup = EventManagerFactory.create(this, "wp");
        wakeup.registerListener(this); //  EventListener 中 onEvent方法
    }

//    @Override
//    protected int getLayout() {
//        return R.layout.camera;
//    }

    @Override
    protected int getLayout() {
        return R.layout.activity_chuxin;
    }

    @Override
    public void onEvent(String name, String params, byte[] data, int i, int i1) {
        Log.i("sss","name " +name +" params "+params);
        if (params != null && !params.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(params);
                String  wakup = jsonObject.optString("errorDesc");
                if(wakup.equals("wakup success")){
                    wakup();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public  void wakup(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        startWakeUp();
    }

    /**
     * 测试参数填在这里
     * 基于SDK唤醒词集成第2.1 设置唤醒的输入参数
     */
    protected void startWakeUp() {
        // 基于SDK唤醒词集成第2.1 设置唤醒的输入参数
        Map<String, Object> params = new TreeMap<String, Object>();
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.WP_WORDS_FILE, "assets:///WakeUp1.bin");
        String json = new JSONObject(params).toString();
        wakeup.send(SpeechConstant.WAKEUP_START, json, null, 0, 0);
    }

    // 基于SDK唤醒词集成第4.1 发送停止事件
    private void stop() {
        wakeup.send(SpeechConstant.WAKEUP_STOP, null, null, 0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wakeup.send(SpeechConstant.WAKEUP_STOP, "{}", null, 0, 0);
    }

}
