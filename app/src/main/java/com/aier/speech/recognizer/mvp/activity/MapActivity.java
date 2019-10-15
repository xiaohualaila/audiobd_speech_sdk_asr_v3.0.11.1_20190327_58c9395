package com.aier.speech.recognizer.mvp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
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
import com.aier.speech.recognizer.bean.AllMapResult;
import com.aier.speech.recognizer.bean.EventResult;
import com.aier.speech.recognizer.bean.JingdianResult;
import com.aier.speech.recognizer.bean.MapDataResult;
import com.aier.speech.recognizer.bean.MapSearchResult;
import com.aier.speech.recognizer.bean.RenWuResult;
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

public class MapActivity extends BaseActivity implements MapContract.View,
        AMap.OnMarkerClickListener, MapSearchAdapter.SearchInterface {
    private MapView mapView;
    private AMap aMap;
    @BindView(R.id.rv_search)
    RecyclerView mRecyclerView;
    @BindView(R.id.et)
    EditText et;

    @BindView(R.id.tv_renwu)//人物
            TextView tv_renwu;
    @BindView(R.id.tv_fengjing)//风景
            TextView tv_fengjing;
    @BindView(R.id.tv_dang)//党支部
            TextView tv_dang;
    @BindView(R.id.tv_story)//故事
            TextView tv_story;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.tip)
    TextView tip;

    private boolean isClickIcon = true;

    private int type =1;
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
        adapter.setSearchInterface(this);
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
        tip.setText("热烈祝贺2019年赣州经开区“传承红色基因·牢记初心使命”赣南苏区红色故事演讲大赛圆满成功!");
        tip.setSelected(true);
    }


    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
            type = 4;
            presenter.dangzhibuMapBtn();
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

    @OnClick({R.id.take_photo, R.id.iv_back, R.id.iv_back_, R.id.iv_right_btn, R.id.iv_delete,
            R.id.iv_answer_question, R.id.tv_renwu, R.id.tv_fengjing, R.id.tv_dang, R.id.tv_story,
            R.id.ll_tip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right_btn://菜单
                startActiviys(MapActivity.class);
                finish();
                break;
            case R.id.iv_delete:
                et.setText("");
                hideKeyboard();
                adapter.setListData(null);
                setViewVisible();
                break;
            case R.id.iv_answer_question:
                startActiviys(AnswerQuestionActivity.class);
                finish();
                break;
            case R.id.tv_renwu://人物 1景点 2事件 3人物
                type = 3;
                isClickIcon = true;
                presenter.loadMapData("3");//人物
                break;
            case R.id.tv_fengjing://1景点
                type = 1;
                isClickIcon = true;
                presenter.loadMapData("1");//1景点
                break;
            case R.id.tv_dang://党支部
                type = 4;
             //   presenter.loadMapData("4");
                isClickIcon = true;
               presenter.dangzhibuMapBtn();
                break;
            case R.id.tv_story://故事
                type = 2;
                isClickIcon = true;
                presenter.loadMapData("2");//故事
                break;
            case R.id.ll_tip://点击顶部消息
                startActiviys(NewsActivity.class);
                break;
            default:
                finish();
                break;
        }
    }

    private void hideKeyboard() {
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }



    private Map<String,MapDataResult.DataBean.ListBean> dangjian_map = new HashMap<>();
