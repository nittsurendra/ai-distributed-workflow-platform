package com.surendratech.workflow.workflow_engine.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surendratech.workflow.workflow_engine.engine.WorkflowExecution;
import com.surendratech.workflow.workflow_engine.engine.WorkflowExecutor;
import com.surendratech.workflow.workflow_engine.model.WorkflowDefinition;
import com.surendratech.workflow.workflow_engine.service.WorkflowRegistry;

@RestController
@RequestMapping("/workflows")
public class WorkflowController {

    private final WorkflowRegistry registry;
    private final WorkflowExecutor executor;

    public WorkflowController(WorkflowRegistry registry, WorkflowExecutor executor) {
        this.registry = registry;
        this.executor = executor;
    }

    @PostMapping
    public String createWorkflow(@RequestBody WorkflowDefinition workflow) {
        registry.save(workflow);
        return "Workflow saved with id: " + workflow.getWorkflowId();
    }

    @PostMapping("/{id}/execute")
    public WorkflowExecution run(@PathVariable String id) {
        return executor.start(id);
    }
}
