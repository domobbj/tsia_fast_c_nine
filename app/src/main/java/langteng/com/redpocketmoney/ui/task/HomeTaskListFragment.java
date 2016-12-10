package langteng.com.redpocketmoney.ui.task;

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

import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.personnal.ActivityWorker;
import langteng.com.redpocketmoney.ui.task.adapter.HomeTaskListAdapter;

/**
 * Created by lang on 16/11/17.
 */
public class HomeTaskListFragment extends Fragment {


    private ListView taskLv;
    private HomeTaskListAdapter adapter;
    private List<TaskModel> taskModelList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, null);
        initView(view);
        setDate();
        return view;
    }

    private void initView(View view) {
        taskLv =(ListView)view.findViewById(R.id.task_lv);
        adapter = new HomeTaskListAdapter(getActivity(),taskModelList);
        taskLv.setAdapter(adapter);
        taskLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    TaskActivity.startActivityWorker(getActivity(),
                            "PublishTaskListFragment","任务分配");
                }else if (position==1){
                    ActivityWorker.startActivityWorker(getActivity(),
                            "WebviewFragment","会议室预定",
                            "http://www.bluefocusmix.com/parts.php?mod=meetingroom");
                }else if (position==2){
                    ActivityWorker.startActivityWorker(getActivity(),
                            "WebviewFragment","员工投票",
                            "https://www.wenjuan.com/s/3A3emu/");
                }else if (position==3){
                    ActivityWorker.startActivityWorker(getActivity(),
                            "WebviewFragment","菜谱预告",
                            "http://h5.dbbar.net/caipu1");
                }

            }
        });
    }

    private void setDate(){

        TaskModel model ;
        model = new TaskModel();
        model.title = "通知";
        model.content = "一键通知 一呼百应";
        model.iconurl = R.mipmap.notice_icon+"";
//        taskModelList.add(model);

        model = new TaskModel();
        model.title = "任务分配";
        model.content = "工作进展，最新掌握";
        model.iconurl = R.mipmap.task_icon+"";
        taskModelList.add(model);

        model = new TaskModel();
        model.title = "会议室预定";
        model.content = "预定会议室，一手搞定";
        model.iconurl = R.mipmap.meetingroom+"";
        taskModelList.add(model);

        model = new TaskModel();
        model.title = "员工投票";
        model.content = "全民公投 即得结果";
        model.iconurl = R.mipmap.data_icon+"";
        taskModelList.add(model);

        model = new TaskModel();
        model.title = "菜谱预告";
        model.content = "好吃的，提前知道";
        model.iconurl = R.mipmap.food_icon+"";
        taskModelList.add(model);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
