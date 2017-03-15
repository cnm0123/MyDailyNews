package util;

import android.util.Log;

/**
 * Created by Administrator on 2017/2/13 0013.
 */

public class LogUtils {
    private static boolean debug = true ;
    public static void d(Object obj, String msg){
        if (debug) {
            Log.d(obj.getClass().getSimpleName(), msg);
        }
    }
}
