### Testing
First check that you are able to compile and pass the tests:
```
mvn clean test
```

For test report and code coverage: 

```
mvn surefire-report:report
open target/site/surefire-report.html

mvn jacoco:report
open target/site/jacoco/index.html
```

### Start Server

To run the backend API locally using Maven: 

```
mvn spring-boot:run
```

Otherwise, to build it as a fat jar and execute it:

```
mvn clean install 
java -jar target/spring-boot-junit5-1.0-SNAPSHOT.jar
```

### Check Server

To access to the database (H2) on dev mode:

```
open http://localhost:8080/h2-console 
```

API Documentation (Swagger): 

```
open http://localhost:8080/v2/api-docs
open http://localhost:8080/swagger-ui.html
```
