package langteng.com.redpocketmoney.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

import cn.bmob.v3.listener.SaveListener;
import langteng.com.baselib.baseui.LibBaseActivity;
import langteng.com.baselib.utils.ActivityJump;
import langteng.com.baselib.utils.SPUtil;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.baselib.utils.Tools;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.main.MainActivity;

/***
 * 登陆
 ***/

public class ActivityLogin extends LibBaseActivity implements View.OnClickListener {


    private EditText accountEdt;
    private EditText passwordEdt;
    private TextView forgetPasswordTv;
    private ImageButton loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
    }


    private void initView() {
        accountEdt = (EditText) findViewById(R.id.ev_account);
        passwordEdt = (EditText) findViewById(R.id.ev_password);

        forgetPasswordTv = (TextView) findViewById(R.id.tv_forget_password);
        loginBtn = (ImageButton) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(this);
        forgetPasswordTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                final String name = accountEdt.getText().toString();
                String pw = passwordEdt.getText().toString();
                if (StringUtil.isEmpty(name)) {
                    showToast("请输入账号");
                    return;
                }
                if (StringUtil.isEmpty(pw)) {
                    showToast("请输入密码");
                    return;
                }
                showDialog("");
                _User userModel = new _User();
                userModel.setUsername(name);
                userModel.setPassword(pw);
                userModel.login(ActivityLogin.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        hiddenDialog();
                            showToast("登陆成功");
                            SPUtil.setString1("userid", name);
                            ActivityJump.getInsanceJump(ActivityLogin.this, MainActivity.class, null, true);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        hiddenDialog();
                    }
                });
                Tools.hideSoftInputFromWindow(this, passwordEdt);
                break;
            case R.id.tv_forget_password: // 找回密码
                HashMap<String, String> map = new HashMap<String, String>();
                if (Tools.isMobileNO(accountEdt.getText().toString())) {
                    map.put("mobleNum", accountEdt.getText().toString());
                }
                break;
        }
    }


}



