# Getting Started
https://www.youtube.com/watch?v=CZcoKflJwlw

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites

```bash
Java 11
Maven 3.6.1
Docker 19.03.5
AWS S3 credentials
```

# Running

## Setup AWS S3 credentials
https://docs.aws.amazon.com/sdk-for-php/v3/developer-guide/guide_credentials_profiles.html
```
1 - Create plain text file "credentials" on /.aws folder;
2 - Paste the following content with your own credentials:
[default]
aws_access_key_id = YOUR_AWS_ACCESS_KEY_ID
aws_secret_access_key = YOUR_AWS_SECRET_ACCESS_KEY
```


## Create Docker network
```bash
$ docker network create quarkus-hackaton-network
```

## Run Docker Compose (setup all needed services):
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


## Run BE application w/ live reload (don't forget to set your AWS S3 credentials file on /.aws/credentials location):
```bash
$ mvn compile quarkus:dev
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
