package com.onrkrdmn;

import com.onrkrdmn.jmx.JMXReader;

import java.util.Map;

public class App {

    private static String JMX_URL = "localhost:10099";

    // This is example to read Session object over jmx
    private static String JMX_OBJECT_NAME = "org.quickfixj:type=Session,";

    public static void main(String[] args) {
        JMXReader jmxReader = new JMXReader();
        Map<String, String> stringStringMap = jmxReader.readAllAttributes(JMX_URL, JMX_OBJECT_NAME);
        stringStringMap.forEach((key, value) -> System.out.println(key + "   " + value));
    }
}
