# IBM MQ via Apaceh Camel Project
This is example project to show hwo to upload data to ibm mq jms.


I run ibm mq inside docker container. You can find how to run ibm mq in docker container -> https://developer.ibm.com/messaging/learn-mq/mq-tutorials/mq-connect-to-queue-manager/#docker

You might need to update username and password for regarding your ibm mq configuration:
cf.setStringProperty(WMQConstants.USERID, "<yourUser>");
 cf.setStringProperty(WMQConstants.PASSWORD, "<yourPassword>");