package langteng.com.redpocketmoney.ui.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import langteng.com.baselib.utils.ViewHolderUtils;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.task.TaskModel;


/**
 */
public class MinePublishTaskListAdapter extends BaseAdapter {

    private Context context;
    private List<TaskModel> taskModelList;

    private LayoutInflater inflater;
    private View view;

    public MinePublishTaskListAdapter(Context context, List<TaskModel> taskModelList) {
        this.context = context;
        this.taskModelList = taskModelList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return taskModelList == null ? 0 : taskModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.publish_tasklist_item, null);
        }
        TextView titleTv = ViewHolderUtils.getViewHolderView(convertView, R.id.task_title);
        TextView subtitleTv = ViewHolderUtils.getViewHolderView(convertView, R.id.task_endtime);
        TextView iconImg = ViewHolderUtils.getViewHolderView(convertView, R.id.task_icon);
        titleTv.setText(taskModelList.get(position).title);
        subtitleTv.setText("截至时间：" + taskModelList.get(position).endTime);
        if (taskModelList.get(position).status == 0) {
            iconImg.setText("未开始");
            iconImg.setBackgroundResource(R.drawable.task_status_notbegin_bg);
        } else if (taskModelList.get(position).status == 1) {
            iconImg.setText("已完成");
            iconImg.setBackgroundResource(R.drawable.task_status_finish_bg);
        } else if (taskModelList.get(position).status == 2) {
            iconImg.setText("未完成");
            iconImg.setBackgroundResource(R.drawable.task_status_notfinish_bg);
        }
        return convertView;
    }

}
