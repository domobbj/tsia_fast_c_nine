package langteng.com.baselib.utils;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.R;


/**
 * 比较好看的Toast
 */
public class MyCustomToast extends Toast {

    private View mNextView;

    private static final int MARGIN_BOT = 128;

    public MyCustomToast(Context context) {
        super(context);
    }

    public static MyCustomToast makeText(Context context, int resId, CharSequence text, int duration) {
        if (context == null) {
            context = GlobalApplication.getInstance();
        }
        MyCustomToast result = new MyCustomToast(context);

        //获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.toast, null);

        //实例化ImageView和TextView对象
        TextView textView = (TextView) layout.findViewById(R.id.tv_toast);

        textView.setText(text);

        result.setView(layout);
        result.setGravity(Gravity.BOTTOM, 0, DeviceUtils.dip2px(MARGIN_BOT));
        result.setDuration(duration);

        return result;
    }

    public static MyCustomToast makeText(Context context, CharSequence text, int duration) {
        MyCustomToast result = new MyCustomToast(context);

        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_toast);
        if (!StringUtil.isEmpty(String.valueOf(text))) {
            tv.setText(Html.fromHtml(String.valueOf(text)));
        } else {
            tv.setText(text);
        }
        result.mNextView = v;

        result.setView(v);
        result.setGravity(Gravity.BOTTOM, 0, DeviceUtils.dip2px(MARGIN_BOT));
        result.setDuration(duration);

        return result;
    }


    public static MyCustomToast makeShortText(CharSequence text) {
        MyCustomToast result = new MyCustomToast(GlobalApplication.getInstance());
        LayoutInflater inflate = (LayoutInflater) GlobalApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_toast);
        if (!StringUtil.isEmpty(String.valueOf(text))) {
            tv.setText(Html.fromHtml(String.valueOf(text)));
        } else {
            tv.setText(text);
        }
        result.mNextView = v;

        result.setView(v);
        result.setGravity(Gravity.BOTTOM, 0, DeviceUtils.dip2px(MARGIN_BOT));
        result.setDuration(Toast.LENGTH_SHORT);
        result.show();
        return result;
    }
    public static MyCustomToast makeLongTimeText(CharSequence text) {
        MyCustomToast result = new MyCustomToast(GlobalApplication.getInstance());

        LayoutInflater inflate = (LayoutInflater) GlobalApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_toast);
        if (!StringUtil.isEmpty(String.valueOf(text))) {
            tv.setText(Html.fromHtml(String.valueOf(text)));
        } else {
            tv.setText(text);
        }
        result.mNextView = v;

        result.setView(v);
        result.setGravity(Gravity.BOTTOM, 0, DeviceUtils.dip2px(MARGIN_BOT));
        result.setDuration(Toast.LENGTH_LONG);
        result.show();
        return result;
    }

    public void setText(CharSequence s) {
        if (mNextView == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        TextView tv = (TextView) mNextView.findViewById(R.id.tv_toast);
        if (tv == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        if (!StringUtil.isEmpty(String.valueOf(s))) {
            tv.setText(Html.fromHtml(String.valueOf(s)));
        } else {
            tv.setText(s);
        }
    }

}
