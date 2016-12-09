package langteng.com.redpocketmoney.ui.search;

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
import langteng.com.redpocketmoney.widget.CircleImageView;


/**
 */
public class HotQuestionAdapter extends BaseAdapter {

    private Context context;
    private List<HotQuestionModel> hotQUestionModelList;
    private LayoutInflater inflater;
    private View view;

    public HotQuestionAdapter(Context context, List<HotQuestionModel> hotQUestionModelList) {
        this.context = context;
        this.hotQUestionModelList = hotQUestionModelList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return hotQUestionModelList == null ? 0 : hotQUestionModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotQUestionModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.department_item, null);
        }
        TextView titleTV = ViewHolderUtils.getViewHolderView(convertView, R.id.title_tv);
        titleTV.setText(hotQUestionModelList.get(position).questionName);

        CircleImageView icon = ViewHolderUtils.getViewHolderView(convertView, R.id.icon_img);
        Glide.with(GlobalApplication.getInstance())
                .load(hotQUestionModelList.get(position).iconUrl)
                .asBitmap()
                .placeholder(R.mipmap.defalut_icon)
                .into(icon);

        return convertView;
    }


}
