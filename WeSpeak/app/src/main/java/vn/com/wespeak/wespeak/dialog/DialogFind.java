package vn.com.wespeak.wespeak.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.com.wespeak.wespeak.Constant;
import vn.com.wespeak.wespeak.ProgressWheel;
import vn.com.wespeak.wespeak.R;
import vn.com.wespeak.wespeak.fragment.SearchFragment;

public class DialogFind extends DialogFragment {

    @BindView(R.id.pw_spinner)
    ProgressWheel mPwSpinner;
    @BindView(R.id.tv_find)
    TextView mTvFind;

    private Unbinder unbinder;

    private boolean wasSpinning = false;
    private DialogDismissListener mDialogDismissListener;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mDialogDismissListener != null){
                mDialogDismissListener.onFind();
                mDialogDismissListener.onDismiss();
                dismiss();
            }

        }
    };

    public static DialogFind getInstance(int type) {
        DialogFind dialogFind = new DialogFind();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA.DIALOG_FIND_TYPE,type);
        dialogFind.setArguments(bundle);
        return dialogFind;
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
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogFade;
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
        if (getArguments() != null){
            int type = getArguments().getInt(Constant.EXTRA.DIALOG_FIND_TYPE);
            switch (type){
                case Constant.TypeLogin.LEANER:
                    mTvFind.setText(this.getString(R.string.find_learner_dialog));
                    break;
                case Constant.TypeLogin.TEACHER:
                    mTvFind.setText(this.getString(R.string.find_teacher_dialog));
                    break;
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPwSpinner.setDelayMillis(5);
        mPwSpinner.startSpinning();

        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasSpinning) {
            mPwSpinner.startSpinning();
        }
        wasSpinning = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (wasSpinning = mPwSpinner.isSpinning()) mPwSpinner.stopSpinning();
    }

    protected Activity getThis() {
        return getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        handler.removeCallbacks(runnable);
        handler = null;
        runnable = null;
    }

    @OnClick({R.id.contain_dismiss})
    public void onViewClicked() {
        if (mDialogDismissListener != null)
            mDialogDismissListener.onDismiss();
        dismiss();
    }

    public void setDialogDismissListener(DialogDismissListener mDialogDismissListener) {
        this.mDialogDismissListener = mDialogDismissListener;
    }

    public interface DialogDismissListener {
        void onFind();
        void onDismiss();
    }
}
