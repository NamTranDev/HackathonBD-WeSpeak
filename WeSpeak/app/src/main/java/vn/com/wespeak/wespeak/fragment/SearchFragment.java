package vn.com.wespeak.wespeak.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.com.wespeak.wespeak.Constant;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.dialog.DialogFind;
import vn.com.wespeak.wespeak.dialog.DialogStartCalling;
import vn.com.wespeak.wespeak.model.User;

public class SearchFragment extends Fragment implements DialogFind.DialogDismissListener {

    @BindView(R.id.contain_search)
    RelativeLayout mContain;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.tv_find)
    TextView mTvFind;

    private Unbinder unbinder;
    private OnSearchListener mOnSearchListener;
    private int type;

    public static SearchFragment getInstance(int type){
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA.SEARCH_TYPE,type);
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgress.setVisibility(View.GONE);
        mContain.setVisibility(View.VISIBLE);

        if (getArguments() != null){
            type = getArguments().getInt(Constant.EXTRA.SEARCH_TYPE);
            switch (type){
                case Constant.TypeLogin.LEANER:
                    mTvFind.setText(this.getString(R.string.find_learner));
                    break;
                case Constant.TypeLogin.TEACHER:
                    mTvFind.setText(this.getString(R.string.find_teacher));
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_find)
    public void onViewClicked() {
        DialogFind dialog = DialogFind.getInstance(type);
        dialog.setDialogDismissListener(this);
        if (mOnSearchListener != null)
            mOnSearchListener.onShowDialog();
        dialog.show(getActivity().getSupportFragmentManager(),"Dialog Find");
    }

    @Override
    public void onFind() {
        if (mOnSearchListener != null)
            mOnSearchListener.onFind();

    }

    @Override
    public void onDismiss() {
        if (mOnSearchListener != null)
            mOnSearchListener.onDismissDialog();
    }

    public void setmOnSearchListener(OnSearchListener mOnSearchListener) {
        this.mOnSearchListener = mOnSearchListener;
    }

    public interface OnSearchListener{
        void onShowDialog();
        void onDismissDialog();
        void onFind();
    }

    protected Activity getThis() {
        return getActivity();
    }
}
