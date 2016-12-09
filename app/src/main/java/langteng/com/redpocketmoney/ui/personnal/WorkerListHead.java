package langteng.com.redpocketmoney.ui.personnal;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import langteng.com.redpocketmoney.R;

/**
 */

public class WorkerListHead extends LinearLayout {

    public WorkerListHead(Context context) {
        super(context);
        initView();
    }

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public WorkerListHead(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkerListHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.worklist_head, null);

    }
}
