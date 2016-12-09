package langteng.com.redpocketmoney.ui.task.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.ProgrammingApplication;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.ui.task.adapter.MinePublishTaskListAdapter;
import langteng.com.redpocketmoney.ui.task.TaskActivity;
import langteng.com.redpocketmoney.ui.task.TaskModel;

/**
 *
 */
public class MineTaskListFragment extends Fragment  {



    private ListView taskLv;

    private List<TaskModel>  taskModelList = new ArrayList<>();
    private  MinePublishTaskListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_tasklist, null);
        initView(view1);
        getTaskModels();
        return view1;
    }

    private void initView(View view) {
        taskLv  =(ListView)view.findViewById(R.id.publish_lv);
        adapter = new MinePublishTaskListAdapter(getActivity(),taskModelList);
        taskLv.setAdapter(adapter);
        taskLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskActivity.startActivityWorker(getActivity(),
                        "PublishTaskDetailFragment","发布的任务",
                        taskModelList.get(position).getObjectId());
            }
        });
    }


    private void getTaskModels() {
        BmobQuery<TaskModel> query = new BmobQuery<TaskModel>();
        query.setLimit(50);
        _User user = ProgrammingApplication.get().getUser();
        query.addWhereEqualTo("publisherId", user.getUsername());
        query.findObjects(getActivity(), new FindListener<TaskModel>() {
            @Override
            public void onSuccess(List<TaskModel> list1) {
                taskModelList.addAll(list1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
                Logger.i("-----CircleModel---", "s: " + s);
            }
        });

    }
}
