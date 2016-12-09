package langteng.com.redpocketmoney.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import langteng.com.baselib.baseui.LibBaseActivity;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.baselib.utils.Tools;
import langteng.com.redpocketmoney.R;

/**
 * Created by JHS on 2015/8/25.
 */
public class RegisterActivity extends LibBaseActivity implements View.OnClickListener {


    private TextView phoneVerificationTv;

    private TextView userAgreementTv;
    private TextView registTv;
    private TextView registHintTv;
    private EditText mobleEdt;
    private TextView verificationEdt;

    private TextView passwordEdt;

    private TextView passwordConfirmEdt;

    private TimeCount mTimeCount;

    private boolean timeIsStart = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
        BaseSetContentView(R.layout.register_activity);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        setTopbarName(R.string.registe);
        phoneVerificationTv = (TextView) findViewById(R.id.regist_phone_verification_tv);
        userAgreementTv = (TextView) findViewById(R.id.user_agreement_tv);
        registTv = (TextView) findViewById(R.id.regist_btn);

        registHintTv = (TextView) findViewById(R.id.user_agreement_tv1);

        mobleEdt = (EditText) findViewById(R.id.moble_edittext);
        verificationEdt = (EditText) findViewById(R.id.regist_phone_verification_et);
        passwordEdt = (EditText) findViewById(R.id.password_et);
        passwordConfirmEdt = (EditText) findViewById(R.id.password_et_confirm);
        phoneVerificationTv.setOnClickListener(this);
        userAgreementTv.setOnClickListener(this);
        registTv.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regist_phone_verification_tv:  //   获取验证码
                String mobleNum = mobleEdt.getText().toString();
                if (Tools.isMobileNO(mobleNum)) {
                } else {
                    showToast("手机号码错误");
                    return;
                }
                break;
            case R.id.user_agreement_tv:  //   用户协议
                break;
            case R.id.regist_btn:  //   注册
                String mobleNum1 = mobleEdt.getText().toString();
                String verificationStr = verificationEdt.getText().toString();
                String passwordStr = passwordEdt.getText().toString();
                String passwordConfirmStr = passwordConfirmEdt.getText().toString();
                if (!Tools.isMobileNO(mobleNum1)) {
                    showToast("手机号码错误");
                    return;
                } else if (StringUtil.isEmpty(verificationStr) || verificationStr.length() != 6) {
                    showToast("请输入验证码");
                    return;
                } else if (StringUtil.isEmpty(passwordStr)) {
                    showToast("密码不一致");
                    return;
                } else if (StringUtil.isEmpty(passwordConfirmStr)) {
                    showToast("请确认密码");
                    return;
                } else if (!passwordStr.equals(passwordConfirmStr)) {
                    showToast("密码不一致");
                    return;
                } else {
                    if (passwordStr.length() >= 6 && passwordStr.length() <= 16) {
                        Tools.hideSoftInputFromWindow(this, passwordConfirmEdt);
                    } else {
                        showToast("密码长度必须在[6, 16]之间!");
                    }
                }
                break;
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            timeIsStart = false;
            phoneVerificationTv.setText("重新获取");
            phoneVerificationTv.setClickable(true);
            phoneVerificationTv.setBackgroundResource(R.drawable.bn_login);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            phoneVerificationTv.setText(millisUntilFinished / 1000 + "秒");
        }
    }

}
