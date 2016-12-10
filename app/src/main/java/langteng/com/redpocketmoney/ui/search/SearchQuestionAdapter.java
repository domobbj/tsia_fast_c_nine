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


/**
 */
public class SearchQuestionAdapter extends BaseAdapter {

    private Context context;
    private List<SearchInfoModel.ResponseBean.DocsBean> docsBeanList;
    private LayoutInflater inflater;
    private View view;

    public SearchQuestionAdapter(Context context, List<SearchInfoModel.ResponseBean.DocsBean> docsBeanList) {
        this.context = context;
        this.docsBeanList = docsBeanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return docsBeanList == null ? 0 : docsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return docsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.searchquestion_item, null);
        }
        TextView titleTV = ViewHolderUtils.getViewHolderView(convertView, R.id.title_tv);
        titleTV.setText(docsBeanList.get(position).key);
        return convertView;
    }


}
