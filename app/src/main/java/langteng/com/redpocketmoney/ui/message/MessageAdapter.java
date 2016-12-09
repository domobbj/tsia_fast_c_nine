package langteng.com.redpocketmoney.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.utils.Logger;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.baselib.utils.ViewHolderUtils;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.widget.CircleImageView;


/**
 */
public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<MessageListModel> articlesModels;
    private LayoutInflater inflater;
    private View view;

    public MessageAdapter(Context context, List<MessageListModel> articlesModels) {
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
            convertView = inflater.inflate(R.layout.msg_item, null);
        }
        TextView titleTV = ViewHolderUtils.getViewHolderView(convertView, R.id.msg_title);
        TextView timeTv = ViewHolderUtils.getViewHolderView(convertView, R.id.msg_time);
        TextView hintTv = ViewHolderUtils.getViewHolderView(convertView, R.id.msg_hint);
        CircleImageView iconImg = ViewHolderUtils.getViewHolderView(convertView, R.id.msg_icon);

        titleTV.setText(articlesModels.get(position).title);
        hintTv.setText(articlesModels.get(position).hint);
//        timeTv.setText(articlesModels.get(position).getCreatedAt());

        timeTv.setText(articlesModels.get(position).publishTime);

        Logger.i("-------iconUrl---","iconUrl: " + articlesModels.get(position).iconUrl);
        if (!StringUtil.isEmpty(articlesModels.get(position).iconUrl)&&(
                articlesModels.get(position).iconUrl.contains(".png") ||
                articlesModels.get(position).iconUrl.contains(".jpg"))) {
            Glide.with(GlobalApplication.getInstance())
                    .load((articlesModels.get(position).iconUrl))
                    .asBitmap()
                    .placeholder(R.mipmap.defalut_icon)
                    .into(iconImg);
        } else {
            if (!StringUtil.isEmpty(articlesModels.get(position).iconUrl)) {
                iconImg.setImageResource(
                        Integer.parseInt(articlesModels.get(position).iconUrl));
            }
        }
        return convertView;
    }


}
