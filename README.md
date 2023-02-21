# Commission Calculator Rest Api

## Prerequisites
- Java 17
- Docker

## Installation
1. Create `.env` file with environment variables (they can contain any string value):
   - `REST_API_USERNAME`
   - `REST_API_PASSWORD`
   - `MONGO_USERNAME`
   - `MONGO_PASSWORD`

2. Make sure `JAVA_HOME` environment variable points to Java 17

## Running
1. Run docker compose file:
   ```shell
   docker compose up
   ```
2. Build application:
   ```shell
   .\gradlew build
   ```
3. Run application:
   ```shell
   java -jar build/libs/restApi-0.0.1-SNAPSHOT.jar
   ```
   
# Testing
Run:
```shell
.\gradlew test
```