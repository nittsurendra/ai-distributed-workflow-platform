package com.surendratech.workflow.workflow_engine.engine;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.surendratech.workflow.workflow_engine.model.StageDefinition;
import com.surendratech.workflow.workflow_engine.model.TaskDefinition;
import com.surendratech.workflow.workflow_engine.model.WorkflowDefinition;
import com.surendratech.workflow.workflow_engine.model.WorkflowInstance;
import com.surendratech.workflow.workflow_engine.service.WorkflowRegistry;

@Service
public class WorkflowExecutor {
    private static final Logger log = LoggerFactory.getLogger(WorkflowExecutor.class);
    private final WorkflowRegistry registry;

    public WorkflowExecutor(WorkflowRegistry registry) {
        this.registry = registry;
    }

    // Existing sync method (keep for backward compatibility)
    public WorkflowExecution start(String workflowId) {
        WorkflowDefinition workflow = registry.get(workflowId);
        if (workflow == null) {
            throw new RuntimeException("Workflow not found: " + workflowId);
        }
        WorkflowExecution exec = new WorkflowExecution(UUID.randomUUID().toString(), workflowId);
        run(exec, workflow);
        return exec;
    }

    // NEW: Run with async instance (used by ExecutionService)
    public void runExecution(WorkflowInstance instance, WorkflowDefinition workflow) {
        log.info("Executing workflow: {} with instance: {}", workflow.getWorkflowId(), instance.getInstanceId());
        runWorkflow(instance, workflow);
    }

    private void runWorkflow(WorkflowInstance instance, WorkflowDefinition workflow) {
        for (StageDefinition stage : workflow.getStages()) {
            log.info("Starting Stage: {} [execution={}]", stage.getStageId(), instance.getInstanceId());

            for (TaskDefinition task : stage.getTasks()) {
                log.info("Running Task: {} with type: {} [execution={}]", 
                    task.getTaskId(), task.getType(), instance.getInstanceId());
                executeTask(task);
            }
        }
        instance.markCompleted();
        log.info("Execution completed: {}", instance.getInstanceId());
    }

    private void executeTask(TaskDefinition task) {
        try {
            log.debug("Executing task: {}", task.getTaskId());
            Thread.sleep(500); // simulate work
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Keep existing run for WorkflowExecution
    private void run(WorkflowExecution exec, WorkflowDefinition workflow) {
        for (int s = 0; s < workflow.getStages().size(); s++) {
            StageDefinition stage = workflow.getStages().get(s);
            log.info("Starting Stage: {}", stage.getStageId());

            for (int t = 0; t < stage.getTasks().size(); t++) {
                TaskDefinition task = stage.getTasks().get(t);
                executeTask(task);
            }
        }
        exec.markCompleted();
    }
}
