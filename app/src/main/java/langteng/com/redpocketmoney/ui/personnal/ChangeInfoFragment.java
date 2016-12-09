package langteng.com.redpocketmoney.ui.personnal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.listener.UpdateListener;
import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.redpocketmoney.ProgrammingApplication;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.eventbus.EventCenter;
import langteng.com.redpocketmoney.ui.login._User;

/**
 * 同事个人信息
 */
public class ChangeInfoFragment extends LibBaseFragment {

    private EditText editText;


    private void initView(View view) {
        editText = (EditText) view.findViewById(R.id.edit_personnal_info);

        view.findViewById(R.id.edit_personnal_info_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _User user = ProgrammingApplication.get().getUser();
                if (user == null)
                    return;
                String content = editText.getText().toString();
                if ("".equals("")) { //  修改手机号
                    user.setMobilePhoneNumber(content);
                } else if ("".equals("")) { //  修改标签
                    user.setMobilePhoneNumber(content);
                } else if ("".equals("")) {
                    user.setMobilePhoneNumber(content);
                }
                user.update(getActivity(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        showToast("修改成功");
                        EventBus.getDefault().post(new EventCenter("updateIofo", ""));
                        ActivityWorker.startActivityWorker(getActivity(), "PersonnalDetailFragment", "我", "");
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_changeinfo, null);
        initView(view1);
        return view1;
    }


}
