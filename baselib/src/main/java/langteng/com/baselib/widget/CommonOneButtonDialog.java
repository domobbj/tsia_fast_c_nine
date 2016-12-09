package langteng.com.baselib.widget;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import langteng.com.baselib.R;
import langteng.com.baselib.utils.StringUtil;

/**
 * 只有一个 按钮的弹窗
 */
public class CommonOneButtonDialog extends DialogFragment {

    private TextView titleTv;
    private TextView describe;

    private TextView confirm;

    private String describeStr;
    private String titleStr;
    private String confirmStr;

    private int describeResource = -1;

    private View.OnClickListener leftClickListener;
    private View adsView;
    private LinearLayout adsLL;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.onebutton_dialog, container, false);
        initViewTest(v);
        return v;
    }

    private void initViewTest(View view) {
        titleTv = (TextView) view.findViewById(R.id.titletv);
        describe = (TextView) view.findViewById(R.id.errorMessage);
        confirm = (TextView) view.findViewById(R.id.confirm);
        adsLL = (LinearLayout) view.findViewById(R.id.ads_ll);

        if (leftClickListener != null) {
            confirm.setOnClickListener(leftClickListener);
        } else {
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        titleTv.setText(titleStr);
        if (!StringUtil.isEmpty(describeStr)) {
            describe.setText(Html.fromHtml(describeStr));
        } else {
            describe.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.submit_dialog);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setTitle(String title) {
        this.titleStr = title;
        if (titleTv != null) {
            titleTv.setText(title);
        }
    }

    public void setContent(String contentStr) {
        this.describeStr = contentStr;
        if (describe != null) {
            describe.setText(Html.fromHtml(contentStr));
        }
    }

    public void setAds(View view) {
        this.adsView = view;
        if (adsLL != null && null != view) {
            adsLL.addView(view);
        }

    }


    public void setConfirmTextAndClickListener(String confirmStr, View.OnClickListener clickListener) {
        this.confirmStr = confirmStr;
        this.leftClickListener = clickListener;
        if (confirm != null) {
            confirm.setText(confirmStr);
            confirm.setOnClickListener(clickListener);
        }
    }


}
