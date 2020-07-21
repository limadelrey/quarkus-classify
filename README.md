# Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites

```bash
Java 11
Maven 3.6.1
Docker 19.03.5
```

# Running

## Create Docker network
```bash
$ docker network create quarkus-hackaton-network
```

## Run Docker Compose (setups all needed services):
```bash
$ docker-compose up
```

## Run BE source connector properties:
https://github.com/limadelrey/quarkus-hackaton/blob/master/infrastructure/be-source-connector.properties

Open http://localhost:3030 (sometimes it takes ~2 minutes to run)

Open Connectors > New > PostgresConnector >  Paste "be-source-connector.properties" content > Create


# Testing

## Run UI application w/ live reload:
```bash
$ mvn compile quarkus:dev
```

## Run BE application w/ live reload:
```bash
$ mvn quarkus:dev -Ddebug=5006 -Daws.accessKeyId=AKIA5F7ILMI3DKUGAWUR -Daws.secretAccessKey=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
```

## Generate AI native image:
```bash
$ mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=limadelrey/quarkus-hackaton-graalvm-with-python
```                         

## Run stress tests
Run Apache Bench stress tests:
```bash
$ ab -n 1000 -c 10 http://localhost:8081/api/v1/image-classification
```

# Other informations
Useful endpoints:

- POST http://localhost:8081/api/v1/image-classification
- GET http://localhost:8081/api/v1/image-classification
- GET http://localhost:8081/api/v1/image-classification/{id}
- DELETE http://localhost:8081/api/v1/image-classification/{id}
- GET http://localhost:8081/api/v1/notifications
- GET http://localhost:8080/health
- GET http://localhost:8080/metrics
- GET http://localhost:8080/open-api
- GET http://localhost:8080/swagger-ui

Access UI:
- GET http://localhost:8080

Access Swagger UI:
- GET http://localhost:8081/swagger-ui

Access Kafka:
- GET http://localhost:3030

Access Prometheus:
- GET http://localhost:9090

Access Jaeger:
- GET http://localhost:16686
