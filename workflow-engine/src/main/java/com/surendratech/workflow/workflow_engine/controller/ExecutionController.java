package com.surendratech.workflow.workflow_engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surendratech.workflow.workflow_engine.model.WorkflowInstance;
import com.surendratech.workflow.workflow_engine.service.ExecutionService;

@RestController
@RequestMapping("/executions")
public class ExecutionController {
    private final ExecutionService executionService;

    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping("/start/{workflowId}")
    public String startExecution(@PathVariable String workflowId) {
        return executionService.startExecution(workflowId);
    }

    @GetMapping("/{id}")
    public WorkflowInstance getExecution(@PathVariable String id) {
        WorkflowInstance instance = executionService.getExecution(id);
        if (instance == null) {
            throw new RuntimeException("Execution not found: " + id);
        }
        return instance;
    }
}
