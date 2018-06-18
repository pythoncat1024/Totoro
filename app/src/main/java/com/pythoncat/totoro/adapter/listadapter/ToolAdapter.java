package com.pythoncat.totoro.adapter.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.totoro.R;
import com.pythoncat.totoro.model.ToolModel;
import com.pythoncat.totoro.utils.RxJavaUtil;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by pythonCat on 2016/5/22.
 */
public class ToolAdapter extends BaseAdapter {

    private final List<ToolModel> data;
    private final Context context;
    private final ListView mListView;
    private Subscription subscribe;

    public ToolAdapter(Context context, ListView listView, List<ToolModel> data) {
        this.context = context;
        this.mListView = listView;
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data == null) throw new IllegalArgumentException("adapter's data can not be null!");
        return data.size();
    }

    @Override
    public ToolModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position % data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tools_layout, parent, false);
            holder.toggleLayout = (RelativeLayout) convertView.findViewById(R.id.toggle_layout_tool);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name_tool);
            holder.cbState = (CheckBox) convertView.findViewById(R.id.item_state_cb);
            holder.showOrHideLayout = (LinearLayout) convertView.findViewById(R.id.item_show_or_hide_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ToolModel item = getItem(position);
        holder.toggleLayout.setOnClickListener(v -> {
            RxJavaUtil.close(subscribe);
            subscribe = Observable.from(data)
                    .subscribe(
                            it -> it.checked = it.equals(item) ? !it.checked : false,
                            LogUtils::e,
                            () -> notifyDataSetChanged()
                    );
        });
        holder.tvName.setText(item.name);
        holder.cbState.setChecked(item.checked);
        holder.showOrHideLayout.setVisibility(holder.cbState.isChecked() ? View.VISIBLE : View.GONE);
        return convertView;
    }

    class ViewHolder {
        public RelativeLayout toggleLayout;
        TextView tvName;
        public CheckBox cbState;
        public LinearLayout showOrHideLayout;
    }
}
