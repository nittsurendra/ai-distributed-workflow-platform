package com.surendratech.workflow.workflow_engine.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.surendratech.workflow.workflow_engine.model.WorkflowDefinition;

@Service
public class WorkflowRegistry {

    private final ConcurrentHashMap<String, WorkflowDefinition> workflows = new ConcurrentHashMap<>();

    public void save(WorkflowDefinition workflow) {
        workflows.put(workflow.getWorkflowId(), workflow);
    }

    public WorkflowDefinition get(String id) {
        return workflows.get(id);
    }
}
