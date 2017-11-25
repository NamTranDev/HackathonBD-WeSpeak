package vn.com.wespeak.wespeak;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchFragment extends Fragment implements DialogFind.DialogDismissListener {

    private Unbinder unbinder;
    private OnSearchListener mOnSearchListener;

    public static SearchFragment getInstance(){
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_find)
    public void onViewClicked() {
        DialogFind dialog = DialogFind.getInstance();
        dialog.setDialogDismissListener(this);
        if (mOnSearchListener != null)
            mOnSearchListener.onShowDialog();
        dialog.show(getActivity().getSupportFragmentManager(),"Dialog Find");
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
    }
}
