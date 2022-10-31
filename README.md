# POS Backend

## Features

- Manage CRUD operations.
- Manage DB migrations.

## Prerequisites

You need the following installations in your local machine to build and run the project:

| Dependency | URL |
| ------ | ------ |
|PostgreSQL 14.2|https://www.postgresql.org/ftp/source/v14.2/|
| Git | https://git-scm.com/downloads |
| Gradle 7.3.3 | https://gradle.org/releases/ |
| Java 17 | https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html |

To check if your installation is ok, use the following commands:
### Gradle
```sh
gradle -v
```
### Git
```sh
git --version
```
### Java
```sh
java --version
```
## Setting up database
For this project to work it's necessary that your PostgreSQL DB is up in your localhost. Once your database is running create a database with the user **postgres**, so that the following connection credentials should work on the port **5432**:

**User: postgres**

**Password: 12345**

**DB name: posDB**

## Build

To build the project use the following steps:

1) Clone the repository.
2) Enter to the cloned project directory using the command prompt.
3) Build the project using the following command:
    ```sh
    gradle build
    ```
4) Once the compilation has finished, run the project on your local machine with:
    ```sh
    java -jar build/libs/pos-backend-0.0.1-SNAPSHOT.jar
    ```
## Check if the project is running
To check if the project is running use the following url:
**http://localhost:8081/api/v1/app/health**