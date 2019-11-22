#### Spring Boot x509 Authentication (Spring Security)


*Reference blog post:* https://www.baeldung.com/x-509-authentication-in-spring-security


In a SSL Mutual Authentication scenario, these are the overall steps that take place during SSL handshake:

* Client initiates the request.
* The server sends its own certificate which is found from its keystore.
* The client verifies its certificate if it can be trusted. If the server’s certificate or its CA’s certificate are found in truststore, then the server is authenticated.
* If client authentication is enabled at server side, the server requests’s for client’s certificate.
* The client sends its own certificate which is found from its keystore.
* The server verifies the client’s certificate if it can be trusted. If the client’s certificate or its CA’s certificate are found in its truststore, then the client is authenticated.



## Certificate Management


# Generate Keystore

This generate certificate of authority (CA)
```
$> make create-keystore PASSWORD=123456
```

# Generate localhost cert and add into keystore
This generate a certificate for development host and adds to keystore. Host certificate is signed by certificate authority (CA) (which is generated above)
```
$> make add-host HOSTNAME=localhost
```


# Generate truststore
To allow client authentication we need truststore. Truststore contains valid certificates of our certificate authority and all of the allowed clients.
```
$> make create-truststore PASSWORD=123456
```

# Generate client certificate and add into truststore
```
$> make add-client CLIENTNAME=cid
```

## Testing
import src/test/resources/certificate/client1.p12 into browser password 123456 (this can be changed if you create new certificate)

Browse the following URL
```
https://localhost:8443/api/v1/hello
```