package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.adapter.AnswerAdapter;
import com.aier.speech.recognizer.bean.AnswerQuestionResult;
import com.aier.speech.recognizer.bean.ListBean;
import com.aier.speech.recognizer.mvp.contract.AnswerQuestionContract;
import com.aier.speech.recognizer.mvp.presenter.AnswerQuestionPresenter;
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
    private AnswerQuestionPresenter presenter;
    private AnswerAdapter mMyAdapter;
    private GridLayoutManager manager;
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

    @OnClick({R.id.take_photo, R.id.iv_next, R.id.iv_left_btn,R.id.iv_right_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                finish();
                break;
            case R.id.iv_next:
                index++;
                if (index < size) {
                    bean = (ListBean) questionslist.get(index);
                    String quest = bean.getQuestion();
                    tv_question.setText(quest);
                    mMyAdapter.setList(bean.getTopics(), false);
                    if(index==1){
                        iv_people.setImageResource(R.drawable.img2);
                    }else if(index==2){
                        iv_people.setImageResource(R.drawable.img3);
                    }else if(index==3){
                        iv_people.setImageResource(R.drawable.img4);
                    }else {
                        iv_people.setImageResource(R.drawable.img5);
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
            mMyAdapter = new AnswerAdapter(bean.getTopics(), mContext, false);

            manager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(manager);
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
