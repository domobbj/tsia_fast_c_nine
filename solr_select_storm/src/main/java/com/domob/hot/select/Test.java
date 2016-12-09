package com.domob.hot.select;

public class Test {
    public static void main(String[] args) {
        String s = "params={q=*:*&indent=on&wt=json}";
        System.out.println(s.substring(s.indexOf("{")+1, s.length()-1));
    }

}
