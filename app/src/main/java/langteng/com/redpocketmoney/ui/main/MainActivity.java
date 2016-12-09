package langteng.com.redpocketmoney.ui.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.exception.BmobException;
import langteng.com.baselib.baseui.LibBaseActivity;
import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.eventbus.EventCenter;
import langteng.com.redpocketmoney.ui.circle.ActivityCircle;
import langteng.com.redpocketmoney.ui.circle.CircleListFragment;
import langteng.com.redpocketmoney.ui.login.MainViewpagerAdapter;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.ui.message.MessageListFragment;
import langteng.com.redpocketmoney.ui.search.SearchFragment;
import langteng.com.redpocketmoney.ui.task.HomeTaskListFragment;
import langteng.com.redpocketmoney.util.IMMLeaks;


public class MainActivity extends LibBaseActivity implements View.OnClickListener {


    private TextView mainTv;
    private TextView emojiTv;
    private TextView settingTv;
    private TextView circleTv;
    private TextView publishTv;
    private ViewPager viewPager;
    private TextView titleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initIM();
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mainTv = (TextView) findViewById(R.id.main_tv);
        emojiTv = (TextView) findViewById(R.id.emoji_tv);
        settingTv = (TextView) findViewById(R.id.setting_tv);
        circleTv = (TextView) findViewById(R.id.circle_tv);
        publishTv = (TextView) findViewById(R.id.publish_tv);
        publishTv.setOnClickListener(this);


        titleName = (TextView) findViewById(R.id.title_name);
        mainTv.setOnClickListener(this);
        emojiTv.setOnClickListener(this);
        settingTv.setOnClickListener(this);
        settingTv.setOnClickListener(this);
        circleTv.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        List<Fragment> fragmentList = new ArrayList<>();
        MessageListFragment messageListFragment = new MessageListFragment();
        fragmentList.add(messageListFragment);

        SearchFragment searchFragment = new SearchFragment();
        fragmentList.add(searchFragment);

        HomeTaskListFragment taskListFragment = new HomeTaskListFragment();
        fragmentList.add(taskListFragment);

        CircleListFragment circleListFragment = new CircleListFragment();
        fragmentList.add(circleListFragment);


        MainViewpagerAdapter adapter = new MainViewpagerAdapter(getSupportFragmentManager(),
                fragmentList);
        viewPager.setAdapter(adapter);
        setOrginStatus(0);
        mainTv.setPressed(true);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setOrginStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    private void initIM(){
        _User user =  _User.getCurrentUser(this,_User.class);
//        BmobIMUserInfo userInfo = new BmobIMUserInfo();
//        userInfo.setAvatar(user.userIcon);
//        userInfo.setUserId(user.getObjectId());
//        userInfo.setName(user.userNickName);
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    initIMLong();
                    EventBus.getDefault().post(new EventCenter("BmobIMConnect",""));
                    Logger.i("-----initIM----", "Succeed" );
                } else {
                    Logger.i("-----initIM----", "e: " + e.getMessage() + e.getErrorCode());
                }
            }
        });

        initIMLong();
    }


    private void initIMLong(){
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus connectionStatus) {

            }
        });
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());
    }



    @Override
    protected void onResume() {
        super.onResume();
        int count = (int)BmobIM.getInstance().getAllUnReadCount();
        Logger.i("-----count--","count: " + count);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        BmobIM.getInstance().clear();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_tv:
                viewPager.setCurrentItem(0);
                break;
            case R.id.emoji_tv:
                viewPager.setCurrentItem(1);
                break;
            case R.id.setting_tv:
                viewPager.setCurrentItem(2);
                break;
            case R.id.circle_tv:
                viewPager.setCurrentItem(3);
                break;
            case R.id.publish_tv:
                ActivityCircle.startActivityWorker(this,"PublishCircleFragment");
                break;
        }
    }

    private void setOrginStatus(int pos) {
        mainTv.setTextColor(getResources().getColor(R.color.black5));
        emojiTv.setTextColor(getResources().getColor(R.color.black5));
        settingTv.setTextColor(getResources().getColor(R.color.black5));
        circleTv.setTextColor(getResources().getColor(R.color.black5));

        Drawable dra1 = getResources().getDrawable(R.mipmap.message);
        dra1.setBounds(0, 0, dra1.getMinimumWidth(), dra1.getMinimumHeight());
        Drawable dra2 = getResources().getDrawable(R.mipmap.find);
        dra2.setBounds(0, 0, dra2.getMinimumWidth(), dra2.getMinimumHeight());
        Drawable dra3 = getResources().getDrawable(R.mipmap.app);
        dra3.setBounds(0, 0, dra3.getMinimumWidth(), dra3.getMinimumHeight());

        Drawable dra4 = getResources().getDrawable(R.mipmap.circle);
        dra4.setBounds(0, 0, dra4.getMinimumWidth(), dra4.getMinimumHeight());
        mainTv.setCompoundDrawables(null, dra1, null, null);
        emojiTv.setCompoundDrawables(null, dra2, null, null);
        settingTv.setCompoundDrawables(null, dra3, null, null);
        circleTv.setCompoundDrawables(null, dra4, null, null);


        if (pos == 0) {
            mainTv.setTextColor(getResources().getColor(R.color.theme));
            Drawable dra = getResources().getDrawable(R.mipmap.message_press);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            mainTv.setCompoundDrawables(null, dra, null, null);
            titleName.setText("消息");
        } else if (pos == 1) {
            Drawable dra = getResources().getDrawable(R.mipmap.find_press);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            emojiTv.setTextColor(getResources().getColor(R.color.theme));
            emojiTv.setCompoundDrawables(null, dra, null, null);
            titleName.setText("发现同事");
        } else if (pos == 2) {
            Drawable dra = getResources().getDrawable(R.mipmap.app_press);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            settingTv.setTextColor(getResources().getColor(R.color.theme));
            settingTv.setCompoundDrawables(null, dra, null, null);
            titleName.setText("应用");
        }else if (pos == 3) {
            Drawable dra = getResources().getDrawable(R.mipmap.circle_press);
            dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            circleTv.setTextColor(getResources().getColor(R.color.theme));
            circleTv.setCompoundDrawables(null, dra, null, null);
            titleName.setText("同事圈");
            publishTv.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(EventCenter message) {
        if ("updateIcon".equals(message.opreatId)) {
            _User user = _User.getCurrentUser(this,_User.class);

        }
    }

}
