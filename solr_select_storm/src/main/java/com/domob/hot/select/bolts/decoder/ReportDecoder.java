package com.domob.hot.select.bolts.decoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 解析广告主回调原始数据，进行签名校验
 */
class ReportDecoder {

    private static final String _pattern = "([^ ]*) (.*) (.*)  \\((.*)\\) \\[   (.*)\\] (.*) \\[(.*)\\]  (.*) (.*) (.*) (.*) (.*) (.*)";
    private static Pattern _r = Pattern.compile(_pattern);

    ReportDecoder() throws Exception {

    }

    private static Logger _log = LoggerFactory.getLogger(ReportDecoder.class);

    /**
     * 解析一行日志
     * 判断其是否符合一定的正则表达
     *
     * @param log String
     * @return Map Map<String, String> 将解析后的日志的每一部分放入Map中返回
     * @throws Exception e
     */
    String parseLine(String log) throws Exception {
        Matcher m = _r.matcher(log);
        if (!m.find()) {
            _log.error("matcher can not find something useful. log={}", log);
            return null;
        }

        // 1.判断是否为查询请求
        if (!"path=/select".equals(m.group(9))) {
            return null;
        }

        // 2.解析solr参数
        String str = m.group(10);
        String url = str.substring(str.indexOf("{") + 1, str.length() - 1);
        Map<String, String> params = getQueryMap(url);
        if (params.containsKey("q") && !"*.*".equals(params.get("q"))) {
            return params.get("q");
        }
        return null;
    }

    /**
     * parse query string
     * 参数重复时覆盖
     *
     * @param query String
     * @return Map Map<String, String>
     */
    private static Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            try {
                String name = param.split("=")[0];
                // 当用户所传参数为空字符串时，此处会有异常，做一下处理
                String value = param.split("=").length > 1 ? param.split("=")[1] : "";
                map.put(name, value);
            } catch (Exception e) {
                _log.error("query:" + query);
                _log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return map;
    }
}
