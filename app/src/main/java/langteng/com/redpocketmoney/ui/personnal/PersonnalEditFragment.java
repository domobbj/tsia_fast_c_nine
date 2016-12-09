package langteng.com.redpocketmoney.ui.personnal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.bmob.v3.listener.UpdateListener;
import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.baselib.utils.Logger;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.baselib.utils.Tools;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.eventbus.EventCenter;
import langteng.com.redpocketmoney.ui.login._User;

/**
 * 同事个人信息
 */
public class PersonnalEditFragment extends LibBaseFragment {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;

    private void initView(View view) {
        editText1 = (EditText) view.findViewById(R.id.tag_1);
        editText2 = (EditText) view.findViewById(R.id.tag_2);
        editText3 = (EditText) view.findViewById(R.id.tag_3);
        editText4 = (EditText) view.findViewById(R.id.tag_4);
        editText5 = (EditText) view.findViewById(R.id.tag_5);
        if (!"userTag".equals(username)) {
            editText2.setVisibility(View.GONE);
            editText3.setVisibility(View.GONE);
            editText4.setVisibility(View.GONE);
            editText5.setVisibility(View.GONE);
            DigitsKeyListener numericOnlyListener = new DigitsKeyListener(false, true);
            editText1.setKeyListener(numericOnlyListener);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_personnal_edit, null);
        initView(view1);
        return view1;
    }

    private String username;

    public void setTag(String username) {
        this.username = username;
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(EventCenter message) {
        if ("updateinfo".equals(message.opreatId)) {
            String tag1 = editText1.getText().toString();
            if ("userTag".equals(username)) {
                StringBuffer buffer = new StringBuffer();
                String tag2 = editText2.getText().toString();
                String tag3 = editText3.getText().toString();
                String tag4 = editText4.getText().toString();
                String tag5 = editText5.getText().toString();
                if (!StringUtil.isEmpty(tag1)) {
                    buffer.append("," + tag1);
                }
                if (!StringUtil.isEmpty(tag2)) {
                    buffer.append("," + tag2);
                }
                if (!StringUtil.isEmpty(tag3)) {
                    buffer.append("," + tag3);
                }
                if (!StringUtil.isEmpty(tag4)) {
                    buffer.append("," + tag4);
                }
                if (!StringUtil.isEmpty(tag5)) {
                    buffer.append("," + tag5);
                }
                _User user = _User.getCurrentUser(getActivity(), _User.class);
                user.userTag = buffer.toString();
                user.update(getActivity(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        showToast("修改成功");
                        ActivityWorker.startActivityWorker(getActivity(), "PersonnalDetailFragment", "我", "");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Logger.i("--------onFailure---", "s: " + s);
                    }
                });
            } else {
                _User user = _User.getCurrentUser(getActivity(), _User.class);
                user.setMobilePhoneNumber(tag1);
                if (!Tools.isMobileNO(tag1)) {
                    showToast("请输入正确的手机号码");
                }
                user.update(getActivity(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        showToast("修改成功");
                        ActivityWorker.startActivityWorker(getActivity(), "PersonnalDetailFragment", "我", "");
                    }
                    @Override
                    public void onFailure(int i, String s) {
                        Logger.i("--------onFailure---", "s: " + s);
                        if (!StringUtil.isEmpty(s) && s.contains("already taken"))
                            showToast("该手机号已绑定");
                    }
                });
            }
        }
    }

}
