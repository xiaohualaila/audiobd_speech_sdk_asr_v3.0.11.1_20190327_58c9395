package com.aier.speech.recognizer.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aier.speech.recognizer.R;

public class ChooseWineView extends ConstraintLayout implements View.OnClickListener {

    private TextView tv_tiandu_3, tv_tiandu_7, tv_tiandu_5, tv_tiandu_,
            tv_jiujing_11,tv_jiujing_13;

    private ChooseWineCallBack callBack;
    private int before_state_tian = 1;
    private int before_state_suan = 1;
    public void setChooseCallBack(ChooseWineCallBack callBack) {
        this.callBack = callBack;
    }


    public ChooseWineView(Context context) {
        super(context);
    }

    public ChooseWineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.choose_wine_view, this);
        tv_tiandu_3 = view.findViewById(R.id.tv_tiandu_3);
        tv_tiandu_7 = view.findViewById(R.id.tv_tiandu_7);
        tv_tiandu_5 = view.findViewById(R.id.tv_tiandu_5);
        tv_tiandu_ = view.findViewById(R.id.tv_tiandu_);

        tv_jiujing_11 = view.findViewById(R.id.tv_jiujing_11);
        tv_jiujing_13 = view.findViewById(R.id.tv_jiujing_13);


        tv_tiandu_3.setOnClickListener(this);
        tv_tiandu_7.setOnClickListener(this);
        tv_tiandu_5.setOnClickListener(this);
        tv_tiandu_.setOnClickListener(this);

        tv_jiujing_11.setOnClickListener(this);
        tv_jiujing_13.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tiandu_3:
                chanseTianduView(1);
                break;
            case R.id.tv_tiandu_7:
                chanseTianduView(2);
                break;
            case R.id.tv_tiandu_5:
                chanseTianduView(3);
                break;
            case R.id.tv_tiandu_:
                chanseTianduView(4);
                break;
            case R.id.tv_jiujing_11:
                chanseSuanduView(1);
                break;
            case R.id.tv_jiujing_13:
                chanseSuanduView(2);
                break;

        }
    }
    //甜度
    private void chanseTianduView ( int mAccount){
        if (before_state_tian != mAccount) {
                chanseTianViewColor ();
            if (mAccount == 1) {
                tv_tiandu_3.setBackgroundResource(R.drawable.square_bg);
                callBack.setTianWineCallBack(1);
            } else if (mAccount == 2) {
                tv_tiandu_7.setBackgroundResource(R.drawable.square_bg);
                callBack.setTianWineCallBack(2);
            } else if (mAccount == 3) {
                tv_tiandu_5.setBackgroundResource(R.drawable.square_bg);
                callBack.setTianWineCallBack(3);
            } else if (mAccount == 4) {
                tv_tiandu_.setBackgroundResource(R.drawable.square_bg);
                callBack.setTianWineCallBack(4);
            }

            before_state_tian = mAccount;
        }
    }
    //酒精
    private void chanseSuanduView ( int mAccount){
        if (before_state_suan != mAccount) {
            chanseSuanViewColor ();
            if (mAccount == 1) {
                tv_jiujing_11.setBackgroundResource(R.drawable.square_bg);
                callBack.setGuoliWineCallBack(1);
            } else if (mAccount == 2) {
                tv_jiujing_13.setBackgroundResource(R.drawable.square_bg);
                callBack.setGuoliWineCallBack(2);
            }
            before_state_suan = mAccount;
        }

    }


    private void chanseTianViewColor (){
            if (before_state_tian == 1) {
                tv_tiandu_3.setBackgroundResource(R.drawable.square_bg_no_color);
            } else if (before_state_tian == 2) {
                tv_tiandu_7.setBackgroundResource(R.drawable.square_bg_no_color);
            } else if (before_state_tian == 3) {
                tv_tiandu_5.setBackgroundResource(R.drawable.square_bg_no_color);
            } else if (before_state_tian == 4) {
                tv_tiandu_.setBackgroundResource(R.drawable.square_bg_no_color);
            }
    }


    private void chanseSuanViewColor (){
        if (before_state_suan == 1) {
            tv_jiujing_11.setBackgroundResource(R.drawable.square_bg_no_color);
        } else if (before_state_suan == 2) {
            tv_jiujing_13.setBackgroundResource(R.drawable.square_bg_no_color);
        }
    }

    public interface ChooseWineCallBack{
        void setTianWineCallBack(int num);
        void setGuoliWineCallBack(int num);
    }
}