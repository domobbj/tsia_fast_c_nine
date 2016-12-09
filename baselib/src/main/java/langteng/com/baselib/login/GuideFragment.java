package langteng.com.baselib.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import langteng.com.baselib.R;

/**
 * Created by lang on 16/8/21.
 */
public class GuideFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_guide_item, null);
        setView(view);
        return view;
    }

    private void setView(View view) {
        ImageView guideImg = (ImageView) view.findViewById(R.id.guide_img);
//        guideImg.setBackgroundResource(getArguments().getInt("guideimg",
//                R.mipmap.guide1));
//        if (getArguments().getInt("guideimg", R.mipmap.guide1) == R.mipmap.guide4) {
//            guideImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SPUtil.setBoolean(SpUtilConfig.isFirst, true);
//                    ActivityJump.getInsanceJump(getActivity(), ActivityLogin.class, null, true);
//                }
//            });
//        }
    }

}
