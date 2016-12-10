package langteng.com.redpocketmoney.ui.search;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 */
public class SearchInfoModel extends BmobObject {


    /**
     * status : 0
     * QTime : 1
     * params : {"q":"你好","indent":"on","wt":"json"}
     */

    public ResponseHeaderBean responseHeader;
    /**
     * numFound : 4
     * start : 0
     * docs : [{"id":200,"uid":1,"create_time":1481327988,"_version_":1553286759385661440,"key":"你好"},{"id":203,"uid":1,"create_time":1481328007,"_version_":1553286759391952896,"key":"你好"},{"id":202,"uid":1,"create_time":1481327988,"_version_":1553286759389855744,"key":"好的"},{"id":205,"uid":1,"create_time":1481328007,"_version_":1553286759395098624,"key":"好的"}]
     */

    public ResponseBean response;

    public static class ResponseHeaderBean {
        public int status;
        public int QTime;
        /**
         * q : 你好
         * indent : on
         * wt : json
         */

        public ParamsBean params;

        public static class ParamsBean {
            public String q;
            public String indent;
            public String wt;
        }
    }

    public static class ResponseBean {
        public int numFound;
        public int start;
        /**
         * id : 200
         * uid : 1
         * create_time : 1481327988
         * _version_ : 1553286759385661440
         * key : 你好
         */

        public List<DocsBean> docs;

        public static class DocsBean {
            public int id;
            public int uid;
            public int create_time;
            public long _version_;
            public String key;
        }
    }
}
