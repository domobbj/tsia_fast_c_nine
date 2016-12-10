package langteng.com.redpocketmoney.ui.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.redpocketmoney.R;

/**
 */
public class CircleImageFragment extends LibBaseFragment  {

    private ImageViewTouch myZoomImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_circleimg, null);
        initView(view1);
        return view1;
    }

    private void initView(View view) {
        myZoomImageView = (ImageViewTouch) view.findViewById(R.id.circle_img);
        Glide.with(GlobalApplication.getInstance())
                .load(imgUrl)
                .asBitmap()
                .placeholder(R.mipmap.defalut_icon)
                .into(myZoomImageView);
    }


    private String imgUrl;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
