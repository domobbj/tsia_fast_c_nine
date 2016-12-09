package langteng.com.redpocketmoney.ui.task;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import langteng.com.redpocketmoney.ui.login._User;

/**
 */
public class TaskModel extends BmobObject {


    public String title;

    public String content;

    public int status;

    public String iconurl;

    public String moreDes;

    public String endTime;

    public String publisherName;

    public String publisherId;

    public BmobFile bmobFile;

    public List<String> imgList = new ArrayList<>();

    public int type;


    public List<_User> user = new ArrayList<>();
    /****
     * 发布对象者们
     ***/
    public List<Worker> workerList = new ArrayList<>();

    public static class Worker {

        public String userNickNames;

        public String username;

        public String userIcon;

        /***
         * 0  未开始  1 已开始   2 已完成
         ***/
        public int status;

    }


}
