package com.aier.speech.recognizer.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.bean.MapSearchResult;

import java.util.ArrayList;
import java.util.List;

public class MapSearchAdapter extends RecyclerView.Adapter<MapSearchAdapter.MyViewHolder> {
    //数据源
    private List<MapSearchResult.DataBean.ListBean> mList = new ArrayList<>();


    public SearchInterface searchInterface;

    public void setSearchInterface(SearchInterface searchInterface) {
        this.searchInterface = searchInterface;
    }

    public MapSearchAdapter() {
    }

    public void setListData(List<MapSearchResult.DataBean.ListBean> list){

        mList.clear();
        if(list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    //返回item个数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    //创建ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.map_search_item, parent, false));
    }

    //填充视图
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        MapSearchResult.DataBean.ListBean listBean = mList.get(position);
        holder.answer.setText( listBean.getKeyword());
        holder.answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInterface.backSearch(listBean);
            }
        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView answer;
        public MyViewHolder(View itemView) {
            super(itemView);
            answer = itemView.findViewById(R.id.text_view);
        }
    }

    public interface SearchInterface {
        void backSearch(MapSearchResult.DataBean.ListBean data);
    }



}
