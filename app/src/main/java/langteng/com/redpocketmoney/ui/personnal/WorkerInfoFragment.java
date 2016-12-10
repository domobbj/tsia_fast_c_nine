package langteng.com.redpocketmoney.ui.personnal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.ui.message.ChatActivity;
import langteng.com.redpocketmoney.widget.CircleImageView;

/**
 * 同事个人信息
 */
public class WorkerInfoFragment extends LibBaseFragment {

    private _User user;

    private CircleImageView userIcon;
    private TextView userName;
    private TextView userWork;
    private ImageView userMob;
    private TextView userEmail;

    private ImageView sendMsg;

    private TextView userPos;


    private void initView(View view) {
        userIcon = (CircleImageView) view.findViewById(R.id.user_icon);
        userName = (TextView) view.findViewById(R.id.user_name);
        userWork = (TextView) view.findViewById(R.id.user_work);
        userMob = (ImageView) view.findViewById(R.id.user_mobile);
        userEmail = (TextView) view.findViewById(R.id.worker_email_tv);
        sendMsg = (ImageView) view.findViewById(R.id.send_msg);

        userPos = (TextView) view.findViewById(R.id.worker_pos_tv);
        userPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityWorker.startActivityWorker(getActivity(), "WebviewFragment"
                        , "位置", "http://10.0.0.206:29097/tsia_fast_c_nine/h5service/index.php/map/index");
            }
        });


        userMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = user.getMobilePhoneNumber();
                if (StringUtil.isEmpty(number)) {
                    showToast("没有获得电话号码");
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobIMUserInfo info = new BmobIMUserInfo();
                Map<String, Object> map = new HashMap<>();
                if (user == null) return;
                info.setName(user.userNickName);
                info.setUserId(user.getObjectId());
                info.setAvatar(user.userIcon);
                BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, false, null);
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("c", c);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_worker, null);
        initView(view1);
        getDepartmentModelDate(username);
        return view1;
    }

    private String username;

    public void setUserName(String username) {
        this.username = username;
    }


    private void getDepartmentModelDate(String username) {
        BmobQuery<_User> query = new BmobQuery<_User>();
        query.addWhereEqualTo("username", username);
        query.setLimit(10);
        query.findObjects(getActivity(), new FindListener<_User>() {
            @Override
            public void onSuccess(List<_User> list) {
                if (list.size() > 0) {
                    user = list.get(0);
                    setDate(list.get(0));
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void setDate(_User user) {
        if (user == null) {
            return;
        }
        Glide.with(GlobalApplication.getInstance())
                .load(user.userIcon)
                .asBitmap()
                .placeholder(R.mipmap.defalut_icon)
                .into(userIcon);
        userName.setText(user.userNickName);
        userWork.setText(user.userWork);
//        userMob.setText("电话： " + user.getMobilePhoneNumber() + "");
        if (!StringUtil.isEmpty(user.getEmail())) {
            userEmail.setText("邮箱： " + user.getEmail());
        } else {
            userEmail.setText("邮箱：mail@domob.cn ");
        }
    }

}
