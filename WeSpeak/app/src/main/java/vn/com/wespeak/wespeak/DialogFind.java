package vn.com.wespeak.wespeak;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DialogFind extends DialogFragment {

    private static final float BLUR_RADIUS = 20.5f;
    @BindView(R.id.pw_spinner)
    ProgressWheel mPwSpinner;

    private Unbinder unbinder;

    private boolean wasSpinning = false;
    private DialogDismissListener mDialogDismissListener;

    public static DialogFind getInstance() {
        return new DialogFind();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Dialog dialog = new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                if (mDialogDismissListener != null)
                    mDialogDismissListener.onDismiss();
                super.onBackPressed();
            }
        };
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.Dialog;
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getThis(), R.drawable.bg_find));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPwSpinner.setDelayMillis(5);
        mPwSpinner.startSpinning();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(wasSpinning) {
            mPwSpinner.startSpinning();
        }
        wasSpinning = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(wasSpinning = mPwSpinner.isSpinning()) mPwSpinner.stopSpinning();
    }

    protected Activity getThis() {
        return getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_dismiss)
    public void onViewClicked() {
        if (mDialogDismissListener != null)
            mDialogDismissListener.onDismiss();
        dismiss();
    }

    public void setDialogDismissListener(DialogDismissListener mDialogDismissListener) {
        this.mDialogDismissListener = mDialogDismissListener;
    }

    public interface DialogDismissListener{
        void onDismiss();
    }
}
