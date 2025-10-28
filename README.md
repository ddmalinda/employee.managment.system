# 🚀 Spring Boot Employee Management System & SonarQube Code Scan 🩺

## Project Overview ✨

Welcome to the Employee Management System project! This initiative tackles two key objectives:

1.  **Develop a Robust CRUD API:** Build a fully functional RESTful service for managing employee data at ABC Company Pvt Ltd. using the power of Spring Boot, JPA, and MySQL.
2.  **Ensure Code Quality:** Integrate SonarQube into the development workflow by setting it up on a Linux VM, scanning the application code, and analyzing the results for bugs, vulnerabilities, and code smells.

---

## Task 1: Building the Employee Management CRUD API 👨‍💻👩‍💻

### Core Functionality
* **👥 Add Employees:** Create new records for incoming employees.
* **✍️ Update Employees:** Modify details for existing employees.
* **🗑️ Delete Employees:** Remove records, for instance, when an employee resigns.
* **📋 List All Employees:** Retrieve a complete list of employee records.
* **👤 Get Specific Employee:** Fetch a single employee record by their unique ID.
* **🏢 Entity Relationships:** Model at least two related entities (like Employee and Department) and establish their connections (e.g., using JPA relationships).
* **🧹 Clean Architecture:** Structure the application code following clean architecture principles for better maintainability.
* **⚠️ Error Handling:** Implement robust error handling for scenarios like invalid input or attempts to access non-existent records.

### Tech Stack 🛠️
* **Java** (17+ recommended)
* **Spring Boot** (Latest stable version)
* **Spring Web** (for building REST controllers)
* **Spring Data JPA** (for simplifying database interactions)
* **Hibernate** (JPA implementation)
* **MySQL** (Relational database)
* **Maven** (Build tool and dependency management)
* **Lombok** (Optional but handy for reducing boilerplate)
* **Spring Boot DevTools** (Optional, enables auto-restarts during development)

### Quick Setup Guide ⚙️

1.  **Database Prep:**
    * Ensure your MySQL server is up and running.
    * Create a dedicated database schema (e.g., `abc_company_db`).
    * Tables will be automatically managed by Hibernate based on your `@Entity` definitions.
2.  **Configuration (`application.properties`):**
    * Update `src/main/resources/application.properties` with your database connection details:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/abc_company_db
        spring.datasource.username=your_mysql_username
        spring.datasource.password=your_mysql_password
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
        ```

### API Endpoints 🌐

Base Path: `/api/employees`

| Method   | Path                       | Description                 | Body Example (JSON)                    | Success Response                         |
| :------- | :------------------------- | :-------------------------- | :------------------------------------- | :--------------------------------------- |
| `POST`   | `/`                        | Create New Employee         | `{ "firstName": "...", ... }`          | `201 Created` + Saved Employee           |
| `GET`    | `/`                        | Get All Employees           | -                                      | `200 OK` + List of Employees             |
| `GET`    | `/{id}`                    | Get Employee by ID          | -                                      | `200 OK` + Employee / `404 Not Found`    |
| `PUT`    | `/{id}`                    | Update Employee by ID       | `{ "firstName": "...", ... }`          | `200 OK` + Updated Employee / `404 Not Found` |
| `DELETE` | `/{id}`                    | Delete Employee by ID       | -                                      | `204 No Content` / `404 Not Found`      |
| `GET`    | `/department/{deptId}`   | Get Employees by Department | -                                      | `200 OK` + List of Employees             |

### Testing Your API 🧪
* **Manual Testing:** Use tools like **Postman** or **curl** to hit the endpoints and verify responses.
* **Unit Testing:** Write JUnit 5 tests for your `EmployeeService` class, mocking the repository layer to isolate business logic.

---

## Task 2: Code Analysis with SonarQube 🧐

### What You'll Need
* A **Linux** environment (VM recommended).
* **SonarQube Server** running on the Linux machine.
* **SonarScanner** (command-line tool) installed and available in the Linux PATH.
* **Maven** and **Java** installed on the Linux machine.

### Configuration Steps

1.  **Maven Plugin:** Add the SonarScanner plugin to your `pom.xml`:
    ```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.sonarsource.scanner.maven</groupId>
                <artifactId>sonar-maven-plugin</artifactId>
                <version>3.11.0.3922</version> </plugin>
        </plugins>
    </build>
    ```
2.  **(Optional) `sonar-project.properties`:** Create this file in your project root on Linux for explicit configuration:
    ```properties
    sonar.projectKey=unique-employee-management-key
    sonar.projectName=Employee Management System
    sonar.projectVersion=1.0
    sonar.sources=src/main/java
    sonar.sourceEncoding=UTF-8
    sonar.java.binaries=target/classes
    # Optional: Point to your SonarQube server and provide a token
    # sonar.host.url=http://<linux-vm-ip>:9000
    # sonar.token=your_sonarqube_auth_token
    ```

### Running the Scan (on Linux VM) 🏃‍♀️

1.  **Build:** Open a terminal in your project directory and build the application:
    ```bash
    mvn clean package
    ```
2.  **Scan:** Execute the SonarScanner via Maven. Ensure your SonarQube server is running.
    ```bash
    # If SonarQube is on localhost:9000 with no auth
    mvn sonar:sonar

    # If SonarQube is elsewhere or needs a token
    mvn sonar:sonar -Dsonar.host.url=http://<linux-vm-ip>:9000 -Dsonar.token=your_sonarqube_auth_token
    ```

### Reviewing the Report 📊

1.  **Access SonarQube:** Open your browser and navigate to the SonarQube UI (e.g., `http://<linux-vm-ip>:9000`).
2.  **Login** and find your project.
3.  **Analyze:** Dive into the dashboard! Check for Bugs 🐞, Vulnerabilities 🛡️, Code Smells 👃, Code Coverage % (if configured), and the overall Quality Gate status ✅/❌.

---

## Getting Started 🏁

1.  Clone this repository.
2.  Set up your MySQL database and update `application.properties`.
3.  Ensure you have the correct Java and Maven versions installed.
4.  Run the application: `mvn spring-boot:run`
5.  Access the API at `http://localhost:8080`.
6.  Follow Task 2 steps to perform SonarQube analysis.

Happy Coding! 😊
