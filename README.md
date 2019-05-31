# Exercise [basic]: Kafka MongoDB
Exercise for simple system reading data from SQL DB and loading it into MongoDB through Kafka.

## Requirements
#### Functional
The system should provide the following functionality:
- read data from a SQL database
- check if any of received data fields is null
- load the data to Kafka
- read the data from Kafka
- persist the data to MongoDB

#### Nonfunctional Requirements
Functionality should be delivered under given constraints:
- use Java 8 Stream API
- avoid using frameworks like Spring, etc.

## Modules
Implemented system consists of the following functional modules:
- **kafka-loader** - Module responsible for reading data from SQL database, validating it and loading it on Kafka topic.
After start it reads all the data and pushes it in a single run, then exits.
- **mongo-loader** - Module responsible for consuming data from Kafka topic and persisting it to MongoDB.
After start it polls Kafka for 1 second, persists the data and repeats this process indefinitely. 
- **kafka-mongo-common** - Module holding common classes like models, validators or utility classes.
- **kafka-mongo-parent** - Parent module providing properties and common dependencies versions for other modules.


## How to run
#### Prerequisites
- Java 8+
- Maven
- Docker
- Docker Compose

#### Guide
1. go to directory `REPO_PATH/docker` and execute command `docker-compose up`
2. build project
3. run Kafka Loader jar file
4. run Mongo Loader jar file
5. open browser and go to address `localhost:8085` to verify the results in Mongo Express