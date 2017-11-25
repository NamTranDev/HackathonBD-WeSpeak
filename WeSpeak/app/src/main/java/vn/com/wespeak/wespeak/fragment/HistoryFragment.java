package vn.com.wespeak.wespeak.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.adapter.HistoryAdapter;
import vn.com.wespeak.wespeak.model.User;

public class HistoryFragment extends Fragment {

    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;
    @BindView(R.id.progress)
    ProgressBar mProgress;

    public static HistoryFragment getInstance(){
        return new HistoryFragment();
    }

    private Unbinder unbinder;
    private HistoryAdapter mHistoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvHistory.setLayoutManager(new LinearLayoutManager(getThis()));
        mHistoryAdapter = new HistoryAdapter(new ArrayList<>());
        mRvHistory.setAdapter(mHistoryAdapter);
    }

    protected Activity getThis() {
        return getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void initData(List<User> users) {
        if (getView() != null){
            mRvHistory.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
            if (mHistoryAdapter != null){
                mHistoryAdapter.updateData(users);
            }
        }

    }
}
