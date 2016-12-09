package langteng.com.redpocketmoney.ui.task.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.baselib.utils.Logger;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.redpocketmoney.ProgrammingApplication;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.ui.task.TaskModel;

/**
 * 任务详情页面
 */
public class PublishTaskDetailFragment extends LibBaseFragment {


    private TextView taskName;
    private TextView taskDetail;

    private TextView taskWorkers;
    private TextView taskEndtime;
    private TextView taskStatus;
    private TextView taskHint;

    private void initView(View view) {
        taskName = (TextView) view.findViewById(R.id.taskname);
        taskDetail = (TextView) view.findViewById(R.id.taskdetail);

        taskWorkers = (TextView) view.findViewById(R.id.task_worker);
        taskEndtime = (TextView) view.findViewById(R.id.task_endtime);
        taskStatus = (TextView) view.findViewById(R.id.task_status);
        taskHint = (TextView) view.findViewById(R.id.task_hint);
    }

    private void setData() {
        if (taskModel == null) return;
        taskName.setText(taskModel.title);
        taskDetail.setText(taskModel.content);
        StringBuffer buffer = new StringBuffer();

        int finishNum = 0;
        int notStart = 0;
        for (int i = 0; i < taskModel.workerList.size(); i++) {
            if (i != 0) {
                buffer.append(" ,");
            }
            buffer.append(taskModel.workerList.get(i).userNickNames);
            if (taskModel.workerList.get(i).status == 2) {
                finishNum++;
            } else if (taskModel.workerList.get(i).status == 0) {
                notStart++;
            }
        }
        taskWorkers.setText(buffer.toString());
        taskEndtime.setText(taskModel.endTime);
        if (notStart == taskModel.workerList.size()) {
            taskStatus.setText("未开始");
        } else if (finishNum == taskModel.workerList.size()) {
            taskStatus.setText("已完成");
        } else {
            taskStatus.setText("未完成");
        }
        if (StringUtil.isEmpty(taskModel.moreDes)) {
            taskHint.setText("暂无");
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_publish_taskdetail, null);
        initView(view1);
        getTaskModels();
        return view1;
    }


    private String id;
    private TaskModel taskModel;

    public void setTaskId(String id) {
        this.id = id;
    }

    private void getTaskModels() {
        BmobQuery<TaskModel> query = new BmobQuery<TaskModel>();
        query.setLimit(50);
        query.addWhereEqualTo("objectId", id);
        final _User user = ProgrammingApplication.get().getUser();
        query.findObjects(getActivity(), new FindListener<TaskModel>() {
            @Override
            public void onSuccess(List<TaskModel> list1) {
                if (list1 != null && list1.size() > 0) {
                    taskModel = list1.get(0);
                    Logger.i("-----getTaskModels---", "s: " + list1.size());
                    setData();
                }
            }

            @Override
            public void onError(int i, String s) {
                Logger.i("-----CircleModel---", "s: " + s);
            }
        });
    }
}
