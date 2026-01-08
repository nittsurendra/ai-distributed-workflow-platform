package com.surendratech.workflow.workflow_engine.model;

import java.time.Instant;

public class WorkflowInstance {
    private final String instanceId;
    private final String workflowId;
    private String status; // PENDING, RUNNING, COMPLETED, FAILED
    private final Instant startedAt;
    private Instant completedAt;

    public WorkflowInstance(String instanceId, String workflowId) {
        this.instanceId = instanceId;
        this.workflowId = workflowId;
        this.status = "PENDING";
        this.startedAt = Instant.now();
    }

    // Getters
    public String getInstanceId() { return instanceId; }
    public String getWorkflowId() { return workflowId; }
    public String getStatus() { return status; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getCompletedAt() { return completedAt; }
    
    // Status change methods
    public void markRunning() { this.status = "RUNNING"; }
    public void markCompleted() { 
        this.status = "COMPLETED"; 
        this.completedAt = Instant.now();
    }
    public void markFailed() { 
        this.status = "FAILED"; 
        this.completedAt = Instant.now();
    }
}
