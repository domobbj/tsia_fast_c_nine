package langteng.com.redpocketmoney.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.baselib.utils.ListViewUtility;
import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.personnal.ActivityWorker;

/**
 */
public class SearchFragment extends LibBaseFragment {

    private ListView hotQuestionLv;
    private ListView departmentLv;
    private List<DepartmentModel> departmentModelList = new ArrayList<>();
    private List<HotQuestionModel> hotQUestionModelList = new ArrayList<>();

    private DepartmentAdapter departmentAdapter;
    private HotQuestionAdapter hotQuestionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_searchlist, null);
        initView(view1);
        return view1;
    }

    private void initView(View view) {
        hotQuestionLv = (ListView) view.findViewById(R.id.hot_question_lv);
        departmentLv = (ListView) view.findViewById(R.id.department_lv);

        departmentAdapter = new DepartmentAdapter(getActivity(), departmentModelList);
        hotQuestionAdapter = new HotQuestionAdapter(getActivity(), hotQUestionModelList);

        hotQuestionLv.setAdapter(hotQuestionAdapter);
        hotQuestionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActivityWorker.startActivityWorker(getActivity(), "WorkerFragment",
                        hotQUestionModelList.get(i).username,
                        hotQUestionModelList.get(i).username);
            }
        });

        departmentLv.setAdapter(departmentAdapter);
        departmentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActivityWorker.startActivityWorker(getActivity(), "WorkerListFragment"
                        ,"员工名片",
                        departmentModelList.get(i).departmentId);
            }
        });
        getDepartmentModelDate();
        getHotQUestionDate();
    }


    private void getDepartmentModelDate() {
        DepartmentModel departmentModel ;

        departmentModel = new DepartmentModel();
        departmentModel.departmentId ="";

        BmobQuery<DepartmentModel> query = new BmobQuery<DepartmentModel>();
        query.setLimit(80);
        query.findObjects(getActivity(), new FindListener<DepartmentModel>() {
            @Override
            public void onSuccess(List<DepartmentModel> list) {
                departmentModelList.addAll(list);
                Logger.i("-------getDepartmentModelDate--", "object：" + list.size());
                departmentAdapter.notifyDataSetChanged();
                ListViewUtility.setListViewHeightBasedOnChildren(departmentLv);
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }


    private void getHotQUestionDate() {
        BmobQuery<HotQuestionModel> query = new BmobQuery<HotQuestionModel>();
        query.setLimit(80);
        query.findObjects(getActivity(),new FindListener<HotQuestionModel>() {
            @Override
            public void onSuccess(List<HotQuestionModel> list) {
                hotQUestionModelList.addAll(list);
                Logger.i("-------getHotQUestionDate--", "object：" + list.size());
                hotQuestionAdapter.notifyDataSetChanged();
                ListViewUtility.setListViewHeightBasedOnChildren(hotQuestionLv);
            }
            @Override
            public void onError(int i, String s) {

            }
        });

    }
}
