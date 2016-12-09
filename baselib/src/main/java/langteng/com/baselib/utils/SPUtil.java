package langteng.com.baselib.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import langteng.com.baselib.GlobalApplication;

public class SPUtil {

    public static final String KEY = "com.langteng.android.sp_key";

    private static String TAG = "-------SPUtil--";



    /**
     * 获取string，默认值为""
     */
    public static void setString1(String key, String value) {
        SharedPreferences mySharedPreferences = GlobalApplication.getInstance()
                .getSharedPreferences("yiqizhuan",
                        Activity.MODE_PRIVATE);
//实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
//用putString的方法保存数据
        editor.putString(key, value);
//提交当前数据
        editor.commit();
    }

    /**
     * 获取string
     */
    public static String getString1(String key) {
        SharedPreferences sharedPreferences = GlobalApplication.getInstance().getSharedPreferences("yiqizhuan",
                Activity.MODE_PRIVATE);
//// 使用getString方法获得value，注意第2个参数是value的默认值
//            LogUtil.i("----value--", " value: " + value);
        return sharedPreferences.getString(key, "");
    }


    /**
     * 获取int
     */
    public static int getIntShareData(String key, int defValue) {
        SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }


    public static void putIntShareData(String key, int value) {
        SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putInt(key, value);
        et.commit();
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putBoolean(key, value);
        et.commit();
    }

    public static boolean getBoolean(String key) {
        SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }


    public static void setInt(String key, int value) {
        SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putInt(key, value);
        et.commit();
    }

    /****
     * 默认值为 -1
     **/
    public static int getInt(String key) {
        SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }


    public static void setLong(String key, long value) {
        SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putLong(key, value);
        et.commit();
    }

    /****
     * 默认值为 -1
     **/
    public static long getLong(String key) {
        SharedPreferences sp = GlobalApplication.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getLong(key, -1);
    }

}
