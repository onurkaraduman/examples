package com.onrkrdmn.fileprocess;

import org.apache.camel.Exchange;

public class LoggingProcessor implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        System.out.println("received order = [" + exchange .getIn().getBody(String.class)+ "]");
    }
}
