package langteng.com.redpocketmoney.ui.message.adapter.bean;

import cn.bmob.v3.BmobObject;
import langteng.com.redpocketmoney.ui.login._User;

/**好友表
 * @author smile
 * @project Friend
 * @date 2016-04-26
 */
public class Friend extends BmobObject {

    private _User user;
    private _User friendUser;

    //拼音
    private transient String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public _User getUser() {
        return user;
    }

    public void setUser(_User user) {
        this.user = user;
    }

    public _User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(_User friendUser) {
        this.friendUser = friendUser;
    }
}
