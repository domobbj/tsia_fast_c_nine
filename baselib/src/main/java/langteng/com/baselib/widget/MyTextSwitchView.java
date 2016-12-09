package langteng.com.baselib.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import langteng.com.baselib.R;

/**
 * @author (●—●)
 * @data 2015-12-15下午3:36:00
 * @describe
 */
public class MyTextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private int index = -1;
    private Context context;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    index = next(); //取得下标值
                    updateText();  //更新TextSwitcherd显示内容;
                    break;
            }
        }

    };
    List<String> resources;
    private Timer timer; //

    public MyTextSwitchView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyTextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        if (timer == null)
            timer = new Timer();
        this.setFactory(this);
        this.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.in_animation));
        this.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.out_animation));
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public void setTextStillTime(long time) {
        if (timer == null) {
            timer = new Timer();
        } else {
            timer.scheduleAtFixedRate(new MyTask(), 1, time);//每3秒更新
        }
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    }

    private int next() {
        int flag = index + 1;
        if (flag > resources.size() - 1) {
            flag = flag - resources.size();
        }
        return flag;
    }



    private void updateText() {
        this.setText(resources.get(index));
    }

    @Override
    public View makeView() {
        TextView tv = new TextView(context);
        tv.setTextSize(20);
        return tv;
    }
}