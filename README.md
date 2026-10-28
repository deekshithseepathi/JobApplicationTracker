# Job Application Tracker

A console-based Job Application Tracker built using Java, JDBC, and MySQL. This project helps users manage job applications and track their progress throughout the job search process.

## Features

- **Add Application:** Store company name, job role, location, application status, and application date.
- **View Applications:** Display all saved job applications with their unique IDs.
- **Search Applications:** Search by company name or job role.
- **Update Status:** Update an application's status using its ID.
- **Delete Application:** Delete a selected application using its ID.
- **Dashboard:** View total applications and counts by status.
- **Input Validation:** Handle empty fields and invalid menu choices.
- **Database Error Handling:** Display errors when database operations fail.
- **Duplicate Prevention:** Prevent duplicate company and job-role combinations.

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- JDBC (Java Database Connectivity)
- MySQL
- MySQL Connector/J
- Visual Studio Code

## Application Statuses

Applications can have one of the following statuses:

- Applied
- Online Assessment
- Interview
- Selected
- Rejected

## Database Setup

1. Install MySQL Server.
2. Open MySQL Workbench or the MySQL command line.
3. Execute the following SQL statements:

```sql
CREATE DATABASE job_tracker;

USE job_tracker;

CREATE TABLE job_applications (
    id INT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(100) NOT NULL,
    job_role VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL,
    UNIQUE (company_name, job_role),
    application_date DATE NOT NULL
);
```

If you are using the existing database created while developing this project, do not create the table again. Use the existing table and ensure that the `application_date` column is present.

## Database Configuration

Open `DatabaseConnection.java` and configure the following values for your local MySQL installation:

- Database URL: `jdbc:mysql://localhost:3306/job_tracker`
- Username: your MySQL username
- Password: your local MySQL password

**Security:** Never publish your actual database password to GitHub. Use environment variables or another secure configuration method before publishing the project.

## How to Run

1. Install a compatible JDK.
2. Install MySQL Server and start the service.
3. Create the database and table using the SQL instructions above.
4. Add MySQL Connector/J to the project's classpath.
5. Configure your local database connection.
6. Open the project in Visual Studio Code.
7. Run `Main.java`.
8. Follow the console menu to manage your job applications.

## Project Structure

```text
JobApplicationTracker/
├── Main.java
├── JobApplication.java
├── JobApplicationTracker.java
├── JobApplicationDAO.java
├── DatabaseConnection.java
├── README.md
└── lib/
    └── mysql-connector-j.jar
```

## Learning Outcomes

This project demonstrates practical experience with:

- Java classes, objects, constructors, and encapsulation
- Collections and iteration
- Exception handling and input validation
- JDBC database connectivity
- SQL CRUD operations
- Prepared statements
- MySQL primary keys and unique constraints
- Separating application logic from database operations

## Future Enhancements

- Build a web interface using HTML, CSS, and JavaScript.
- Develop REST APIs using Spring Boot.
- Add filtering by application status.
- Add interview dates and notes.
- Create a graphical dashboard with application statistics.

## Author

Developed as a Java and MySQL learning project.
