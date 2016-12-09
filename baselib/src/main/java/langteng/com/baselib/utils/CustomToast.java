package langteng.com.baselib.utils;

/**
 * Created by Administrator on 2015/8/31.
 */

import android.content.Context;
import android.os.Handler;

import langteng.com.baselib.GlobalApplication;


public class CustomToast {
    private static MyCustomToast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void showToast(Context mContext, String text, int duration) {
        if (mContext == null) {
            mContext = GlobalApplication.getInstance();
        }
        mHandler.removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(text);
        } else {
            if (duration == 1) {
                mToast = MyCustomToast.makeText(mContext, text, MyCustomToast.LENGTH_LONG);
            } else {
                mToast = MyCustomToast.makeText(mContext, text, MyCustomToast.LENGTH_SHORT);
            }
            mHandler.postDelayed(r, 1000);
        }
        mToast.show();
    }

    public static void showToast(Context mContext, int resId, int duration) {
        if (mContext != null)
            showToast(mContext.getApplicationContext(), GlobalApplication.getInstance().getString(resId), duration);
    }

    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
