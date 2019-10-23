# todoapp
Spring boot sample app with API first approach. Integrates Open API Generator to generate API interfaces and Domain models.

## Project Structure
Gradle Multimodule project. All directories at level 1 are gradle modules.

    .
    ├── api  // controllers
    │   └── src
    │       ├── main
    │       │   ├── java
    │       │   ├── resources
    │       │   └── spec // openAPI spec. here
    │       └── test
    │           ├── java
    │           └── resources
    ├── app
    │   └── src
    │       ├── main
    │       │   ├── java
    │       │   └── resources
    │       └── test
    │           ├── java
    │           └── resources
    ├── config
    │   └── src
    │       ├── main
    │       │   ├── java
    │       │   └── resources
    │       └── test
    │           ├── java
    │           └── resources
    ├── dao
    │   └── src
    │       ├── main
    │       │   ├── java
    │       │   └── resources
    │       └── test
    │           ├── java
    │           └── resources
    ├── entities
    │   └── src
    │       ├── main
    │       │   ├── java
    │       │   └── resources
    │       └── test
    │           ├── java
    │           └── resources
    └── service  // bussiness logic
        └── src
            ├── main
            │   ├── java
            │   └── resources
            └── test
                ├── java
                └── resources

## OpenAPI Generator
- OpenAPI generator gradle plugin is integrated in `:api` gradle module
- Edit Open API spec in `:api/src/main/spec` folder and execute `codegen` gradle task
- Interfaces and models are generated at `:api/build/src/main/java`
- Open API spec is written for two APIs, `Todo Entries` and `Users` in separate yaml files. Models are shared between the two APIs.
- Two separate gradle tasks exists to generate the Todo (generateTodoApi) and User (generateUserApi) APIs.
- The above tasks are combined using a single `codegen` task.

## Swagger Setup
- Swagger UI is available at `http://localhost:8083/swagger-ui.html`, via `io.springfox:springfox-swagger-ui'
- Swagger API docs at `http://localhost:8083/v2/api-docs`
- Swagger Docket config and API info is configured at `net.ripper.todoapp.api.config.SwaggerConfig`
- Tags are used to group APIs. an additional `@Api(tags=aTag)' is needed on the Controller implementation for correct grouping.

## Flyway DB Migrations
- Install Flyway cli from `http://flywaydb.org`
- Change directory to `:db` and run `flyway -configFiles=flyway-{env}.conf migrate` to apply migrations in `:db\sql` folder
- Refer flyway documentation on how to create new migrations. 

## Jacoco and Sonar
- Use `start-sonar.sh` script to start a local sonar instance at port 9001
- Add `systemProp.sonar.host.url=http://localhost:9001` to `~/.gradle/gradle.properties`
- Run gradle task `test` followed by `sonarqube`
- Open sonar UI running at `http://localhost:9001` to see code quality results

## Todo
- [ ] JWT security
- [ ] Unit Tests and Integration tests separation
- [ ] Custom spring templates for OpenAPI generator
- [ ] Config module implementation
- [X] Integrate Jacoco or sonar for code quality reports
- [X] Git hooks to format code before code
