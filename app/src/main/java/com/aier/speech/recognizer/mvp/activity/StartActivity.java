package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import com.aier.speech.recognizer.mvp.presenter.StartPresenter;
import butterknife.BindView;

public class StartActivity extends BaseActivity implements StartContract.View {
    private static String TAG = "StartActivity";
    @BindView(R.id.tv_date)
    TextView tv_date;
    private StartPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StartPresenter(this);
        presenter.getTime();
        startDelay();
    }

    private void startDelay() {
        new Handler().postDelayed(() -> {
            startActiviys(MainActivity.class);
            finish();
        }, 2000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }

//    public void openIv1(View view) {
//        startActivity(new Intent(this, .class));
//    }
//    public void openIv2(View view) {
//        startActivity(new Intent(this, .class));
//    }
//    public void openIv3(View view) {
//        startActivity(new Intent(this, .class));
//    }
//    public void openIv4(View view) {
//        startActivity(new Intent(this, .class));
//    }
//    public void openIv5(View view) {
//        startActivity(new Intent(this, .class));
//    }
//    public void openIv6(View view) {
//        startActivity(new Intent(this, .class));
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    @Override
    public void backTime(String time) {
        tv_date.setText(time);
    }
}
