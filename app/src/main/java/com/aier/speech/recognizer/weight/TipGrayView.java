package com.aier.speech.recognizer.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aier.speech.recognizer.R;

public class TipGrayView extends ConstraintLayout  {

    private ImageView tip_bg;
    private TextView tip_text;


    public TipGrayView(Context context) {
        super(context);
    }

    public TipGrayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.tip_gray_view, this);
        tip_bg = view.findViewById(R.id.tip_bg);
        tip_text = view.findViewById(R.id.tip_text);


    }


    public void setTipText(String tipText){
        tip_text.setText(tipText);
    }


}