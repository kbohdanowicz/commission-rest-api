# Commission Calculator Rest Api

## Prerequisites
- Java 17
- Gradle

## Installation
Create `.env` file with variables:
   - `API_USERNAME`
   - `API_PASSWORD`
   - `MONGO_USERNAME`
   - `MONGO_PASSWORD`

## Running

1. Run mongodb:
   ```shell
   docker compose up
   ```
2. Build application:
   ```shell
   gradle build
   ```
3. Run application:
   ```shell
   java -jar build/libs/restApi-0.0.1-SNAPSHOT.jar
   ```
   
# Testing
Run:
```shell
gradle test
```