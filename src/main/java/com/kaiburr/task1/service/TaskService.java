package com.kaiburr.task1.service;

import com.kaiburr.task1.model.Task;
import com.kaiburr.task1.model.TaskExecution;
import com.kaiburr.task1.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service class containing business logic for Task operations.
 */
@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    /**
     * Validates if a command is safe to execute.
     * Blocks potentially dangerous commands.
     * 
     * @param command the command to validate
     * @return true if the command is safe, false otherwise
     */
    private boolean isCommandSafe(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }
        
        String[] blockedCommands = {"rm", "sudo", "mv", "cp", "chmod", "chown", "reboot", "shutdown", "dd", "mkfs", "format", "del /f", "rmdir /s"};
        String normalizedCommand = command.toLowerCase().split(" ")[0];
        
        for (String blocked : blockedCommands) {
            if (normalizedCommand.equals(blocked) || normalizedCommand.contains(blocked)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Creates a new task after validating the command is safe.
     * 
     * @param task the task to create
     * @return the created task
     * @throws IllegalArgumentException if the command is unsafe
     */
    public Task createTask(Task task) {
        if (!isCommandSafe(task.getCommand())) {
            throw new IllegalArgumentException("Unsafe command detected: " + task.getCommand());
        }
        return taskRepository.save(task);
    }
    
    /**
     * Retrieves all tasks from the database.
     * 
     * @return list of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    /**
     * Retrieves a task by its ID.
     * 
     * @param id the task ID
     * @return Optional containing the task if found
     */
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }
    
    /**
     * Finds tasks by name containing the specified string.
     * 
     * @param name the name fragment to search for
     * @return list of matching tasks
     */
    public List<Task> getTasksByName(String name) {
        return taskRepository.findByNameContaining(name);
    }
    
    /**
     * Deletes a task by its ID.
     * 
     * @param id the task ID
     */
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
    
    /**
     * Executes a task by running its command locally and recording the execution details.
     * 
     * @param id the task ID
     * @return the updated task with execution details
     * @throws NoSuchElementException if the task is not found
     */
    public Task executeTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));
        
        String command = task.getCommand();
        Date startTime = new Date();
        String output = "";
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            
            // Detect OS and set appropriate command shell
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                processBuilder.command("cmd.exe", "/c", command);
            } else {
                // Linux/macOS
                processBuilder.command("sh", "-c", command);
            }
            
            processBuilder.redirectErrorStream(true); // Combine stdout and stderr
            Process process = processBuilder.start();
            
            // Read output
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append(System.lineSeparator());
                }
                output = sb.toString();
            }
            
            int exitCode = process.waitFor(); // Wait for the process to complete
            output += "Process exited with code: " + exitCode;
            
        } catch (Exception e) {
            output = "Execution failed: " + e.getMessage();
        }
        
        Date endTime = new Date();
        
        TaskExecution execution = new TaskExecution(startTime, endTime, output);
        task.getTaskExecutions().add(execution);
        
        return taskRepository.save(task);
    }
    
}
