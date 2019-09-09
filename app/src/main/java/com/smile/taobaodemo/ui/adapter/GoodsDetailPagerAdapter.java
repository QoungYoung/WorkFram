package com.smile.taobaodemo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.smile.taobaodemo.base.Type;
import com.smile.taobaodemo.model.entity.CommentBase;
import com.smile.taobaodemo.model.entity.DescBase;
import com.smile.taobaodemo.ui.activity.ListActivity;
import com.smile.taobaodemo.ui.fragment.GoodsCommentFragment;
import com.smile.taobaodemo.ui.fragment.GoodsDescFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Smile Wei
 * @since 2016/8/6.
 */
public class GoodsDetailPagerAdapter extends FragmentPagerAdapter {
    private String[] title;

    public GoodsDetailPagerAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                List<DescBase> list = new ArrayList<>();
                DescBase bean = new DescBase(DescBase.TYPE_DESC, "我们农场全部采用了高保温大棚，棚内形成独立的生态圈\n" +
                        "有机蔬菜完全不加农药\n" +
                        "让您一年四季体验到春天的感觉\n" +
                        "吃到绝对放心的有机蔬菜\n" +
                        "不管是白菜，萝卜，茄子，豆角，统统都能种\n" +
                        "我们农场还支持顺丰包邮\n" +
                        "让您第一天收菜，第二天上桌！\n" +
                        "农场大哥热情好客\n" +
                        "欢迎来我们农场实地做客\n" +
                        "让您感受到家的感觉！\n", "");
                list.add(bean);
                list.add(new DescBase(DescBase.TYPE_PICTURE, "", "http://dpic.tiankong.com/9l/nc/QJ8140340820.jpg"));
                list.add(new DescBase(DescBase.TYPE_PICTURE, "", "http://dpic.tiankong.com/xz/w8/QJ9109041598.jpg"));
                list.add(new DescBase(DescBase.TYPE_PICTURE, "", "http://dpic.tiankong.com/8g/ze/QJ6228359137.jpg"));
                list.add(new DescBase(DescBase.TYPE_PICTURE, "", "http://dpic.tiankong.com/r0/j6/QJ8138126869.jpg"));
                list.add(new DescBase(DescBase.TYPE_PICTURE, "", "http://dpic.tiankong.com/5c/al/QJ6685264120.jpg"));
                return GoodsDescFragment.newInstance(list);
            }
            default: {
                List<CommentBase> list = new ArrayList<>();
                List<String> stringList = new ArrayList<>();
                stringList.add("很棒的农场，我已经包了好几块地了！");
                stringList.add("儿子喜欢吃萝卜，买了几块地种萝卜，发现种的挺好吃的");
                stringList.add("刚刚买的地，先试试再追评");
                stringList.add("农场主大哥很热情啊，很耐心的给我解答，而且蔬菜也长得非常好！赞！");
                for (int i = 0; i < 20; i++) {
                    CommentBase bean = new CommentBase();

                    bean.setContent(stringList.get(new Random().nextInt(4)));
                    bean.setPicture("https://cdn.56come.cn/upload/6571/2016/0428/8uxd63yvy4umfwuprwhsuqb5g.thumb.jpg");
                    bean.setProduct_score(5);
                    bean.setType(Type.TYPE_SHOW);
                    bean.setCreate_date(System.currentTimeMillis() - 10000000 * i);
                    bean.setUser_name("匿名用户");
                    list.add(bean);
                }
                return GoodsCommentFragment.newInstance(list);
            }
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
