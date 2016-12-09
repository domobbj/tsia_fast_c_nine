package com.domob.hot.select.bolts.settle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * 处理结算逻辑
 */
class SettleProcessor {


    private static Logger _log = LoggerFactory.getLogger(SettleProcessor.class);
    private DatabaseProcessor _databaseProcessor;

    SettleProcessor(Map conf) {
        _databaseProcessor = new DatabaseProcessor(conf);
    }


    boolean process(String word) {
        _log.info("start process >>>");
        return _databaseProcessor.insert(word);
    }
}
