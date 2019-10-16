package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.adapter.AnswerAdapter;
import com.aier.speech.recognizer.bean.AnswerQuestionResult;
import com.aier.speech.recognizer.bean.ListBean;
import com.aier.speech.recognizer.model.MessageWrap;
import com.aier.speech.recognizer.mvp.contract.AnswerQuestionContract;
import com.aier.speech.recognizer.mvp.presenter.AnswerQuestionPresenter;
import com.aier.speech.recognizer.util.ToastyUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerQuestionActivity extends BaseActivity implements AnswerQuestionContract.View {
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_question)
    TextView tv_question;
    @BindView(R.id.iv_people)
    ImageView iv_people;
    @BindView(R.id.tv_num_question)
    TextView tv_num_question;
    @BindView(R.id.tip)
    TextView tip;
    private AnswerQuestionPresenter presenter;
    private AnswerAdapter mMyAdapter;
    private List questionslist;
    private int size;
    private int index = 0;
    private int score = 0;
    private int every_score = 0;
    private ListBean bean;
    private boolean isCanBtnNext = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().unregister(this);
        String msg = getIntent().getStringExtra("msg");
        tip.setText(msg);
        presenter = new AnswerQuestionPresenter(this);
        presenter.loadData();
        tv_num_question.setText("第一题");
        iv_people.setBackground(getResources().getDrawable(R.drawable.img1));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_answer_question;
    }

    @OnClick({R.id.take_photo, R.id.iv_next, R.id.jj_icon, R.id.iv_left_btn, R.id.iv_right_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                finish();
                break;
            case R.id.iv_next:
                if(!isCanBtnNext){
                    ToastyUtil.INSTANCE.showNormal("请答题！");
                   return;
                }
                isCanBtnNext = false;
                index++;
                if (index < size) {
                    bean = (ListBean) questionslist.get(index);
                    String quest = bean.getQuestion();
                    tv_question.setText(quest);
                    mMyAdapter.setList(bean.getTopics(), false,6);
                    if(index==1){
                        tv_num_question.setText("第二题");
                        iv_people.setBackground(getResources().getDrawable(R.drawable.img2));
                    }else if(index==2){
                        tv_num_question.setText("第三题");
                        iv_people.setBackground(getResources().getDrawable(R.drawable.img3));
                    }else if(index==3){
                        tv_num_question.setText("第四题");
                        iv_people.setBackground(getResources().getDrawable(R.drawable.img4));
                    }else {
                        tv_num_question.setText("第五题");
                        iv_people.setBackground(getResources().getDrawable(R.drawable.img5));
                    }

                } else {
                    Intent intent = new Intent(this, AnswerFinishActivity.class);
                    intent.putExtra("score",score);
                    startActivity(intent);
                    finish();
                }

                break;

            case R.id.iv_left_btn://初心地图
                startActiviys(MapActivity.class);
                finish();
                break;
            case R.id.iv_right_btn://菜单
                startActiviys(MenuActivity.class);
                finish();
                break;
            case R.id.jj_icon:
                startActiviys(IntroductionActivity.class);
                finish();
                break;
        }
    }



    @Override
    public void getDataSuccess(AnswerQuestionResult value) {
        AnswerQuestionResult.DataBean dataBean = value.getData();
        questionslist = dataBean.getList();
        size = questionslist.size();
        if (size > 0) {
            index = 0;
            every_score = 100 / size;
            bean = (ListBean) questionslist.get(index);
            String quest = bean.getQuestion();
            tv_question.setText(quest);
            mMyAdapter = new AnswerAdapter(bean.getTopics(), mContext, false);


            mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
            mRecyclerView.setAdapter(mMyAdapter);

            mMyAdapter.setXianShiInterface(new AnswerAdapter.XianShiInterface() {
                @Override
                public void setXingShi(boolean doRight, int position) {
                    if (doRight) {
                        score += every_score;
                        ToastyUtil.INSTANCE.showSuccess("答题正确！");

                        Log.i("score", "score " + score);
                    } else {
                        ToastyUtil.INSTANCE.showError("答题错误！");
                    }
                    isCanBtnNext = true;
                    mMyAdapter.setList(bean.getTopics(), true,position);
                }
            });
        }
    }

    @Override
    public void getDataFail() {
        ToastyUtil.INSTANCE.showError("网络请求失败！");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        Log.i("zzz", " -->"+message.message );
        tip.setText(message.message);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
        EventBus.getDefault().unregister(this);
    }
}
