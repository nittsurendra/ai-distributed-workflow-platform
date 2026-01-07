package com.surendratech.workflow.workflow_engine.model;

import java.util.List;

public class WorkflowDefinition {
    private String workflowId;
    private String name;
    private List<StageDefinition> stages;

    public String getWorkflowId() { return workflowId; }
    public void setWorkflowId(String workflowId) { this.workflowId = workflowId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<StageDefinition> getStages() { return stages; }
    public void setStages(List<StageDefinition> stages) { this.stages = stages; }
}
