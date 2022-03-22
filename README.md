# Project Title

Spring microservices

## Description

This is a project identify how to use microservices with spring using spring cloud.
The project contain :

* Config microservice 

  It's a microservice that allows to load the configuration of each microservice, the configuration is located in the folder (config), the folder contains properties files, the name of each file is the name of the microservice.
  
* Eureka microservice 

  It's a registration microservice that will allow each microservice to register (by its ip address, port and microservice name), this will allow the gateway microservice to know to which it should redirect when sends an HTTP request from a client. We can go to (localhost:8761) and see the instances of each registered microservices.
  
* Gateway microservice 

  It's a microservice that can intercept all HTTP requests, and redirect them to the microservice concerned, it provides load balancing between instances of a microservice, it uses the spring cloud gateway dependency and the reactive spring dependency web, it's based on reactive programming using Netty as the application server, and it ensures the verification of users access of all microservices.
  
* Login microservice 

  It's a microservice which ensures the authentication and the creation of users accounts, it uses spring web as a dependency and tomcat as an application server.
  
* Dashboard microservice 

  It's a microservice that retrieves dashboard data from a Mongodb database, it uses spring web as a dependency and tomcat as an application server.
  
  The frontend part witch contain a dashboard and users management of the project is developed using angular, and it's in: 
  
  https://github.com/mohamedanouarbencheikh/angular-dashboard-auth
  
  The cross origin part at the backend level is configured at the gateway microservice.
  
## Getting Started
  
### Dependencies

The project is created using spring https://start.spring.io/.

* Config microservice 
  - spring cloud config (config server)
  
* Eureka microservice 
  - spring cloud discovery (eureka server)
  - spring cloud config (config client)
  
* Gateway microservice 
  - spring cloud routing (gateway)
  - web (spring web reactive)
  - ops (spring boot actuator)
  - spring cloud config (config client)
  - spring cloud discovery (eureka discovery client)
  - developer tools (lombok)
  - security (spring security)
  - nosql (spring data mongodb)
  
* Login microservice 
  - web (spring web)
  - ops (spring boot actuator)
  - spring cloud config (config client)
  - spring cloud discovery (eureka discovery client)
  - developer tools (lombok)
  - security (spring security)
  - nosql (spring data mongodb)
  - jwt
  - nimbus-jose-jwt for JWE
  
* Dashboard microservice 
  - web (spring web)
  - ops (spring boot actuator)
  - spring cloud config (config client)
  - spring cloud discovery (eureka discovery client)
  - developer tools (lombok)
  - security (spring security)
  - nosql (spring data mongodb)
  - jwt
  - nimbus-jose-jwt for JWE

### Installing

* Mongodb as server.
* MongoDBCompass or NoSQL Manager for MongoDB as client.

### Executing program

The microservices must launch in the following order :

  1- Config microservice 
  
  2- Eureka microservice 
  
  3- Gateway microservice
  
  4- Login microservice 
  
  5- Dashboard microservice   


