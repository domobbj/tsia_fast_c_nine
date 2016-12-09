package langteng.com.baselib.widget;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import langteng.com.baselib.R;


/**
 */
public class UpdateDialog extends DialogFragment {


    private TextView confirm;
    private ImageView zingImg;
    private RingProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_update, container, false);
        initViewTest(v);
        return v;
    }

    private void initViewTest(View view) {
        view.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        progressBar = (RingProgressBar) view.findViewById(R.id.progress_bar_2);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ShareDialog);
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

    public void updatePro(int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
            progressBar.setOnProgressListener(new RingProgressBar.OnProgressListener() {

                @Override
                public void progressToComplete() {
                    // Progress reaches the maximum callback default Max value is 100
                }
            });
        }
    }

}
