package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.aier.speech.recognizer.util.ToastyUtil;

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
    private LinearLayoutManager mLayoutManager;
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
        presenter = new AnswerQuestionPresenter(this);
        presenter.loadData();
        tip.setText("热烈祝贺2019年赣州经开区“传承红色基因·牢记初心使命”赣南苏区红色故事演讲大赛圆满成功!");
        tip.setSelected(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_answer_question;
    }

    @OnClick({R.id.take_photo, R.id.iv_next, R.id.iv_left_btn,R.id.iv_right_btn,R.id.tips_view})
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
                    mMyAdapter.setList(bean.getTopics(), false);
                    if(index==1){
                        tv_num_question.setText("第二题");
                        iv_people.setImageResource(R.drawable.question_1);
                    }else if(index==2){
                        tv_num_question.setText("第三题");
                        iv_people.setImageResource(R.drawable.question_2);
                    }else if(index==3){
                        tv_num_question.setText("第四题");
                        iv_people.setImageResource(R.drawable.question_3);
                    }else {
                        tv_num_question.setText("第五题");
                        iv_people.setImageResource(R.drawable.question_1);
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
            case R.id.tips_view://点击顶部消息
                startActiviys(NewsActivity.class);
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
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mMyAdapter);

            mMyAdapter.setXianShiInterface(doRight -> {
                if (doRight) {
                    score += every_score;
                    ToastyUtil.INSTANCE.showSuccess("答题正确！");

                    Log.i("score", "score " + score);
                }else {
                    ToastyUtil.INSTANCE.showError("答题错误！");
                }
                isCanBtnNext = true;
                mMyAdapter.setList(bean.getTopics(), true);
            });
        }
    }

    @Override
    public void getDataFail() {
        ToastyUtil.INSTANCE.showError("网络请求失败！");
    }
}
