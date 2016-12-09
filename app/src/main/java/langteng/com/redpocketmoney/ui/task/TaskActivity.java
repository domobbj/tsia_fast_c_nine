package langteng.com.redpocketmoney.ui.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import langteng.com.baselib.baseui.LibBaseActivity;
import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.task.fragment.MineTaskListFragment;
import langteng.com.redpocketmoney.ui.task.fragment.MyReceivedTaskFragment;
import langteng.com.redpocketmoney.ui.task.fragment.PublishTaskDetailFragment;
import langteng.com.redpocketmoney.ui.task.fragment.PublishTaskFragment;
import langteng.com.redpocketmoney.ui.task.fragment.PublishTaskListFragment;

/**
 */
public class TaskActivity extends LibBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseSetContentView(R.layout.activity_worker);
        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction transacction = fragment.beginTransaction();
        String title = getIntent().getStringExtra("title");
        Logger.i("------tag--", "---" + getIntent().getStringExtra("tag"));
        if ("PublishTaskFragment".equals(getIntent().getStringExtra("tag"))) {
            PublishTaskFragment testFragment = new PublishTaskFragment();
            transacction.replace(R.id.fragment, testFragment);
        } else if ("PublishTaskListFragment".equals(getIntent().getStringExtra("tag"))) {
            PublishTaskListFragment testFragment = new PublishTaskListFragment();
            transacction.replace(R.id.fragment, testFragment);
        } else if ("PublishTaskDetailFragment".equals(getIntent().getStringExtra("tag"))) {
            //   我布置的任务详情
            PublishTaskDetailFragment testFragment = new PublishTaskDetailFragment();
            testFragment.setTaskId(getIntent().getStringExtra("id"));
            transacction.replace(R.id.fragment, testFragment);


        } else if ("MyReceivedTaskFragment".equals(getIntent().getStringExtra("tag"))) {
            //   我收到的任务
            MyReceivedTaskFragment testFragment = new MyReceivedTaskFragment();
            testFragment.setTaskId(getIntent().getStringExtra("id"));
            transacction.replace(R.id.fragment, testFragment);
        } else if ("TaskListFragment".equals(getIntent().getStringExtra("tag"))) {
            //   我发布的任务列表
            MineTaskListFragment testFragment = new MineTaskListFragment();
            transacction.replace(R.id.fragment, testFragment);
//            rightTv.setBackgroundResource(R.mipmap.chuangjian);
            rightTv.setTextSize(45);
            setRightTv(true, "+", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskActivity.startActivityWorker(TaskActivity.this,
                            "PublishTaskFragment", "会议室预定"
                    );
                }
            });
        }
        setTopbarName(title);
        transacction.commit();

    }


    public static void startActivityWorker(Activity activity, String tag, String title) {
        Intent intent = new Intent(activity, TaskActivity.class);
        intent.putExtra("tag", tag);
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }

    public static void startActivityWorker(Activity activity, String tag, String title, String id) {
        Intent intent = new Intent(activity, TaskActivity.class);
        intent.putExtra("tag", tag);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

}
