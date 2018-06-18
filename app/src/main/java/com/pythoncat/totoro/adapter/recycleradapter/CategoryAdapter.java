package com.pythoncat.totoro.adapter.recycleradapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pythoncat.totoro.App;
import com.pythoncat.totoro.R;
import com.pythoncat.totoro.activity.detail.DetailActivity;
import com.pythoncat.totoro.model.AdapterBean;
import com.pythoncat.totoro.utils.ToastUtil;

import java.util.List;

/**
 * Created by pythonCat on 2016/5/9.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {
    public static final String sendKey = "detail_url";
    private final LayoutInflater mInflater;
    private final List<AdapterBean> mData;
    private final Context mContext;

    public CategoryAdapter(Context c, List<AdapterBean> data) {
        mInflater = LayoutInflater.from(c);
        this.mContext = c;
        this.mData = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mInflater.inflate(R.layout.item_layout_history_category, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
//        int pos = holder.getAdapterPosition();
        AdapterBean bean = mData.get(position);
        holder.desc.setText(bean.desc);
        holder.who.setText(bean.who);
        holder.date.setText(bean.publishedAt);
        holder.rootView.setOnClickListener((v) -> {
            clickItem(bean);
        });
    }

    private void clickItem(AdapterBean bean) {
        String url = bean.url;//跳转链接 blog or image or video
        if (url == null) {
            ToastUtil.showToastShort(App.getString(R.string.detail_url_null));
        } else {
            Intent it = new Intent(mContext, DetailActivity.class);
            it.putExtra("detail_url", url);
            mContext.startActivity(it);
            //直接打开浏览器
//            Uri uri = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            throw new IllegalArgumentException("adapter's data can't be null !");
        return mData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        View rootView;
        TextView desc;
        TextView who;
        TextView date;

        public Holder(View itemView) {
            super(itemView);
            rootView = itemView;
            desc = (TextView) itemView.findViewById(R.id.item_tv_desc_history_category);
            who = (TextView) itemView.findViewById(R.id.item_tv_who_history_category);
            date = (TextView) itemView.findViewById(R.id.item_tv_publish_date_history_category);
        }
    }
}
