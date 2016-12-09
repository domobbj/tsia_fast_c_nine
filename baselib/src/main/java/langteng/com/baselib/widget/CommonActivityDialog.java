package langteng.com.baselib.widget;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import langteng.com.baselib.R;


/**
 * 有两个按钮的弹窗
 */
public class CommonActivityDialog extends FragmentActivity {


    private TextView titleTv;
    private TextView contentTv;
    private TextView cancleTv;

    private TextView deleteTv;

    private String titleStr;
    private String contentStr;
    private String leftStr;
    private String rightStr;
    private View.OnClickListener leftClickListener;
    private View.OnClickListener rightClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_twobutton);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView();
    }

    private void initView() {
        cancleTv = (TextView) findViewById(R.id.cancle);
        contentTv = (TextView) findViewById(R.id.contenthint);
        deleteTv = (TextView) findViewById(R.id.confirm);
        titleTv = (TextView) findViewById(R.id.titletv);

        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri url = Uri.parse("http://p.gdown.baidu.com/a5bf75d09e51ed4b674313a8415c7cee5110cbf7e6046716c1b4061d87ded682205e1f363ced16a5cea6185bc5744368eb84a6004cd99773dc22936e7f7495bd7f02fff5f46879ad07889d4fefed0bed00a8dd6c36e40bc46209709ff6155e1c56ed6050c81595b67885eedb66166903af0a4f191b7326338b47aa85f4b7861b1538926fee90cd00a4e8c16028f782efb1fa28749d761b837dafe34eee0873f9ea9277396f0d28e9b7cab62327693773d7d220a1f847a41d05ef0fa0ef5aa4e716039698cc34f53fd60a21850d535a61");
                intent.setData(url);
                startActivity(intent);
                finish();
            }
        });

        cancleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
