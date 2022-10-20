# BonnieFrontend

## Developing Bonnie Frontend

### Prerequisites

To develop bonnie frontend you need to install the followings:

  - [NodeJS](https://nodejs.org/en/download/) 16.18.0
  - NPM 8.19.2
  - [Angular CLI](https://angular.io/cli) CLI 14.2.4 (or above)

To download the UI dependencies run:

    npm install

Bonie frontend communicates with backend via REST endpoints, because of this the REST client has to be generated. We can to this in in the backend's rest module. To generate REST client implementation please run (in backend's rest module):

    mvn verify
 
 After mvn install finished it should create a "generated-client" directory in the fronted folder.

Please remember that if you change the REST interface you should re-generate REST client with the Maven command above.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.


This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.2.4.
