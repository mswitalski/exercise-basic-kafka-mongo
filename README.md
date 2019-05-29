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

## How to run
#### Prerequisites
- Java 8+
- Docker
- Docker Compose

#### Guide
1. go to directory `REPO_PATH/docker` and execute command `docker-compose up`
2. build project
3. run Kafka Loader jar file
4. run Mongo Loader jar file
5. open browser and go to address `localhost:8081` to verify the results in Mongo Express