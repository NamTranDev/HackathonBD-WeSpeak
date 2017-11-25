package vn.com.wespeak.wespeak;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;

public class LoadingDialog {

    private Dialog dialog;
    private Activity mActivity;

    @SuppressLint("InflateParams")
    public LoadingDialog(Activity cxt) {
        mActivity = cxt;

        dialog = new Dialog(cxt, R.style.LoadingTheme);
        View view = LayoutInflater.from(cxt).inflate(
                R.layout.nte_include_progress, null);

        AVLoadingIndicatorView mLoadingView = view.findViewById(R.id.progress_loading);
        mLoadingView.setVisibility(View.VISIBLE);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.getAttributes().dimAmount = 0.5f;
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
    }


    public void showDialog() {
        try {
            if (!mActivity.isFinishing()) {
                dialog.show();
            }

        } catch (Exception e) {
            Log.d("LoadingDialog", e.getMessage());
        }
    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    public void hideDialog() {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
        } catch (Exception ignored) {
        }
    }

    public void cancelDialog() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.cancel();
                dialog = null;
            } catch (Exception ignored) {
            }
        }
    }


}
