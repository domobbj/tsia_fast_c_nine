package langteng.com.redpocketmoney.ui.task.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import langteng.com.baselib.baseui.LibBaseFragment;
import langteng.com.baselib.utils.Logger;
import langteng.com.baselib.utils.StringUtil;
import langteng.com.baselib.utils.TimeUtils;
import langteng.com.redpocketmoney.ProgrammingApplication;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.eventbus.EventCenter;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.ui.task.adapter.MemberSelectAdapter;
import langteng.com.redpocketmoney.ui.task.TaskModel;
import langteng.com.redpocketmoney.widget.ListViewDialog;
import langteng.com.redpocketmoney.widget.NoScrollGridView;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * 发表任务
 */
public class PublishTaskFragment extends LibBaseFragment implements View.OnClickListener {
    private EditText nameEdt;
    private EditText contentEdt;
    private TextView endTimeTv;
    private NoScrollGridView membersGv;
    private MemberSelectAdapter adapter;
    private List<_User> selectMembers = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getMembers();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_task_publish, null);
        initView(view1);
        return view1;
    }

    private void initView(View view) {
        nameEdt = (EditText) view.findViewById(R.id.task_edt_name);
        contentEdt = (EditText) view.findViewById(R.id.task_edt_content);
        endTimeTv = (TextView) view.findViewById(R.id.publish_time_tv);
        membersGv = (NoScrollGridView) view.findViewById(R.id.user_gv);

        adapter = new MemberSelectAdapter(getActivity(), selectMembers);
        membersGv.setAdapter(adapter);
        view.findViewById(R.id.add_img).setOnClickListener(this);
        view.findViewById(R.id.member_icon).setOnClickListener(this);
        view.findViewById(R.id.publish_img).setOnClickListener(this);
        endTimeTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_img:
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(9);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.member_icon: //   增加任务成员
                showMembers();
                break;
            case R.id.publish_img:
                publishCircle();
                break;
            case R.id.publish_time_tv:
                showTimeDialog();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(EventCenter message) {
        if ("memberList".equals(message.opreatId)) {
            selectMembers.clear();
            selectMembers.addAll((List<_User>) message.value);
            adapter.notifyDataSetChanged();
        }
    }

    private void showMembers() {
        final ListViewDialog oneButtonDialog = new ListViewDialog();
        oneButtonDialog.setMemberList(list);
        oneButtonDialog.show(getFragmentManager(), "ListViewDialog");
    }

    /**
     * 显示时间选择器
     */
    private void showTimeDialog() {
        TimeUtils.showTimeDialog(getActivity(), onDateSetListener, System
                .currentTimeMillis()+  Long.parseLong("5184000000"), System
                .currentTimeMillis());
    }

    String endTime;
    /**
     * 时间选择监听器
     */
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            monthOfYear = (monthOfYear + 1);
            endTime = year + "-" + monthOfYear + "-" + dayOfMonth;
            Logger.i("-----endTime--", "endTime :  " + endTime);
            endTimeTv.setText("截至时间：" + endTime);
        }
    };


    private void publishCircle() {
        final TaskModel taskModel = new TaskModel();
        String content = contentEdt.getText().toString();
        String title = nameEdt.getText().toString();

        if (StringUtil.isEmpty(content)) {
            showToast("请输入要发布的内容");
            return;
        }
        if (StringUtil.isEmpty(title)) {
            showToast("请输入标题");
            return;
        }
        taskModel.content = content;
        taskModel.title = title;

        _User user = ProgrammingApplication.get().getUser();
        taskModel.publisherName = user.userNickName;
        taskModel.publisherId = user.userId;
        taskModel.endTime = endTime;
        for (int i = 0; i < selectMembers.size(); i++) {
            TaskModel.Worker worker = new TaskModel.Worker();
            worker.username = selectMembers.get(i).getUsername();
            worker.userNickNames = selectMembers.get(i).userNickName;
            worker.userIcon = selectMembers.get(i).userIcon;
            taskModel.workerList.add(worker);
        }
        taskModel.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                showToast("发布成功");
            }
            @Override
            public void onFailure(int i, String s) {

            }
        });

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


    private List<_User> list = new ArrayList<>();

    private void getMembers() {
        BmobQuery<_User> query = new BmobQuery<_User>();
        query.setLimit(50);
        query.findObjects(getActivity(), new FindListener<_User>() {
            @Override
            public void onSuccess(List<_User> list1) {
                Logger.i("---CircleModel--query---", "list: " + list.size());
                list.addAll(list1);
            }

            @Override
            public void onError(int i, String s) {
                Logger.i("-----CircleModel---", "s: " + s);
            }
        });

    }
}
