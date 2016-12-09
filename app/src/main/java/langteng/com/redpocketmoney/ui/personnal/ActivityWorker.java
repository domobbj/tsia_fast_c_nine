package langteng.com.redpocketmoney.ui.personnal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import langteng.com.baselib.baseui.LibBaseActivity;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.eventbus.EventCenter;
import langteng.com.redpocketmoney.ui.main.WebviewFragment;
import langteng.com.redpocketmoney.ui.message.NoticeListFragment;

/***
 *
 ***/

public class ActivityWorker extends LibBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseSetContentView(R.layout.activity_worker);
        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction transacction = fragment.beginTransaction();
        String title =getIntent().getStringExtra("title");
        String id =getIntent().getStringExtra("id");
        if ("WorkerListFragment".equals(getIntent().getStringExtra("tag"))) {
            WorkerListFragment testFragment = new WorkerListFragment();
            transacction.replace(R.id.fragment, testFragment);
            testFragment.setDepartmentId(id);
        } else if ("PersonnalDetailFragment".equals(getIntent().getStringExtra("tag"))) {
            //   自己基本信息
            PersonnalDetailFragment testFragment = new PersonnalDetailFragment();
            transacction.replace(R.id.fragment, testFragment);
        } else if ("WorkerFragment".equals(getIntent().getStringExtra("tag"))) {
            //   他人基本信息
            WorkerFragment workerFragment = new WorkerFragment();
            transacction.replace(R.id.fragment, workerFragment);
            workerFragment.setUserName(id);
        } else if ("WebviewFragment".equals(getIntent().getStringExtra("tag"))) {
            WebviewFragment webviewFragment = new WebviewFragment();
            webviewFragment.setContentUrl(id);
            transacction.replace(R.id.fragment, webviewFragment);
        }else if ("NoticeListFragment".equals(getIntent().getStringExtra("tag"))) {
            NoticeListFragment webviewFragment = new NoticeListFragment();
            transacction.replace(R.id.fragment, webviewFragment);
        }else if ("PersonnalEditFragment".equals(getIntent().getStringExtra("tag"))) {
            PersonnalEditFragment webviewFragment = new PersonnalEditFragment();
            webviewFragment.setTag(id);
            transacction.replace(R.id.fragment, webviewFragment);
            setRightTv(true, "保存", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EventCenter("updateinfo",""));
                }
            });
        }
        setTopbarName(title);
        transacction.commit();
    }

    public static void startActivityWorker(Activity activity, String tag,String title,String id) {
        Intent intent = new Intent(activity, ActivityWorker.class);
        intent.putExtra("tag", tag);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

}



