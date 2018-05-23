package com.yuanxin.clan.core.activity_groups;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.yuanxin.clan.core.news.bean.NewsType;
import com.yuanxin.clan.core.util.MyShareUtil;

import java.util.List;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/3/27 0027 11:10
 */

public class GroupsFragmentAdapter extends FragmentPagerAdapter {
    private List<NewsType> titleList;
    boolean[] fragmentsUpdateFlag = { false, false, false, false };
    private FragmentManager fm;

    public GroupsFragmentAdapter(FragmentManager fragmentManager, List<NewsType> titleList) {
        super(fragmentManager);
        this.titleList = titleList;
        this.fm = fragmentManager;


        StringBuffer selectstring = new StringBuffer();
        for (int c = 0;c<titleList.size();c++){
            if (c==titleList.size()-1){
                selectstring.append(titleList.get(c).getNewsTypeId());
            }else {
                selectstring.append(titleList.get(c).getNewsTypeId()+",");
            }
        }
        String onetypes = selectstring.toString();

        MyShareUtil.sharedPstring("seletypes",onetypes);

    }

    @Override
    public Fragment getItem(int position) {
        return GroupsFragmentone.newInstance(titleList.get(position).getNewsTypeId());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //得到缓存的fragment
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        //得到tag，这点很重要
//        String fragmentTag = fragment.getTag();
        String fragmentTag = fragment.getTag();
        //如果这个fragment需要更新

        FragmentTransaction ft = fm.beginTransaction();
        //移除旧的fragment
        ft.remove(fragment);
        //换成新的fragment
        fragment = GroupsFragmentone.newInstance(titleList.get(position).getNewsTypeId());

        //添加新fragment时必须用前面获得的tag，这点很重要
        ft.add(container.getId(), fragment, fragmentTag);
        ft.attach(fragment);
        ft.commit();

        return fragment;
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position).getNewsTypeNm();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
