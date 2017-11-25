package vn.com.wespeak.wespeak.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import vn.com.wespeak.wespeak.Logger;
import vn.com.wespeak.wespeak.R;

public class PermissionHelper {
    private static String TAG = PermissionHelper.class.getSimpleName();

    private static final int REQUEST_ALL = 99;
    private static final String[] PERMISSIONS_ALL = {
            Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
    };

    private Activity activity;

    public PermissionHelper(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!PermissionUtil.hasPermissions(activity, PERMISSIONS_ALL)) {
                Logger.debug("All required Permission have not been granted.");
                return false;
            } else {
                Logger.debug("All required Permission have been granted.");
                return true;
            }
        } else {
            return true;
        }
    }

    public void requestAllPermission() {
        if (PermissionUtil.shouldShowPermissionRationale(activity, PERMISSIONS_ALL)) {
            Logger.debug(TAG, "Displaying permission rationale");

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            alertDialogBuilder.setMessage(activity.getResources().getString(R.string.nte_permission_message))
                    .setCancelable(false)
                    .setPositiveButton(activity.getResources().getString(R.string.nte_permission_button_positive),
                            (dialog, which) -> ActivityCompat
                                    .requestPermissions(activity, PERMISSIONS_ALL,
                                            REQUEST_ALL))
                    .setNegativeButton(activity.getResources().getString(R.string.nte_permission_button_negative),
                            (dialog, which) -> activity.finish());
            alertDialogBuilder.show();
        } else {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_ALL, REQUEST_ALL);
        }
    }

    public boolean onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ALL:
                Logger.debug("Received response for All permission request.");
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    Logger.debug("All permissions were granted.");
                    return true;
                } else {
                    Logger.debug("All permissions were NOT granted.");
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    alertDialogBuilder.setMessage(activity.getResources().getString(R.string.nte_permission_message))
                            .setCancelable(false)
                            .setPositiveButton(activity.getResources().getString(R.string.nte_permission_button_positive),
                                    (dialog, which) -> ActivityCompat
                                            .requestPermissions(activity, PERMISSIONS_ALL,
                                                    REQUEST_ALL))
                            .setNegativeButton(activity.getResources().getString(R.string.nte_permission_button_negative),
                                    (dialog, which) -> activity.finish());
                    alertDialogBuilder.show();
                    return false;
                }
            default:
                return false;
        }
    }
}
