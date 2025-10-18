# Kaiburr Assessment - Task 1

This is a Spring Boot application that provides a REST API to create, manage, and run simple "task" objects. It uses a MongoDB Atlas database to store all task data.

## How to Run

### What you'll need
* **Java 17**
* **Maven**
* **A MongoDB Atlas Cluster**: The connection string is already in the `application.properties` file. Just make sure your IP address is whitelisted (using `0.0.0.0/0` is easiest).

### Build and Run
In your terminal, navigate to the project folder and run:

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
The server will start on http://localhost:8080
Here is the complete, finalized README.md file in a single block.

You can copy and paste this entire block into your README.md file, save it, and then push it to GitHub. This version includes the correct filenames for all the screenshots you've taken.

Markdown

# Kaiburr Assessment - Task 1

This is a Spring Boot application that provides a REST API to create, manage, and run simple "task" objects. It uses a MongoDB Atlas database to store all task data.

## How to Run

### What you'll need
* **Java**
* **Maven**
* **A MongoDB Atlas Cluster**: The connection string is already in the `application.properties` file. Just make sure your IP address is whitelisted (using `0.0.0.0/0` is easiest).

### Build and Run
In your terminal, navigate to the project folder and run:

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
The server will start on http://localhost:8080.

API Endpoints

1. Create a Task

Method: PUT
URL: /api/tasks
Body: A JSON object for the task. This endpoint will reject unsafe commands.
JSON
{
    "name": "Print Hello",
    "owner": "Mahesh",
    "command": "echo Hello World!"
}

2. Get Tasks
Method: GET
URL: /api/tasks (Gets all tasks)
URL: /api/tasks?id={taskId} (Gets a single task by its ID)

3. Find Tasks by Name
Method: GET
URL: /api/tasks/find?name={searchString}
Details: Returns any tasks with a name that contains your search string.

4. Execute a Task
Method: PUT
URL: /api/tasks/execute/{id}
Details: Runs the task's command locally and saves the output to the database.

5. Delete a Task
Method: DELETE
URL: /api/tasks/{id}
Details: Deletes a task from the database.

## MongoDB Configuration
The application connects to MongoDB Atlas using the connection string in `application.properties .


## API Test Screenshots

### 1. Application Startup
![Application Startup](./screenshots/1-start.png)

### 2. Create Task (PUT /api/tasks)
![Create Task](./screenshots/2.png)

### 3. Get All Tasks (GET /api/tasks)
![Get All Tasks](./screenshots/3.png)

### 4. Execute Task (PUT /api/tasks/execute/{id})
![Execute Task](./screenshots/4.png)

### 5. Delete Task (DELETE /api/tasks/{id})
![Delete Task](./screenshots/5.png)