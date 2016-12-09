package langteng.com.redpocketmoney.ui.task.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.ProgrammingApplication;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.ui.task.TaskModel;

/**
 * 发表圈子内容
 */
public class MyReceivedTaskFragment extends LibBaseFragment {


    private EditText updateEdt;
    private TextView taskName;
    private TextView publisher;
    private TextView taskDetail;
    private TextView taskMembers;

    private TextView taskEndtime;
    private TextView taskStatus;

    private void initView(View view) {
        updateEdt = (EditText) view.findViewById(R.id.circle_publish_edt);
        taskName = (TextView) view.findViewById(R.id.taskname);
        publisher = (TextView) view.findViewById(R.id.publisher);
        taskDetail = (TextView) view.findViewById(R.id.taskdetail);
        taskMembers = (TextView) view.findViewById(R.id.task_worker);
        taskEndtime = (TextView) view.findViewById(R.id.task_endtime);

        taskStatus = (TextView) view.findViewById(R.id.task_status);


        view.findViewById(R.id.update_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        taskStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskModel.moreDes = updateEdt.getText().toString();
                taskModel.update(getActivity(), new UpdateListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        });
    }

    private void setData() {
        if (taskModel == null) return;
        taskName.setText(taskModel.title);
        publisher.setText(taskModel.publisherName);
        taskDetail.setText(taskModel.content);
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < taskModel.workerList.size(); i++) {
            if (i != 0) {
                buffer.append(" ,");
            }
            buffer.append(taskModel.workerList.get(i).userNickNames);
        }
        taskMembers.setText(buffer.toString());
        taskEndtime.setText(taskModel.endTime);
        if (taskModel.status == 0) {
            taskStatus.setText("未开始");
        } else if (taskModel.status == 1) {
            taskStatus.setText("未完成");
        } else if (taskModel.status == 2) {
            taskStatus.setText("已完成");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_received_taskdetail, null);
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
