package langteng.com.redpocketmoney.ui.circle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**

 */
public class CircleViewpagerAdapter extends FragmentPagerAdapter {


    private List<CircleImageFragment> fragmentActivityList;

    public CircleViewpagerAdapter(FragmentManager fm, List<CircleImageFragment> fragmentActivityList) {
        super(fm);
        this.fragmentActivityList = fragmentActivityList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentActivityList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentActivityList.size();
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
