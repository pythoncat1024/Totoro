package com.pythoncat.totoro.adapter.recycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pythoncat.totoro.R;
import com.pythoncat.totoro.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by pythonCat on 2016/5/8.
 * 历史页面的 adapter
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Holder> {

    private final LayoutInflater mInflater;
    private static OnItemClickListener mOnclickListener;
    private List<String> mData;

    public HistoryAdapter(Context c, OnItemClickListener clickListener) {
        mInflater = LayoutInflater.from(c);
        mOnclickListener = clickListener;
    }

    public void setData(List<String> data) {
        this.mData = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new Holder(mInflater.inflate(R.layout.item_layout_history, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        int pos = holder.getAdapterPosition();
        holder.tvItem.setText(mData.get(pos));
        holder.itemView.setOnClickListener(v -> {
            if (mOnclickListener != null) {
                mOnclickListener.onItemClick(v,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            throw new IllegalArgumentException("adapter's data can't be null !");
        return mData.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        View itemView;
        TextView tvItem;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvItem = (TextView) itemView.findViewById(R.id.item_tv_history);
        }
    }
}
