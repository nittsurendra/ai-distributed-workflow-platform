package com.surendratech.workflow.workflow_engine.model;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskEvent {
    @JsonProperty("event_id")
    private String eventId;
    
    @JsonProperty("execution_id")
    private String executionId;
    
    @JsonProperty("workflow_id")
    private String workflowId;
    
    @JsonProperty("task_id")
    private String taskId;
    
    @JsonProperty("task_type")
    private String taskType;
    
    @JsonProperty("inputs")
    private Object inputs;
    
    @JsonProperty("timestamp")
    private Instant timestamp;
    
    @JsonProperty("status")
    private String status; // QUEUED, IN_PROGRESS, COMPLETED, FAILED
    
    public TaskEvent() {}
    
    public TaskEvent(String executionId, String workflowId, String taskId, String taskType, Object inputs) {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.executionId = executionId;
        this.workflowId = workflowId;
        this.taskId = taskId;
        this.taskType = taskType;
        this.inputs = inputs;
        this.timestamp = Instant.now();
        this.status = "QUEUED";
    }

    // Getters & Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getExecutionId() { return executionId; }
    public void setExecutionId(String executionId) { this.executionId = executionId; }

    public String getWorkflowId() { return workflowId; }
    public void setWorkflowId(String workflowId) { this.workflowId = workflowId; }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getTaskType() { return taskType; }
    public void setTaskType(String taskType) { this.taskType = taskType; }

    public Object getInputs() { return inputs; }
    public void setInputs(Object inputs) { this.inputs = inputs; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
