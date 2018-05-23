package com.yuanxin.clan.core.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanxin.clan.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lenovo1 on 2017/2/28.
 */
public class FragmentMyMarketAll extends Fragment {


    //    private List<MyAllCrowdFundingEntity> myAllCrowdFundingEntities = new ArrayList<>();
//    private MyAllCrowdFundingAdapter myAllCrowdFundingAdapter;
    public Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_market_all, container, false);
//        initRecyclerView();
        unbinder = ButterKnife.bind(this, view);
        return view;

    }
}
