package com.surendratech.workflow.workflow_engine.service;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final TaskEventProducer taskEventProducer;
    private final ThreadPoolTaskExecutor taskExecutor;

    public ExecutionService(
            WorkflowRegistry registry,
            WorkflowExecutor executor,
            WorkflowExecutionRepository executionRepository,
            TaskEventProducer taskEventProducer,
            ThreadPoolTaskExecutor taskExecutor
    ) {
        this.registry = registry;
        this.executor = executor;
        this.executionRepository = executionRepository;
        this.taskEventProducer = taskEventProducer;
        this.taskExecutor = taskExecutor;
    }

    @Transactional
    public String startExecution(String workflowId) {
        WorkflowDefinition wf = registry.get(workflowId);
        if (wf == null) {
            throw new RuntimeException("Workflow not found: " + workflowId);
        }

        String executionId = UUID.randomUUID().toString();

        WorkflowExecutionEntity entity = new WorkflowExecutionEntity();
        entity.setInstanceId(executionId);
        entity.setWorkflowId(workflowId);
        entity.setStatus("PENDING");
        entity.setStartedAt(Instant.now());
        executionRepository.save(entity);

        log.info("Execution {} created for workflow {}", executionId, workflowId);

        taskExecutor.execute(() -> executeAsync(executionId, wf));

        return executionId;
    }

    private void executeAsync(String executionId, WorkflowDefinition wf) {
        MDC.put("workflow.execution_id", executionId);
        try {
            updateExecutionStatus(executionId, "RUNNING");

            WorkflowInstance instance =
                new WorkflowInstance(executionId, wf.getWorkflowId());

            executor.runExecutionWithEvents(instance, wf, taskEventProducer);

            updateExecutionStatus(executionId, "COMPLETED", Instant.now());
            log.info("Execution {} completed", executionId);

        } catch (Exception ex) {
            log.error("Execution {} failed", executionId, ex);
            updateExecutionStatus(executionId, "FAILED", Instant.now());
        } finally {
            MDC.clear();
        }
    }

    public WorkflowInstance getExecution(String executionId) {
        return executionRepository.findByInstanceId(executionId)
            .map(e -> {
                WorkflowInstance instance =
                    new WorkflowInstance(e.getInstanceId(), e.getWorkflowId());
                instance.setStatus(e.getStatus());
                instance.setStartedAt(e.getStartedAt());
                instance.setCompletedAt(e.getCompletedAt());
                return instance;
            })
            .orElse(null);
    }

    @Transactional
    void updateExecutionStatus(String executionId, String status) {
        updateExecutionStatus(executionId, status, null);
    }

    @Transactional
    void updateExecutionStatus(String executionId, String status, Instant completedAt) {
        executionRepository.findByInstanceId(executionId).ifPresent(e -> {
            e.setStatus(status);
            if (completedAt != null) {
                e.setCompletedAt(completedAt);
            }
            executionRepository.save(e);
            log.debug("Execution {} → {}", executionId, status);
        });
    }
}
