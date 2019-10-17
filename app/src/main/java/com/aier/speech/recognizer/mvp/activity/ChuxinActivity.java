package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.bean.YUBAIBean2;
import com.aier.speech.recognizer.mvp.contract.ChuxinContract;
import com.aier.speech.recognizer.mvp.presenter.ChuxinPresenter;
import com.aier.speech.recognizer.weight.WaveView;
import butterknife.BindView;
import butterknife.OnClick;

public class ChuxinActivity extends RobotSpeechActivity implements ChuxinContract.View{

    private static final String TAG ="ChuxinActivity" ;
    private ChuxinPresenter presenter;
    @BindView(R.id.voice_view)
    WaveView waveView;
    @BindView(R.id.my_word)
    TextView my_word;
    @BindView(R.id.back_word)
    TextView back_word;
    @BindView(R.id.chat_bg)
    ImageView chat_bg;//聊天背景
    @BindView(R.id.btn_start)
    ImageView btn_start;

    @BindView(R.id.right_people)
    ImageView right_people;

    @BindView(R.id.iv_ren)
    ImageView iv_ren;
    @BindView(R.id.chuxin2)
    ImageView chuxin2;

    private boolean isShibieIng=false;//判断是否在识别

    private boolean isShowWord= false;

    private String mySpeakWord ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ChuxinPresenter(this);
        waveView.setWaveScaleWidth(0.50f);
        waveView.setWaveScaleHeight(0.3f);
        waveView.setSpeed(512);
    }



    @Override
    public void getDataFail() {
        speak("未找到"+mySpeakWord+"相关资料");
    }


    @OnClick({R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Log.i("isShibieIng","isShibieIng ---> "+isShibieIng);
                if(isShowWord){
                    if(!isShibieIng){
                         stopTTS();
                         stopSpeach();
                         isShowWord = false;
                    }
                }else {
                    isShowWord = true;
                    stopTTS();
                    startSpeech();
                    mySpeakWord="";
                }

                break;
        }
    }

    //隐藏聊天图片
    private void hideWord() {
        btn_start.setImageResource(R.drawable.yuyin_ing_btn);
        right_people.setVisibility(View.GONE);//隐藏右侧小人图片
        chat_bg.setVisibility(View.GONE);//隐藏聊天背景图片
    }

    @Override
    public void getYubaiDataSuccess(YUBAIBean2 bean) {
        if(bean!=null){
            YUBAIBean2.ResultBean result = bean.getResult();
            String answer = result.getAnswer();
            Log.i(TAG,answer+" 长度 "+ answer.length());
            if(!TextUtils.isEmpty(answer)){
                if(answer.length()>400){
                    answer=answer.substring(0,400);
                }
                speak(answer);
                //显示滚动文字
                showBackWord(answer);
            }
        }
    }

    //显示初心返回的文字
    private void showBackWord(String answer) {
        back_word.setText(answer);
        back_word.setVisibility(View.VISIBLE);
        chuxin2.setVisibility(View.VISIBLE);
    }

    //显示我识别到的文字
    private void showMyWord(String msg) {
        my_word.setText(msg);
        iv_ren.setVisibility(View.VISIBLE);
        my_word.setVisibility(View.VISIBLE);
    }

    public void wakup() {
        Log.i(TAG, "唤醒");
       if(!isShowWord){
           isShowWord = true;
           stopTTS();
           startSpeech();
           mySpeakWord="";
       }
    }



    protected void speechStart() {
        Log.i(TAG, "msg ----> 开始识别   ");
        isShibieIng = true;
        waveView.setVisibility(View.VISIBLE);
        hideWord();
    }

    protected void speechBackMsg(String msg) {
        Log.i(TAG, "msg 识别出的文字---->     " + msg);
        mySpeakWord = msg;
        showMyWord(mySpeakWord);
        presenter.loadData(mySpeakWord);

    }


    //临时显示输出识别文字
    protected void speechTemporary(String msg) {
      //  tip.setText(msg);
    }

    /**
     * 语音识别结束
     */
    protected void speechFinish() {
        isShibieIng = false;
        //    Log.i(TAG, "msg ---->   speechFinish  ");
        waveView.setVisibility(View.GONE);
        if(TextUtils.isEmpty(mySpeakWord)){
            btn_start.setImageResource(R.drawable.yuyin_btn);
            right_people.setVisibility(View.VISIBLE);
            chat_bg.setVisibility(View.VISIBLE);
            stopSpeach();
        }
    }

    /**
     * 语音合成播放完成
     */
    protected void ttsFinish() {
        stopSpeach();
    }

    private void stopSpeach() {
        isShowWord = false;
        back_word.setText("");
        back_word.setVisibility(View.GONE);
        chuxin2.setVisibility(View.GONE);
        my_word.setText("");
        iv_ren.setVisibility(View.GONE);
        my_word.setVisibility(View.GONE);

        btn_start.setImageResource(R.drawable.yuyin_btn);
        right_people.setVisibility(View.VISIBLE);
        chat_bg.setVisibility(View.VISIBLE);
        mySpeakWord ="";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }


}
