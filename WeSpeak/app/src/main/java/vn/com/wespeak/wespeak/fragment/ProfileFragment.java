package vn.com.wespeak.wespeak.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.com.wespeak.wespeak.Constant;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.ShapedImageView;
import vn.com.wespeak.wespeak.model.User;

public class ProfileFragment extends Fragment {

    @BindView(R.id.iv_user)
    ShapedImageView mIvUser;
    @BindView(R.id.iv_national_flag)
    AppCompatImageView mIvNationalFlag;
    @BindView(R.id.tv_user)
    TextView mTvUser;
    @BindView(R.id.tv_conversation)
    TextView mTvConversation;
    @BindView(R.id.tv_total_hour)
    TextView mTvTotalHour;
    @BindView(R.id.rating)
    AppCompatRatingBar mRating;
    @BindView(R.id.tv_rating)
    TextView mTvRating;
    @BindView(R.id.contain_profile)
    NestedScrollView mContain;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    private Unbinder unbinder;

    public static ProfileFragment getInstance(User user){
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA.PROFILE_USER,user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void initData(User user){
        if (user != null && getView() != null){
            mRating.setRating(user.rating);
            mTvRating.setText(String.valueOf(user.rating));
            mTvUser.setText(user.name);
            if (user.country.equals("VN")){
                mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getThis(),R.drawable.vietnam_national_flag));
            }else if (user.country.equals("Germany")){
                mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getThis(),R.drawable.germany_national_flag));
            }else {
                mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getThis(),R.drawable.usa_national_flag));
            }
        }
    }

    protected Activity getThis() {
        return getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
