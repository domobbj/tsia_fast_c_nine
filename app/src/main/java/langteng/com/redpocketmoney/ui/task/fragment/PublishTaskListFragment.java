package langteng.com.redpocketmoney.ui.task.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.ProgrammingApplication;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.ui.task.adapter.MinePublishTaskListAdapter;
import langteng.com.redpocketmoney.ui.task.TaskActivity;
import langteng.com.redpocketmoney.ui.task.TaskModel;

/**
 * 收到的任务列表 和 布置任务入口
 */
public class PublishTaskListFragment extends LibBaseFragment {

    private TextView publishTv;
    private ListView taskLv;
    private List<TaskModel> taskModelList = new ArrayList<>();
    MinePublishTaskListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_task_publishlist, null);
        initView(view1);
        getTaskModels();
        return view1;
    }

    private void initView(View view) {
        publishTv = (TextView) view.findViewById(R.id.publish_tv);
        publishTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskActivity.startActivityWorker(getActivity(), "TaskListFragment", "任务发布");
            }
        });

        taskLv = (ListView) view.findViewById(R.id.publish_lv);
        adapter = new MinePublishTaskListAdapter(getActivity(), taskModelList);
        taskLv.setAdapter(adapter);

        taskLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskActivity.startActivityWorker(getActivity(),
                        "MyReceivedTaskFragment", "收到的任务",
                        taskModelList.get(position).getObjectId());
            }
        });
    }


    private void getTaskModels() {
        BmobQuery<TaskModel> query = new BmobQuery<TaskModel>();
        query.setLimit(50);
        final _User user = ProgrammingApplication.get().getUser();
        query.findObjects(getActivity(), new FindListener<TaskModel>() {
            @Override
            public void onSuccess(List<TaskModel> list1) {
                for (int i = 0; i < list1.size(); i++) {
                    for (int j = 0; j < list1.get(i).workerList.size(); j++) {
                        if (list1.get(i).workerList.get(j).username.
                                contains(user.getUsername())) {
                            taskModelList.add(list1.get(i));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onError(int i, String s) {
                Logger.i("-----CircleModel---", "s: " + s);
            }
        });
    }

}
