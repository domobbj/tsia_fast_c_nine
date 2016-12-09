package langteng.com.redpocketmoney.ui.task;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import langteng.com.redpocketmoney.ui.login._User;

/**
 */
public class PublishTaskModel extends BmobObject {


    public String title;

    public String content;

    public String iconurl;

    public String endTime;

    /***
     * 完成状态
     **/
    public String status;

    public BmobFile bmobFile;

    public List<String> imgList = new ArrayList<>();


    public int type;


    public List<_User> user = new ArrayList<>();


    public List<String> userNickName = new ArrayList<>();

    public List<String> username = new ArrayList<>();


}
