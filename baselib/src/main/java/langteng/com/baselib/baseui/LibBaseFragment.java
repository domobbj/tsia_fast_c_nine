package langteng.com.baselib.baseui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import langteng.com.baselib.R;
import langteng.com.baselib.utils.MyCustomToast;
import langteng.com.baselib.widget.LoadingDialog;


/**
 * JHS
 * 2015/8/25
 */
public class LibBaseFragment extends Fragment {

    protected LoadingDialog loadingDialog;

    protected LinearLayout topView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (topView != null) {
            return topView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }


    protected View BaseContentView(View view) {
        topView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_title, null);
        topView.addView(view);

        return topView;
    }


    protected void setTitle(String title) {
        TextView titleTv = (TextView) topView.findViewById(R.id.title_tv);
        titleTv.setText(title);
    }


    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    protected void setRightTv(String title, View.OnClickListener clickListener) {
        TextView titleTv = (TextView) topView.findViewById(R.id.right_tv);
        titleTv.setText(title);
        titleTv.setOnClickListener(clickListener);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog(String msg) {
        if (loadingDialog == null && getActivity() != null) {
            loadingDialog = LoadingDialog.createDialog(getActivity());
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
        }
        loadingDialog.show();
    }

    public void hideDialog() {
        if (loadingDialog != null && getActivity() != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            loadingDialog = null;
        }
    }

    protected void showToast(int StringID) {
        MyCustomToast.makeText(getActivity(), StringID, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String toastStr) {
        MyCustomToast.makeText(getActivity(), toastStr, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        }
        super.onDestroyView();
    }


}
