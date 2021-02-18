# Spring Boot sample web api
To start the API run the command `gradlew bootRun` you can use the parameter `--args='--spring.profiles.active={profile}'` replacing '{profile}' with the desired profile in which to run the app.

In the 'dev'-profile the Application starts on port 8000 and in the 'prod'-profile on port 9000.

There currently is only one Endpoint: `localhost:{port}/users`

It supports CRUD-operations and has an optional URL-Parameter named `firstName` to filter the users by their first name. Both the parameter-name and the given first name are case-sensitive.

The embedded Database is pre-loaded with 3 Users

| firstName |       lastName      |            email            |
|:---------:|:-------------------:|:---------------------------:|
| Klaus     | Sievers             | klaus@sievers.de            |
| Klaus     | Kappenstiel         | klaus@kappenstiel.de        |
| Elke      | Kappenstiel-Sievers | elke@kappenstiel-sievers.de |
