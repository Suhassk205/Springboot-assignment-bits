# Spring Boot Assignment: Library Management System

## Overview
This is a Spring Boot application to manage Books and Authors. It demonstrates:
- JPA with H2 in-memory database
- ManyToOne and OneToMany relationships
- Custom Inner Join queries
- JSP views with JSTL and custom CSS styles
- JUnit & Mockito for unit testing

## Requirements
- Java 17
- Maven 3.8+

## How to Run
1. Run `mvn spring-boot:run` or execute `LibraryApplication.main()` from your IDE.
2. The H2 database will automatically be populated with 10 Authors and 10 Books.
3. Access the application at `http://localhost:8080/books`.
4. H2 Console is available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, User: `sa`, no password).

## Features
- **Read**: List all books along with their author details.
- **Create**: Add a new book by filling out a form and selecting an author.
- **Update**: Edit an existing book's details.
