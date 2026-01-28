package com.surendratech.worker.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskResult {

    private String eventId;
    private String executionId;
    private String taskId;
    private String status; // SUCCESS / FAILED
    private String errorMessage;
    private Instant completedAt;

    public TaskResult() {}

    public TaskResult(
            String eventId,
            String executionId,
            String taskId,
            String status,
            String errorMessage,
            Instant completedAt
    ) {
        this.eventId = eventId;
        this.executionId = executionId;
        this.taskId = taskId;
        this.status = status;
        this.errorMessage = errorMessage;
        this.completedAt = completedAt;
    }

    public String getEventId() { return eventId; }
    public String getExecutionId() { return executionId; }
    public String getTaskId() { return taskId; }
    public String getStatus() { return status; }
    public String getErrorMessage() { return errorMessage; }
    public Instant getCompletedAt() { return completedAt; }
}
