package com.domob.hot.select;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.domob.hot.select.bolts.decoder.DecoderBolt;
import com.domob.hot.select.bolts.settle.SettleBolt;
import com.domob.hot.select.utils.StringMessageScheme;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

import java.util.Iterator;


/**
 * @author lixiang@domob.com
 * @desc 热搜关键字处理拓扑
 */
public class HotSelectTopology {

    public static void main(String[] args) throws Exception {
        // 配置解析
        Configuration config = new PropertiesConfiguration(args[0]);
        Config stormConf = new Config();
        stormConf.setDebug(config.getBoolean("storm.debug"));

        Iterator<String> keys = config.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            String val = config.getList(key).toString().replaceFirst("^\\[", "").replaceAll("\\]$", "");
            stormConf.put(key, val);
        }

        // KafkaSpout配置
        BrokerHosts brokerHosts = new ZkHosts(stormConf.get("kafka.zk.root").toString());
        String spoutName = stormConf.get("topology.spout.name").toString();
        // 这里需要注意的是，spout会根据SpoutConfig的后面两个参数在zookeeper上为每个kafka分区创建保存读取偏移的节点
        SpoutConfig spoutConfig = new SpoutConfig(
                brokerHosts,
                stormConf.get("kafka.read.topic").toString(),
                stormConf.get("kafka.spout.root").toString(),
                spoutName
        );
        // 数据格式相关
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringMessageScheme());
        /* offset相关
         * 参数time，表示where to start reading data：
         kafka.api.OffsetRequest.EarliestTime()，the beginning of the data in the logs
         kafka.api.OffsetRequest.LatestTime()，will only stream new messages
         */
        spoutConfig.startOffsetTime = kafka.api.OffsetRequest.LatestTime();

        // 构建拓扑
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(
                "kafka_reader",
                new KafkaSpout(spoutConfig),
                config.getInt("topology.spout.num")
        );
        // 解析
        builder.setBolt(
                "decoder",
                new DecoderBolt(),
                config.getInt("topology.decoder.num")
        ).localOrShuffleGrouping("kafka_reader");

        // 入库
        builder.setBolt(
                "settle",
                new SettleBolt(),
                config.getInt("topology.settle.num")
        ).localOrShuffleGrouping("decoder");

        stormConf.setNumWorkers(config.getInt("topology.worker.num"));
        stormConf.setNumAckers(config.getInt("topology.ack.num"));

        // 这里启动了新的进程来执行拓扑
        stormConf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, config.getInt("spout.max.pending"));
        StormSubmitter.submitTopology(config.getString("topology.name"), stormConf, builder.createTopology());
        //LocalCluster cluster = new LocalCluster();
        //cluster.submitTopology(config.getString("topology.name"), stormConf, builder.createTopology());

        System.out.println("Topology Storm Done!");
        System.out.println("Hot Select Stream done!");
    }

}
