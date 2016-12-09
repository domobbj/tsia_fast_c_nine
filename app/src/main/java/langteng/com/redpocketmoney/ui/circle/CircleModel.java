package langteng.com.redpocketmoney.ui.circle;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 */
public class CircleModel extends BmobObject {

    public String title;

    public String tag;

    public String userNickName;

    public String userIcon;
    public String username;

    /**
     * 内容
     **/
    public String content;

    /**
     * 发表时间
     **/
    public String publishTime;

    public BmobFile imgFile;


    /**
     * 内容 图片
     **/
    public List<String> contentImgs = new ArrayList<>();


    /**
     * 评论
     **/
    public List<Comment> commentList = new ArrayList<>();


    public List<Prase> praseList = new ArrayList<>();

    public static class Comment {
        public String commentUserName;
        public String commentUserIcon;
        public String commentContent;
    }

    public static class Prase {
        public String praseUserName;
        public String praseUserIcon;
    }

}
