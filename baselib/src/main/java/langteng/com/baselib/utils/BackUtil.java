package langteng.com.baselib.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import java.util.List;

/**
 * 判断activity 后台运行，等行为的工具类。
 */
public class BackUtil {
	public static final String PAGENAME = "com.kuailexue.studennt";

	/**
	 * 判断程序时候后台运行
	 * 
	 * @param context
	 * @param pageName
	 * @return
	 */
	public static boolean isBackgroundRunning(Context context, String pageName) {

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		if (activityManager == null)
			return false;
		List<RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo process : processList) {
			if (process.processName.startsWith(pageName)) {
				boolean isBackground = process.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND
						&& process.importance != RunningAppProcessInfo.IMPORTANCE_VISIBLE;
				boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
				return isBackground || isLockedState;
			}
		}
		return false;

	}

	/**
	 * 判断某个activity 时候后台运行
	 * 
	 * @param mContext
	 * @param activityClassName
	 *            要判断的activity 的class
	 * @return
	 */
	public static boolean isActivityRunning(Context mContext, String activityClassName) {
		if (mContext != null) {
			ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningTaskInfo> info = activityManager.getRunningTasks(1);
			if (info != null && info.size() > 0) {
				ComponentName component = info.get(0).topActivity;
				if (component != null && activityClassName.equals(component.getClassName())) {
					return true;
				}
			}
		}
		return false;
	}

	/** 判断程序是否运行 */
	public static boolean isAPPRunning(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		boolean isAppRunning = false;
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(PAGENAME) || info.baseActivity.getPackageName().equals(PAGENAME)) {
				isAppRunning = true;
			}
		}
		return isAppRunning;
	}

	/** 判断程序是否在前台运行 */
	public static boolean isTopActivity(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);

		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			if (PAGENAME.equals(tasksInfo.get(0).topActivity.getPackageName())) {
				return true;

			}
		}
		return false;

	}

	/** 判断安装了次程序 */
	public static boolean isPacketExist(Context context, String packname) {
		PackageInfo info;
		boolean isExist = false;
		try {
			info = context.getPackageManager().getPackageInfo(packname, 0);
			if (info != null) {
				isExist = true;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return isExist;
	}

}
