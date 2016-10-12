package com.soundai.simplewaterwave;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by fez on 2016/10/11.
 */

public class Utils {
    public static int getScreenWidthPixels(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    public static int dip2px(Context context,int dip){
        float density;
        int result;
        try {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            density = dm.density;
        } catch (Exception e) {
            e.printStackTrace();
            density = DisplayMetrics.DENSITY_DEFAULT;
        }
        result = (int) (dip * density + 0.5f);
        return result;
    }
}
