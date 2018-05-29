# Game of Life

This is an implementation of the Game of Life

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development purposes. See running for notes on how to run the application.

```
~ git clone https://github.com/VC8/gameoflife.git
~ cd gameoflife
~ mvn clean install
~ cd game-of-life-frontend
~ npm install
```

### Requirements

- Docker
- Java 1.8
- Maven

### Installing

A step by step series of how to start development

#### start mongodb

```
~ docker run -d -p 27017:27017 mongo:3.6 mongodb
```

#### run backend (with IntelliJ)

1. open run configurations
2. choose Spring Boot
3. add „de.cassens.gameoflife.TheGameOfLifeApplication“ to main class
4. hit ‚OK‘-Button
5. run application

the application is now running at localhost:8080.

If you make changes to a file and build via IntelliJ, the running app gets updated automatically.

#### run frontend

```
~ cd cd game-of-life-frontend
~ npm run start
```
the application is now running at localhost:3000.

If you make changes to a file, the running app gets updated automatically.

## Run the Application

1. make sure the ports 27015, 8080 and 3000 are not used by other services/applications
2. do the following in the project root folder
```
docker-compose -f docker/docker-compose.yml up
```
## Authors

* **Venance Cassens** - [VC8](https://github.com/VC8)
