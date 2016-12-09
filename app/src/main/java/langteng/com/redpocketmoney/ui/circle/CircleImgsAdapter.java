package langteng.com.redpocketmoney.ui.circle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.utils.ViewHolderUtils;
import langteng.com.redpocketmoney.R;


/**
 */
public class CircleImgsAdapter extends BaseAdapter {

    private Context context;
    private List<String> imgList;
    private LayoutInflater inflater;
    private View view;

    public CircleImgsAdapter(Context context, List<String> imgList) {
        this.context = context;
        this.imgList = imgList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgList == null ? 0 : imgList.size();
    }

    @Override
    public Object getItem(int position) {
        return imgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.circle_item_img, null);
        }
        ImageView img = ViewHolderUtils.getViewHolderView(convertView, R.id.imgs);
        Glide.with(GlobalApplication.getInstance())
                .load(imgList.get(position))
                .asBitmap()
                .placeholder(R.mipmap.defalut_icon)
                .into(img);
        return convertView;
    }


}
