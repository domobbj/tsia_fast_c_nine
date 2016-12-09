package langteng.com.redpocketmoney.ui.circle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import langteng.com.baselib.baseui.LibBaseActivity;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.login.LoginFragment;

/***
 ***/

public class ActivityCircle extends LibBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseSetContentView(R.layout.activity_worker);
        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction transacction = fragment.beginTransaction();
        if ("WorkerListFragment".equals(getIntent().getStringExtra("tag"))) {
            LoginFragment testFragment = new LoginFragment();
            transacction.replace(R.id.fragment, testFragment);
        } else if ("PublishCircleFragment".equals(getIntent().getStringExtra("tag"))) {
            PublishCircleFragment testFragment = new PublishCircleFragment();
            transacction.replace(R.id.fragment, testFragment);
            setTopbarName("圈子");
        }
        transacction.commit();
    }

    public static void startActivityWorker(Activity activity, String tag) {
        Intent intent = new Intent(activity, ActivityCircle.class);
        intent.putExtra("tag", tag);
        activity.startActivity(intent);
    }

}



