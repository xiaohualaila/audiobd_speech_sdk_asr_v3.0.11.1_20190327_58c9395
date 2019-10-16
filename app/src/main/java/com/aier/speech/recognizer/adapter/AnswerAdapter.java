package com.aier.speech.recognizer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.bean.TopicsBean;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder> {
    //数据源
    private List<TopicsBean> mList;
    private Context mContext;
    private boolean mXingshi, doRight;
    private  int index;
    public XianShiInterface xianShiInterface;

    public void setXianShiInterface(XianShiInterface xianShiInterface) {
        this.xianShiInterface = xianShiInterface;
    }

    public AnswerAdapter(List<TopicsBean> list, Context context, boolean xingshi) {
        mList = list;
        mContext = context;
        mXingshi = xingshi;
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
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false));
    }

    //填充视图
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (mXingshi) {
            TopicsBean topicsBeans = mList.get(position);
            holder.answer.setText(topicsBeans.getAnswer());

            int n = topicsBeans.getIs_answer();
            if (n == 1) {//答对了
                holder.layout_item.setBackgroundResource(R.drawable.rounded_green_btn);
                holder.iv_right.setVisibility(View.VISIBLE);
                holder.iv_wrong.setVisibility(View.GONE);
                holder.answer.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {//答错了
                if(index==position){
                    holder.layout_item.setBackgroundResource(R.drawable.rounded_red_btn);
                    holder.iv_right.setVisibility(View.GONE);
                    holder.iv_wrong.setVisibility(View.VISIBLE);
                    holder.answer.setTextColor(mContext.getResources().getColor(R.color.white));
                }else {
                    holder.layout_item.setBackgroundResource(R.drawable.item_bg);
                    holder.iv_right.setVisibility(View.GONE);
                    holder.iv_wrong.setVisibility(View.GONE);
                    holder.answer.setTextColor(mContext.getResources().getColor(R.color.black));
                }
            }

        } else {
            TopicsBean topicsBeans = mList.get(position);
            holder.answer.setText(topicsBeans.getAnswer());
            holder.layout_item.setBackgroundResource(R.drawable.item_bg);
            holder.iv_right.setVisibility(View.GONE);
            holder.iv_wrong.setVisibility(View.GONE);
            holder.answer.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.layout_item.setOnClickListener(v -> {
                int n = topicsBeans.getIs_answer();
                if (n == 1) {//答对了
                    doRight = true;
                } else {//答错了
                    doRight = false;
                }
                xianShiInterface.setXingShi(doRight,position);//显示对错结果
            });
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView answer;
        public ConstraintLayout layout_item;
        ImageView iv_right, iv_wrong;

        public MyViewHolder(View itemView) {
            super(itemView);
            answer = itemView.findViewById(R.id.text_view);
            layout_item = itemView.findViewById(R.id.layout_item);
            iv_right = itemView.findViewById(R.id.iv_right);
            iv_wrong = itemView.findViewById(R.id.iv_wrong);
        }
    }

    public void setList(List<TopicsBean> list, boolean xingshi,int index) {
        mList = list;
        mXingshi = xingshi;
        this.index = index;
        notifyDataSetChanged();
    }

    public interface XianShiInterface {
        void setXingShi(boolean doRight,int position);
    }

}
