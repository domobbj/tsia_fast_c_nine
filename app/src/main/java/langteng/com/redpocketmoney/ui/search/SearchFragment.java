package langteng.com.redpocketmoney.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.baselib.utils.ListViewUtility;
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
    private ScrollView scrollView;

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
        scrollView = (ScrollView) view.findViewById(R.id.scroll_search);
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
                        , "员工名片",
                        departmentModelList.get(i).departmentId);
            }
        });

        view.findViewById(R.id.search_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getDepartmentModelDate();
        getHotQUestionDate();
    }


    private void getDepartmentModelDate() {

        DepartmentModel  departmentModel = new DepartmentModel();
        departmentModel.departmentId = "hrdepartment";
        departmentModel.departmentName = "行政部";
        departmentModel.iconUrl = R.mipmap.administration+"";
        departmentModelList.add(departmentModel);


        DepartmentModel   departmentModel1 = new DepartmentModel();
        departmentModel1.departmentId = "financedepartment";
        departmentModel1.departmentName = "财务部";
        departmentModel1.iconUrl = R.mipmap.finance+"";
        departmentModelList.add(departmentModel1);

        DepartmentModel  departmentModel2 = new DepartmentModel();
        departmentModel2.departmentId = "designdepartment";
        departmentModel2.departmentName = "设计中心";
        departmentModel2.iconUrl = R.mipmap.design+"";
        departmentModelList.add(departmentModel2);

        DepartmentModel departmentModel3 = new DepartmentModel();
        departmentModel3.departmentId = "pad";
        departmentModel3.departmentName = "效果广告部";
        departmentModel3.iconUrl = R.mipmap.ads+"";
        departmentModelList.add(departmentModel3);

        departmentAdapter.notifyDataSetChanged();
        ListViewUtility.setListViewHeightBasedOnChildren(departmentLv);

//        BmobQuery<DepartmentModel> query = new BmobQuery<DepartmentModel>();
//        query.setLimit(80);
//        query.findObjects(getActivity(), new FindListener<DepartmentModel>() {
//            @Override
//            public void onSuccess(List<DepartmentModel> list) {
//                departmentModelList.addAll(list);
//                Logger.i("-------getDepartmentModelDate--", "object：" + list.size());
//
//                ListViewUtility.setListViewHeightBasedOnChildren(departmentLv);
//                scrollView.smoothScrollTo(0, 0);
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });
    }


    private void getHotQUestionDate() {
        HotQuestionModel hotQuestionModel;

        hotQuestionModel = new HotQuestionModel();
        hotQuestionModel.questionName = "办公wifi问题反馈";
        hotQuestionModel.iconUrl = R.mipmap.question + "";
        hotQuestionModel.userId = "0025";
        hotQUestionModelList.add(hotQuestionModel);

        hotQuestionModel = new HotQuestionModel();
        hotQuestionModel.questionName = "报销流程问题";
        hotQuestionModel.iconUrl = R.mipmap.question_red + "";
        hotQuestionModel.userId = "0026";
        hotQUestionModelList.add(hotQuestionModel);

        hotQuestionModel = new HotQuestionModel();
        hotQuestionModel.questionName = "合同盖章问题";
        hotQuestionModel.iconUrl = R.mipmap.question + "";
        hotQuestionModel.userId = "0026";
        hotQUestionModelList.add(hotQuestionModel);
        hotQuestionAdapter.notifyDataSetChanged();
        ListViewUtility.setListViewHeightBasedOnChildren(hotQuestionLv);

//        BmobQuery<HotQuestionModel> query = new BmobQuery<HotQuestionModel>();
//        query.setLimit(80);
//        query.findObjects(getActivity(), new FindListener<HotQuestionModel>() {
//            @Override
//            public void onSuccess(List<HotQuestionModel> list) {
//                hotQUestionModelList.addAll(list);
//                Logger.i("-------getHotQUestionDate--", "object：" + list.size());
//                hotQuestionAdapter.notifyDataSetChanged();
//                ListViewUtility.setListViewHeightBasedOnChildren(hotQuestionLv);
//                scrollView.smoothScrollTo(0, 0);
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });

    }
}
