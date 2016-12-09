package langteng.com.redpocketmoney.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import langteng.com.redpocketmoney.R;

/**
 */
public class NoticeListFragment extends Fragment {

    private List<MessageListModel> modelList = new ArrayList<>();

    private ListView msgLv;
    private MessageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_message, null);
        initView(view1);
        return view1;
    }

    private void initView(View view) {
        msgLv =(ListView)view.findViewById(R.id.msg_lv) ;
        adapter = new MessageAdapter(getActivity(),modelList);
        msgLv.setAdapter(adapter);

        MessageListModel messageListModel = new MessageListModel();
        messageListModel.title = "通知";
        messageListModel.hint = "未读通知";
        messageListModel.iconUrl = R.mipmap.ic_launcher+"";
        modelList.add(messageListModel);

        MessageListModel messageListModel1 = new MessageListModel();
        messageListModel1.title = "小助理";
        messageListModel1.hint = "您被分配了一个新任务";
        messageListModel1.iconUrl = R.mipmap.ic_launcher+"";
        modelList.add(messageListModel1);
        adapter.notifyDataSetChanged();

        msgLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }

}
