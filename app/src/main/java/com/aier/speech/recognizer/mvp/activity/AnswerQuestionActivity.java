package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.adapter.MyAdapter;
import com.aier.speech.recognizer.bean.AnswerQuestionResult;
import com.aier.speech.recognizer.bean.ListBean;
import com.aier.speech.recognizer.mvp.contract.AnswerQuestionContract;
import com.aier.speech.recognizer.mvp.presenter.AnswerQuestionPresenter;
import com.aier.speech.recognizer.util.ToastyUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerQuestionActivity extends BaseActivity implements AnswerQuestionContract.View {
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_question)
    TextView tv_question;

    private AnswerQuestionPresenter presenter;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mMyAdapter;
    private List questionslist;
    private int size;
    private int index = 0;
    private int score = 0;
    private int every_score = 0;
    private ListBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AnswerQuestionPresenter(this);
        presenter.loadData();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_answer_question;
    }

    @OnClick({R.id.take_photo, R.id.iv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                startActiviys(Camera2Activity.class);
                finish();
                break;
            case R.id.iv_next:
                index++;
                if (index < size) {
                    bean = (ListBean) questionslist.get(index);
                    String quest = bean.getQuestion();
                    tv_question.setText(quest);
                    mMyAdapter.setList(bean.getTopics(), false);
                } else {
                    startActiviys(AnswerResultsActivity.class,score);
                    finish();
                }

                break;
            case R.id.right_btn://菜单
                startActiviys(MenuActivity.class);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
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
            mMyAdapter = new MyAdapter(bean.getTopics(), mContext, false);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mMyAdapter);

            mMyAdapter.setXianShiInterface(doRight -> {
                if (doRight) {
                    score += every_score;
                    Log.i("score", "score " + score);
                }
                mMyAdapter.setList(bean.getTopics(), true);
            });
        }
    }

    @Override
    public void getDataFail() {

    }
}
