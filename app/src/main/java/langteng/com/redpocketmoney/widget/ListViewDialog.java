package langteng.com.redpocketmoney.widget;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.eventbus.EventCenter;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.ui.task.adapter.MemberListAdapter;

/**
 */
public class ListViewDialog extends DialogFragment {

    private ListView userListView;

    private MemberListAdapter adapter;
    private List<_User> memberList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.listview_dialog, container, false);
        initViewTest(v);
        return v;
    }

    private void initViewTest(View view) {
        userListView = (ListView) view.findViewById(R.id.user_lv);
        adapter = new MemberListAdapter(getActivity(), memberList);
        userListView.setAdapter(adapter);
        Logger.i("-----memberList---","memberList: " + memberList.size());
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (memberList.get(position).isSelect) {
                    memberList.get(position).isSelect = false;
                } else {
                    memberList.get(position).isSelect = true;
                }
                adapter.notifyDataSetChanged();
            }
        });
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<_User> memberList1 = new ArrayList<_User>();
                for (int i = 0; i < memberList.size(); i++) {
                    if (memberList.get(i).isSelect){
                        memberList1.add(memberList.get(i));
                    }
                }
                EventBus.getDefault().post(new EventCenter("memberList",memberList1));
                dismiss();
            }
        });
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

    public void setMemberList(List<_User> memberList1) {
        memberList.addAll(memberList1);
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }


}
