package langteng.com.baselib.widget;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import langteng.com.baselib.R;
import langteng.com.baselib.utils.StringUtil;

/**
 *  有两个按钮的弹窗
 */
public class CommonTwoButtonDialog extends DialogFragment {


    private TextView titleTv;
    private TextView contentTv;
    private TextView cancleTv;

    private TextView deleteTv;

    private String titleStr;
    private String contentStr;
    private String leftStr;
    private String rightStr;
    private View.OnClickListener leftClickListener;
    private View.OnClickListener rightClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.dialog_twobutton, container, false);
        initView(v);
        return v;
    }

    private void initView(View view) {
        cancleTv = (TextView) view.findViewById(R.id.cancle);
        contentTv = (TextView) view.findViewById(R.id.contenthint);
        deleteTv = (TextView) view.findViewById(R.id.confirm);
        titleTv = (TextView) view.findViewById(R.id.titletv);


        if (!StringUtil.isEmpty(titleStr)) {
            titleTv.setText(titleStr);
        }else {
            titleTv.setVisibility(View.GONE);
        }
        if (leftClickListener != null) {
            cancleTv.setOnClickListener(leftClickListener);
            cancleTv.setText(leftStr);
        } else {
            cancleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        deleteTv.setText(rightStr);
        deleteTv.setOnClickListener(rightClickListener);


        if (!StringUtil.isEmpty(contentStr)) {
            contentTv.setText(contentStr);
        } else {
            contentTv.setVisibility(View.GONE);
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
        this.contentStr = contentStr;
        if (contentTv != null) {
            contentTv.setText(Html.fromHtml(contentStr));
        }
    }

    public void setLeftTextAndClickListener(String leftStr, View.OnClickListener clickListener) {
        this.leftStr = leftStr;
        this.leftClickListener = clickListener;
        if (cancleTv != null) {
            cancleTv.setText(leftStr);
            cancleTv.setOnClickListener(clickListener);
        }
    }

    public void setRightTextAndClickListener(String rightStr, View.OnClickListener clickListener) {
        this.rightStr = rightStr;
        this.rightClickListener = clickListener;
        if (deleteTv != null) {
            deleteTv.setText(leftStr);
            deleteTv.setOnClickListener(clickListener);
        }
    }


}
