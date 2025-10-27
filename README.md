# Spring Boot Employee Management System & SonarQube Scan

## Overview

This project involves two main tasks:
1.  [cite_start]Developing a simple **CRUD (Create, Read, Update, Delete)** RESTful API for an Employee Management System using **Spring Boot, Spring Data JPA, and MySQL**[cite: 2, 10, 11, 25].
2.  [cite_start]Building the application, setting up **SonarQube** on a Linux VM, and generating a code quality/vulnerability report using **SonarScanner**[cite: 37, 39, 40, 44, 45].

---

## Task 1: Employee Management CRUD Application

### Features
* [cite_start]Add a new employee record[cite: 15].
* [cite_start]Update an existing employee record[cite: 16].
* [cite_start]Delete employee records (e.g., for resigned employees)[cite: 17].
* [cite_start]List all employee records[cite: 18].
* [cite_start]List a specific employee record by ID[cite: 19, 31].
* [cite_start]Includes at least two related entities (e.g., Employee and Department) with relationships defined[cite: 21, 22].
* [cite_start]Follows clean code architecture principles[cite: 26].
* [cite_start]Implements proper error handling for invalid requests[cite: 34].

### Technologies Used
* [cite_start]**Java** [cite: 6]
* [cite_start]**Spring Boot** (Latest stable version) [cite: 7]
* [cite_start]**Spring Web** (for REST APIs) [cite: 9]
* [cite_start]**Spring Data JPA** (for database interaction) [cite: 10]
* **Hibernate** (as JPA implementation)
* [cite_start]**MySQL** (as the database) [cite: 2, 11, 20]
* [cite_start]**Maven** (for project build and dependency management) [cite: 5]
* **Lombok** (Optional, recommended for reducing boilerplate)
* [cite_start]**Spring Boot DevTools** (Optional, for auto-restarts) [cite: 12]

### Setup & Configuration

1.  **Database:**
    * Ensure MySQL server is running.
    * Create a database (e.g., `abc_company_db`).
    * The application uses JPA/Hibernate to automatically create/update tables based on the Entity classes (`@Entity`).
2.  **Application Properties:**
    * [cite_start]Configure the database connection details in `src/main/resources/application.properties`[cite: 23]:
        ```properties
        # MySQL Database Connection
        spring.datasource.url=jdbc:mysql://localhost:3306/abc_company_db # Use your DB name
        spring.datasource.username=your_mysql_username
        spring.datasource.password=your_mysql_password
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

        # JPA/Hibernate Properties
        spring.jpa.hibernate.ddl-auto=update # Creates/updates schema automatically
        spring.jpa.show-sql=true # Logs SQL statements
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
        ```

### API Endpoints

The base path for the API is `/api/employees`.

| Method | Path                        | Description                       | Request Body   | Response                                  |
| :----- | :-------------------------- | :-------------------------------- | :------------- | :---------------------------------------- |
| `POST` | `/`                         | Add a new employee                | Employee JSON  | `201 Created` + Saved Employee JSON       |
| `GET`  | `/`                         | List all employees                | -              | `200 OK` + List of Employee JSON          |
| `GET`  | `/{id}`                     | Get employee by ID                | -              | `200 OK` + Employee JSON / `404 Not Found` |
| `PUT`  | `/{id}`                     | Update employee by ID             | Employee JSON  | `200 OK` + Updated Employee JSON / `404 Not Found` |
| `DELETE`| `/{id}`                     | Delete employee by ID             | -              | `204 No Content` / `404 Not Found`       |
| `GET`  | `/department/{departmentId}`| List employees by department ID | -              | `200 OK` + List of Employee JSON          |

*(Note: Assumes a base path of `/api/employees` based on previous code examples).*

### Testing
* [cite_start]**API Testing:** Use tools like **Postman** or **curl** to send requests to the endpoints listed above[cite: 35].
* [cite_start]**Unit Testing:** Develop JUnit tests for the service layer (`EmplyeeService`) methods using mocking frameworks if necessary[cite: 36].

---

## Task 2: SonarQube Scan

### Prerequisites
* [cite_start]A Linux environment, preferably on a Virtual Machine (VM)[cite: 39].
* [cite_start]SonarQube Server installed and running on the Linux VM[cite: 41].
* [cite_start]SonarScanner (command-line tool) installed and configured in the Linux VM's PATH[cite: 40].
* Maven installed on the Linux VM.
* Java installed on the Linux VM.

### Setup

1.  [cite_start]**Maven Dependencies:** Ensure the SonarQube Maven plugin is added to your project's `pom.xml` file[cite: 38].
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
2.  [cite_start]**`sonar-project.properties` (Optional with Maven, but Recommended):** Create this file in the project root directory on your Linux machine[cite: 42]. Configure it with your project key, source directories, and potentially the SonarQube server URL and authentication token.
    ```properties
    sonar.projectKey=your-unique-project-key
    sonar.projectName=Employee Management System
    sonar.projectVersion=1.0
    sonar.sources=src/main/java
    sonar.tests=src/test/java
    sonar.java.binaries=target/classes
    sonar.sourceEncoding=UTF-8
    # sonar.host.url=http://<linux-server-ip>:9000 # If not localhost
    # sonar.token=your_sonarqube_token # If authentication is needed
    ```

### Build & Scan Steps (on Linux VM)

1.  **Navigate:** Open a terminal in the Linux VM and `cd` into your project's root directory.
2.  [cite_start]**Build:** Compile the project and generate necessary class files using Maven[cite: 43].
    ```bash
    mvn clean package
    ```
3.  [cite_start]**Scan:** Run the SonarScanner using the Maven plugin[cite: 44]. Make sure your SonarQube server is running.
    ```bash
    # Basic scan (assumes SonarQube on http://localhost:9000, no auth)
    mvn sonar:sonar

    # Scan with specific server URL and authentication token
    mvn sonar:sonar -Dsonar.host.url=http://<linux-server-ip>:9000 -Dsonar.token=your_sonarqube_token
    ```

### Checking Results

1.  [cite_start]**Access Dashboard:** Open a web browser and go to your SonarQube server URL (e.g., `http://<linux-server-ip>:9000`)[cite: 46].
2.  [cite_start]**Login:** Log in to SonarQube[cite: 47].
3.  **View Project:** Find your project on the dashboard.
4.  [cite_start]**Analyze:** Review the reported bugs, vulnerabilities, code smells, code coverage (if tests were run and configured), and overall quality gate status[cite: 47].

---

## How to Run the Application

1.  Make sure you have Java (version specified in `pom.xml`, e.g., 17 or 25) and Maven installed.
2.  Ensure your MySQL database server is running.
3.  Update the database credentials in `src/main/resources/application.properties`.
4.  Navigate to the project's root directory in your terminal.
5.  Run the application using Maven:
    ```bash
    mvn spring-boot:run
    ```
6.  The application will start on `http://localhost:8080`.
