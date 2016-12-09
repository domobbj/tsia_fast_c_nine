package langteng.com.redpocketmoney.ui.login;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.iflytek.autoupdate.IFlytekDownloadListener;
import com.iflytek.autoupdate.IFlytekUpdate;
import com.iflytek.autoupdate.IFlytekUpdateListener;
import com.iflytek.autoupdate.UpdateErrorCode;
import com.iflytek.autoupdate.UpdateInfo;
import com.iflytek.autoupdate.UpdateType;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobUser;
import langteng.com.baselib.baseui.LibBaseActivity;
import langteng.com.baselib.config.SdkConfig;
import langteng.com.baselib.utils.ActivityJump;
import langteng.com.baselib.utils.AppInfoUtil;
import langteng.com.baselib.utils.SPUtil;
import langteng.com.baselib.widget.CommonOneButtonDialog;
import langteng.com.baselib.widget.CommonTwoButtonDialog;
import langteng.com.baselib.widget.UpdateDialog;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.config.SpUtilConfig;
import langteng.com.redpocketmoney.ui.main.MainActivity;
import langteng.com.redpocketmoney.utils.Utils;


/****
 * 启动页面
 ***/

public class ActivitySplash extends LibBaseActivity {


    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                checkUpdate();
            }

        }
    }

    private MyHandler handler;
    private ImageView splashImg;

    private final Timer timer = new Timer();
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.splash_activity);
        //   百度移动统计
        StatService.setAppKey(SdkConfig.BAIDU_ID);
        StatService.setAppChannel(this, AppInfoUtil.getChannelValue(), true);
        StatService.setOn(this, StatService.EXCEPTION_LOG);

        checkShortcut();
        handler = new MyHandler();
        splashImg = (ImageView) findViewById(R.id.splash_img);


        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(1800);

        ScaleAnimation scaleAnimation = new ScaleAnimation(2f, 1.1f, 2f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(1800);
        scaleAnimation.setInterpolator(new OvershootInterpolator(0.8f));

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        set.setFillAfter(true);
        set.setStartOffset(100);
        splashImg.startAnimation(set);
        task = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.arg1 = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, 2500);
    }


    /**
     * 检查是否已经添加图标
     */
    private void checkShortcut() {
        boolean isAddedShortcut = SPUtil.getBoolean(SpUtilConfig.Shortcut);
        if (!isAddedShortcut) {
            // 添加快捷图标
            Utils.addShortcut(this);
            SPUtil.setBoolean(SpUtilConfig.Shortcut, false);
        }
    }

    private void changeIcon() {  //
        PackageManager pm = getApplicationContext().getPackageManager();
        //去除旧图标，不去除的话会出现2个App图标
        pm.setComponentEnabledSetting(getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        //显示新图标
        pm.setComponentEnabledSetting(new ComponentName(
                        getBaseContext(),
                        "com.test.MainActivity1"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private CommonTwoButtonDialog refuseConnectDialog;
    //自动更新回调方法,详情参考demo
    IFlytekUpdateListener updateListener = new IFlytekUpdateListener() {
        @Override
        public void onResult(int errorcode, final UpdateInfo result) {

            if (errorcode == UpdateErrorCode.OK && result != null) {
                if (result.getUpdateType() == UpdateType.NoNeed) {
                    activityJump();
                    return;
                }
                if (result.getUpdateDetail().contains("强制更新")) {
                    final CommonOneButtonDialog oneButtonDialog = new CommonOneButtonDialog();
                    oneButtonDialog.setTitle("更新提示");
                    oneButtonDialog.setCancelable(false);
                    oneButtonDialog.setContent(result.getUpdateDetail().replace("强制更新", ""));
                    oneButtonDialog.setConfirmTextAndClickListener("更新", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadDialog(result);
                            oneButtonDialog.dismiss();
                        }
                    });
                    oneButtonDialog.show(getSupportFragmentManager(), "oneButtonDialog");
                } else {
                    refuseConnectDialog = new CommonTwoButtonDialog();
                    refuseConnectDialog.setTitle("更新提示");
                    refuseConnectDialog.setContent(result.getUpdateDetail());
                    refuseConnectDialog.setCancelable(false);
                    refuseConnectDialog.show(getSupportFragmentManager(), "refuseConnectDialog");
                    refuseConnectDialog.setLeftTextAndClickListener("暂不更新", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            activityJump();
                            refuseConnectDialog.dismiss();
                        }
                    });
                    refuseConnectDialog.setRightTextAndClickListener("更新", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadDialog(result);
                            refuseConnectDialog.dismiss();
                        }
                    });
                }
            } else {
                activityJump();
            }

        }
    };


    private void downloadDialog(UpdateInfo result) {
        final UpdateDialog dialog = new UpdateDialog();
        dialog.show(getSupportFragmentManager(), "test");
        dialog.setCancelable(false);
        updManager.startDownload(ActivitySplash.this, result, new IFlytekDownloadListener() {
            @Override
            public void onDownloadStart() {

            }

            @Override
            public void onDownloadUpdate(int i) {
                dialog.updatePro(i);
            }

            @Override
            public void onDownloadEnd(int i, String s) {
                dialog.updatePro(100);
                dialog.setCancelable(true);
            }
        });

    }


    private IFlytekUpdate updManager;

    private void checkUpdate() {
        BmobUser user = (BmobUser) _User.getCurrentUser(this);
        if (user == null) {
            ActivityJump.getInsanceJump(this, ActivityLogin.class, null, true);
        } else {
            //初始化自动更新对象
//            updManager = IFlytekUpdate.getInstance(ActivitySplash.this); //开启调试模式,默认不开启
//            updManager.setDebugMode(false);
//            //开启wifi环境下检测更新,仅对自动更新有效,强制更新则生效
//            updManager.setParameter(UpdateConstants.EXTRA_STYLE, UpdateConstants.UPDATE_UI_DIALOG);
//            updManager.autoUpdate(ActivitySplash.this, updateListener);
            activityJump();
        }
    }


    private void activityJump() {
        ActivityJump.getInsanceJump(this, MainActivity.class, null, true);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}



