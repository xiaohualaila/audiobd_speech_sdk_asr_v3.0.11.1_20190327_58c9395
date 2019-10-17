package com.aier.speech.recognizer.mvp.activity;

import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.bean.QuestionRankResult;
import com.aier.speech.recognizer.bean.SimilarFaceResult;
import com.aier.speech.recognizer.bean.UniqidResult;
import com.aier.speech.recognizer.bean.YUBAIBean2;
import com.aier.speech.recognizer.mvp.contract.CameraContract;
import com.aier.speech.recognizer.mvp.presenter.CameraPresenter;
import com.aier.speech.recognizer.weight.WaveView;

import butterknife.BindView;

public class ChuxinActivity extends RobotSpeechActivity implements CameraContract.View{

    private static final String TAG ="ChuxinActivity" ;
    private CameraPresenter presenter;
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.voice_view)
    WaveView waveView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CameraPresenter(this);
        waveView.setWaveScaleWidth(0.62f);
        waveView.setWaveScaleHeight(0.1f);
        waveView.setSpeed(512);
    }

    @Override
    public void getDataSuccess(SimilarFaceResult value) {

    }

    @Override
    public void getUniqidDataSuccess(UniqidResult value) {

    }

    @Override
    public void getDataFail() {

    }

    @Override
    public void getUniqidDataFail() {

    }

    @Override
    public void getQuestionRankDataSuccess(QuestionRankResult value) {

    }

    @Override
    public void getQuestionRankDataFail(String msg) {

    }

    @Override
    public void getYubaiDataSuccess(YUBAIBean2 bean) {
        if(bean!=null){
            YUBAIBean2.ResultBean result = bean.getResult();
            String answer = result.getAnswer();
            Log.i("sss",answer);
            if(!TextUtils.isEmpty(answer)){
                speak(answer);
                //显示滚动文字
                tip.setText(answer);
                tip.setSelected(true);
            }
        }
    }


    public void wakup() {
        Log.i(TAG, "唤醒");
        stopTTS();
        speak("在");
        startSpeech();
    }

    protected void speechStart() {
        Log.i(TAG, "msg ---->   speechStart  ");
        waveView.setVisibility(View.VISIBLE);
        tip.setText("正在识别");
    }

    protected void speechBackMsg(String msg) {
        Log.i(TAG, "msg ---->     " + msg);
        tip.setText(msg);
        presenter.loadData(msg);
        Log.i(TAG, "msg ---->   speechBackMsg  " + msg);
    }

    protected void speechTemporary(String msg) {
        tip.setText(msg);
    }

    /**
     * 语音识别结束
     */
    protected void speechFinish() {
        //    Log.i(TAG, "msg ---->   speechFinish  ");
        waveView.setVisibility(View.GONE);
        tip.setText("");
    }

    /**
     * 语音合成播放完成
     */
    protected void ttsFinish() {
        tip.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
