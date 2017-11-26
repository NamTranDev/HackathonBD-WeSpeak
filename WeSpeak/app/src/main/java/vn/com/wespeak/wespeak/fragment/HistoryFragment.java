package vn.com.wespeak.wespeak.fragment;

import android.app.Activity;
import android.content.Intent;
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
import vn.com.wespeak.wespeak.Constant;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.activity.ConversationActivity;
import vn.com.wespeak.wespeak.adapter.HistoryAdapter;
import vn.com.wespeak.wespeak.model.User;

public class HistoryFragment extends Fragment implements HistoryAdapter.OnItemClick {

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
        mHistoryAdapter.setOnItemClick(this);
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

    @Override
    public void onItemClick(User user) {
        Intent intent = new Intent(getThis(),ConversationActivity.class);
        intent.putExtra(Constant.EXTRA.USER_CONVERSATION,user);
        getThis().startActivity(intent);
    }
}
