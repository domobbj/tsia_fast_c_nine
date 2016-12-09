package langteng.com.redpocketmoney.ui.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.baselib.utils.Logger;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.redpocketmoney.ProgrammingApplication;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.widget.Tag;
import langteng.com.redpocketmoney.widget.TagListView;
import langteng.com.redpocketmoney.widget.TagView;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * 发表圈子内容
 */
public class PublishCircleFragment extends LibBaseFragment implements View.OnClickListener {


    private EditText publishEdt;

    private Button uploadImg;
    private TagListView tagListView;
    private String circleTag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_circle_publish, null);
        initView(view1);
        return view1;
    }

    private void initView(View view) {
        publishEdt = (EditText) view.findViewById(R.id.circle_publish_edt);
        view.findViewById(R.id.add_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(9);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        tagListView = (TagListView) view.findViewById(R.id.circle_tagview);
        view.findViewById(R.id.circle_publish_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishCircle();
            }
        });
        getDate();
    }


    private void publishCircle() {
        final CircleModel circleModel = new CircleModel();
        String content = publishEdt.getText().toString();
        if (StringUtil.isEmpty(content)) {
            showToast("请输入要发布的内容");
            return;
        }
        circleModel.content = content;
        circleModel.tag = circleTag;
        _User user = ProgrammingApplication.get().getUser();
        circleModel.userNickName = user.userNickName;
        circleModel.userIcon = user.userIcon;
        circleModel.username = user.getUsername();
        if (filePaths != null && filePaths.length > 0) {
            BmobFile.uploadBatch(getActivity(), filePaths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> files, List<String> urls) {
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的完整url地址
                    if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                        //do something
                        circleModel.contentImgs.addAll(urls);
                        circleModel.save(getActivity(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                showToast("发布成功");
                                getActivity().finish();
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
                    //1、curIndex--表示当前第几个文件正在上传
                    //2、curPercent--表示当前上传文件的进度值（百分比）
                    //3、total--表示总的上传文件数
                    //4、totalPercent--表示总的上传进度（百分比）


                }
            });
        }


    }


    @Override
    public void onClick(View view) {

    }


    private final int REQUEST_CODE = 1001;
    String[] filePaths;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                filePaths = new String[photos.size()];
                for (int i = 0; i < filePaths.length; i++) {
                    filePaths[i] = photos.get(i);
                }
                for (int i = 0; i < photos.size(); i++) {
                    Logger.i("------photos--", "photos: " +
                            photos.get(i));

                }
            }
        }
    }


    private void getDate() {

        final List<Tag> mTags = new ArrayList<Tag>();
        BmobQuery<CircleTag> query = new BmobQuery<CircleTag>();
        query.setLimit(50);
        query.findObjects(getActivity(), new FindListener<CircleTag>() {
            @Override
            public void onSuccess(List<CircleTag> list) {
                Logger.i("-----CircleTag---", "list: " + list.size());
                for (int i = 0; i < list.size(); i++) {
                    Tag tag = new Tag();
                    tag.setId(i);
                    tag.setChecked(true);
                    tag.setTitle(list.get(i).circleTag);
                    mTags.add(tag);
                }
                tagListView.setTags(mTags);
            }

            @Override
            public void onError(int i, String s) {
                Logger.i("-----CircleTag---", "s: " + s);
            }
        });

        tagListView.setOnTagClickListener(new TagListView.OnTagClickListener() {
            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                circleTag = tag.getTitle();
                showToast(circleTag + "   0");
            }
        });
    }
}
