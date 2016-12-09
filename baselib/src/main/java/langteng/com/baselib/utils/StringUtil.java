package langteng.com.baselib.utils;

import android.app.Activity;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 字符串工具类.
 */
public class StringUtil {

    /**
     * @description 验证字符串是否为空
     * @version 1.0
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals("null") || str.trim().equals("");

    }

    /**
     * 一个字符串中含有另一个字符串的个数
     */
    public static int getCountStr(String str1, String str2) {
        int counter = 0;
        if (str1.indexOf(str2) == -1) {
            return 0;
        }
        while (str1.indexOf(str2) != -1) {
            counter++;
            str1 = str1.substring(str1.indexOf(str2) + str2.length());
        }
        return counter;
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * 处理空字符串
     *
     * @param str
     * @return String
     */
    public static String doEmpty(String str) {
        return doEmpty(str, "");
    }

    /**
     * 处理空字符串
     *
     * @param str
     * @param defaultValue
     * @return String
     */
    public static String doEmpty(String str, String defaultValue) {
        if (str == null || str.equalsIgnoreCase("null")
                || str.trim().equals("") || str.trim().equals("－请选择－")) {
            str = defaultValue;
        } else if (str.startsWith("null")) {
            str = str.substring(4, str.length());
        }
        return str.trim();
    }


    public static boolean num(Object o) {
        int n = 0;
        try {
            n = Integer.parseInt(o.toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean decimal(Object o) {
        double n = 0;
        try {
            n = Double.parseDouble(o.toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return n > 0.0;
    }


    public static String readStream(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;

        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = is.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    /**
     * 将字符串转换成浏览器utf-8的形式
     *
     * @param s
     * @return
     */
    public static String getUtf8Url(String s) {
        try {
            s = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * 按字节截取字符串
     *
     * @param orignal 原始字符串
     * @param count   截取位数
     * @return 截取后的字符串
     * @throws UnsupportedEncodingException 使用了JAVA不支持的编码格式
     */
    public static String substring(String orignal, int count)
            throws UnsupportedEncodingException {
        try {
            // 原始字符不为null，也不是空字符串
            if (orignal != null && !"".equals(orignal)) {
                // 将原始字符串转换为GBK编码格式
                orignal = new String(orignal.getBytes(), "UTF-8");//
                // 要截取的字节数大于0，且小于原始字符串的字节数
                if (count > 0 && count < orignal.getBytes("UTF-8").length) {
                    StringBuffer buff = new StringBuffer();
                    char c;
                    for (int i = 0; i < count; i++) {
                        c = orignal.charAt(i);
                        buff.append(c);
                        if (isChineseChar(c)) {
                            // 遇到中文汉字，截取字节总数减1
                            --count;
                        }
                    }
                    return new String(buff.toString().getBytes(), "UTF-8");
                }
            }

        } catch (Exception e) {

        }
        return orignal;
    }

    /**
     * 判断是否是一个中文汉字
     *
     * @param c 字符
     * @return true表示是中文汉字，false表示是英文字母
     * @throws UnsupportedEncodingException 使用了JAVA不支持的编码格式
     */
    public static boolean isChineseChar(char c)
            throws UnsupportedEncodingException {
        // 如果字节数大于1，是汉字
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了
        return String.valueOf(c).getBytes("UTF-8").length > 1;
    }


    /**
     * 得到英文汉字混排的文字的长度
     *
     * @param value
     * @return
     */
    public static int getStringLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        if (value==null){
            return 0;
        }
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 获得通知栏的高度
     *
     * @param activity
     * @return
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = activity.getResources()
                        .getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }


    /**
     * 秒转换成分钟形式
     *
     * @return
     * @update 2014-11-11 下午9:19:51
     */
    public static String secondToMinute(int second) {
        String minute = "";
        int m = second / 60;
        int y = second % 60;
        if (y == 0) {
            minute = m + ":" + y + "0";
        } else if (y > 0 && y < 10) {
            minute = m + ":0" + y;
        } else {
            minute = m + ":" + y;
        }
        return minute;
    }

    /**
     * stringToInt
     *
     * @param text
     * @return
     * @update 2014-11-11 下午9:19:51
     */
    public static int stringToInt(String text) {
        int number = 0;
        try {
            number = Integer.parseInt(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public static float stringToFloat(String text) {
        float number = 0f;
        try {
            number = Float.parseFloat(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }


    /**
     * 是否全是空格
     *
     * @param str
     * @return true:是
     * @author wjh
     */
    public static boolean isTotalTrim(String str) {
        if (str == null) {
            throw new NullPointerException("str == null");
        }
        return str.length() > 0 && str.trim().length() == 0;

    }


    public static String string2Unicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 2; i < bytes.length - 1; i += 2) {
                out.append("u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);

                out.append(str);
                out.append(str1);
                out.append(" ");
            }
            return out.toString().toUpperCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    //    汉字转 Unicode
    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {//汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }


    public static String sql4CreateTable(String tableName, Map<String, String> columns) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table ");
        stringBuilder.append(tableName);
        stringBuilder.append("(");
        int i = 0, size = columns.size();
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(' ');
            stringBuilder.append(entry.getValue());
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
            ++i;
        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }


    private static final String HEX_STR = "0123456789abcdef";

    public static String toMD5Hex(String str) {
        return toHex(toMD5(str.getBytes()));
    }

    public static String toHex(byte[] bytes) {
        StringBuilder buider = new StringBuilder(bytes.length << 1);

        for (int i = 0; i < bytes.length; ++i) {
            buider.append(HEX_STR.charAt((bytes[i] & 0xf0) >> 4));
            buider.append(HEX_STR.charAt(bytes[i] & 0x0f));
        }

        return buider.toString();
    }

    public static byte[] toMD5(byte[] bytes) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        }

        md5.update(bytes);
        return md5.digest();
    }


    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

}
