package com.yuanxin.clan.core.zxing;

import android.content.Intent;
import android.os.Bundle;

import com.yuanxin.clan.core.activity.LoginActivity;

public abstract class SubBasicFragment extends BaseFragment {
    private static int nOpeningSecondCount = 0;
    private boolean isHaveQuicklyBack = true;
    private boolean isCanClose=true;

    private Observer quicklyBackObserver = new Observer() {
        @Override
        public void update(int id) {
            if (isCanClose)
                getActivity().finish();
        }
    };

    public void toLogin(){
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        QuicklyBackNotice.getInstance().addObserver(quicklyBackObserver);
        nOpeningSecondCount++;

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        QuicklyBackNotice.getInstance().removeObserver(quicklyBackObserver);
        nOpeningSecondCount--;
        if (nOpeningSecondCount < 0)
            nOpeningSecondCount = 0;
        super.onDetach();
    }

    public void onBackPressed() {
//        KeyBoardControllerUtil.Hide(mContext, contentView);
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            getActivity().finish();
            return;
        }
        getFragmentManager().popBackStack();
    }

    public void setCanClose(boolean canClose) {
        isCanClose = canClose;
    }

    public void setIsHaveQuicklyBack(boolean isHaveQuicklyBack) {
        this.isHaveQuicklyBack = isHaveQuicklyBack;
    }
}
