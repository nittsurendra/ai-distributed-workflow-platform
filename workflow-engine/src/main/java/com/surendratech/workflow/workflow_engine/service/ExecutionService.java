package com.surendratech.workflow.workflow_engine.service;

import java.util.UUID;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import com.surendratech.workflow.workflow_engine.engine.WorkflowExecutor;
import com.surendratech.workflow.workflow_engine.model.WorkflowDefinition;
import com.surendratech.workflow.workflow_engine.model.WorkflowInstance;

@Service
public class ExecutionService {
    private static final Logger log = LoggerFactory.getLogger(ExecutionService.class);

    private final WorkflowRegistry registry;
    private final WorkflowExecutor executor;
    private final ConcurrentHashMap<String, WorkflowInstance> executions = new ConcurrentHashMap<>();
    private final ExecutorService pool = Executors.newFixedThreadPool(4);

    public ExecutionService(WorkflowRegistry registry, WorkflowExecutor executor) {
        this.registry = registry;
        this.executor = executor;
    }

    public String startExecution(String workflowId) {
        WorkflowDefinition wf = registry.get(workflowId);
        if (wf == null) {
            throw new RuntimeException("Workflow not found: " + workflowId);
        }

        String executionId = UUID.randomUUID().toString();
        WorkflowInstance instance = new WorkflowInstance(executionId, workflowId);
        executions.put(executionId, instance);

        log.info("Starting execution: {} for workflow: {}", executionId, workflowId);

        // Run async in background
        pool.submit(() -> {
            MDC.put("workflow.execution_id", executionId);
            try {
                instance.markRunning();
                executor.runExecution(instance, wf);
            } catch (Exception ex) {
                log.error("Execution failed", ex);
                instance.markFailed();
            } finally {
                MDC.remove("workflow.execution_id");
            }
        });

        return executionId;
    }

    public WorkflowInstance getExecution(String executionId) {
        return executions.get(executionId);
    }
}
