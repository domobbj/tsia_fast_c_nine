package langteng.com.redpocketmoney.ui.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import langteng.com.baselib.baseui.LibBaseActivity;
import langteng.com.baselib.login.GuideFragment;
import langteng.com.redpocketmoney.R;

/**
 */
public class GuideActivity extends LibBaseActivity {

    private ViewPager guideViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);
        initView();
    }


    private void initView() {
        guideViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        List<Fragment> guideFragmentList = new ArrayList<>();
        GuideFragment fragment;
        Bundle bundle;
        fragment = new GuideFragment();
        bundle = new Bundle();
        fragment.setArguments(bundle);
        guideFragmentList.add(fragment);

        guideViewPager.setAdapter(new MainViewpagerAdapter(getSupportFragmentManager(),
                guideFragmentList));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
