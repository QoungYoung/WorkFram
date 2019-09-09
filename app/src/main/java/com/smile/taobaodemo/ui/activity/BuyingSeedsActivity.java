package com.smile.taobaodemo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bumptech.glide.Glide;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.base.Constant;
import com.smile.taobaodemo.bean.SeedsInfo;
import com.smile.taobaodemo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BuyingSeedsActivity extends Activity
{
    ListView list;//列表显示控件变量
    TextView tv1,tv2;//种子数量和类型
    Button buy;//购买
    List<Data> mPostlist = new ArrayList<Data>();
    List<BmobObject> seedsInfoList = new ArrayList<BmobObject>();
    Map<String,List<String>> map = new HashMap<String,List<String>>();
    private SimpleAdapter simp;//适配器变量
    //默认数量
    int ba=0;
    int max_num = 0;
    int selectedNum =0;

    double factorOneInt1=0;//数值
    double result=0;//合计总数
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_buying_seeds);
        Intent intent = getIntent();
        max_num = intent.getIntExtra("freeArea",0);
        //绑定ID
        list=(ListView) findViewById(R.id.gmzzListView1);
        tv1=(TextView) findViewById(R.id.gmzzTextView1);
        tv2=(TextView) findViewById(R.id.gmzzTextView2);
        Button buy = (Button) findViewById(R.id.goto_buy);
        Log.d("BUTTON", "onCreate: "+buy.getText());
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        //lists适配器
        simp = new SimpleAdapter(BuyingSeedsActivity.this, maps, R.layout.list,
                new String[]{"background","mc", "cl","zq","jg"},
                new int[]{R.id.listImageView1,R.id.listTextView1, R.id.listTextView2,R.id.listTextView3,R.id.listTextView4});

        //隐藏listview分割线
        list.setDividerHeight(0);

        list.setAdapter(simp);

        //自定义查询数据
        final BmobQuery<Data> query=new BmobQuery<Data>();
        query.order("-createdAt");//依照maps排序时间排序
        query.setLimit(10);//第一次加载默认显示10条数据
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        //加载数据调用findObjects接口查询数据详细(必须要这个，不然第一次不显示)
        query.findObjects(new FindListener<Data>(){

            @Override
            public void done(List<Data> p1, BmobException p2)
            {
                //成功

                if(p2==null){

                    //   mPostlist.clear();
                    // TODO Auto-generated method stub
                    for (int i = 0; i < p1.size(); i++)
                    {
                        mPostlist.add(p1.get(i));
                        List<String> strings = new ArrayList<String>();
                        strings.add(p1.get(i).getZq());
                        strings.add(p1.get(i).getBackground());
                        strings.add("0");
                        map.put(p1.get(i).getMc(),strings);
                    }
                    list.setAdapter(new ItemListAdapter());

                    // TODO: Implement this method

                    simp.notifyDataSetChanged();//更新内容*/
                }else{

                    Toast.makeText(BuyingSeedsActivity.this,"加载失败"+p2.getMessage(),Toast.LENGTH_LONG).show();
                }
                // TODO: Implement this method
            }
        });

        buy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result == 0)
                    ToastUtil.showShortToast(BuyingSeedsActivity.this,"您还没有选择种子哦！");
                else {
                    SeedsInfo seedsInfo = new SeedsInfo();
                    Iterator<Map.Entry<String, List<String>>> iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, List<String>> entry = iterator.next();

                        if(Integer.valueOf(entry.getValue().get(2)) > 0){
                            seedsInfo.setSeedName(entry.getKey());
                            seedsInfo.setSeedStatus("待种植");
                            seedsInfo.setSeedArea(Integer.valueOf(entry.getValue().get(2)));
                            seedsInfo.setSeedTime(Integer.valueOf(entry.getValue().get(0)));
                            seedsInfo.setUrl(entry.getValue().get(1));
                            seedsInfo.setUserId(Constant.USER.getObjectId());
                            Log.d("MAP", "onClick: "+seedsInfo.getSeedName());
                            seedsInfoList.add(seedsInfo);
                        }
                    }
                    new BmobBatch().insertBatch(seedsInfoList).doBatch(new QueryListListener<BatchResult>() {
                        @Override
                        public void done(List<BatchResult> list, BmobException e) {
                            if(e==null){
                                for(int i=0;i<list.size();i++){
                                    BatchResult result = list.get(i);
                                    BmobException ex =result.getError();
                                    if(ex==null){
                                        Log.d("BMOB","第"+i+"个数据批量添加成功："+result.getCreatedAt()+","+result.getObjectId()+","+result.getUpdatedAt());
                                    }else{
                                        Log.d("BMOB","第"+i+"个数据批量添加失败："+ex.getMessage()+","+ex.getErrorCode());
                                    }

                                }
                                Constant.PERSON_AREA_INFO.setUsedArea(Constant.PERSON_AREA_INFO.getUsedArea()+ba);
                                Constant.PERSON_AREA_INFO.setFreedArea(Constant.PERSON_AREA_INFO.getFreedArea()-ba);
                                Constant.PERSON_AREA_INFO.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e == null){
                                            startActivity(new Intent(BuyingSeedsActivity.this,SucessActivity.class));
                                        }
                                    }
                                });
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }
            }
        });

    }

    //BaseAdapter适配器
    class ItemListAdapter extends BaseAdapter
    {
        //适配器
        @Override
        public int getCount()
        {
            if (mPostlist.size() > 0)
            {
                return mPostlist.size();
            }
            return 0;
        }
        @Override
        public Object getItem(int position)
        {
            return mPostlist.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder = null;


            if (convertView == null)
            {
                //绑定自定义界面
                convertView =BuyingSeedsActivity.this. getLayoutInflater().inflate(R.layout.list, null);
                viewHolder = new ViewHolder();
                //绑定ID
                viewHolder.background=(ImageView) convertView.findViewById(R.id.listImageView1);//种子图片
                viewHolder.mc=(TextView)convertView.findViewById(R.id.listTextView1);//种子名称
                viewHolder.cl = (TextView) convertView.findViewById(R.id.listTextView2);//种子产量
                viewHolder.zq =  (TextView) convertView.findViewById(R.id.listTextView3);//生长周期
                viewHolder.jg = (TextView) convertView.findViewById(R.id.listTextView4);//购买价格

                viewHolder.ib1 = (ImageButton) convertView.findViewById(R.id.listImageButton1);
                viewHolder. tv = (TextView) convertView.findViewById(R.id.seed_num);
                viewHolder.ib2 = (ImageButton) convertView.findViewById(R.id.listImageButton2);


                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final Data post = mPostlist.get(position);

            // 这里调用了图片加载工具类的setImage方法将图片直接显示到控件上
            Glide.with(BuyingSeedsActivity.this).load(post.getBackground()) .into(viewHolder.background);
            /*Glide.with(context)     //关联activity或fragment的生命周期
             .load(url)          //加载地址(url,资源id，本地图片file)
             .placeholder(id)    //未加载时的占位图
             .error(id)          //加载失败时的占位图
             .into(ImageView);   //加载对象*/

            viewHolder.mc.setText(post.getMc());//种子名称
            viewHolder.cl.setText("种子产量："+post.getCl()); //种子产量
            viewHolder.zq.setText("生长周期："+post.getZq());//生长周期
            viewHolder.jg.setText("购买价格："+post.getJg()+"元/m²");//购买价格

            //获取文本框内容
            final String sl=viewHolder.tv.getText().toString();
            final ViewHolder finalViewHolder = viewHolder;

            //减
            viewHolder.ib1.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1)
                {

                    if(selectedNum == 0)
                        ba = 0;
                    else if (Integer.valueOf(finalViewHolder.tv.getText().toString())>0){
                        finalViewHolder.tv.setText(""+(Integer.valueOf(finalViewHolder.tv.getText().toString())-1));
                        ba--;
                        selectedNum--;
                        map.get(post.getMc()).set(2, finalViewHolder.tv.getText().toString());
                        tv1.setText(ba+"");//购物车数量


                        //转化成整形
                        factorOneInt1 = Double.parseDouble(post.getJg());
                        //计算两个值的积
                        result=result-factorOneInt1;
                        tv2.setText("共"+ba+"件，合计¥："+result+"元");//计算价格

                        //刷新item
                        updateProgressPartly(position,position);

                        // TODO: Implement this method
                    }
                }
            });

            //加

            viewHolder.ib2.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    if(selectedNum == max_num){
                        ToastUtil.showShortToast(BuyingSeedsActivity.this,"已经达到土地可种植上限啦！");
                    }else {
                        finalViewHolder.tv.setText(""+(Integer.valueOf(finalViewHolder.tv.getText().toString())+1));
                        ba++;
                        selectedNum++;
                        map.get(post.getMc()).set(2, finalViewHolder.tv.getText().toString());
                        tv1.setText(ba+"");//购物车数量

                        //购物车数量
                        int fa = Integer.parseInt(sl);
                        //计算两个值的积
                        int re = fa + 1;


                        factorOneInt1 = Double.parseDouble(post.getJg());
                        //计算两个值的积
                        result = factorOneInt1 + result;
                        tv2.setText("共"+ba+"件，合计¥："+result+"元");//计算价格

                        //刷新item
                        updateProgressPartly(position,position);
                        // TODO: Implement this method

                    }
                }
            });



            return convertView;
        }

        //局部更新
        private void updateProgressPartly(int progress,int position){
            int firstVisiblePosition = list.getFirstVisiblePosition();
            int lastVisiblePosition = list.getLastVisiblePosition();
            if(position>=firstVisiblePosition && position<=lastVisiblePosition){
                View view = list.getChildAt(position - firstVisiblePosition);
                // ViewHolder viewHolder = null;
//                if(view.getTag() instanceof ViewHolder){
//                    ViewHolder vh = (ViewHolder)view.getTag();
//
//
//                    vh.tv.setText(ba+"");
//
//                }
            }
        }

        public class ViewHolder
        {
            public ImageView background;
            public TextView mc;
            public TextView cl;
            public TextView zq;
            public TextView jg;

            public ImageButton ib1;
            public TextView tv;
            public ImageButton ib2;

        }
    }


}
