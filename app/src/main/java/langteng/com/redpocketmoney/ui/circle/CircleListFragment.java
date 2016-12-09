package langteng.com.redpocketmoney.ui.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigkoo.convenientbanner.ConvenientBanner;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.R;

/**
 */
public class CircleListFragment extends Fragment implements View.OnClickListener {


    private ConvenientBanner banner;

    private ListView circleLv;
    private List<CircleModel> circleModelList = new ArrayList<>();
    private CircleAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_circle, null);
        initView(view1);
        return view1;
    }

    private void initView(View view) {
        banner = (ConvenientBanner) view.findViewById(R.id.circle_banner);
        circleLv = (ListView) view.findViewById(R.id.circle_lv);
        adapter = new CircleAdapter(getActivity(),circleModelList);
        circleLv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        getCircleTagDate();
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }

    private ArrayList<CircleTag> circleTagArrayList = new ArrayList<>();
    public StringBuffer buffer = new StringBuffer();

    private void getCircleTagDate() {

        BmobQuery<CircleModel> query = new BmobQuery<CircleModel>();
        query.setLimit(50);
        query.findObjects(getActivity(), new FindListener<CircleModel>() {
            @Override
            public void onSuccess(List<CircleModel> list) {
                Logger.i("---CircleModel--query---", "list: " + list.size());
                circleModelList.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
                Logger.i("-----CircleModel---", "s: " + s);
            }
        });

    }

}
