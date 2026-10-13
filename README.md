# SauceDemo Selenium + Cucumber login tests

Maven-based browser tests using Java 17, Selenium, Cucumber, JUnit Platform, and Allure. Each configured account runs as a separate Scenario Outline example. Browser drivers are resolved automatically by Selenium Manager.

## Prerequisites

- JDK 17 or newer
- Maven 3.8.9 or newer
- Google Chrome installed

## Run the tests

Run headlessly (the same command used in GitHub Actions):

```shell
mvn test -Dheadless=true
```

Run with a visible browser:

```shell
mvn test -Dheadless=false
```

## Allure report

```shell
mvn allure:serve
```

The command generates a report from `target/allure-results`, starts a temporary local server, and opens the report in a browser. If it does not open automatically, use the URL printed in the Maven output. Stop the server with `Ctrl+C`.

The static report files are still available under `target/site/allure-maven-plugin/`; when you need to serve that already-generated folder, run this from the project root in PowerShell:

```powershell
py -m http.server 8000 --directory target/site/allure-maven-plugin
```

Then open `http://localhost:8000` in a browser and stop the server with `Ctrl+C`.

The GitHub Actions workflow uploads both the raw results and generated HTML report as an artifact named `allure-report-<run number>`.

## Test configuration

The base URL, ordered account list, shared password, and locked account are in `src/test/resources/test-data.properties`. The feature Examples select entries by zero-based index, keeping credentials out of step definitions and Page Objects.
