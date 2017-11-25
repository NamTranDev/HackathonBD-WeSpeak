package vn.com.wespeak.wespeak;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;

public class Blur {

    private static final float BLUR_RADIUS = 25f; // 25 is maximum radius

    // returns blur drawable if api >= 17. returns original drawable if not.
    @Nullable
    public static Drawable applyBlur(Drawable drawable, Context context) {
        Bitmap fromDrawable = drawableToBitmap(drawable);
        int width = Math.round(fromDrawable.getWidth() * 0.8f);
        int height = Math.round(fromDrawable.getHeight() * 0.8f);

        Bitmap inBitmap = Bitmap.createScaledBitmap(fromDrawable, width, height, false);
        Bitmap outBitmap = Bitmap.createBitmap(inBitmap);

        RenderScript renderScript = RenderScript.create(context);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));

        Allocation in = Allocation.createFromBitmap(renderScript, inBitmap);
        Allocation out = Allocation.createFromBitmap(renderScript, outBitmap);

        blur.setRadius(BLUR_RADIUS);
        blur.setInput(in);
        blur.forEach(out);

        out.copyTo(outBitmap);
        renderScript.destroy();

        return new BitmapDrawable(context.getResources(), outBitmap);
    }

    // returns blur bitmap if api >= 17. returns original bitmap if not.
    @Nullable
    public static Bitmap applyBlur(Bitmap bitmap, Context context) {
        RenderScript rs = RenderScript.create(context);
        Bitmap bitmapCopy;
        int width = Math.round(bitmap.getWidth() * 0.8f);
        int height = Math.round(bitmap.getHeight() * 0.8f);

        if (bitmap.getConfig() == Bitmap.Config.ARGB_8888) {
            bitmapCopy = bitmap;
        } else {
            bitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }

        Bitmap outBitmap = Bitmap.createBitmap(width, height, bitmapCopy.getConfig());

        Allocation in = Allocation.createFromBitmap(rs, bitmapCopy,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        Allocation out = Allocation.createTyped(rs, in.getType());

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, out.getElement());
        blur.setRadius(BLUR_RADIUS);
        blur.setInput(in);
        blur.forEach(out);

        out.copyTo(bitmap);
        rs.destroy();

        return outBitmap;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
