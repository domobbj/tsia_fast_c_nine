package langteng.com.baselib.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by lenovo on 2016/8/12.
 */
public class ShareUtil {
    public static  final String share2WechatImgName =  "share2wechat.png";

    public static  final String shareImgPath = Environment.getExternalStorageDirectory()
            + File.separator + "yiqizhuan/";


    public static void shareText(Context context, String content) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        shareIntent.setPackage("com.tencent.mm");
        Intent chooserIntent = Intent.createChooser(shareIntent, "分享到");
        try {
            context.startActivity(chooserIntent);
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }


    /***
     * 分享到朋友圈
     ***/
    public static void shareToWechatZone(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        File file = new File(shareImgPath+share2WechatImgName);
        if (file != null && file.exists() && file.isFile()) {
            intent.setType("image/*");
            Uri u = Uri.fromFile(file);
            intent.putExtra(Intent.EXTRA_STREAM, u);
            Logger.i("-------file--", "file");
        }
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.SEND");
        intent.putExtra(Intent.EXTRA_TEXT, "固定字段");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        context.startActivity(intent);
    }

    public static void shareImgToWechat(Context context) {
        Intent intent = new Intent();
        File file = new File(shareImgPath+share2WechatImgName);
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        context.startActivity(intent);
    }

}
