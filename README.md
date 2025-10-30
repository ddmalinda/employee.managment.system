# 🚀 Spring Boot Employee Management System

## ✨ Project Overview

This project is a comprehensive Employee Management System for **ABC Company Pvt Ltd**, built with Spring Boot. It provides a robust RESTful API for managing employees, departments, and projects, demonstrating key concepts like CRUD operations, JPA entity relationships, and clean architecture.

---

## 🛠️ Tech Stack

*   **Java 17+**
*   **Spring Boot**
*   **Spring Web** (for REST controllers)
*   **Spring Data JPA** (for database interactions)
*   **Hibernate** (JPA implementation)
*   **MySQL** (Relational Database)
*   **Maven** (Build Tool)
*   **Lombok** (to reduce boilerplate code)

---

## ⚙️ Setup and Configuration

### 1. Database Setup
*   Ensure your MySQL server is running.
*   Create a database named `abc_company_db`.
    ```sql
    CREATE DATABASE abc_company_db;
    ```
*   Tables will be automatically created by Hibernate.

### 2. Application Properties
Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/abc_company_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 3. Build and Run
1.  **Build the project:**
    ```bash
    mvn clean package
    ```
2.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
The application will be available at `http://localhost:8080`.

---

## 🗄️ Database Schema

The application uses three main entities: `Department`, `employee`, and `Project`.

*   **Department**: The central entity.
*   **employee**: Belongs to one `Department` (**Many-to-One**).
*   **Project**: Belongs to one `Department` (**Many-to-One**).
*   **employee & Project**: Have a **Many-to-Many** relationship, linked by a join table named `employee_project`.

---

## 🌐 API Endpoints

### Department API
*   **Base Path:** `/api/Departments`

| Method   | Path      | Description               |
| :------- | :-------- | :------------------------ |
| `POST`   | `/`       | Create a new department   |
| `GET`    | `/`       | Get all departments       |
| `GET`    | `/{id}`   | Get a department by ID    |
| `PUT`    | `/{id}`   | Update a department       |
| `DELETE` | `/{id}`   | Delete a department       |

### Employee API
*   **Base Path:** `/api/employees`

| Method   | Path                       | Description                 |
| :------- | :------------------------- | :-------------------------- |
| `POST`   | `/`                        | Create a new employee       |
| `GET`    | `/`                        | Get all employees           |
| `GET`    | `/{id}`                    | Get an employee by ID       |
| `PUT`    | `/{id}`                    | Update an employee          |
| `DELETE` | `/{id}`                    | Delete an employee          |
| `GET`    | `/department/{deptId}`   | Get employees by department |

### Project API
*   **Base Path:** `/api/projects`

| Method   | Path                          | Description                       |
| :------- | :---------------------------- | :-------------------------------- |
| `POST`   | `/`                           | Create a new project              |
| `GET`    | `/`                           | Get all projects                  |
| `GET`    | `/{id}`                       | Get a project by ID               |
| `PUT`    | `/{id}`                       | Update a project                  |
| `DELETE` | `/{id}`                       | Delete a project                  |
| `PUT`    | `/{projectId}/employees/{employeeId}` | Assign an employee to a project   |
| `DELETE` | `/{projectId}/employees/{employeeId}` | Remove an employee from a project |
| `GET`    | `/{projectId}/employees`      | Get all employees for a project   |

---

## 🧪 How to Test

Use a tool like **Postman** to send requests to the endpoints listed above.

### Example: Create a Full Workflow
1.  **Create a Department:**
    *   `POST /api/Departments`
    *   Body: `{ "departmentName": "Engineering" }`

2.  **Create an Employee:**
    *   `POST /api/employees`
    *   Body: `{ "firstName": "John", "lastName": "Doe", "email": "john.doe@company.com", "password": "123", "Department": { "DepartmentID": 1 } }`

3.  **Create a Project:**
    *   `POST /api/projects`
    *   Body: `{ "projectName": "Website Redesign", "Department": { "DepartmentID": 1 } }`

4.  **Assign Employee to Project:**
    *   `PUT /api/projects/1/employees/1`

Happy Coding! 😊