//    private Map<String,String> map = new HashMap<>();
    private Map<String, AllMapResult.DataBean.ListBean> all_map = new HashMap<>();
    @Override
    public void getDataSuccess(MapDataResult.DataBean dataBean) {
        if (aMap != null) {
            aMap.clear();
            dangjian_map.clear();

            List<MapDataResult.DataBean.ListBean> listBean = dataBean.getList();
            MapDataResult.DataBean.ListBean bean;
            LatLng mlatLng;
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(dingwei));//将地图移动到指定位置
            aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
            if (listBean.size() > 0) {
                for (int i = 0; i < listBean.size(); i++) {
                    bean = listBean.get(i);
                    mlatLng = new LatLng(Double.valueOf(bean.getLat()), Double.valueOf(bean.getLng()));
                    markerOption = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.small_dang_marker))
                            .position(mlatLng)
                            .draggable(true);
                    marker = aMap.addMarker(markerOption);
                    dangjian_map.put(marker.getId(), bean);
                }
            }
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
            if(type==1){//景点
                oldMarker.setIcon(BitmapDescriptorFactory.fromResource(
                        R.drawable.jingdian_marker));
            }else if(type==2){//故事
                oldMarker.setIcon(BitmapDescriptorFactory.fromResource(
                        R.drawable.story_marker));
            } else if(type==3){//人物
                oldMarker.setIcon(BitmapDescriptorFactory.fromResource(
                        R.drawable.people_marker));
            }else{
                oldMarker.setIcon(BitmapDescriptorFactory.fromResource(
                        R.drawable.small_dang_marker));
            }
        }
        Animation animation = new ScaleAnimation(0, 1, 0, 1);
        animation.setInterpolator(new LinearInterpolator());
        //整个移动所需要的时间
        animation.setDuration(1000);
        //设置动画
        marker.setAnimation(animation);
        //开始动画
        marker.startAnimation();

        if(type==4){//党建
            View markerView = ViewGroup.inflate(MapActivity.this, R.layout.dangjian_map_markerview, null);
            TextView title = markerView.findViewById(R.id.title);
            TextView shuji = markerView.findViewById(R.id.shuji);
            TextView tv_huodong = markerView.findViewById(R.id.tv_huodong);//活动
            TextView tv_jianjie = markerView.findViewById(R.id.tv_jianjie);

            MapDataResult.DataBean.ListBean bean = dangjian_map.get(marker.getId());
            title.setText(bean.getTitle());
            shuji.setText(bean.getContact_name());//书记
            tv_huodong.setText("暂无");//活动
            String content =bean.getCompany();
            if(content !=null){
                if(!TextUtils.isEmpty(content)){
                    if(content.length()>108){
                        content = content.substring(0,108);
                        Log.i("sss",content);
                        tv_jianjie.setText(content+"..");
                    }else {
                        tv_jianjie.setText(content);
                    }

                }else {
                    tv_jianjie.setText(content+"..");
                }
            }

            marker.setIcon(BitmapDescriptorFactory.fromView(markerView));
        }else if(type==3) {//人物
            View markerView = ViewGroup.inflate(MapActivity.this, R.layout.people_map_markerview, null);
            TextView name = markerView.findViewById(R.id.name);
            TextView jibie_ = markerView.findViewById(R.id.jibie_);
            TextView chengli_time = markerView.findViewById(R.id.chengli_time);
            TextView time_death_ = markerView.findViewById(R.id.time_death_);
            TextView tv_jianjie = markerView.findViewById(R.id.tv_jianjie);
            AllMapResult.DataBean.ListBean bean = all_map.get(marker.getId());
            name.setText(bean.getName());

            jibie_.setText(bean.getGrade());
            chengli_time.setText(bean.getBirthtime());
            time_death_.setText(bean.getDeathtime());
            String content =bean.getShortcontent();
            tv_jianjie.setText(content);
            Log.i("sss",content);
            marker.setIcon(BitmapDescriptorFactory.fromView(markerView));

        }

        else {//2故事，1景点
            View markerView = ViewGroup.inflate(MapActivity.this, R.layout.story_map_markerview, null);
            TextView title = markerView.findViewById(R.id.title);
            TextView address = markerView.findViewById(R.id.address);
            TextView chengli_time = markerView.findViewById(R.id.chengli_time);
            TextView tv_jianjie = markerView.findViewById(R.id.tv_jianjie);
            ImageView marker_icon = markerView.findViewById(R.id.marker_icon);

            AllMapResult.DataBean.ListBean bean = all_map.get(marker.getId());
            if(type==3){
                title.setText(bean.getName());
            }else {
                title.setText(bean.getTitle());
            }
            address.setText(bean.getAddress());
            chengli_time.setText(bean.getTime());
            String content =bean.getShortcontent();
            tv_jianjie.setText(content);

            if (type ==1){
                marker_icon.setImageResource(R.drawable.jingdian_marker_big);
            }else {
                marker_icon.setImageResource(R.drawable.story_marker_big);
            }
            marker.setIcon(BitmapDescriptorFactory.fromView(markerView));
        }
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

    //2景点
    @Override
    public void getJingdianMapSuccess(JingdianResult value) {
//        if (aMap != null) {
//            aMap.clear();
//            jingdian_map.clear();
//        }
//        JingdianResult.DataBean.NewsInfoBean bean =value.getData().getNews_info();
//        double d_lat = Double.valueOf(bean.getLat());
//        double d_lng = Double.valueOf(bean.getLng());
//        LatLng latLng = new LatLng(d_lat, d_lng);
//        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));//将地图移动到指定位置
//        markerOption = new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.small_dang_marker))
//                .position(latLng)
//                .draggable(true);
//        marker = aMap.addMarker(markerOption);
//        jingdian_map.put(marker.getId(), bean);

    }




    //1人物
    @Override
    public void getRenWuMapSuccess(List<RenWuResult.DataBean.ListBean> listBeans) {
//        if (aMap != null) {
//            aMap.clear();
//            renwu_map.clear();
//            LatLng mlatLng;
//            aMap.moveCamera(CameraUpdateFactory.zoomTo(8));
//            if (listBeans.size() > 0) {
//                for (int i = 0; i < listBeans.size(); i++) {
//                    RenWuResult.DataBean.ListBean bean = listBeans.get(i);
//                    mlatLng = new LatLng(Double.valueOf(bean.getLat()), Double.valueOf(bean.getLng()));
//                    if (i == 0) {
//                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(mlatLng));
//                    }
//                    markerOption = new MarkerOptions()
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.small_dang_marker))
//                            .position(mlatLng)
//                            .draggable(true);
//                    marker = aMap.addMarker(markerOption);
//                    renwu_map.put(marker.getId(), bean);
//                }
//            }
//        }
    }

    //3事件
    @Override
    public void getEventMapSuccess(EventResult.DataBean dataBean) {
//        if (aMap != null) {
//            aMap.clear();
//            map.clear();
//            LatLng mlatLng;
//            aMap.moveCamera(CameraUpdateFactory.zoomTo(8));
//            List<EventResult.DataBean.ListBean> listBeans = dataBean.getList();
//            if (listBeans.size() > 0) {
//                for (int i = 0; i < listBeans.size(); i++) {
//                    EventResult.DataBean.ListBean bean = listBeans.get(i);
//                    mlatLng = new LatLng(Double.valueOf(bean.getLat()), Double.valueOf(bean.getLng()));
//                    if (i == 0) {
//                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(mlatLng));
//                    }
//                    markerOption = new MarkerOptions()
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.small_dang_marker))
//                            .position(mlatLng)
//                            .draggable(true);
//                    marker = aMap.addMarker(markerOption);
//                    map.put(marker.getId(), bean.getTitle());
//                }
//            }
//        }

    }

    @Override
    public void getAllMapSuccess(AllMapResult.DataBean data) {
        if (aMap != null) {
            aMap.clear();
          //  map.clear();
            all_map.clear();
            dangjian_map.clear();
            LatLng mlatLng;
            aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            List<AllMapResult.DataBean.ListBean> listBeans = data.getList();
            if (listBeans.size() > 0) {
                for (int i = 0; i < listBeans.size(); i++) {
                    AllMapResult.DataBean.ListBean bean = listBeans.get(i);
                    mlatLng = new LatLng(Double.valueOf(bean.getLat()), Double.valueOf(bean.getLng()));
                    if (i == 0) {
                        if(isClickIcon){
                            if(type==2){
                                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(25.613346582293,115.0069737372)));
                            }else
                            if(type==3){
                                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(26.006830785714,115.70202539626)));
                            }else
                            {
                                aMap.moveCamera(CameraUpdateFactory.changeLatLng(mlatLng));
                            }
                        }else {
                            aMap.moveCamera(CameraUpdateFactory.changeLatLng(mlatLng));
                        }
                    }
                    if(type == 1){//景点
                        markerOption = new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.jingdian_marker))
                                .position(mlatLng)
                                .draggable(true);
                    }else if(type == 2){//故事
                        markerOption = new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.story_marker))
                                .position(mlatLng)
                                .draggable(true);
                    }else if(type == 3){//人物
                        markerOption = new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.people_marker))
                                .position(mlatLng)
                                .draggable(true);
                    }else {
                        markerOption = new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.small_dang_marker))
                                .position(mlatLng)
                                .draggable(true);
                    }

                    marker = aMap.addMarker(markerOption);
                    all_map.put(marker.getId(),bean);
                }
            }
        }
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

    @Override
    public void backSearch(MapSearchResult.DataBean.ListBean data) {
        isClickIcon = false;
        int t = data.getType();//1人物 2景点 3事件  对应1景2故3人
        Log.i("sss", "类型 " + t+"  name " +data.getKeyword());
        if (t == 1) {
            type = 3;
            presenter.loadMapDataForName("3",data.getKeyword());
          //  presenter.searchRenWuDetailData(data.getKeyword());
        } else if (t == 2) {
            type = 1;
            presenter.loadMapDataForName("1",data.getKeyword());
           // presenter.searchJingDianDetailData(data.getKeyword());
        } else if (t == 3) {
            type = 2;
            presenter.loadMapDataForName("2",data.getKeyword());
        }
        setViewVisible();
        hideKeyboard();
        et.setText("");
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

}
