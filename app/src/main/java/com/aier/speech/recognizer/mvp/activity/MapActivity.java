package com.aier.speech.recognizer.mvp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.adapter.MapSearchAdapter;
import com.aier.speech.recognizer.bean.MapDataResult;
import com.aier.speech.recognizer.bean.MapSearchResult;
import com.aier.speech.recognizer.mvp.contract.MapContract;
import com.aier.speech.recognizer.mvp.presenter.MapPresenter;
import com.aier.speech.recognizer.util.ToastyUtil;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MapActivity extends BaseActivity implements MapContract.View, AMap.OnMarkerClickListener {
    private MapView mapView;
    private AMap aMap;
    @BindView(R.id.rv_search)
    RecyclerView mRecyclerView;
    @BindView(R.id.et)
    EditText et;

    @BindView(R.id.tv_renwu)
    TextView tv_renwu;
    @BindView(R.id.tv_fengjing)
    TextView tv_fengjing;
    @BindView(R.id.tv_dang)
    TextView tv_dang;
    @BindView(R.id.tv_story)
    TextView tv_story;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    Marker marker;
    private CustomMapStyleOptions mapStyleOptions = new CustomMapStyleOptions();

    private MarkerOptions markerOption;
    private MapPresenter presenter;
    LatLng dingwei = new LatLng(25.8453141267, 114.8645992051);//

    private LinearLayoutManager mLayoutManager;
    private MapSearchAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MapPresenter(this);
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();

        adapter = new MapSearchAdapter();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                Log.e("输入过程中执行该方法", "文字变化");
                // 输入后的监听
                Log.e("输入结束执行该方法", "输入结束");
                String str = s.toString();
                if (str.trim().length() > 0) {
                    presenter.searchData(str);
                    setViewGone();
                }
                Log.i("aaa", str);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听
                Log.e("输入前确认执行该方法", "开始输入");
                setViewGone();
            }

            @Override
            public void afterTextChanged(Editable s) {
               setViewVisible();

            }
        });

//        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Log.e("输入完点击确认执行该方法", "输入结束");
//                // 输入后的监听
//                Log.e("输入结束执行该方法", "输入结束");
//                String str= v.getText().toString();
//              //  String str = s.toString();
//                if (str.trim().length() > 0) {
//                    presenter.searchData(str);
//                    setViewGone();
//                }
//                Log.i("aaa", str);
//                return false;
//            }
//        });


    }


    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
            presenter.loadMapData();
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(dingwei));//将地图移动到指定位置
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        }
        setMapCustomStyleFile(this);
    }


    private void setMapCustomStyleFile(Context context) {
        String styleName = "style_new.data";
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            if (mapStyleOptions != null) {
                // 设置自定义样式
                mapStyleOptions.setStyleData(b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_map;
    }

    @OnClick({R.id.take_photo, R.id.iv_back, R.id.iv_back_, R.id.iv_right_btn, R.id.iv_delete, R.id.iv_answer_question})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                startActiviys(Camera2Activity.class);
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_back_:
                finish();
                break;
            case R.id.iv_right_btn://菜单
                startActiviys(MapActivity.class);
                finish();
                break;
            case R.id.iv_delete:
                et.setText("");
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                adapter.setListData(null);
                setViewVisible();
                break;
            case R.id.iv_answer_question:
                startActiviys(AnswerQuestionActivity.class);
                finish();
                break;
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aMap != null) {//清除marker
            marker.destroy();
        }
        mapView.onDestroy();
    }


    private Map<String, String> map = new HashMap<>();

    @Override
    public void getDataSuccess(MapDataResult.DataBean dataBean) {
        List<MapDataResult.DataBean.ListBean> listBean = dataBean.getList();
        MapDataResult.DataBean.ListBean bean;
        LatLng mlatLng;

        for (int i = 0; i < listBean.size(); i++) {

            bean = listBean.get(i);
            mlatLng = new LatLng(Double.valueOf(bean.getLat()), Double.valueOf(bean.getLng()));
            markerOption = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.small_dang_marker))
                    .position(mlatLng)
                    .draggable(true);
            marker = aMap.addMarker(markerOption);
            map.put(marker.getId(), bean.getTitle());
        }
    }

    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (aMap != null) {
            growInto(marker);
        }
        return false;
    }

    /**
     * 从地上生长效果，
     *
     * @param marker
     */
    Marker oldMarker;

    private void growInto(final Marker marker) {
        if (oldMarker != null) {
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(
                    R.drawable.small_dang_marker));
        }
        View markerView = ViewGroup.inflate(MapActivity.this, R.layout.map_markerview, null);
        TextView marker_title = markerView.findViewById(R.id.marker_title);
        Animation animation = new ScaleAnimation(0, 1, 0, 1);
        animation.setInterpolator(new LinearInterpolator());
        //整个移动所需要的时间
        animation.setDuration(1000);
        //设置动画
        marker.setAnimation(animation);
        //开始动画
        marker.startAnimation();
        String str = map.get(marker.getId());
        marker_title.setText(str);
        marker.setIcon(BitmapDescriptorFactory.fromView(markerView));
        oldMarker = marker;
    }

    @Override
    public void getDataFail(String msg) {
        ToastyUtil.INSTANCE.showError(msg);
    }

    @Override
    public void getSearchDataSuccess(MapSearchResult.DataBean bean) {
        adapter.setListData(bean.getList());
        setViewGone();
    }

    private void setViewGone() {
        tv_renwu.setVisibility(View.GONE);
        tv_fengjing.setVisibility(View.GONE);
        tv_story.setVisibility(View.GONE);
        tv_dang.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void setViewVisible() {
        tv_renwu.setVisibility(View.VISIBLE);
        tv_fengjing.setVisibility(View.VISIBLE);
        tv_story.setVisibility(View.VISIBLE);
        tv_dang.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }
}
