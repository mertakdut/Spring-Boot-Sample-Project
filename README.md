<img align="left" width="48" height="48" src="./spring-boot-logo.jpg">

# Spring Boot Application Example

[![Build Status](https://travis-ci.org/mertakdut/Spring-Boot-Sample-Project.svg?branch=master)](https://travis-ci.org/mertakdut/Spring-Boot-Sample-Project)
[![Coverage Status](https://coveralls.io/repos/github/mertakdut/Spring-Boot-Sample-Project/badge.svg?branch=master)](https://coveralls.io/github/mertakdut/Spring-Boot-Sample-Project?branch=master)

This is a sample Java / Maven / Spring Boot application which provides RESTful services. It can be used as a starter project. Currently it is designed to work as [this project](https://github.com/mertakdut/React-Sample-Project)'s backend.

## Installation Instructions
  You can import the project as a maven application to your favorite IDE. I made my tests by using eclipse jee-2018-12.
  
  If lombok gets in your way, by referring [this answer](https://stackoverflow.com/a/22332248/4130569), you can install lombok by its jar file.

## To run the application
Use one of the several ways of running a Spring Boot application. Below are just three options:

1. Build using maven goal (or by using maven wrapper): `mvn clean package` and execute the resulting artifact as follows `java -jar BankApplicationBackend-0.0.1-SNAPSHOT.jar` or
2. On Unix/Linux based systems: run `mvn clean package` then run the resulting jar as any other executable `./BankApplicationBackend-0.0.1-SNAPSHOT.jar`
3. Run as a [Docker](https://www.docker.com/) container.  
    1) Clone the repository.
    2) cd to project root directory.
    3) `docker build -t demo/bankapp .`
      * If you get a `./mvnw not found` error, just run `mvn -N io.takari:maven:wrapper -Dmaven=3.5.3` while in the root directory of the project.
    4) `docker run --expose 8080 -p 8080:8080 demo/bankapp`

## To test the application
  1. Create a user with /api/user/create url.
      
    `$ curl -X POST localhost:8080/api/user/create -d "{\"username\": \"yourUsername\", \"password\": \"yourPassword\", \"tcno\": \"12512561125\"}" -H "Content-Type:application/json"`
  You'll get a response as in below.
  
    `{"username":"yourUsername","tcno":"12512561125"}`
  2. Generate an access token by /api/login url.
  
  `$ curl -H "Content-Type: application/json" -X POST -d "{\"username\": \"yourUsername\", \"password\": \"yourPassword\"}" http://localhost:8080/api/login`
  
  You'll be getting an access token similar to this.
  
  `eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b3VyVXNlcm5hbWUiLCJleHAiOjE1NTI0NDMzNjZ9.0WSCg4vaP7BVeJz8tQnL3s-BYjBB6UWXlQKCZHm1_zqEVIiA8_71Ni7tbPDm2DbW-Qc_fPP9CQF1jKcRC9njFQ`
  
  3. Use the token to access content available to all authenticated users, through the RESTful API.
    
  Http.Get request example:
    `curl -i -H "Accept: application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b3VyVXNlcm5hbWUiLCJleHAiOjE1NTI0NDMzNjZ9.0WSCg4vaP7BVeJz8tQnL3s-BYjBB6UWXlQKCZHm1_zqEVIiA8_71Ni7tbPDm2DbW-Qc_fPP9CQF1jKcRC9njFQ" -X GET http://localhost:8080/api/user/find/all`
    
  Http.Post request example:
    `curl -H "Content-Type: application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b3VyVXNlcm5hbWUiLCJleHAiOjE1NTI0NDMzNjZ9.0WSCg4vaP7BVeJz8tQnL3s-BYjBB6UWXlQKCZHm1_zqEVIiA8_71Ni7tbPDm2DbW-Qc_fPP9CQF1jKcRC9njFQ" -X POST -d "{\"username\": \"yourUsername\", \"buying\": \"true\", \"currency\": \"USD\", \"amount\": \"250\"}" http://localhost:8080/api/transaction/create`
