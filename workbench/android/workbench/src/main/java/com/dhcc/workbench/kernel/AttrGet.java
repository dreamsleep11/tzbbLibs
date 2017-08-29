package com.dhcc.workbench.kernel;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by dreamsleep on 2017/8/17.
 */

public class AttrGet {
    private static Handler mainHandle;
    private static boolean debug;
    public static Context getContext() {
        return context;
    }
    public static void setDebug(boolean d){
        debug=d;
    }
    public static void setContext(Context context) {
        AttrGet.context = context;
        mainHandle = new Handler(Looper.getMainLooper());
    }

    private static Context context;

    public static void postMain(Runnable runnable) {
        mainHandle.post(runnable);
    }

    public static String getPackageName() {
        Log.e("context",(context==null)+"");
        return context.getPackageName();
    }

    public static boolean isDebug(){
        return debug;
    }
}
