package langteng.com.redpocketmoney.ui.circle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import langteng.com.baselib.baseui.LibBaseActivity;
import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.R;

/***
 ***/

public class ActivityCircleImageView extends LibBaseActivity {

    private ViewPager viewPager;
    private TextView posTv;
    List<CircleImageFragment> fragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseSetContentView(R.layout.activity_circleimg);
        setTopbarName("图片详情");
        viewPager = (ViewPager) findViewById(R.id.img_viewpager);
        posTv = (TextView) findViewById(R.id.img_pos);
        String imgList = getIntent().getStringExtra("imgs");
        CircleImageFragment fragment;
        for (int i = 0; i < imgList.split("articlesModel").length; i++) {
            fragment = new CircleImageFragment();
            fragment.setImgUrl(imgList.split("articlesModel")[i]);
            fragmentList.add(fragment);
            Logger.i("------articlesModel---","" + imgList.split("articlesModel")[i]);
        }
        int pos = getIntent().getIntExtra("position", 0);
        CircleViewpagerAdapter adapter =
                new CircleViewpagerAdapter(getSupportFragmentManager(),
                        fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
        posTv.setText(pos + "/" + fragmentList.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                posTv.setText(position + "/" + fragmentList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void startActivityWorker(Activity activity, String tag) {
        Intent intent = new Intent(activity, ActivityCircleImageView.class);
        intent.putExtra("tag", tag);
        activity.startActivity(intent);
    }

}



