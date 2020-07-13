package com.onrkrdmn;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;

import javax.jms.JMSContext;
import javax.jms.JMSException;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Camel IBM MQ integration is running");
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.start();

        addIbmJmsComponentToCamelContext(camelContext);
        addIbmMqRouteToCamelContext(camelContext);

        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBody("direct:route1", "hello ibm mq!!!!");

    }

    public static void addIbmMqRouteToCamelContext(CamelContext camelContext) throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:route1")
                        .to("ibmmq-jms:queue:///DEV.QUEUE.1");
            }
        });
    }

    public static void addIbmJmsComponentToCamelContext(CamelContext camelContext) throws JMSException {
        UserCredentialsConnectionFactoryAdapter ucf = getJmsConnectionFactory();
        camelContext.addComponent("ibmmq-jms", JmsComponent.jmsComponentAutoAcknowledge(ucf));
    }

    public static UserCredentialsConnectionFactoryAdapter getJmsConnectionFactory() throws JMSException {
        JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
        JmsConnectionFactory cf = ff.createConnectionFactory();

        // Set the properties
        cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, "localhost");
        cf.setIntProperty(WMQConstants.WMQ_PORT, 1414);
        cf.setStringProperty(WMQConstants.WMQ_CHANNEL, "DEV.APP.SVRCONN");
        cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
        cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, "QM1");
        cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsPutGet (JMS)");
        cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
        cf.setStringProperty(WMQConstants.USERID, "app");
        cf.setStringProperty(WMQConstants.PASSWORD, "1234");
        JMSContext context = cf.createContext();


        UserCredentialsConnectionFactoryAdapter ucf = new UserCredentialsConnectionFactoryAdapter();
        ucf.setTargetConnectionFactory(cf);
        return ucf;
    }
}
