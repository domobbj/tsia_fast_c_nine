package langteng.com.redpocketmoney.ui.personnal;

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
import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.login._User;

/**
 */
public class WorkerListFragment extends LibBaseFragment {

    private ListView listView;
    private List<_User> workerModelList = new ArrayList<>();
    private WorkerListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_workerlist, null);
        initView(view1);
        return view1;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.worker_lv);
        adapter = new WorkerListAdapter(getActivity(), workerModelList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActivityWorker.startActivityWorker(getActivity(), "WorkerFragment","员工名片",
                        workerModelList.get(i).getUsername());
            }
        });
        getDate();
    }
    private String departmentId;

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    private void getDate() {
        BmobQuery<_User> query = new BmobQuery<_User>();
        query.addWhereEqualTo("departmentId", departmentId);
        query.setLimit(50);
        query.findObjects(getActivity(), new FindListener<_User>() {
            @Override
            public void onSuccess(List<_User> list) {
                workerModelList.addAll(list);
                Logger.i("-----workerModelList---","list: " + list.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

}
