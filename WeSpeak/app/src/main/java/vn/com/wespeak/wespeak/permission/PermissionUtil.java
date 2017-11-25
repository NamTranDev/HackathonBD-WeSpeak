package vn.com.wespeak.wespeak.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

class PermissionUtil {

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    static boolean hasPermissions(Activity activity, @NonNull String... permissions) {
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, permission)) {
                return false;
            }
        }
        return true;
    }

    static boolean shouldShowPermissionRationale(Activity activity, @NonNull String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

}
