package com.aier.speech.recognizer.mvp.activity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.util.SharedPreferencesUtil;
import com.aier.speech.recognizer.util.ToastyUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AddrActivity extends BaseActivity {

    @BindView(R.id.ed_name)
    EditText ed_name;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.ed_city)
    EditText ed_city;
    @BindView(R.id.ec_addr)
    EditText ec_addr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        AssetManager assets = getAssets();
        Typeface tf = Typeface.createFromAsset(assets, "fonts/MStiffHeiPRC.ttf");
//        tv_total.setTypeface(tf);
//        tv_num.setTypeface(tf);
//        tv_tiandu.setTypeface(tf);


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_addr;
    }

    @OnClick({R.id.iv_back,R.id.iv_save_btn})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_save_btn:
                String name = ed_name.getText().toString().trim();
                String phone = ed_phone.getText().toString().trim();
                String city = ed_city.getText().toString().trim();
                String addr = ec_addr.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    ToastyUtil.INSTANCE.showInfo("收货人不能为空！");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    ToastyUtil.INSTANCE.showInfo("联系方式不能为空！");
                    return;
                }
                if(TextUtils.isEmpty(city)){
                    ToastyUtil.INSTANCE.showInfo("所在地区不能为空！");
                    return;
                }
                if(TextUtils.isEmpty(addr)){
                    ToastyUtil.INSTANCE.showInfo("详细地址不能为空！");
                    return;
                }
                SharedPreferencesUtil.putString(this,"name",name);
                SharedPreferencesUtil.putString(this,"phone",phone);
                SharedPreferencesUtil.putString(this,"city",city);
                SharedPreferencesUtil.putString(this,"addr",addr);
                break;
        }
    }
}