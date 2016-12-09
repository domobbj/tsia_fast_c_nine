package langteng.com.redpocketmoney.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import langteng.com.baselib.utils.ViewHolderUtils;
import langteng.com.redpocketmoney.R;


/**
 */
public class NoticeAdapter extends BaseAdapter {

    private Context context;
    private List<NoticeModel> noticeModelList;
    private LayoutInflater inflater;
    private View view;

    public NoticeAdapter(Context context, List<NoticeModel> noticeModelList) {
        this.context = context;
        this.noticeModelList = noticeModelList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return noticeModelList == null ? 0 : noticeModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.notice_item, null);
        }
        TextView titleTV = ViewHolderUtils.getViewHolderView(convertView, R.id.notice_title);

        titleTV.setText(noticeModelList.get(position).title);

        return convertView;
    }

}
