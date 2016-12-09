package langteng.com.redpocketmoney.ui.main;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 */
public class PersonInfoModel extends BmobObject {

    public ShareBean shareBean;
    public List<BannerBean> bannerBeanList = new ArrayList<>();

    public static class ShareBean {
        public String title;
        public String clickurl;
        public String iconurl;
        public String desc;
    }

    public static class BannerBean {
        public String title;
        public String jumpurl;
        public String iconurl;
        public int jumpuype;
        public int isshow;
    }


}
