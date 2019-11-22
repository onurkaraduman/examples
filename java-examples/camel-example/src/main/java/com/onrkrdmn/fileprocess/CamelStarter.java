package com.onrkrdmn.fileprocess;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelStarter {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new IntegrationRoute());
        context.start();

        Thread.sleep(2000);

    }
}
