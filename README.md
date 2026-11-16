# Temperature Converter

## 1. Assignment Description
A Java Maven project that converts temperatures and is built, tested and deployed through a CI/CD pipeline.

Deliverables:
- `TemperatureConverter` class: Fahrenheit to Celsius, Celsius to Fahrenheit, Kelvin to Celsius, and extreme temperature check (below -40 °C or above 50 °C).
- JUnit 5 unit tests.
- JaCoCo code coverage report.
- Jenkins pipeline (`Jenkinsfile`).
- Docker image (`Dockerfile`) pushed to Docker Hub (`sampowes/temperature-converter`).

The project also includes a JavaFX GUI localized into 5 languages with a database (see section 6).

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

## 6. Database Localization Approach: Translation Table
Unit names and descriptions are read from the database in the selected language (UI labels stay in `messages_xx.properties`).
- `unit` (id, unit_key, kind): language-neutral base table (celsius, fahrenheit, kelvin, kmh, km, hour).
- `unit_translation` (unit_id FK, lang, name, description): one row per unit and language (en, fi, sv, ja, ar).
- `conversion_record` (from_unit_id FK, to_unit_id FK, input_value, result, lang, created_at): saved conversions; the last 5 are shown in the GUI.
- `UnitDAO` / `TempRecordDAO` use a JOIN with an English fallback (`COALESCE`), and the language selector changes both the ResourceBundle and the DB query language.

Why: adding a language only needs new rows (no schema change), non-text data is not duplicated, and a missing translation falls back to English. It is the cleanest normalized option for this design.
The schema and data are in `src/main/resources/init.sql` (UTF-8, H2 database).
