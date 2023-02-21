# Commission Calculator Rest Api

## Prerequisites
- Java 17
- Docker

## Installation
Create `.env` file with variables:
   - `REST_API_USERNAME`
   - `REST_API_PASSWORD`
   - `MONGO_USERNAME`
   - `MONGO_PASSWORD`

These variables can contain any string value

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