# F1zzBuzzPr10r1tyQ
This is a Spring Boot project which allows clients to add and remove WorkOrders to a priority queue in a RESTful manner.

# Building and running the app
This is a gradle project which uses the gradle wrapper.
On Windows the following commands can be used in the project's root folder

To build the project:
````
gradlew.bat build
````
To run the generated jar navigate to {projectDir}/build/libs then run:
````
java -jar app-1.0.jar
````
To run unit tests and generate jacoco reports run (from the project root), jacoco reports will be  at {projectDir}/build/reports/jacoco/test/html
````
gradlew.bat test jacocoTestReport
````
To generate javadocs run (from the project root), javadocs will be available at {projectDir}/build/docs/javadoc
````
gradlew.bat javadoc
````
SwaggerUI can be used to view documentation and test the REST API here when the app is running
````
http://localhost:8080/jgriffin-priorityq/api/v1/swagger-ui.html
````

# General Notes
* All REST API parameters are mandatory
* DateTime fields use the standard ISO-8601 DateTime format, example: "2017-04-18T23:28:12.000Z"
