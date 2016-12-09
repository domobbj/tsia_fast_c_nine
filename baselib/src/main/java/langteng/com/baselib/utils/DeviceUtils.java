package langteng.com.baselib.utils;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import java.io.File;
import java.net.NetworkInterface;
import java.net.SocketException;

import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.utils.anti.emulator.FindEmulator;

/**
 * Created by zhangyiqun on 14-9-1.
 * 获取手机信息  包括px,dp,sp单位转换
 */
public class DeviceUtils {


    /**
     * 获取屏幕的高度
     *
     * @return 屏幕的高度
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = GlobalApplication.getInstance().getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    public static Boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getVersionName() {
        try {
            String versionName = GlobalApplication.getInstance().getPackageManager().getPackageInfo(GlobalApplication.getInstance().getPackageName(), 0).versionName;
            if (null != versionName && 0 < versionName.length())
                return versionName;
            else
                return "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static int getVersionCode() {
        try {
            return GlobalApplication.getInstance().getPackageManager().getPackageInfo(
                    GlobalApplication.getInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getPackageName() {
        return GlobalApplication.getInstance().getApplicationInfo().packageName;
    }



    /***
     * 判断是真机还是模拟器
     **/
    public static boolean isEmulatorDetected(Context context) {
        if (FindEmulator.hasKnownDeviceId(context)
                || FindEmulator.hasKnownImsi(context)
                || FindEmulator.hasEmulatorBuild(context)
                || FindEmulator.hasKnownPhoneNumber(context) || FindEmulator.hasPipes()
                || FindEmulator.hasQEmuDrivers() || FindEmulator.hasEmulatorAdb()
                || FindEmulator.hasGenyFiles() || FindEmulator.hasBaseband()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否已经root
     *
     * @return
     */
    public static boolean isRootSystem() {
        File f = null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/",
                "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getWidth(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        return width;
    }

    public static int defaultWidth;
    public static int defaultHeight;

    /****
     * 获取字符串占的屏幕宽度
     **/

    public float getTextWidth(Context context, String text, int textSize) {
        TextView textView = new TextView(context);
        TextPaint textPaint = textView.getPaint();
        float textPaintWidth = textPaint.measureText(text);
        return textPaintWidth;
    }

    public static void setScreenSize(Point p) {
        defaultWidth = p.x - 100;
        defaultHeight = p.y;
        Logger.i("klx_app", "screen size: " + p);
    }

    public static void setScreenSize(int width, int height) {
        defaultWidth = width - 100;
        defaultHeight = height;
        Logger.i("klx_app", "screen size: width=" + width + ", height=" + height);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dp(float pxVal) {
        final float scale = GlobalApplication.getInstance().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public static float getDensity(){
        return GlobalApplication.getInstance().getResources().getDisplayMetrics().density;
    }

    public static int dip2px(float dipValue) {
        final float scale = GlobalApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @return
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, GlobalApplication.getInstance().getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(float pxVal) {
        return (pxVal / GlobalApplication.getInstance().getResources().getDisplayMetrics().scaledDensity);
    }

    /****
     * 获取手机唯一序列号
     */
    public static String getImei() {
        String imei="";
        try {
            TelephonyManager tm = (TelephonyManager) GlobalApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            imei  = tm.getDeviceId();
        }catch (Exception e){
         e.printStackTrace();
        }
        return imei;
    }


    /****
     * 获取手机唯一序列号
     */
    public static String getMobleVersion() {
        String mobleVersion = android.os.Build.MODEL + " "+android.os.Build.VERSION.SDK;
        return mobleVersion;
    }

    /****
     * 获取手机手机系统号
     */
    public static String getOSVersion() {
        String mobleVersion = android.os.Build.VERSION.SDK;
        return mobleVersion;
    }


    public static String getMacAddress() {
//		该方法在6.0 系统 不再开发，返回 02:00:00:00:00:00
        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:02";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "02:00:00:00:00:02";
        }
        return macAddress;
    }


}
