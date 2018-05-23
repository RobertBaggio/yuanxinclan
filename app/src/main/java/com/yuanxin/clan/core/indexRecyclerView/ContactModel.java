/**
 * created by jiang, 16/2/2
 * Copyright (c) 2016, jyuesong@gmail.com All Rights Reserved.
 * *                #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 */

package com.yuanxin.clan.core.indexRecyclerView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 16/2/2.
 */
public class ContactModel {
//    private CreaterEntity creater;
//
//
//    private List<AdminsEntity> admins = new ArrayList<>();


    private List<MembersEntity> members = new ArrayList<>();

//    public void setCreater(CreaterEntity creater) {
//        this.creater = creater;
//    }
//
//    public void setAdmins(List<AdminsEntity> admins) {
//        this.admins = admins;
//    }

    public void setMembers(List<MembersEntity> members) {
        this.members = members;
    }

//    public CreaterEntity getCreater() {
//        return creater;
//    }
//
//    public List<AdminsEntity> getAdmins() {
//        return admins;
//    }

    public List<MembersEntity> getMembers() {
        return members;
    }

//    public static class CreaterEntity {
//
//        private String id;
//
//        private String username;
//
//        private String profession;
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public void setUsername(String username) {
//            this.username = username;
//        }
//
//        public void setProfession(String profession) {
//            this.profession = profession;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public String getUsername() {
//            return username;
//        }
//
//        public String getProfession() {
//            return profession;
//        }
//    }
//
//    public static class AdminsEntity {
//
//        private String id;
//
//        private String username;
//
//        private String profession;
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public void setUsername(String username) {
//            this.username = username;
//        }
//
//        public void setProfession(String profession) {
//            this.profession = profession;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public String getUsername() {
//            return username;
//        }
//
//        public String getProfession() {
//            return profession;
//        }
//    }
//    public static class MembersEntity{
//    private String newsTypeNm;
//    private String createDt;
//    private String content;
//
//    public String getNewsTypeNm() {
//        return newsTypeNm;
//    }
//
//    public void setNewsTypeNm(String newsTypeNm) {
//        this.newsTypeNm = newsTypeNm;
//    }
//
//    public String getCreateDt() {
//        return createDt;
//    }
//
//    public void setCreateDt(String createDt) {
//        this.createDt = createDt;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//}

    public static class MembersEntity implements Indexable {

        private String id;

        private String username;

        private String profession;//职业

        public String getSortLetters() {
            return sortLetters;
        }

        @Override
        public String getIndex() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }

        private String sortLetters; //把信件分类

        public void setId(String id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getProfession() {
            return profession;
        }
    }
}
