package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);//获取个人信息

        return null;
    }

    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfoByDB(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);

        return null;
    }

    /**
     * set user avatar
     *
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView,String image,String phone) {
//        EaseUser user = getUserInfo(username);
//        if (user != null && user.getAvatar() != null) {
//            try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
//                Glide.with(context).load(avatarResId).into(imageView);
//            } catch (Exception e) {
//                //use default avatar
//                Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(imageView);
//            }
//        } else {
//            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
//        }
        /**
         * 如果传入是图片链接，则展示图片，否则获取用户信息
         * */
        if (username.startsWith("http")) {
            Glide.with(context).load(username).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(imageView);
        } else {
            EaseUser user = getUserInfo(username);
            String simage = user.getAvatar();
            if (TextUtils.isEmpty(user.getAvatar())&&username.equals(phone)){
                simage = image;
            }
//            EaseUser user = EaseUserUtils.getUserInfo(toChatUsername);

//        Log.v("Lgq","ger============"+user.getAvatar()+"...  "+username+"    "+  simage);//聊天头像

            if (user == null) {
                Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
                return;
            } else  {
                if (simage == null && !"系统管理员".equals(username)) {
                    user = getUserInfoByDB(username);
                    Glide.with(context).load(simage).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(imageView);
                } else if (simage != null) {
                    try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
                        Glide.with(context).load(simage).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(imageView);

                    } catch (Exception e) {
                        //use default avatar 设置个人头像
                        Glide.with(context).load(simage).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(imageView);
                    }
                } else {
                    Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
                }
            }
        }
    }

    /**
     * set group avatar
     *
     * @param username
     */
    public static void setGroupAvatar(Context context, String username, ImageView imageView) {
        EaseUser user = getUserInfoByDB(username);
        if (user != null && user.getAvatar() != null) {
            try {
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_groups_icon).into(imageView);
            }
        } else {
            Glide.with(context).load(R.drawable.ease_groups_icon).into(imageView);
        }
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
//        if (textView != null) {
//            EaseUser user = getUserInfo(username);
//            if (user != null && user.getNick() != null) {
//                textView.setText(user.getNick());
//            } else {
//                textView.setText(username);
//            }
//        }
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user == null) {
                textView.setText(username);
                return;
            }
            if (user.getNick() == null) {
                user = getUserInfoByDB(username);
            }
            if (user != null && user.getNick() != null) {
                textView.setText(user.getNick());
            } else {
                textView.setText(username);
            }
        }
    }

}
