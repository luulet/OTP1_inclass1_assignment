# Temperature Converter

## 1. Assignment Description
A Java Maven project that converts temperatures and is built, tested and deployed through a CI/CD pipeline.

Deliverables:
- `TemperatureConverter` class: Fahrenheit to Celsius, Celsius to Fahrenheit, Kelvin to Celsius, and extreme temperature check (below -40 °C or above 50 °C).
- JUnit 5 unit tests.
- JaCoCo code coverage report.
- Jenkins pipeline (`Jenkinsfile`).
- Docker image (`Dockerfile`) pushed to Docker Hub (`sampowes/temperature-converter`).

Note: the JavaFX GUI and database part of the later stage is not implemented in this repository.

## 2. Technologies & Tools Used
- Java 24, Maven
- JUnit 5
- JaCoCo
- Jenkins (pipeline from SCM)
- Docker / Docker Hub
- Git / GitHub, IntelliJ IDEA

## 3. Design Approach & Implementation Method
- `TemperatureConverter` holds all conversion logic as simple instance methods, so it can be tested without any UI.
- `Main` is the application entry point.
- `pom.xml` configures JUnit, Surefire and the JaCoCo plugin (`prepare-agent` and `report`).
- `Dockerfile` is a multi-stage build: Maven builds the jar, then a lightweight JRE image runs it.
- `Jenkinsfile` stages: Build, Test, Code Coverage, Publish Test Results, Publish Coverage Report, Docker Build, Docker Run Verify, Docker Push.

## 4. Testing & Quality Assurance Steps
Automated tests (`TemperatureConverterTest`, JUnit 5) cover:
- `fahrenheitToCelsius` with multiple inputs
- `celsiusToFahrenheit` with multiple inputs
- `isExtremeTemperature` edge cases (-40, 50, and values beyond them)
- `kelvinToCelsius` (e.g. 300 K = 26.85 °C)

Results: all tests pass (4 tests, 0 failures) locally and in Jenkins.
Coverage (JaCoCo, from Jenkins): instruction 63%, branch 67%, line 50%, method 71%, class 50%. The `Main` class is not covered.
The report is at `target/site/jacoco/index.html` after `mvn clean test`.

## 5. How to Run
Prerequisites: JDK 24, Maven, Docker (optional).

```
mvn clean test          # run tests and generate JaCoCo report
mvn javafx:run          # start the JavaFX GUI (database file tempdb is created in the project folder)
mvn clean package       # build the jar
java -jar target/OTP1_inclass1_assignment-1.0-SNAPSHOT.jar

docker build -t sampowes/temperature-converter .
docker run --rm sampowes/temperature-converter
```

Jenkins: create a Pipeline job, choose "Pipeline script from SCM", set the Git repository URL and Script Path `Jenkinsfile`.
