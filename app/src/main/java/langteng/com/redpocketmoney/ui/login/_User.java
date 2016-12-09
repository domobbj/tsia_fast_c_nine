package langteng.com.redpocketmoney.ui.login;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 */
public class _User extends BmobUser {


    public String userNickName;

    public String userIcon;

    public String userWork;

    public String userPos;

    public String userId;

    public String departmentId;

    public String userTag;

    public boolean isSelect = false;

    public BmobFile iconFile;

    /***
     * 用户角色
     **/
    public int userRole;
}
