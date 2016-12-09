package com.domob.hot.select.utils;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by lixiang on 16/4/7.
 */
public class StringMessageScheme implements Scheme {

    public List<Object> deserialize(byte[] ser) {
        String msg;
        try {
            msg = new String(ser, "UTF-8");
            return new Values(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Fields getOutputFields() {
        return new Fields("msg");
    }

}
