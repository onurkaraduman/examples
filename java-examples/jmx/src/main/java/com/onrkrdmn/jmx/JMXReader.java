package com.onrkrdmn.jmx;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JMXReader {

    public Map<String, String> readAllAttributes(String jmxUrl, String objectName) {
        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + jmxUrl + "/jmxrmi");
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
            Set<ObjectInstance> objectInstances = mbsc.queryMBeans(new ObjectName(objectName), null);

            Map<String, String> result = new HashMap<>();
            for (ObjectInstance fixSettingsMbean : objectInstances) {
                MBeanInfo mBeanInfo = mbsc.getMBeanInfo(fixSettingsMbean.getObjectName());
                for (MBeanAttributeInfo attribute : mBeanInfo.getAttributes()) {
                    String attrValue = mbsc.getAttribute(fixSettingsMbean.getObjectName(), attribute.getName()).toString();
                    result.put(attribute.getName(), attrValue);
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> readAttributes(String jmxUrl, String objectName, String[] attributes) {
        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + jmxUrl + "/jmxrmi");
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
            Set<ObjectInstance> objectInstances = mbsc.queryMBeans(new ObjectName(objectName), null);

            Map<String, String> result = new HashMap<>();
            for (ObjectInstance fixSettingsMbean : objectInstances) {
                for (String attribute : attributes) {
                    String attrValue = mbsc.getAttribute(fixSettingsMbean.getObjectName(), attribute).toString();
                    result.put(attribute, attrValue);
                }
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
