package langteng.com.redpocketmoney.ui.message;

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

public class ActivityMessage extends LibBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction transacction = fragment.beginTransaction();
        if ("WorkerListFragment".equals(getIntent().getStringExtra("tag"))) {
            LoginFragment testFragment = new LoginFragment();
            transacction.replace(R.id.fragment, testFragment);
        } else if ("PersonnalDetailFragment".equals(getIntent().getStringExtra("tag"))) {
            LoginFragment testFragment = new LoginFragment();
            transacction.replace(R.id.fragment, testFragment);
        }
        transacction.commit();
    }

    public static void startActivityWorker(Activity activity, String tag) {
        Intent intent = new Intent(activity, ActivityMessage.class);
        intent.putExtra("tag", tag);
        activity.startActivity(intent);
    }

}



