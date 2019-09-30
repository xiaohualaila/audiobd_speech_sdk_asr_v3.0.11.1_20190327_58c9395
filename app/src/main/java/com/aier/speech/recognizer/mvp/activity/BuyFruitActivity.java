package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.dialog.DialogSubmit;
import com.aier.speech.recognizer.util.SharedPreferencesUtil;
import com.aier.speech.recognizer.weight.ChooseFruitView;
import com.aier.speech.recognizer.weight.ChooseJuiceView;
import com.aier.speech.recognizer.weight.ChooseWineView;

import butterknife.BindView;
import butterknife.OnClick;

public class BuyFruitActivity extends BaseActivity implements ChooseFruitView.ChooseFruitCallBack
        , ChooseJuiceView.ChooseJuiceCallBack, ChooseWineView.ChooseWineCallBack {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_addr)
    TextView tv_addr;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_tiandu)
    TextView tv_tiandu;
    @BindView(R.id.choose_fruit)
    ChooseFruitView choose_fruit;
    @BindView(R.id.choose_juice)
    ChooseJuiceView choose_juice;
    @BindView(R.id.choose_wine)
    ChooseWineView choose_wine;
    @BindView(R.id.zhifubao_bg)
    ImageView zhifubao_bg;
    @BindView(R.id.weixin_bg)
    ImageView weixin_bg;
    @BindView(R.id.iv_fruit)
    ImageView iv_fruit;
    private String name;
    private String phone;
    private String city;
    private String addr;
    private int type;
    private int num = 1;
    private int prices = 100;
    private boolean isZhifubao = true;
    DialogSubmit dialogSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AssetManager assets = getAssets();
        Typeface tf = Typeface.createFromAsset(assets, "fonts/MStiffHeiPRC.ttf");
        tv_total.setTypeface(tf);
        tv_num.setTypeface(tf);
        tv_tiandu.setTypeface(tf);
        choose_fruit.setChooseCallBack(this);
        choose_juice.setChooseCallBack(this);
        choose_wine.setChooseCallBack(this);
          type = getIntent().getIntExtra("type",0);
          if(type==1){
              prices=100;
              iv_fruit.setImageResource(R.drawable.fruit_2);
              choose_fruit.setVisibility(View.VISIBLE);//果酱
          }else if(type==2){
              prices=300;
              iv_fruit.setImageResource(R.drawable.fruit_1);
              choose_wine.setVisibility(View.VISIBLE);//香槟
          }else if(type==3){
              prices=230;
              iv_fruit.setImageResource(R.drawable.fruit_4);
              choose_juice.setVisibility(View.VISIBLE);//果粒
          }else if(type==4){
              prices=180;
              iv_fruit.setImageResource(R.drawable.fruit_3);
              choose_fruit.setVisibility(View.VISIBLE);//罐头
          }

        tv_total.setText(num * prices + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        name = SharedPreferencesUtil.getString(this, "name", "张三");
        phone = SharedPreferencesUtil.getString(this, "phone", "17767666656");
        city = SharedPreferencesUtil.getString(this, "city", "河北省 石家庄");
        addr = SharedPreferencesUtil.getString(this, "addr", "河北省石家庄2019数字经济博览会");
        tv_name.setText(name);
        tv_addr.setText(addr);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_buy_fruit;
    }

    @OnClick({R.id.iv_back, R.id.tv_addr, R.id.location_icon, R.id.iv_jian,
            R.id.iv_add, R.id.iv_submit, R.id.zhifubao_bg, R.id.weixin_bg})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_addr:
                startActivity(new Intent(this, AddrActivity.class));
                break;
            case R.id.location_icon:
                startActivity(new Intent(this, AddrActivity.class));
                break;
            case R.id.iv_jian:
                if (num > 1) {
                    num--;
                } else {
                    num = 1;
                }

                tv_num.setText(num + "");
                tv_total.setText(num * prices + "");
                break;
            case R.id.iv_add:
                num++;
                tv_num.setText(num + "");
                tv_total.setText(num * prices + "");
                break;

            case R.id.zhifubao_bg:
                isZhifubao = true;
                zhifubao_bg.setImageResource(R.drawable.zhifubao_btn_1);
                weixin_bg.setImageResource(R.drawable.weixin_btn);
                break;
            case R.id.weixin_bg:
                zhifubao_bg.setImageResource(R.drawable.zhifubao_btn);
                weixin_bg.setImageResource(R.drawable.weixin_btn_1);
                isZhifubao = false;
                break;
            case R.id.iv_submit:
                dialogSubmit = new DialogSubmit(this);
                dialogSubmit.setDoClick(() -> {
                    dialogSubmit.dismiss();
                });
                dialogSubmit.show();
                break;
        }
    }


    @Override
    public void setTianCallBack(int num) {
        //甜度
        Log.i("sss", "setTianCallBack" + num);
    }

    @Override
    public void setSuanCallBack(int num) {
        //酸度
        Log.i("sss", "setSuanCallBack" + num);
    }

    @Override
    public void setTianJuiceCallBack(int num) {
        //甜度
        Log.i("sss", "setTianJuiceCallBack" + num);
    }

    @Override
    public void setGuoliJuiceCallBack(int num) {
        //果粒
        Log.i("sss", "setGuoliJuiceCallBack" + num);
    }

    @Override
    public void setTianWineCallBack(int num) {
        Log.i("sss", "setTianWineCallBack " + num);
    }

    @Override
    public void setGuoliWineCallBack(int num) {
        Log.i("sss", "setGuoliWineCallBack " + num);
    }
}
