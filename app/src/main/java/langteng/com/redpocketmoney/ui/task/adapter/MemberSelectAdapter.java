package langteng.com.redpocketmoney.ui.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.utils.ViewHolderUtils;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.widget.CircleImageView;


/**
 */
public class MemberSelectAdapter extends BaseAdapter {

    private Context context;
    private List<_User> membersList;

    private LayoutInflater inflater;
    private View view;

    public MemberSelectAdapter(Context context, List<_User> membersList) {
        this.context = context;
        this.membersList = membersList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return membersList == null ? 0 : membersList.size();
    }

    @Override
    public Object getItem(int position) {
        return membersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.member_select_item, null);
        }
        CircleImageView iconImg = ViewHolderUtils.getViewHolderView(convertView, R.id.user_icon);
        Glide.with(GlobalApplication.getInstance())
                .load(membersList.get(position).iconFile)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .into(iconImg);
        return convertView;
    }
}
