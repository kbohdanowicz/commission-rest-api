# Commission Calculator REST API

REST API that allows you to calculate commission for transactions made by a given bank customer. Includes a sample data set.

## Built With:
- Kotlin
- Gradle
- Spring Boot
- MongoDB

## Getting Started

### Prerequisites
- Java 17
- Docker

### Installation
1. Create `.env` file with environment variables (they can contain any string value):
   - `REST_API_USERNAME`
   - `REST_API_PASSWORD`
   - `MONGO_USERNAME`
   - `MONGO_PASSWORD`

2. Make sure `JAVA_HOME` environment variable points to Java 17

### Running
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
4. App is available at:
   `http://localhost:8080`

### Testing
Run:
```shell
.\gradlew test
```

## Usage
Endpoint `api` provided with a key `customer_id` allows you to search for a commission data of users in a few ways:
- by entering an identification number ranging from 1 to 5 (e.g. "2"),
- by entering several identification numbers separated by a comma (e.g. "1, 5"),
- by entering the string "ALL" or an empty string to search for commission data of all users.

### Example
#### Calculate data of a user with identification number "3"
GET request:
```
http://localhost:8080/api?customer_id=3
```

Response body:
```json
{
    "First name": "Adam",
    "Last name": "Adamowski",
    "Customer ID": 3,
    "Number of transactions": 4,
    "Total value of transactions": "134.47",
    "Transactions fee value": "6.82",
    "Last transaction date": "29.12.2020 21:00:03"
}
```

## Implementation details
- App uses stateless basic authentication
- Each request is logged to local MongoDB database and each log contains the client's ID, the amount of the commission and the date of commission calculation.
