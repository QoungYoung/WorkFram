package com.smile.taobaodemo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.taobaodemo.base.BundleKey;
import com.smile.taobaodemo.ui.activity.DetailActivity;
import com.smile.taobaodemo.utils.GlideUtil;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.AreaBean;
import com.smile.taobaodemo.model.entity.HomeBase;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class AreaAdapter extends RecyclerView.Adapter<AreaViewHolder> {

    private Context context;
    private List<HomeBase> list;
    private LayoutInflater areaInflater;
    private View view;
    private Activity activity;
    private AreaItemClickListener listener;

    public AreaAdapter(Context context, Activity activity, List<HomeBase> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        areaInflater = LayoutInflater.from(context);
    }

    @Override
    public AreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = areaInflater.inflate(R.layout.item_area_info,parent,false);
        AreaViewHolder areaViewHolder = new AreaViewHolder(view,listener);
        return areaViewHolder;
    }

    @Override
    public void onBindViewHolder(AreaViewHolder holder, final int position) {

        GlideUtil.load(activity,list.get(position).getUrl(),holder.imageView);
        holder.title.setText(list.get(position).getName());
        switch (list.get(position).getType()){
            case 1:
                holder.type.setText("农场");
                break;
            case 2:
                holder.type.setText("牧场");
                break;
            case 3:
                holder.type.setText("鱼塘");
                break;
        }

        holder.info.setText(list.get(position).getInfo());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, DetailActivity.class);
                intent.putExtra(BundleKey.PARCELABLE, list.get(position));
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 为Adapter暴露一个Item点击监听的公开方法
     *
     * @param listener
     */
    public void setOnItemClickListener(AreaItemClickListener listener) {
        this.listener = listener;
    }
}
class AreaViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView title;
    TextView type;
    TextView info;
    TextView price;
    View itemView;

    public AreaViewHolder(View itemView, final AreaItemClickListener areaItemClickListener) {
        super(itemView);
        this.itemView = itemView;
        imageView = (ImageView) itemView.findViewById(R.id.area_img);
        title = (TextView) itemView.findViewById(R.id.area_title);
        info = (TextView) itemView.findViewById(R.id.area_info);
        type = (TextView) itemView.findViewById(R.id.area_type);
        price = (TextView) itemView.findViewById(R.id.area_price);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areaItemClickListener.onItemClick(view,getAdapterPosition());
            }
        });

    }
}



interface AreaItemClickListener {
    void onItemClick(View view, int postion);
}
