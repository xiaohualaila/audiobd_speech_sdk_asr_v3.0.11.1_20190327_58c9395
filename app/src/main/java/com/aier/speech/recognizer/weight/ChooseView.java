package com.aier.speech.recognizer.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.aier.speech.recognizer.R;

public class ChooseView extends ConstraintLayout implements View.OnClickListener {

    private ImageView box_1, box_2, box_3, iv_line_1, iv_line_2;

    private ChooseCallBack callBack;
    private int before_state = 1;
    public void setChooseCallBack(ChooseCallBack callBack) {
        this.callBack = callBack;
    }


    public ChooseView(Context context) {
        super(context);
    }

    public ChooseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.choose_line_view, this);
        box_1 = view.findViewById(R.id.box_1);
        box_2 = view.findViewById(R.id.box_2);
        box_3 = view.findViewById(R.id.box_3);
        iv_line_1 = view.findViewById(R.id.iv_line_1);
        iv_line_2 = view.findViewById(R.id.iv_line_2);


        box_1.setOnClickListener(this);
        box_2.setOnClickListener(this);
        box_3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.box_1:
                chanseView(1);
                break;
            case R.id.box_2:
                chanseView(2);
                break;
            case R.id.box_3:
                chanseView(3);
                break;
        }


    }

    private void chanseView ( int mAccount){
        if (before_state != mAccount) {
            if (mAccount == 1) {
                box_1.setImageResource(R.drawable.icon_box3);//绿色大
                box_2.setImageResource(R.drawable.icon_box1);//灰色
                box_3.setImageResource(R.drawable.icon_box1);
                iv_line_2.setImageResource(R.drawable.icon_box5);//灰色长
                callBack.setCallBack(1);
            } else if (mAccount == 2) {
                box_1.setImageResource(R.drawable.icon_box2);
                box_2.setImageResource(R.drawable.icon_box3);
                box_3.setImageResource(R.drawable.icon_box1);
                iv_line_2.setImageResource(R.drawable.icon_box5);
                callBack.setCallBack(2);
            } else if (mAccount == 3) {
                box_1.setImageResource(R.drawable.icon_box2);
                box_2.setImageResource(R.drawable.icon_box2);
                box_3.setImageResource(R.drawable.icon_box3);
                iv_line_2.setImageResource(R.drawable.icon_box4);//灰色长
                callBack.setCallBack(3);
            }

            before_state = mAccount;
        }

    }

    public interface ChooseCallBack{
        void setCallBack(int num);
    }
}