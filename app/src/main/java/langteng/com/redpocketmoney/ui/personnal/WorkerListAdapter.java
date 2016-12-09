package langteng.com.redpocketmoney.ui.personnal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.utils.ViewHolderUtils;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.widget.CircleImageView;


/**
 */
public class WorkerListAdapter extends BaseAdapter {

    private Context context;
    private List<_User> articlesModels;
    private LayoutInflater inflater;
    private View view;

    public WorkerListAdapter(Context context, List<_User> articlesModels) {
        this.context = context;
        this.articlesModels = articlesModels;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return articlesModels == null ? 0 : articlesModels.size();
    }

    @Override
    public Object getItem(int position) {
        return articlesModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.workerlist_item, null);
        }
        TextView userName = ViewHolderUtils.getViewHolderView(convertView, R.id.user_name);
        TextView workName = ViewHolderUtils.getViewHolderView(convertView, R.id.user_workname);
        CircleImageView userIcon = ViewHolderUtils.getViewHolderView(convertView, R.id.user_icon);
        userName.setText("姓名：" +articlesModels.get(position).userNickName);
        workName.setText("职位：" +articlesModels.get(position).userWork);
        Glide.with(GlobalApplication.getInstance())
                .load(articlesModels.get(position).userIcon)
                .asBitmap()
                .placeholder(R.mipmap.defalut_icon)
                .into(userIcon);


        return convertView;
    }


}
