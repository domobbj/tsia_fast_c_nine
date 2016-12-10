package langteng.com.redpocketmoney.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.v3.exception.BmobException;
import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.utils.Logger;
import langteng.com.baselib.utils.TimeUtils;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.eventbus.EventCenter;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.ui.personnal.ActivityWorker;
import langteng.com.redpocketmoney.widget.CircleImageView;
import langteng.com.redpocketmoney.widget.ClearEditText1;

/**
 */
public class MessageListFragment extends Fragment {

    private List<MessageListModel> modelList = new ArrayList<>();

    private ListView msgLv;
    private MessageAdapter adapter;

    private TextView userName;
    private CircleImageView userIcon;

    private ClearEditText1 messageEdt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        initView(view);
        getConversations();
        return view;
    }

    private void initView(View view) {
        msgLv = (ListView) view.findViewById(R.id.msg_lv);
        adapter = new MessageAdapter(getActivity(), modelList);
        msgLv.setAdapter(adapter);
        userName = (TextView) view.findViewById(R.id.user_name);
        userIcon = (CircleImageView) view.findViewById(R.id.user_icon);
        adapter.notifyDataSetChanged();

        msgLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BmobIMUserInfo info = new BmobIMUserInfo();
                info.setName("1111");
                info.setUserId("1111");
                BmobIM.getInstance().startPrivateConversation(info,
                        new ConversationListener() {
                            @Override
                            public void done(BmobIMConversation c, BmobException e) {
                                if (e == null) {
                                    //在此跳转到聊天页面
                                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                                    Logger.i("----c--", " " + c.getId() + "  getConversationIcon: " + c.getConversationIcon());
                                    intent.putExtra("c", c);
                                    startActivity(intent);
                                } else {
                                    Logger.i("------startPrivateConversation--", "---e--" +
                                            e.getMessage() + " " + e.getErrorCode());
                                }
                            }
                        });
            }
        });

        _User user = _User.getCurrentUser(getActivity(), _User.class);

        Glide.with(GlobalApplication.getInstance())
                .load(user.userIcon)
                .asBitmap()
                .placeholder(R.mipmap.defalut_icon)
                .into(userIcon);
        userName.setText(user.userNickName);
        view.findViewById(R.id.head_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityWorker.startActivityWorker(getActivity(), "PersonnalDetailFragment", "我", "");
            }
        });
//        messageEdt = (ClearEditText1) view.findViewById(R.id.message_edt);

    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(EventCenter message) {
        if ("updateIcon".equals(message.opreatId)) {
            _User user = _User.getCurrentUser(getActivity(), _User.class);
            Logger.i("-----user--", "'user:" + user.userIcon);
            Glide.with(GlobalApplication.getInstance())
                    .load(user.userIcon)
                    .asBitmap()
                    .placeholder(R.mipmap.defalut_icon)
                    .into(userIcon);
        } else if ("BmobIMConnect".equals(message.opreatId)) {
            getConversations();
        }
    }


    @Override
    public void onResume() {
        getConversations();
        super.onResume();
    }

    /**
     * 获取会话列表的数据：增加新朋友会话
     *
     * @return
     */
    private void getConversations() {
        //添加会话
        modelList.clear();
        initPartDate();
        List<BmobIMConversation> list = BmobIM.getInstance().loadAllConversation();
        if (list != null && list.size() > 0) {
            MessageListModel messageListModel;
            for (int i = 0; i < list.size(); i++) {
                List<BmobIMMessage> messageList = list.get(i).getMessages();
                messageListModel = new MessageListModel();
                messageListModel.iconUrl = list.get(i).getConversationIcon();
                messageListModel.title = list.get(i).getConversationTitle();
                messageListModel.publishTime = TimeUtils.formatMsgMainDateTime(
                        list.get(i).getUpdateTime());
                messageListModel.object = list.get(i).client;
                if (messageList.size() > 0) {
                    messageListModel.hint = messageList.get(messageList.size() - 1).getContent();
                }
                for (int j = 0; j < messageList.size(); j++) {
                    Logger.i("-----messageList--", i + "  messageList: " + j + "  " +
                            "   "
                            + messageList.get(j).getContent()
                    );
                }
                modelList.add(messageListModel);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void initPartDate() {
        MessageListModel messageListModel = new MessageListModel();
        messageListModel.title = "通知";
        messageListModel.hint = "未读通知";
        messageListModel.object = "messageListModel";
        messageListModel.iconUrl = R.mipmap.ic_launcher + "";
        modelList.add(messageListModel);

        MessageListModel messageListModel1 = new MessageListModel();
        messageListModel1.title = "小助理";
        messageListModel1.hint = "您被分配了一个新任务";
        messageListModel1.object = "messageListModel";
        messageListModel1.iconUrl = R.mipmap.ic_launcher + "";
        modelList.add(messageListModel1);
    }


}
