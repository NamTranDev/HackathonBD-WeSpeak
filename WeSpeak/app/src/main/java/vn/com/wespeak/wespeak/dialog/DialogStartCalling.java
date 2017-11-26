package vn.com.wespeak.wespeak.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.com.wespeak.wespeak.Constant;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.ShapedImageView;
import vn.com.wespeak.wespeak.activity.MainActivity;
import vn.com.wespeak.wespeak.model.User;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DialogStartCalling extends DialogFragment {

    @BindView(R.id.iv_partner)
    ShapedImageView mIvPartner;
    @BindView(R.id.iv_national_flag)
    AppCompatImageView mIvNationalFlag;
    @BindView(R.id.tv_partner)
    TextView mTvPartner;
    @BindView(R.id.tv_user)
    TextView mTvUser;
    Unbinder unbinder;

    private onDialogStartCallingCallBack onDialogStartCallingCallBack;
    private User user;

    public static DialogStartCalling getInstance(User user){
        DialogStartCalling dialogStartCalling = new DialogStartCalling();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA.DIALOG_START_CALLING,user);
        dialogStartCalling.setArguments(bundle);
        return dialogStartCalling;
    }

    public void setOnDialogStartCallingCallBack(DialogStartCalling.onDialogStartCallingCallBack onDialogStartCallingCallBack) {
        this.onDialogStartCallingCallBack = onDialogStartCallingCallBack;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getThis());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.Dialog;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_start_calling, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            user = getArguments().getParcelable(Constant.EXTRA.DIALOG_START_CALLING);
            if (user != null){
                switch (user.id){
                    case Constant.TypeLogin.LEANER:
                        mTvUser.setText(getThis().getString(R.string.start_learner));
                        break;
                    case Constant.TypeLogin.TEACHER:
                        mTvUser.setText(getThis().getString(R.string.start_teacher));
                        break;
                }
                if (!TextUtils.isEmpty(user.url)){
                    Picasso.with(getContext()).load(user.url).placeholder(R.drawable.image_holder).error(R.drawable.image_holder).into(mIvPartner);
                }
                mTvPartner.setText(user.name);
                if (user.country.equals("VN")){
                    mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getThis(),R.drawable.vietnam_national_flag));
                }else if (user.country.equals("Germany")){
                    mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getThis(),R.drawable.germany_national_flag));
                }else {
                    mIvNationalFlag.setImageDrawable(ContextCompat.getDrawable(getThis(),R.drawable.usa_national_flag));
                }
            }
        }
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        if (window != null) {
            Point size = new Point();
            // Store dimensions of the screen in `size`
            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);
            // Set the width of the dialog proportional to 75% of the screen width
            if (maxWidth() != 0 && maxHeight() != 0) {
                window.setLayout((int) (size.x * maxWidth()), (int) (size.y * maxHeight()));
            } else if (maxWidth() != 0) {
                window.setLayout((int) (size.x * maxWidth()), WRAP_CONTENT);
            } else if (maxHeight() != 0) {
                window.setLayout(WRAP_CONTENT, (int) (size.y * maxHeight()));
            } else {
                window.setLayout(WRAP_CONTENT, WRAP_CONTENT);
            }
            window.setGravity(Gravity.CENTER);
            // Call super onResume after sizing

        }
        super.onResume();
    }

    protected Activity getThis() {
        return getActivity();
    }

    protected float maxHeight() {
        return 0f;
    }

    protected float maxWidth() {
        return 0.7f;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_start)
    public void onViewClicked() {
        if (onDialogStartCallingCallBack != null)
            onDialogStartCallingCallBack.onDialogStartListener(this,user);
    }

    public interface onDialogStartCallingCallBack{
        void onDialogStartListener(DialogStartCalling dialog,User user);
    }
}
