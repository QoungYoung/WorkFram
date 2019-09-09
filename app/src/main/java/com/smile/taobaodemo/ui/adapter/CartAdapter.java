package com.smile.taobaodemo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.logisticsInfo;
import com.smile.taobaodemo.utils.GlideUtil;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> implements View.OnClickListener {
    private Context context;
    private List<logisticsInfo> list;
    private LayoutInflater areaInflater;
    private View view;
    private Activity activity;
    private OnItemClickListener mItemClickListener;

    public CartAdapter(Context context, Activity activity, List<logisticsInfo> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        areaInflater = LayoutInflater.from(context);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = areaInflater.inflate(R.layout.item_cart_info,parent,false);
        CartViewHolder areaViewHolder = new CartViewHolder(view,mItemClickListener);
        view.setOnClickListener(this);
        return areaViewHolder;
    }
    @Override
    public void onBindViewHolder(CartViewHolder holder, final int position) {

        GlideUtil.load(activity,list.get(position).getUrl(),holder.imageView);
        holder.title.setText(list.get(position).getLogisticsName());

        holder.copy.setText(list.get(position).getLogisticsNum()+"\t 复制");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 为Adapter暴露一个Item点击监听的公开方法
     *
     */
    @Override
    public void onClick(View view) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick(view,(Integer) view.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
class CartViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView title;
    TextView type;
    TextView info;
    TextView price;
    Button trace;
    Button copy;
    Button confrim;
    View itemView;
    public CartViewHolder(View itemView, final CartAdapter.OnItemClickListener areaItemClickListener) {
        super(itemView);
        this.itemView = itemView;
        imageView = (ImageView) itemView.findViewById(R.id.area_img);
        title = (TextView) itemView.findViewById(R.id.area_title);
        trace = (Button) itemView.findViewById(R.id.trace_button);
        copy = (Button) itemView.findViewById(R.id.trace_copy);
        confrim = (Button) itemView.findViewById(R.id.reap);
        trace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areaItemClickListener.onItemClick(view,getAdapterPosition());
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areaItemClickListener.onItemClick(view,getAdapterPosition());
            }
        });
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areaItemClickListener.onItemClick(view,getAdapterPosition());
            }
        });

    }
}
