package com.surendratech.workflow.workflow_engine.engine;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.surendratech.workflow.workflow_engine.model.StageDefinition;
import com.surendratech.workflow.workflow_engine.model.TaskDefinition;
import com.surendratech.workflow.workflow_engine.model.WorkflowDefinition;
import com.surendratech.workflow.workflow_engine.service.WorkflowRegistry;

@Service
public class WorkflowExecutor {

    private final WorkflowRegistry registry;

    public WorkflowExecutor(WorkflowRegistry registry) {
        this.registry = registry;
    }

    public WorkflowExecution start(String workflowId) {
        WorkflowDefinition workflow = registry.get(workflowId);

        if (workflow == null) {
            throw new RuntimeException("Workflow not found: " + workflowId);
        }

        WorkflowExecution exec = new WorkflowExecution(
                UUID.randomUUID().toString(),
                workflowId
        );

        run(exec, workflow);

        return exec;
    }

    private void run(WorkflowExecution exec, WorkflowDefinition workflow) {
        for (int s = 0; s < workflow.getStages().size(); s++) {
            StageDefinition stage = workflow.getStages().get(s);
            System.out.println("Starting Stage: " + stage.getStageId());

            for (int t = 0; t < stage.getTasks().size(); t++) {
                TaskDefinition task = stage.getTasks().get(t);
                executeTask(task);
            }
        }

        exec.markCompleted();
    }

    private void executeTask(TaskDefinition task) {
        System.out.println("Running Task → " + task.getType());
        System.out.println("Inputs → " + task.getInputs());

        try {
            Thread.sleep(500); // simulate real work
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
