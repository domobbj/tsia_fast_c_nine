package langteng.com.baselib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import langteng.com.baselib.R;


/**
 * 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要
 * <p/>
 * 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 */
public class LoadingDialog extends Dialog {
    private LoadingDialog(Context context) {
        super(context);
    }

    ImageView imageView;

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public static LoadingDialog createDialog(Context context) {
        LoadingDialog customProgressDialog = new LoadingDialog(context, R.style.dialog);
        customProgressDialog.setContentView(R.layout.dialog_loding);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return customProgressDialog;
    }


    @Override
    public void show() {
        super.show();
        imageView = (ImageView) this.findViewById(R.id.loadimg);
        Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.img_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (operatingAnim != null) {
            imageView.startAnimation(operatingAnim);
        }

    }

    @Override
    public void dismiss() {
        ImageView imageView = (ImageView) this.findViewById(R.id.loadimg);
        imageView.clearAnimation();
        super.dismiss();
    }

}
