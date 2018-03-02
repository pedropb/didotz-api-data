Didotz API Data
===============

This project is a Spring Boot Web Application that implements a REST API and persistence layer for a simple [Rewards program](https://en.wikipedia.org/wiki/Loyalty_program).


## How to run

**Pre-requisites:** Java 8 installed. (JDK recommended)

- Clone the repo
- `./mvnw install`
- Run `java -jar target/didotz-api-data-0.0.1-SNAPSHOT.jar`
- Browse to [http://localhost:8080/clients](http://localhost:8080/clients) (you should see an empty JSON).
- Run [Postman](https://www.getpostman.com/apps) and start submitting Requests against this server.
