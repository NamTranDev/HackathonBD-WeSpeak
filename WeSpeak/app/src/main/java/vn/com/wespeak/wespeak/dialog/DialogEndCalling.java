package vn.com.wespeak.wespeak.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.com.wespeak.wespeak.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DialogEndCalling extends DialogFragment {

    private Unbinder unbinder;
    private OnDialogEndCall onDialogEndCall;

    public static DialogEndCalling getInstance(){
        return new DialogEndCalling();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getThis());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.Dialog;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_end_calling, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setOnDialogEndCall(OnDialogEndCall onDialogEndCall) {
        this.onDialogEndCall = onDialogEndCall;
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
        return 0f;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_end, R.id.contain_layout, R.id.tv_dismiss, R.id.contain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_end:
                if (onDialogEndCall != null)
                    onDialogEndCall.onEnd();
                dismiss();
                break;
            case R.id.contain_layout:
                break;
            case R.id.tv_dismiss:
            case R.id.contain:
                dismiss();
                break;
        }
    }

    public interface OnDialogEndCall{
        void onEnd();
    }
}
