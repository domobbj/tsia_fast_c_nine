package com.domob.hot.select;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        Pattern r = Pattern.compile("^solr\\..*");
        Matcher m = r.matcher("solr.2016-11-10.log");
        System.out.println(m.matches());
    }

}
