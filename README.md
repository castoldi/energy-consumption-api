# Getting Started

## Prerequisites
* JDK 11 installed
* Maven
* Preference IDE

### How to run the app
* The mainclass is com.landisgyr.energyconsumption.EnergyConsumptionApiApplication
* Run the app using below maven command.

```sh
mvn clean spring-boot:run
```
* The available endpoints will be as default in below URLs.

[POST] http://localhost:8080/event

Need to add Content-Type: application/json in the header.

[GET] http://localhost:8080/consumption?meter_number=1714A6

[GET] http://localhost:8080/microgeneration?meter_number=1714A6

* Run tests using below command.

```sh
mvn clean test
```

* Run SonarQube with below command.

```sh
mvn install sonar:sonar -Dsonar.login=login -Dsonar.password=password
```

### Run the app using SSL certificates
* To enable SSL need to start the application with "ssl" profile as per below.
* When ssl profile is enabled the app will start on port 8443.
* Must use https in the URL.

```sh
mvn clean spring-boot:run -Dspring-boot.run.profiles=ssl
```

* https://localhost:8443/consumption?meter_number=1714A6
* In order to test you will need to import the cert from /src/main/resources/certs/energyconsumption.p12 into the testing tool.

### Generate javadoc
* Run maven command below to generate the javadocs.

```sh
mvn javadoc:javadoc
```

* Javadoc is generated in folder \energy-consumption-api\target\site\apidocs

### Generate new certificates
Use below commands to generate non-production certificates.

```sh
keytool -genkeypair -alias energyconsumption -keyalg RSA -keysize 4096 -storetype JKS -keystore energyconsumption.jks -validity 3650 -storepass changeit
keytool -genkeypair -alias energyconsumption -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore energyconsumption.p12 -validity 3650 -storepass changeit
Should you have a signed JKS, use this command to convert to PKCS12:
keytool -import -alias energyconsumption -file myCertificate.crt -keystore energyconsumption.p12 -storepass changeit
```


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)