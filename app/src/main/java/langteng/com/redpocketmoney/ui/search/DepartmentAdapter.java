package langteng.com.redpocketmoney.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import langteng.com.baselib.utils.ViewHolderUtils;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.widget.CircleImageView;


/**
 */
public class DepartmentAdapter extends BaseAdapter {

    private Context context;
    private List<DepartmentModel> articlesModels;
    private LayoutInflater inflater;
    private View view;

    public DepartmentAdapter(Context context, List<DepartmentModel> articlesModels) {
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
            convertView = inflater.inflate(R.layout.department_item, null);
        }
        TextView titleTV = ViewHolderUtils.getViewHolderView(convertView, R.id.title_tv);

        CircleImageView icon = ViewHolderUtils.getViewHolderView(convertView, R.id.icon_img);
//        Glide.with(GlobalApplication.getInstance())
//                .load(articlesModels.get(position).iconUrl)
//                .asBitmap()
//                .placeholder(R.mipmap.defalut_icon)
//                .into(icon);
        icon.setImageResource(Integer.parseInt(articlesModels.get(position).iconUrl));
        titleTV.setText(articlesModels.get(position).departmentName);


        return convertView;
    }


}
