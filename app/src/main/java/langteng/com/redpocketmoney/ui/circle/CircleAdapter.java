package langteng.com.redpocketmoney.ui.circle;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.baselib.utils.ViewHolderUtils;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.widget.CircleImageView;
import langteng.com.redpocketmoney.widget.ExpandableTextView;
import langteng.com.redpocketmoney.widget.MyListView;
import langteng.com.redpocketmoney.widget.NoScrollGridView;


/**
 */
public class CircleAdapter extends BaseAdapter {

    private Context context;
    private List<CircleModel> articlesModels;
    private LayoutInflater inflater;
    private View view;

    public CircleAdapter(Context context, List<CircleModel> articlesModels) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.circle_item, null);
        }
        TextView tagTv = ViewHolderUtils.getViewHolderView(convertView, R.id.circle_tag);
        TextView userName = ViewHolderUtils.getViewHolderView(convertView, R.id.user_name);
        TextView publishTime = ViewHolderUtils.getViewHolderView(convertView, R.id.circle_time);

        TextView praiseTimes = ViewHolderUtils.getViewHolderView(convertView, R.id.prase_tv);

        TextView commentTv = ViewHolderUtils.getViewHolderView(convertView, R.id.comment);

        MyListView commentLv = ViewHolderUtils.getViewHolderView(convertView, R.id.comment_lv);


        final ExpandableTextView expandableTextView = ViewHolderUtils.getViewHolderView(convertView,
                R.id.expand_text_view);
        expandableTextView.setText(articlesModels.get(position).content);
        boolean isShow = false;
        expandableTextView.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
                expandableTextView.setText(articlesModels.get(position).content);
                if (articlesModels.get(position).isShow) {
                    articlesModels.get(position).isShow = false;
                    expandableTextView.setmExpandHidden("点击展开");
                } else {
                    articlesModels.get(position).isShow = true;
                    expandableTextView.setmExpandHidden("收起");
                }
            }
        });

        NoScrollGridView authorNameTv = ViewHolderUtils.getViewHolderView(convertView,
                R.id.gridview_img);
        if (articlesModels.get(position).contentImgs.size() == 4) {
            authorNameTv.setNumColumns(2);
        } else {
            authorNameTv.setNumColumns(3);
        }

        authorNameTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                Intent intent = new Intent(context, ActivityCircleImageView.class);
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < articlesModels.get(position).contentImgs.size(); i++) {
                    buffer.append(articlesModels.get(position).contentImgs.get(i));
                    if (i != articlesModels.get(position).contentImgs.size() - 1) {
                        buffer.append("articlesModel");
                    }
                }
                intent.putExtra("imgs",
                        buffer.toString());
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        CircleImgsAdapter adapter = new CircleImgsAdapter(context,
                articlesModels.get(position).contentImgs);
        authorNameTv.setAdapter(adapter);

        CircleImageView userIcon = ViewHolderUtils.getViewHolderView(convertView, R.id.user_icon);
        if (StringUtil.isEmpty(articlesModels.get(position).tag)) {
            tagTv.setVisibility(View.INVISIBLE);
        } else {
            tagTv.setVisibility(View.VISIBLE);
            tagTv.setText(articlesModels.get(position).tag);
        }
        userName.setText(articlesModels.get(position).userNickName);
        publishTime.setText(articlesModels.get(position).getCreatedAt());
        praiseTimes.setText(articlesModels.get(position).praseList.size() + "");
        commentTv.setText(articlesModels.get(position).commentList.size() + " ");

        Glide.with(GlobalApplication.getInstance())
                .load(articlesModels.get(position).userIcon)
                .asBitmap()
                .placeholder(R.mipmap.defalut_icon)
                .into(userIcon);

        return convertView;
    }


}
