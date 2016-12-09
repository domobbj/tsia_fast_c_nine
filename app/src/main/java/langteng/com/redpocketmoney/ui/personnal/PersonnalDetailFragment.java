package langteng.com.redpocketmoney.ui.personnal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import langteng.com.baselib.GlobalApplication;
import langteng.com.baselib.utils.Logger;
import langteng.com.redpocketmoney.ProgrammingApplication;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.eventbus.EventCenter;
import langteng.com.redpocketmoney.ui.login._User;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * 自己个人信息
 */
public class PersonnalDetailFragment extends Fragment {


    private ImageView userIcon;
    private TextView userName;
    private TextView userWork;
    private TextView userMob;
    private TextView userEmail;
    private TextView posTV;

    private TextView userTag;

    private RelativeLayout userPos;


    private void initView(View view) {
        userIcon = (ImageView) view.findViewById(R.id.user_icon);
        userName = (TextView) view.findViewById(R.id.user_name);
        userWork = (TextView) view.findViewById(R.id.user_work);
        userMob = (TextView) view.findViewById(R.id.user_mobile);
        userEmail = (TextView) view.findViewById(R.id.user_email);
        userTag = (TextView) view.findViewById(R.id.user_tag);
        posTV = (TextView) view.findViewById(R.id.user_pos_tv);

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        userMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityWorker.startActivityWorker(getActivity(), "PersonnalEditFragment",
                        "修改手机号",
                        "moblie");
            }
        });



        userTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityWorker.startActivityWorker(getActivity(), "PersonnalEditFragment",
                        "个性标签",
                        "userTag");
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_personnal, null);
        initView(view1);
        return view1;
    }


    @Override
    public void onResume() {
        setDate();
        super.onResume();
    }

    private final int REQUEST_CODE = 1001;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                final String[] filePaths = new String[1];
                filePaths[0] = photos.get(0);
                final _User user = ProgrammingApplication.get().getUser();
                user.iconFile.uploadBatch(getActivity(),filePaths, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> files, List<String> urls) {
                        //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                        //2、urls-上传文件的完整url地址
                        if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                            //do something
                            user.userIcon = urls.get(0);
                            user.update(getActivity(), user.getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Glide.with(GlobalApplication.getInstance())
                                            .load(user.userIcon)
                                            .asBitmap()
                                            .placeholder(R.mipmap.defalut_icon)
                                            .into(userIcon);
                                    EventBus.getDefault().post(new EventCenter("updateIcon",""));
                                }

                                @Override
                                public void onFailure(int i, String s) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int statuscode, String errormsg) {
                    }

                    @Override
                    public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {

                    }
                });
                for (int i = 0; i < photos.size(); i++) {
                    Logger.i("------photos--", "photos: " +
                            photos.get(i));

                }
            }
        }
    }

    private void setDate() {
        _User user = ProgrammingApplication.get().getUser();
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
        userMob.setText("联系电话： " + user.getMobilePhoneNumber() + "");
        userEmail.setText("邮箱： " + user.getEmail());
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < user.userTag.split(",").length; i++) {
            if (i!=0) {
                buffer.append(user.userTag.split(",")[i] + "  ");
            }else {
                buffer.append(user.userTag.split(",")[i] + "  ");
            }
        }
        userTag.setText("专属标签： " + user.userTag);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(EventCenter message) {
        if ("updateIofo".equals(message.opreatId)) {
            setDate();
        }
    }

}
