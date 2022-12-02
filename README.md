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

The Bonnie application is up and running as a docker container on [https://bonnee.eu/](https://bonnee.eu/).
Bonnie has a multibranch build pipeline in Jenkins. This pipeline is triggered by git push events and build new artifacts of Bonnie backend and frontend.
In the last stage of the pipeline we restart Bonnie with the new versions of backend and frontend.
You can see the build pipeline here: https://bonnee.eu/ci/job/build-bonnie/. You find the pipeline definition in the file names "Jenkinsfile" under bonnie's repository.

# FAQ

###### Q: Bonnie accounts (Users/admin) to use for login..?

A: Accounts are located in .... one fo them is..

###### Q: Do I need to Install Jenkins / docker on my local machine to make Bonnie work?

A: There's no need to install these programs, in fact we are not allowed to install docker on our local machines due to lincensing.. Docker and Jenkins are both installed on our remote server.

###### Q: What SDK to use for Bonnie?

A: Open JDK v.18 (free, nonlicensed)  /v.17 ok as well?

###### Q: How can I add stuff on github?

A: You need to be added to the colaborator list by the repository owner.

###### Q: Where can I get the task for bonnie to be implemented?

A: On github you can claim an issue you can work on after a standup discussion. I there is none you can thing about possible implementations and create that issue which you can work on if approved.
