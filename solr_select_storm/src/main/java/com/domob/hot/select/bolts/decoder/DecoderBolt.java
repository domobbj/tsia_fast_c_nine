package com.domob.hot.select.bolts.decoder;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * DecodeBolt 负责解析原始日志
 */
public class DecoderBolt extends BaseRichBolt {

    private ReportDecoder _reportDecoder;
    private OutputCollector _collector;
    private static Logger _log = LoggerFactory.getLogger(DecoderBolt.class);

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        try {
            _reportDecoder = new ReportDecoder();
        } catch (Exception e) {
            _log.error(e.getMessage());
            e.printStackTrace();
        }
        _collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String log = input.getStringByField("msg");
        String callback = null;
        // 解析过程中遇到异常，记录日志
        try {
            callback = _reportDecoder.parseLine(log);
        } catch (Exception e) {
            _log.error("log={}, msg={}", log, e.getMessage());
        }
        if (callback != null) {
            _log.info("decoder bolt parse log success.");
            _collector.emit(input, new Values(callback));
        }
        _collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("settle"));
    }
}