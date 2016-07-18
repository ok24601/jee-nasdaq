[![Build Status](https://travis-ci.org/hrytsenko/jee-nasdaq.svg?branch=master)](https://travis-ci.org/hrytsenko/jee-nasdaq)

# Summary

Case study of the application based on Java EE.

The application gets data from the NASDAQ and provides access to these data through RESTful web services.

# Tools

* Build Tool - [Maven 3.3.x](https://maven.apache.org/)
* Application Server - [WildFly 10.0.0.Final](http://wildfly.org/)
* Database - [MySQL 5.7.x](https://www.mysql.com/)

# Build and Deploy

Scripts for the local environment:

* `app-build` - build the application.
* `app-deploy` - deploy the application.
* `app-check` - check the application using the [Sonar](http://www.sonarqube.org/).
* `db-clean` - remove data from the local database.
* `db-migrate` - apply migrations to the local database (see `local/config/mysql/migrations`).

Configurations:

* `config/wildfly` - configuration for WildFly.
* `config/mysql` - migrations for MySQL.
