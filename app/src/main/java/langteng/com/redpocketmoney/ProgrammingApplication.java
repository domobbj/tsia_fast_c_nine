package langteng.com.redpocketmoney;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;
import langteng.com.baselib.GlobalApplication;
import langteng.com.redpocketmoney.ui.base.UniversalImageLoader;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.widget.DemoMessageHandler;
import okhttp3.OkHttpClient;

/**
 */
public class ProgrammingApplication extends GlobalApplication {

    private _User baseInfoModel;
    private static ProgrammingApplication pocketApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "c9a217fcd677cb7dc0b58f055b1fd0a1");
        //注册消息接收器
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            //im初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));
        }
        initOkhttp();
        pocketApplication = this;
        //uil初始化
        UniversalImageLoader.initImageLoader(this);
    }

    public static ProgrammingApplication get() {
        return pocketApplication;
    }


    private void initOkhttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
    }


    public _User getUser() {
        _User user = _User.getCurrentUser(get(),_User.class);
        return user;
    }



    /**
     * 获取当前运行的进程名
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
