package langteng.com.baselib.utils;

import android.util.Log;

/**
 * 日志打印类:
 *
 */
public class Logger {

	// 是否打印日志
	public static boolean isDebug = true;

	public static void v(String tag, String msg) {
		if (isDebug) {
			if (msg == null || tag == null)
				return;
			Log.v(tag, msg);
		}
	}



	public static void i(String tag, String msg) {
		if (isDebug) {
			if (msg == null || tag == null)
				return;
			Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (isDebug) {
			if (msg == null || tag == null)
				return;
			Log.d(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (isDebug) {
			if (msg == null || tag == null)
				return;
			Log.w(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (isDebug) {
			if (msg == null || tag == null)
				return;
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable e) {
		if (isDebug) {
			if (msg == null || tag == null)
				return;
			Log.e(tag, msg, e);
		}
	}

	public static void print(String tag, String msg) {
		if (isDebug) {
			if (msg == null || tag == null)
				return;
		}
	}

	/**
	 * 设置debug 模式
	 *
	 * @param isDebug
	 *            true 打印日志 false：不打印
	 */
	public static void setIsDebug(boolean isDebug) {
		Logger.isDebug = isDebug;
	}
}
