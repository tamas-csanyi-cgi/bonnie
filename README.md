# Bonnie

A training application for newcomers.

## Architecture

This application implements the hexagonal architecture which is a modular application design pattern.

### Modules (a.k.a Plugins)

### businessrules

The "center" of the hexagonal architecture, contains all the business logic, defines interfaces for data storage, and
messaging. Every other module depend on this core module (it is not a plugin).

### authentication

Using Spring Security the authentication module provides a secure channel to handle requests.

### rest

RESTFUL API for the frontend, calls the core module.

### h2-storage

A data storage plugin that uses Spring JDBC with embedded H2 database packaged into it.

### messaging

Integration module with Kafka. The system gets and send asynchronous events through it.

### starter

It packs all the modules into a single spring boot application. It is a build with a specific set of plugins. The idea is that, we can have multiple starter modules, with other plugin configurations (with another database plugin for instance). You have to start this module, if you want to run the application

## Stack

### Spring

This project uses Spring Boot framework.
We use it for dependency injection and configuration management, but also because it has battle-tested integration
packages with all of the major cloud native components.

### H2 database

For the time of writing the project has a H2 embedded database to store the data in. It has a nice off-the-shelf gui to
manage the database.
To connect to it the project uses Spring JDBC connector.

### Kafka

The project uses Kafka as the message broker. You have to install, and configure it separately before starting the
application. For more information how to do that read the corresponding document in the doc folder.

### Facebook integration

Bonnie provides multiple ways of authentication, such as login form and login via Facebook.
As the application is in the development stage, in order to be able to login through Facebook, you need to set up your
environment. For more information how to do that read the corresponding document in the doc folder.

### Angular frontend

This application has an angular frontend to manage the orderings. A web domain has been registered for Bonnie deployment on https://bonnee.eu/

### Jenkins & Docker

To make the process of *development -> deploy* faster, easier and automatic we use a continuous integration tool **Jenkins** and containerization platform **Docker**. Both Jenkins and Docker have been installed on our remote server which can be accessed on [https://bonnee.eu/ci/](https://bonnee.eu/ci/) or via SSH CLI.
For more information on Jenkins and Docker setup read the corresponding document in the docs folder.

# Building & Running the application locally

First, start the zookeeper, and kafka services that is described in the ```doc/runKafka.txt``` file.

Then, to build the application, issue the following command in the parent project folder

```bash
mvn clean install
```

After this, to run the project, issue the following command from the folder of starter module:

```bash
mvn spring-boot:run
```

To run the angular frontend, issue the following command from the frontend folder:

```bash
ng serve
```

# Bonnie live environment

The Bonnie application is up and running as a docker container and can be operated on [https://bonnee.eu/](https://bonnee.eu/). It can be continuously updated via Jenkins server.
