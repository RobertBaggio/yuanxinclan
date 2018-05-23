package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.GroupMemberBean;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo1 on 2017/5/16.
 */
public class NewSortGroupMemberAdapter extends BaseAdapter implements SectionIndexer {
    private List<GroupMemberBean> allFriendList = null;
    private Context mContext;
    private List<GroupMemberBean> checkList = new ArrayList<GroupMemberBean>();
    private OnMyCheckedChangeListener mOnMyCheckedChangeListener;
    private List<GroupMemberBean> alreadyFriendList = new ArrayList<GroupMemberBean>();

    public NewSortGroupMemberAdapter(Context mContext, List<GroupMemberBean> allFriendList, List<GroupMemberBean> alreadyFriendList) {
        this.mContext = mContext;
        this.allFriendList = allFriendList;
        this.alreadyFriendList = alreadyFriendList;
    }

    public void setCheck() {
        for (GroupMemberBean gbAlready: alreadyFriendList) {
            gbAlready.setIs_checked(true);
        }
        checkList.clear();
        checkList.addAll(alreadyFriendList);
    }

    /**
     * ��ListView���ݷ����仯ʱ,���ô˷���������ListView
     *
     * @param list
     */
    public void updateListView(List<GroupMemberBean> list) {
        this.allFriendList = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.allFriendList.size();
    }

    public Object getItem(int position) {
        return allFriendList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        final ViewHolder viewHolder;
        final GroupMemberBean mContent = allFriendList.get(position);
        setCheck();
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_new_group_member, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.image = (MLImageView) view.findViewById(R.id.title_image);
            viewHolder.smallImage = (ImageView) view.findViewById(R.id.item_friend_list_choose_image);
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.item_new_group_member_layout);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // ����position��ȡ���������ĸ��Char asciiֵ
        int section = getSectionForPosition(position);

        // �����ǰλ�õ��ڸ÷�������ĸ��Char��λ�� ������Ϊ�ǵ�һ�γ���
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        if (mContent.is_checked()) { //如果是选中就选中
            viewHolder.smallImage.setVisibility(View.VISIBLE);
        } else {//如果不是选中就不选中
            viewHolder.smallImage.setVisibility(View.GONE);
        }
        viewHolder.tvTitle.setText(this.allFriendList.get(position).getName());
        ImageManager.loadBitmap(mContext, this.allFriendList.get(position).getImage(), R.drawable.chat_menber_box, viewHolder.image);
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mContent.is_checked()) {
                    mContent.setIs_checked(true);
                    checkList.add(mContent);
                    viewHolder.smallImage.setVisibility(View.VISIBLE);
                } else {
                    mContent.setIs_checked(false);
                    checkList.remove(mContent);//搜集删除
//                        entityList.remove(entity);// 直接删除
                    viewHolder.smallImage.setVisibility(View.GONE);

                }
                mOnMyCheckedChangeListener.onCheckedChange(checkList);
            }
        });

        return view;

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        ImageView image;
        ImageView smallImage;
        RelativeLayout relativeLayout;
    }

    /**
     * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
     */
    public int getSectionForPosition(int position) {
        return allFriendList.get(position).getSortLetters().charAt(0);
    }

    /**
     * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = allFriendList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    public interface OnMyCheckedChangeListener {
        void onCheckedChange(List<GroupMemberBean> checkList);
    }

    public void setOnMyCheckedChangeListener(OnMyCheckedChangeListener mOnMyCheckedChangeListener) {
        this.mOnMyCheckedChangeListener = mOnMyCheckedChangeListener;
    }
}
