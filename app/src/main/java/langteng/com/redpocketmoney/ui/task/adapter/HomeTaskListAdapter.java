package langteng.com.redpocketmoney.ui.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.baselib.utils.ViewHolderUtils;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.task.TaskModel;
import langteng.com.redpocketmoney.widget.CircleImageView;


/**
 */
public class HomeTaskListAdapter extends BaseAdapter {

    private Context context;
    private List<TaskModel> taskModelList;

    private LayoutInflater inflater;
    private View view;

    public HomeTaskListAdapter(Context context, List<TaskModel> taskModelList) {
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
            convertView = inflater.inflate(R.layout.task_item, null);
        }
        TextView titleTv = ViewHolderUtils.getViewHolderView(convertView, R.id.task_title);
        TextView subtitleTv = ViewHolderUtils.getViewHolderView(convertView, R.id.task_subtitle);
        CircleImageView iconImg = ViewHolderUtils.getViewHolderView(convertView, R.id.task_icon);
        titleTv.setText(taskModelList.get(position).title);
        subtitleTv.setText(taskModelList.get(position).content);
        if (!StringUtil.isEmpty(taskModelList.get(position).iconurl) &&
                taskModelList.get(position).iconurl.contains(".png") ||
                taskModelList.get(position).iconurl.contains(".jpg")) {
            Glide.with(GlobalApplication.getInstance())
                    .load(taskModelList.get(position).iconurl)
                    .asBitmap()
                    .placeholder(R.mipmap.defalut_icon)
                    .into(iconImg);
        } else {
            if (StringUtil.isInteger(taskModelList.get(position).iconurl)) {
                iconImg.setImageResource(
                        Integer.parseInt(taskModelList.get(position).iconurl));
            }
        }
        return convertView;
    }
}
