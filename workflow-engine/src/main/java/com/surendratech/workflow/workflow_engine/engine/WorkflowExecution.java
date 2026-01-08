package com.surendratech.workflow.workflow_engine.engine;

public class WorkflowExecution {
    private final String executionId;
    private final String workflowId;
    private int currentStageIndex;
    private int currentTaskIndex;
    private String status;

    public WorkflowExecution(String executionId, String workflowId) {
        this.executionId = executionId;
        this.workflowId = workflowId;
        this.currentStageIndex = 0;
        this.currentTaskIndex = 0;
        this.status = "RUNNING";
    }

    public String getExecutionId() { return executionId; }
    public String getWorkflowId() { return workflowId; }
    public int getCurrentStageIndex() { return currentStageIndex; }
    public int getCurrentTaskIndex() { return currentTaskIndex; }
    public String getStatus() { return status; }

    public void nextTask() { currentTaskIndex++; }
    public void nextStage() { currentStageIndex++; currentTaskIndex = 0; }

    public void markCompleted() { status = "COMPLETED"; }
    public void markFailed() { status = "FAILED"; }
}
