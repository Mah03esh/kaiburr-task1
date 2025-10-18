# Kaiburr Assessment - Task 1
## REST API for Task Management with MongoDB

This is a Spring Boot application that provides a REST API to manage "task" objects stored in a MongoDB database.

## Technology Stack

- **Java**: JDK 17
- **Spring Boot**: 3.2.0
- **Spring Web**: For REST API
- **Spring Data MongoDB**: For database interaction
- **Maven**: For project build
- **Lombok**: To reduce boilerplate code

## Prerequisites

1. **Java 17** or higher installed
2. **Maven** installed
3. **MongoDB Atlas** account (connection string already configured)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Mah03esh/kaiburr-task1.git
cd kaiburr-task1
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### 1. Create a Task
- **Method**: `PUT`
- **URL**: `/api/tasks`
- **Body**: JSON Task object
- **Response**: 201 Created (or 400 Bad Request if command is unsafe)

```json
{
    "name": "Print Hello",
    "owner": "Mahesh",
    "command": "echo Hello World!"
}
```

### 2. Get All Tasks / Get Task by ID
- **Method**: `GET`
- **URL**: `/api/tasks` (all tasks) or `/api/tasks?id={taskId}` (specific task)
- **Response**: 200 OK with task(s) or 404 Not Found

### 3. Find Tasks by Name
- **Method**: `GET`
- **URL**: `/api/tasks/find?name={searchString}`
- **Response**: 200 OK with matching tasks or 404 Not Found

### 4. Delete a Task
- **Method**: `DELETE`
- **URL**: `/api/tasks/{id}`
- **Response**: 204 No Content

### 5. Execute a Task
- **Method**: `PUT`
- **URL**: `/api/tasks/execute/{id}`
- **Response**: 200 OK with updated task (including execution details) or 404 Not Found

## Security Features

The application includes command validation to prevent execution of potentially dangerous commands:

**Blocked Commands**: `rm`, `sudo`, `mv`, `cp`, `chmod`, `chown`, `reboot`, `shutdown`, `dd`, `mkfs`, `format`, `del /f`, `rmdir /s`

## MongoDB Configuration

The application connects to MongoDB Atlas using the connection string in `application.properties`:

```properties
spring.data.mongodb.uri=mongodb+srv://[username]:[password]@cluster0.emfxz5n.mongodb.net/KaiburrAssessmentDB?retryWrites=true&w=majority&appName=Cluster0
```

## Task Execution

When a task is executed via the `/api/tasks/execute/{id}` endpoint:

1. The system retrieves the task from the database
2. Executes the command locally using `ProcessBuilder`
3. Captures the output (stdout and stderr)
4. Records start time, end time, and output
5. Adds the execution details to the task's execution history
6. Saves and returns the updated task

## API Test Screenshots

### 1. Application Startup
![Application Startup](screenshots/1-start.png)

### 2. Create Task (PUT /api/tasks)
![Create Task](screenshots/2.png)

### 3. Get All Tasks (GET /api/tasks)
![Get All Tasks](screenshots/3.png)

### 4. Execute Task (PUT /api/tasks/execute/{id})
![Execute Task](screenshots/4.png)

### 5. Delete Task (DELETE /api/tasks/{id})
![Delete Task](screenshots/5.png)

## Example Workflow

1. **Create a task**: `PUT /api/tasks`
2. **View all tasks**: `GET /api/tasks`
3. **Execute the task**: `PUT /api/tasks/execute/{id}`
4. **View execution history**: `GET /api/tasks?id={id}`
5. **Search for tasks**: `GET /api/tasks/find?name=Hello`
6. **Delete the task**: `DELETE /api/tasks/{id}`

## Notes

- The application runs on Windows with `cmd.exe`, but also supports Linux/macOS with `sh`
- All task executions are stored in the database for historical tracking
- The API uses proper HTTP status codes (200, 201, 204, 400, 404)
- Input validation prevents execution of unsafe system commands

## Author

**Mahesh**  
Created for Kaiburr Assessment - Task 1

## License

This project is created for assessment purposes.
