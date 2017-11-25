package vn.com.wespeak.wespeak;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
