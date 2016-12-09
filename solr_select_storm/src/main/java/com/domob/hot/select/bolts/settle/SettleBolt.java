package com.domob.hot.select.bolts.settle;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 处理结算逻辑 入库
 */
public class SettleBolt extends BaseRichBolt {

    private SettleProcessor _processor;
    private OutputCollector _collector;
    private static Logger _log = LoggerFactory.getLogger(SettleBolt.class);

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
        _processor = new SettleProcessor(stormConf);
    }

    @Override
    public void execute(Tuple input) {
        String word = (String) input.getValueByField("settle");
        if (_processor.process(word)) {
            _log.info("settle bolt success");
        }
        _collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
