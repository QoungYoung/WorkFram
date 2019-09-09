package com.smile.taobaodemo.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.model.entity.TraceBase;

import java.util.ArrayList;
import java.util.List;

public class TraceListAdapter extends BaseAdapter {
    private Context context;
    private List<TraceBase> traceList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;

    public TraceListAdapter(Context context, List<TraceBase> traceList) {
        this.context = context;
        this.traceList = traceList;
    }

    @Override
    public int getCount() {
        return traceList.size();
    }

    @Override
    public TraceBase getItem(int position) {
        return traceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final TraceBase trace = getItem(position);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart_search, parent, false);
            holder.tvAcceptTime = (TextView) convertView.findViewById(R.id.tvAcceptTime);
            holder.tvAcceptStation = (TextView) convertView.findViewById(R.id.tvAcceptStation);
            holder.tvTopLine = (TextView) convertView.findViewById(R.id.tvTopLine);
            holder.tvDot = (TextView) convertView.findViewById(R.id.tvDot);
            holder.tv_new = (TextView) convertView.findViewById(R.id.tv_new);
            convertView.setTag(holder);
        }

        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
            holder.tv_new.setVisibility(View.VISIBLE);
            // 字体颜色加深
            holder.tvAcceptTime.setTextColor(context.getResources().getColor(R.color.font_red_color));
            holder.tvAcceptStation.setTextColor(context.getResources().getColor(R.color.font_red_color));
            holder.tvDot.setBackgroundResource(R.drawable.icon_rate);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            holder.tv_new.setVisibility(View.INVISIBLE);
            holder.tvAcceptTime.setTextColor(0xff999999);
            holder.tvAcceptStation.setTextColor(0xff999999);
            holder.tvDot.setBackgroundResource(R.drawable.icon_rate);
        }

        holder.tvAcceptTime.setText(trace.getAcceptTime());
        holder.tvAcceptStation.setText(trace.getAcceptStation());
        return convertView;
    }

    @Override
    public int getItemViewType(int id) {
        if (id == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    static class ViewHolder {
        public TextView tvAcceptTime, tvAcceptStation;
        public TextView tvTopLine, tvDot,tv_new;
    }
}
