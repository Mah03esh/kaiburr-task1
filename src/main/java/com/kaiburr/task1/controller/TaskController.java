package com.kaiburr.task1.controller;

import com.kaiburr.task1.model.Task;
import com.kaiburr.task1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST Controller for Task management endpoints.
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    
    private final TaskService taskService;
    
    /**
     * Endpoint 1: Create a new task
     * PUT /api/tasks
     * 
     * @param task the task to create
     * @return ResponseEntity with created task and 201 status, or 400 if validation fails
     */
    @PutMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    /**
     * Endpoint 2: Get all tasks or get a task by ID
     * GET /api/tasks
     * GET /api/tasks?id={id}
     * 
     * @param id optional task ID parameter
     * @return ResponseEntity with task(s) or 404 if not found
     */
    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String id) {
        if (id != null) {
            return taskService.getTaskById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Endpoint 3: Find tasks by name
     * GET /api/tasks/find?name={name}
     * 
     * @param name the name fragment to search for
     * @return ResponseEntity with matching tasks or 404 if none found
     */
    @GetMapping("/find")
    public ResponseEntity<List<Task>> findTasksByName(@RequestParam String name) {
        List<Task> tasks = taskService.getTasksByName(name);
        
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * Endpoint 4: Delete a task by ID
     * DELETE /api/tasks/{id}
     * 
     * @param id the task ID
     * @return ResponseEntity with 204 No Content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Even if task doesn't exist, return 204 as per REST best practices
            return ResponseEntity.noContent().build();
        }
    }
    
    /**
     * Endpoint 5: Execute a task
     * PUT /api/tasks/execute/{id}
     * 
     * @param id the task ID
     * @return ResponseEntity with executed task or 404 if not found
     */
    @PutMapping("/execute/{id}")
    public ResponseEntity<Task> executeTask(@PathVariable String id) {
        try {
            Task executedTask = taskService.executeTask(id);
            return ResponseEntity.ok(executedTask);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
