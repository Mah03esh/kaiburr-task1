package com.kaiburr.task1.repository;

import com.kaiburr.task1.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Task entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    
    /**
     * Find tasks by name containing the specified string (case-insensitive search).
     * 
     * @param name the name fragment to search for
     * @return list of tasks matching the search criteria
     */
    List<Task> findByNameContaining(String name);
    
}
