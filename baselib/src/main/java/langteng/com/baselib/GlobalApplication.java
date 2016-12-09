package langteng.com.baselib;

import android.app.Application;

/**
 * Created by lang on 16/11/18.
 */
public class GlobalApplication extends Application {

    public static GlobalApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static GlobalApplication getInstance() {
        return application;
    }
}
