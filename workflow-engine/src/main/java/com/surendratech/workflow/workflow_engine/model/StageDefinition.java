package com.surendratech.workflow.workflow_engine.model;

import java.util.List;

public class StageDefinition {
    private String stageId;
    private List<TaskDefinition> tasks;

    public String getStageId() { return stageId; }
    public void setStageId(String stageId) { this.stageId = stageId; }

    public List<TaskDefinition> getTasks() { return tasks; }
    public void setTasks(List<TaskDefinition> tasks) { this.tasks = tasks; }
}
