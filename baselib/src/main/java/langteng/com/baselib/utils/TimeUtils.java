package langteng.com.baselib.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TimeUtils {


    public static final String MSG_FORMAT_TYPE = "yyyy.MM.dd HH:mm";

    /**
     * 格式化日期时间
     *
     * @return String
     */
    public static String formatMsgMainDateTimeTest(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar lastCal = Calendar.getInstance();
        lastCal.setTime(date);
        Date today = new Date();
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(today);

        int year = lastCal.get(Calendar.YEAR);
        int day = lastCal.get(Calendar.DAY_OF_YEAR);
        int h = lastCal.get(Calendar.HOUR_OF_DAY);
        int min = lastCal.get(Calendar.MINUTE);

        int thisYear = nowCal.get(Calendar.YEAR);
        int thisDay = nowCal.get(Calendar.DAY_OF_YEAR);
        int thisH = nowCal.get(Calendar.HOUR_OF_DAY);
        int thisMin = nowCal.get(Calendar.MINUTE);

//        if (today.getTime() >= date.getTime()) {
//            if (year != thisYear) {
//                // X>30天：按 MM-dd HH:mm:ss 格式显示
//                return dateToString(date, MSG_FORMAT_TYPE);
//            } else {
//                if (thisDay - day >= 2) {
//                    // X>30天：按 MM-dd HH:mm:ss 格式显示
//                    return dateToString(date, MSG_FORMAT_TYPE);
//                } else if ((thisDay - day) >= 1 && (thisDay - day) < 2) {
//                    int compareDay = thisDay - day;
//                    return "昨天";
//                } else {
//                    if ((thisH - h) >= 1 && (thisH - h) < 24) {
//                        int compareH = thisH - h;
//                        return compareH + "小时前";
//                    } else {
//                        if ((thisMin - min) >= 1 && (thisMin - min) < 60) {
//                            int compareMin = thisMin - min;
//                            return compareMin + "分钟前";
//                        } else {
//                            return "刚刚";
//                        }
//                    }
//                }
//            }
//        } else {
//            // X>30天：按 MM-dd HH:mm:ss 格式显示
//            return dateToString(date, MSG_FORMAT_TYPE);
//        }
        return dateToString(date, MSG_FORMAT_TYPE);
    }



    /**
     * 显示时间选择器
     *
     * @param context  上下文
     * @param listener {@link DatePickerDialog.OnDateSetListener} 回调监听
     * @param maxDate  最大时间 Long 类型
     * @param minDate  最小时间 Long 类型
     */
    @SuppressLint("NewApi")
    public static void showTimeDialog(Context context,
                                      DatePickerDialog.OnDateSetListener listener, long maxDate, long minDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dataDialog = new DatePickerDialog(context, listener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        if (android.os.Build.VERSION.SDK_INT > 10) {
            DatePicker datePicker = dataDialog.getDatePicker();
            datePicker.setMinDate(minDate);
            datePicker.setMaxDate(maxDate);
        }
        dataDialog.show();
    }


    /**
     * String类型 时间转换 成Long
     *
     * @param str_date 时间串
     * @param format   时间格式，例如： yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long StringConvertLong(String str_date, String format) {
        SimpleDateFormat myFormatter = new SimpleDateFormat(format);
        try {
            Date date = myFormatter.parse(str_date);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }


    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     * <p/>
     * 时间戳
     *
     * @return
     */
    public static String formatMsgMainDateTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        StringBuffer sb = new StringBuffer();
        long time = System.currentTimeMillis() - date.getTime();
        long mill = (long) Math.ceil(time / 1000);// 秒前
        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时
        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 30) {
            return sb.append(dateToString(date, MSG_FORMAT_TYPE)).toString();
        } else if (day - 1 > 0) {
            sb.append((day - 1) + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append((hour - 1) + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append((minute - 1) + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append("刚刚");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }


    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     * <p/>
     * 时间戳
     *
     * @return
     */
    public static String formatMsgMainDateTime(long timestampe) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        Date date =  (new Date(timestampe));
        StringBuffer sb = new StringBuffer();
        long time = System.currentTimeMillis() - date.getTime();
        long mill = (long) Math.ceil(time / 1000);// 秒前
        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时
        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 30) {
            return sb.append(dateToString(date, MSG_FORMAT_TYPE)).toString();
        } else if (day - 1 > 0) {
            sb.append((day - 1) + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append((hour - 1) + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append((minute - 1) + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append("刚刚");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }


    /**
     * 判断时间
     *
     * @param nowDate 原始
     * @param date    要进行对比的
     * @return 是同一天return true 不是false
     */
    public static boolean compareDate(Date nowDate, Date date) {
        Date today = nowDate;
        int year = date.getYear();
        int thisYear = today.getYear();
        int day = date.getDate();
        int thisDay = today.getDate();
        if (year == thisYear) {
            return thisDay - day == 0;
        } else {
            return false;
        }
    }

    public static Date getDate(double itemDouble) {
        long itemLong = (long) (itemDouble * 1000);
        Date itemDate = new Date(itemLong);
        return itemDate;
    }


    public static String TimeStampDate(String timestampString) {
        Long timestamp = Long.parseLong(timestampString);
        String date = new SimpleDateFormat("MM.dd HH:mm").format(new Date(timestamp));
        return date;
    }


    public static String dateToString(Date date, String formatType) {
        if (date != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(formatType, Locale.CHINA);
                return dateFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return "";
        }
        return "";
    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String formatTime(int time) {
        time = time / 1000;
        int sec = time % 60;
        return time / 60 + ":" + (sec > 9 ? sec + "" : "0" + sec);
    }

    /**
     * 计算时间差
     *
     * @param date
     * @param predate
     * @return long
     */
    public static long getTimeDifference(Date date, Date predate) {
        long time = date.getTime() - predate.getTime();
        long min = time / 1000 / 60;
        return min;
    }

    /**
     * 判断是不是中文环境
     *
     * @param context
     * @return
     */
    private static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language.endsWith("zh");
    }


    /**
     * *
     * 一个时间内的具体日期
     * **
     */

    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * *
     * 时间转时间戳
     * String time="1970-01-06 11:45:55";
     * **
     */
    public static long getTimeToLong(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    // a integer to xx:xx:xx
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time < 60) {
            return time + "秒";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + "分钟" + unitFormat(second) + "秒";
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "小时" + unitFormat(minute) + "分钟" + unitFormat(second) + "秒";
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 毫秒数转格式化日期时间
     *
     * @param millis  要转化的日期毫秒数。
     * @param pattern 要转化为的字符串格式（如：yyyy-MM-dd HH:mm:ss）。("yyyy年MM月dd日 HH:mm:ss")
     * @return 返回日期字符串。
     * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
    Locale.getDefault());
    return format.format(new Date(millis));
     */
    public static String millis2FormatDate(long millis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return format.format(new Date(millis));
    }

    /**
     * 格式化日期时间转毫秒数
     *
     * @param str
     * @param pattern
     * @return
     */
    public static long stringDate2Millis(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern,
                Locale.getDefault());
        long millis = 0;
        try {
            millis = format.parse(str).getTime();
        } catch (ParseException e) {
            Log.e("TAG", "stringDate2Millis error" + e.getMessage());
        }
        return millis;
    }

}

