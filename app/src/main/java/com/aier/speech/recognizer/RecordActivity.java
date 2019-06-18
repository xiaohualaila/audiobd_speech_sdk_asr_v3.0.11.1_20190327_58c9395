package com.aier.speech.recognizer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.IRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.MessageStatusRecogListener;
import com.baidu.aip.asrwakeup3.uiasr.params.CommonRecogParams;
import com.baidu.aip.asrwakeup3.uiasr.params.OnlineRecogParams;
import java.util.Map;
import static com.baidu.aip.asrwakeup3.core.recog.IStatus.STATUS_FINISHED_RESULT;


public class RecordActivity extends AppCompatActivity {
    private static final String TAG ="RecordActivity" ;
    /**
     * 识别控制器，使用MyRecognizer控制识别的流程
     */
    protected MyRecognizer myRecognizer;
    protected Handler handler;
    private  CommonRecogParams apiParams;

    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        text = findViewById(R.id.text);
        handler = new Handler() {

            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }

        };
        IRecogListener listener = new MessageStatusRecogListener(handler);
        myRecognizer = new MyRecognizer(this, listener);
        apiParams = new OnlineRecogParams();
    }
    protected void handleMsg(Message msg) {
        if (text != null && msg.obj != null) {
            if(msg.what == STATUS_FINISHED_RESULT){
                String result = msg.obj.toString();
                text.setText(result);
            }
            Log.i("sss","---->" + msg.obj.toString());
        }
    }

    /**
     * 开始录音，点击“开始”按钮后调用。
     * 基于DEMO集成2.1, 2.2 设置识别参数并发送开始事件
     */
    protected void start() {
        final Map<String, Object> params = fetchParams();
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
        //  上面的获取是为了生成下面的Map， 自己集成时可以忽略
        Map<String, Object> params = apiParams.fetch(sp);
        //  集成时不需要上面的代码，只需要params参数。
        return params;
    }


    protected void stop() {
        myRecognizer.stop();
    }


    protected void cancel() {
        myRecognizer.cancel();
    }


    /**
     * 销毁时需要释放识别资源。
     */
    @Override
    protected void onDestroy() {
        myRecognizer.release();
        super.onDestroy();
    }


    public void speek(View view) {
        start();
    }
}
