package com.onrkrdmn.fileprocess;

import org.apache.camel.builder.RouteBuilder;

public class IntegrationRoute extends RouteBuilder {
    public void configure() throws Exception {
        from("file:target/inbox")
                .process(new LoggingProcessor())
                .bean(new TransformationBean(), "makeUpperCase")
                .to("file:target/outbox/dvd");
    }
}
