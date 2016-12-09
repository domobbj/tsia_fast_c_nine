package langteng.com.baselib.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

import langteng.com.baselib.GlobalApplication;


/**
 */
public class AppInfoUtil {

    //版本名
    public static String getVersionName() {
        return getPackageInfo(GlobalApplication.getInstance()).versionName;
    }

    //版本号
    public static int getVersionCode() {
        return getPackageInfo(GlobalApplication.getInstance()).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }


    /**
     * 获取Manifast文件中 UMENG_CHANNEL 的值
     *
     * @return
     */
    public static String getChannelValue() {
        ApplicationInfo appInfo = null;
        Object umeng_channel = null;
        try {
            appInfo = GlobalApplication.getInstance().getPackageManager()
                    .getApplicationInfo(GlobalApplication.getInstance().getPackageName(),
                            PackageManager.GET_META_DATA);
            umeng_channel = appInfo.metaData.get("BaiduMobAd_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return umeng_channel.toString();
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();

        } else {
            mResources = context.getResources();
        }
        //DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5, xdpi=160.421, ydpi=159.497}
        //DisplayMetrics{density=2.0, width=720, height=1280, scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }


    /**
     * 检测该包名所对应的应用是否存在
     *
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }
}
