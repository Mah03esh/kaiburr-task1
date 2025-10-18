package com.kaiburr.task1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Embedded document representing a single execution of a task.
 * This class is not a separate MongoDB collection but is embedded within Task documents.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskExecution {
    
    private Date startTime;
    private Date endTime;
    private String output;
    
}
