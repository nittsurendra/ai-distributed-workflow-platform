package com.surendratech.workflow.workflow_engine.model;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskResult {
    @JsonProperty("event_id")
    private String eventId;
    
    @JsonProperty("execution_id")
    private String executionId;
    
    @JsonProperty("task_id")
    private String taskId;
    
    @JsonProperty("status")
    private String status; // COMPLETED, FAILED
    
    @JsonProperty("result")
    private Object result;
    
    @JsonProperty("error")
    private String error;
    
    @JsonProperty("timestamp")
    private Instant timestamp;
    
    public TaskResult() {}
    
    public TaskResult(String eventId, String executionId, String taskId, String status, Object result) {
        this.eventId = eventId;
        this.executionId = executionId;
        this.taskId = taskId;
        this.status = status;
        this.result = result;
        this.timestamp = Instant.now();
    }

    // Getters & Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getExecutionId() { return executionId; }
    public void setExecutionId(String executionId) { this.executionId = executionId; }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Object getResult() { return result; }
    public void setResult(Object result) { this.result = result; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
