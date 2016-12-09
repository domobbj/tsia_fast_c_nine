package langteng.com.baselib.utils;

import android.app.Activity;
import android.content.Intent;

import java.util.Iterator;
import java.util.Map;

/**
 * JHS
 */
public class ActivityJump {
    /****
     * Activity  跳转
     * ***/
    public static void getInsanceJump(Activity activity, Class cls, Map<String, String> intentExtra, boolean isActivityFinish) {
        Intent intent = new Intent();
        if (intentExtra != null) {
            Iterator it = intentExtra.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = intentExtra.get(key);
                intent.putExtra(key, value);
            }
        }
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        if (isActivityFinish) {
            activity.finish();
        }
    }
}
