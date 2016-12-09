package langteng.com.baselib.baseui;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;

import langteng.com.baselib.R;
import langteng.com.baselib.utils.CustomToast;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.baselib.widget.LoadingDialog;

/**
 * JHS
 * 2015/8/25
 */

public class LibBaseActivity extends AppCompatActivity {
    /**
     * 首页标题
     */
    protected TextView mTitleTextView;
    protected TextView rightTv;
    protected ImageView leftImg;
    protected LoadingDialog loadingDialog;
    public static LibBaseActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
        setContentView(R.layout.base_activity);
        mTitleTextView = (TextView) findViewById(R.id.header_tv_title);
        leftImg = (ImageView) findViewById(R.id.header_iv_left);
        rightTv = (TextView) findViewById(R.id.header_tv_right);
        leftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mTencent = Tencent.createInstance(SdkConfig.APPID_QQ, GlobalApplication.getInstance());
    }


    public View BaseSetContentView(int layoutID) {
        FrameLayout layout = (FrameLayout) findViewById(R.id.llcontent);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutID, null);
        layout.addView(view);
        return view;
    }


    /**
     * 设置标题名称
     *
     * @param titleName 标题名称
     */
    public void setTopbarName(String titleName) {
        if (StringUtil.getStringLength(titleName) > 16) {

            if (titleName.length() > 16) {
                mTitleTextView.setText(titleName.substring(0, 14) + "...");
            } else {
                mTitleTextView.setText(titleName);
            }
        } else {
            mTitleTextView.setText(titleName);
        }
    }

    /**
     * 设置标题名称
     *
     * @param titleResId 标题资源id
     */
    public void setTopbarName(int titleResId) {
        mTitleTextView.setVisibility(View.VISIBLE);
        mTitleTextView.setText(titleResId);
    }

    /**
     * 右边显示
     *
     * @param isRightShow  右侧按钮是否显示
     * @param rightText    右侧按钮文字设置
     * @param rightOnclick 右侧按钮点击事件
     */
    public void setLeftAndrightTv(boolean isRightShow, int rightText, View.OnClickListener rightOnclick) {
        if (isRightShow) {
            rightTv.setVisibility(View.VISIBLE);
            if (rightText != -1) {
                rightTv.setText(rightText);
            }
            if (rightOnclick != null) {
                rightTv.setOnClickListener(rightOnclick);
            }
        } else {
            rightTv.setVisibility(View.GONE);
        }
    }

    /**
     * 右边显示
     *
     * @param isRightShow  右侧按钮是否显示
     * @param rightText    右侧按钮文字设置
     * @param rightOnclick 右侧按钮点击事件
     */
    public void setRightTv(boolean isRightShow, String rightText, View.OnClickListener rightOnclick) {
        if (isRightShow) {
            rightTv.setVisibility(View.VISIBLE);
            rightTv.setText(rightText);
            if (rightOnclick != null) {
                rightTv.setOnClickListener(rightOnclick);
            }
        } else {
            rightTv.setVisibility(View.GONE);
        }
    }

    public void setLeftInvisible(boolean leftInvisible) {
        if (leftImg != null && leftInvisible) {
            leftImg.setVisibility(View.INVISIBLE);
        }
    }

    public void setOnLeftClick(View.OnClickListener click) {
        leftImg.setOnClickListener(click);
    }

    /**
     * 显示Toast
     *
     * @param StringID 资源id
     */
    protected void showToast(int StringID) {
        CustomToast.showToast(getApplication(), StringID, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param toastStr Toast  文字内容
     */

    protected void showToast(String toastStr) {
        CustomToast.showToast(getApplication(), toastStr, Toast.LENGTH_SHORT);
    }

    public void showDialog(String msg) {
        hiddenDialog();
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.createDialog(this);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
        }
        if (msg != null) {
            TextView tvMsg = (TextView) loadingDialog.findViewById(R.id.id_tv_loadingmsg);
            if (tvMsg != null) {
                tvMsg.setText(msg);
            }
        } else {
            TextView tvMsg = (TextView) loadingDialog.findViewById(R.id.id_tv_loadingmsg);
            if (tvMsg != null) {
                tvMsg.setText("");
            }
        }
        if (!this.isFinishing()) {
            loadingDialog.show();
        }
    }


    public void hiddenDialog() {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            loadingDialog = null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
