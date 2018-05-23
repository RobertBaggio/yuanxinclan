package com.yuanxin.clan.core.activity_groups;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/3/27 0027 17:03
 */

public class GEmployee {
    GroupsFragmentone fragmentone;

    public GEmployee zhuce(GroupsFragmentone jiekou, GEmployee e){
            this.fragmentone=jiekou;
        return e;
         }

    public void dosomething(String name1,int statusid,int timeid ){

        fragmentone.show(name1,statusid,timeid);
    }

}
