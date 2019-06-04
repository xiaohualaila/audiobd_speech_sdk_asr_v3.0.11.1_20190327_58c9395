package com.baidu.aip.asrwakeup3.mvp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.baidu.aip.asrwakeup3.R;
import com.baidu.aip.asrwakeup3.bean.YUBAIBean;
import com.baidu.aip.asrwakeup3.mvp.contract.MainContract;
import com.baidu.aip.asrwakeup3.mvp.presenter.MainPresenter;
import com.baidu.aip.asrwakeup3.network.schedulers.SchedulerProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;


public class MainActivity extends RobotSpeechActivity implements  MainContract.View  {
    @BindView(R.id.imageView)
    ImageView imageView;
    private MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this, SchedulerProvider.getInstance());
        Glide.with(this).load(R.drawable.xiaohui).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

    }

    public  void wakup(){
        stop();//停止语音合成说话
        stopSpeech();
        startSpeech();
        Log.i("sss","唤醒");
    }

    protected void backMsg(String msg){
        presenter.getYubaiData(msg);
    }

    @Override
    public void getDataSuccess(YUBAIBean bean) {
        String result = bean.getResult();
        Log.i("sss","羽白返回的结果---->  "+result);
        speak( result);
    }

    @Override
    public void getDataFail() {

    }
}
