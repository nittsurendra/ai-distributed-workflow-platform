package com.surendratech.workflow.workflow_engine.service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import com.surendratech.workflow.workflow_engine.engine.WorkflowExecutor;
import com.surendratech.workflow.workflow_engine.entity.WorkflowExecutionEntity;
import com.surendratech.workflow.workflow_engine.model.WorkflowDefinition;
import com.surendratech.workflow.workflow_engine.model.WorkflowInstance;
import com.surendratech.workflow.workflow_engine.repository.WorkflowExecutionRepository;

@Service
public class ExecutionService {
    private static final Logger log = LoggerFactory.getLogger(ExecutionService.class);

    private final WorkflowRegistry registry;
    private final WorkflowExecutor executor;
    private final WorkflowExecutionRepository executionRepository;
    private final ExecutorService pool = Executors.newFixedThreadPool(4);

    public ExecutionService(WorkflowRegistry registry, WorkflowExecutor executor, WorkflowExecutionRepository executionRepository) {
        this.registry = registry;
        this.executor = executor;
        this.executionRepository = executionRepository;
    }

    public String startExecution(String workflowId) {
        WorkflowDefinition wf = registry.get(workflowId);
        if (wf == null) {
            throw new RuntimeException("Workflow not found: " + workflowId);
        }

        String executionId = UUID.randomUUID().toString();
        WorkflowInstance instance = new WorkflowInstance(executionId, workflowId);

        // Save to database
        WorkflowExecutionEntity entity = new WorkflowExecutionEntity();
        entity.setInstanceId(executionId);
        entity.setWorkflowId(workflowId);
        entity.setStatus("PENDING");
        entity.setStartedAt(Instant.now());
        executionRepository.save(entity);

        log.info("Starting execution: {} for workflow: {}", executionId, workflowId);

        // Run async in background
        pool.submit(() -> {
            MDC.put("workflow.execution_id", executionId);
            try {
                instance.markRunning();
                updateExecutionStatus(executionId, "RUNNING");
                executor.runExecution(instance, wf);
                updateExecutionStatus(executionId, "COMPLETED", Instant.now());
            } catch (Exception ex) {
                log.error("Execution failed", ex);
                instance.markFailed();
                updateExecutionStatus(executionId, "FAILED", Instant.now());
            } finally {
                MDC.remove("workflow.execution_id");
            }
        });

        return executionId;
    }

    public WorkflowInstance getExecution(String executionId) {
        var entity = executionRepository.findByInstanceId(executionId);
        if (entity.isEmpty()) {
            return null;
        }

        WorkflowExecutionEntity e = entity.get();
        WorkflowInstance instance = new WorkflowInstance(e.getInstanceId(), e.getWorkflowId());
        instance.setStatus(e.getStatus());
        instance.setStartedAt(e.getStartedAt());
        instance.setCompletedAt(e.getCompletedAt());
        return instance;
    }

    private void updateExecutionStatus(String executionId, String status) {
        updateExecutionStatus(executionId, status, null);
    }

    private void updateExecutionStatus(String executionId, String status, Instant completedAt) {
        var entity = executionRepository.findByInstanceId(executionId);
        if (entity.isPresent()) {
            WorkflowExecutionEntity e = entity.get();
            e.setStatus(status);
            if (completedAt != null) {
                e.setCompletedAt(completedAt);
            }
            executionRepository.save(e);
            log.debug("Updated execution {} status to {}", executionId, status);
        }
    }
}
