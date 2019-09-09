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

import com.smile.taobaodemo.bean.SeedsInfo;
import com.smile.taobaodemo.utils.GlideUtil;
import com.smile.taobaodemo.R;

import java.util.List;

public class SeedsAdapter extends RecyclerView.Adapter<SeedsViewHolder> implements View.OnClickListener {

    private Context context;
    private List<SeedsInfo> list;
    private LayoutInflater seedsInflater;
    private View view;
    private Activity activity;
    private OnItemClickListener mItemClickListener;

    public SeedsAdapter(Context context, Activity activity, List<SeedsInfo> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        seedsInflater = LayoutInflater.from(context);
    }

    @Override
    public SeedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = seedsInflater.inflate(R.layout.item_seed_info,parent,false);
        SeedsViewHolder seedsViewHolder = new SeedsViewHolder(view,mItemClickListener);
        return seedsViewHolder;
    }

    @Override
    public void onBindViewHolder(SeedsViewHolder holder, final int position) {

        GlideUtil.load(activity,list.get(position).getUrl(),holder.imageView);
        holder.title.setText(list.get(position).getSeedName());
        holder.area.setText("占用面积:"+String.valueOf(list.get(position).getSeedArea())+"㎡");
        holder.time.setText("种植时间:"+String.valueOf(list.get(position).getCreatedAt()));
        holder.day.setText("预计于 "+String.valueOf(list.get(position).getSeedTime())+"天后成熟");
        holder.status.setText("种植状态:"+list.get(position).getSeedStatus());
        holder.id.setText(list.get(position).getObjectId());
        if(list.get(position).getSeedStatus().equals("待收获"))
            holder.reap.setVisibility(View.VISIBLE);

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

            TextView textView = (TextView) view.findViewWithTag(R.id.seed_objectId);
            String s = textView.getText().toString();
            mItemClickListener.onItemClick(view,(Integer) view.getTag(), s);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position, String s);
    }
}
class SeedsViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView title;
    TextView area;
    TextView time;
    TextView day;
    TextView status;
    TextView id;
    Button reap;

    public SeedsViewHolder(final View itemView, final SeedsAdapter.OnItemClickListener areaItemClickListener) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.seed_img);
        title = (TextView) itemView.findViewById(R.id.seed_title);
        time = (TextView) itemView.findViewById(R.id.seed_time);
        area = (TextView) itemView.findViewById(R.id.seed_area);
        day = (TextView) itemView.findViewById(R.id.seed_day);
        status = (TextView) itemView.findViewById(R.id.seed_status);
        id = (TextView) itemView.findViewById(R.id.seed_objectId);
        reap = (Button) itemView.findViewById(R.id.reap);
        reap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areaItemClickListener.onItemClick(view,getAdapterPosition(),id.getText().toString());
            }
        });

    }
}
